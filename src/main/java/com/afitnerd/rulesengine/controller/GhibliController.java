package com.afitnerd.rulesengine.controller;

import com.afitnerd.rulesengine.model.KeyValueFieldsRequest;
import com.afitnerd.rulesengine.model.ServiceHttpResponse;
import com.afitnerd.rulesengine.model.ghibli.Film;
import com.afitnerd.rulesengine.model.ghibli.Person;
import com.afitnerd.rulesengine.model.ghibli.Species;
import com.afitnerd.rulesengine.model.ghibli.apiresponse.CompositeResponse;
import com.afitnerd.rulesengine.model.ghibli.apiresponse.FilmsResponse;
import com.afitnerd.rulesengine.model.ghibli.apiresponse.PersonResponse;
import com.afitnerd.rulesengine.model.ghibli.apiresponse.SpeciesResponse;
import com.afitnerd.rulesengine.model.ghibli.step.CompositeResponseStep;
import com.afitnerd.rulesengine.model.ghibli.step.FilmsResponseStep;
import com.afitnerd.rulesengine.model.ghibli.step.PersonResponseStep;
import com.afitnerd.rulesengine.model.ghibli.step.SpeciesResponseStep;
import com.afitnerd.rulesengine.model.step.StepsEngine;
import com.afitnerd.rulesengine.service.GhibliService;
import org.apache.http.HttpStatus;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.PostMapping;
import org.springframework.web.bind.annotation.RequestBody;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RestController;

import javax.servlet.http.HttpServletResponse;
import java.util.List;

import static com.afitnerd.rulesengine.controller.GhibliController.API_URI;
import static com.afitnerd.rulesengine.controller.GhibliController.API_VERSION_URI;
import static com.afitnerd.rulesengine.model.ServiceHttpResponse.Status;

@RestController
@RequestMapping(API_URI + API_VERSION_URI)
public class GhibliController {

    public static final String API_URI = "/api";
    public static final String API_VERSION_URI = "/v1";

    public static final String PEOPLE_ENDPOINT = "/people";
    public static final String PERSON_ENDPOINT = "/person";
    public static final String PERSON_BY_ENGINE_ENDPOINT = "/person-engine";

    private GhibliService ghibliService;

    public GhibliController(GhibliService ghibliService) {
        this.ghibliService = ghibliService;
    }

    @GetMapping(PEOPLE_ENDPOINT)
    public ServiceHttpResponse listPeople(HttpServletResponse response) {
        return setStatusAndReturn(ghibliService.listPeople(), response);
    }

    @PostMapping(PERSON_ENDPOINT)
    public ServiceHttpResponse findPerson(@RequestBody KeyValueFieldsRequest request, HttpServletResponse response) {
        PersonResponse personResponse = ghibliService.findPersonByName(request.getByName("name"));
        if (personResponse.getStatus() == Status.FAILURE) {
            return setStatusAndReturn(personResponse, response);
        }
        Person person = personResponse.getPerson();
        FilmsResponse filmsResponse = ghibliService.listFilmsByUrls(person.getFilmUrls());
        if (filmsResponse.getStatus() == Status.FAILURE) {
            return setStatusAndReturn(filmsResponse, response);
        }
        List<Film> films = filmsResponse.getFilms();
        SpeciesResponse speciesResponse = ghibliService.findSpeciesByUrl(person.getSpeciesUrl());
        if (speciesResponse.getStatus() == Status.FAILURE) {
            return setStatusAndReturn(speciesResponse, response);
        }
        Species species = speciesResponse.getSpecies();
        return setStatusAndReturn(new CompositeResponse(
            Status.SUCCESS, HttpStatus.SC_OK, "success",
            person, films, species
        ), response);
    }

    private StepsEngine setupEngine() {
        StepsEngine stepsEngine = new StepsEngine();
        stepsEngine.addStep(new PersonResponseStep(ghibliService));
        stepsEngine.addStep(new FilmsResponseStep(ghibliService));
        stepsEngine.addStep(new SpeciesResponseStep(ghibliService));
        stepsEngine.addStep(new CompositeResponseStep());
        return stepsEngine;
    }

    @PostMapping(PERSON_BY_ENGINE_ENDPOINT)
    public ServiceHttpResponse findPersonEngineStyle(
        @RequestBody KeyValueFieldsRequest request, HttpServletResponse response
    ) {
        StepsEngine stepsEngine = setupEngine();
        ServiceHttpResponse serviceHttpResponse = stepsEngine.process(request);
        return setStatusAndReturn(serviceHttpResponse, response);
    }

    private ServiceHttpResponse setStatusAndReturn(
        ServiceHttpResponse serviceHttpResponse, HttpServletResponse httpServletResponse
    ) {
        httpServletResponse.setStatus(serviceHttpResponse.getHttpStatus());
        return serviceHttpResponse;
    }
}
