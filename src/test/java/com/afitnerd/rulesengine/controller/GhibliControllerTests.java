package com.afitnerd.rulesengine.controller;

import com.afitnerd.rulesengine.model.KeyValueFieldsRequest;
import com.afitnerd.rulesengine.model.ghibli.Film;
import com.afitnerd.rulesengine.model.ghibli.Person;
import com.afitnerd.rulesengine.model.ghibli.Species;
import com.afitnerd.rulesengine.model.ghibli.apiresponse.FilmsResponse;
import com.afitnerd.rulesengine.model.ghibli.apiresponse.PersonResponse;
import com.afitnerd.rulesengine.model.ghibli.apiresponse.SpeciesResponse;
import com.afitnerd.rulesengine.service.GhibliService;
import com.fasterxml.jackson.databind.ObjectMapper;
import org.apache.http.HttpStatus;
import org.junit.Before;
import org.junit.Test;
import org.junit.runner.RunWith;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.boot.test.autoconfigure.web.servlet.WebMvcTest;
import org.springframework.boot.test.mock.mockito.MockBean;
import org.springframework.http.MediaType;
import org.springframework.test.context.junit4.SpringRunner;
import org.springframework.test.web.servlet.MockMvc;
import org.springframework.test.web.servlet.ResultActions;

import java.util.List;

import static com.afitnerd.rulesengine.controller.GhibliController.API_URI;
import static com.afitnerd.rulesengine.controller.GhibliController.API_VERSION_URI;
import static com.afitnerd.rulesengine.controller.GhibliController.PERSON_BY_ENGINE_ENDPOINT;
import static com.afitnerd.rulesengine.controller.GhibliController.PERSON_ENDPOINT;
import static com.afitnerd.rulesengine.model.ServiceHttpResponse.Status;
import static org.mockito.Mockito.times;
import static org.mockito.Mockito.verify;
import static org.mockito.Mockito.when;
import static org.springframework.test.web.servlet.request.MockMvcRequestBuilders.post;
import static org.springframework.test.web.servlet.result.MockMvcResultMatchers.status;

@RunWith(SpringRunner.class)
@WebMvcTest(GhibliController.class)
public class GhibliControllerTests {

    @Autowired
    private MockMvc mvc;

    @MockBean
    GhibliService ghibliService;

    Person person;
    Film film;
    Species species;
    private List<Film> films;

    private static final String NAME = "Micah";
    private static final String FILM_URL = "https://a-film-url.example.com";
    private static final List<String> FILM_URLS = List.of(FILM_URL);
    private static final String SPECIES_URL = "https://a-species-url.example.com";

    KeyValueFieldsRequest request;
    private ObjectMapper mapper;

    @Before
    public void setup() {
        request = new KeyValueFieldsRequest();
        request.setFields(List.of(new KeyValueFieldsRequest.KeyValuePair("name", NAME)));
        mapper = new ObjectMapper();

        person = new Person();
        person.setName(NAME);
        person.setFilmUrls(FILM_URLS);
        person.setSpeciesUrl(SPECIES_URL);

        film = new Film();
        films = List.of(film);

        species = new Species();
    }

    private void setupRequest(Status personResponseStatus, Status filmsResponseStatus, Status speciesResponseStatus) {
        PersonResponse personResponse = (personResponseStatus == Status.SUCCESS) ?
            new PersonResponse(Status.SUCCESS, HttpStatus.SC_OK, "success", person) :
            new PersonResponse(Status.FAILURE, HttpStatus.SC_NOT_FOUND, "failure");
        FilmsResponse filmsResponse = (filmsResponseStatus == Status.SUCCESS) ?
            new FilmsResponse(Status.SUCCESS, HttpStatus.SC_OK, "success", films) :
            new FilmsResponse(Status.FAILURE, HttpStatus.SC_BAD_REQUEST, "failure");
        SpeciesResponse speciesResponse = (speciesResponseStatus == Status.SUCCESS) ?
            new SpeciesResponse(Status.SUCCESS, HttpStatus.SC_OK, "success", species) :
            new SpeciesResponse(Status.FAILURE, HttpStatus.SC_BAD_REQUEST, "failure");

        when(ghibliService.findPersonByName(NAME)).thenReturn(personResponse);
        when(ghibliService.listFilmsByUrls(FILM_URLS)).thenReturn(filmsResponse);
        when(ghibliService.findSpeciesByUrl(SPECIES_URL)).thenReturn(speciesResponse);
    }

    private ResultActions doPerformBase(String endpoint) throws Exception {
        return mvc.perform(post(endpoint)
            .contentType(MediaType.APPLICATION_JSON)
            .content(mapper.writeValueAsString(request))
        );
    }

    private ResultActions doPerform() throws Exception {
        return doPerformBase(API_URI + API_VERSION_URI + PERSON_ENDPOINT);
    }

    private ResultActions doPerformByEngine() throws Exception {
        return doPerformBase(API_URI + API_VERSION_URI + PERSON_BY_ENGINE_ENDPOINT);
    }

    @Test
    public void personEndpoint_Fail_atFindByPerson() throws Exception {
        setupRequest(Status.FAILURE, null, null);

        doPerform().andExpect(status().isNotFound());
        verify(ghibliService, times(1)).findPersonByName(NAME);
        verify(ghibliService, times(0)).listFilmsByUrls(FILM_URLS);
    }

    @Test
    public void personEndpoint_Fail_atListFilms() throws Exception {
        setupRequest(Status.SUCCESS, Status.FAILURE, null);

        doPerform().andExpect(status().isBadRequest());
        verify(ghibliService, times(1)).findPersonByName(NAME);
        verify(ghibliService, times(1)).listFilmsByUrls(FILM_URLS);
        verify(ghibliService, times(0)).findSpeciesByUrl(SPECIES_URL);
    }

    @Test
    public void personEndpoint_Fail_atFindSpecies() throws Exception {
        setupRequest(Status.SUCCESS, Status.SUCCESS, Status.FAILURE);

        doPerform().andExpect(status().isBadRequest());
        verify(ghibliService, times(1)).findPersonByName(NAME);
        verify(ghibliService, times(1)).listFilmsByUrls(FILM_URLS);
        verify(ghibliService, times(1)).findSpeciesByUrl(SPECIES_URL);
    }

    @Test
    public void personEndpoint_Success() throws  Exception {
        setupRequest(Status.SUCCESS, Status.SUCCESS, Status.SUCCESS);

        doPerform().andExpect(status().isOk());
        verify(ghibliService, times(1)).findPersonByName(NAME);
        verify(ghibliService, times(1)).listFilmsByUrls(FILM_URLS);
        verify(ghibliService, times(1)).findSpeciesByUrl(SPECIES_URL);
    }

    @Test
    public void personByEngineEndpoint_Fail_atFindByPerson() throws Exception {
        setupRequest(Status.FAILURE, null, null);

        doPerformByEngine().andExpect(status().isNotFound());
        verify(ghibliService, times(1)).findPersonByName(NAME);
        verify(ghibliService, times(0)).listFilmsByUrls(FILM_URLS);
    }

    @Test
    public void personByEngineEndpoint_Fail_atListFilms() throws Exception {
        setupRequest(Status.SUCCESS, Status.FAILURE, null);

        doPerformByEngine().andExpect(status().isBadRequest());
        verify(ghibliService, times(1)).findPersonByName(NAME);
        verify(ghibliService, times(1)).listFilmsByUrls(FILM_URLS);
        verify(ghibliService, times(0)).findSpeciesByUrl(SPECIES_URL);
    }

    @Test
    public void personByEngineEndpoint_Fail_atFindSpecies() throws Exception {
        setupRequest(Status.SUCCESS, Status.SUCCESS, Status.FAILURE);

        doPerformByEngine().andExpect(status().isBadRequest());
        verify(ghibliService, times(1)).findPersonByName(NAME);
        verify(ghibliService, times(1)).listFilmsByUrls(FILM_URLS);
        verify(ghibliService, times(1)).findSpeciesByUrl(SPECIES_URL);
    }

    @Test
    public void personByEngineEndpoint_Success() throws  Exception {
        setupRequest(Status.SUCCESS, Status.SUCCESS, Status.SUCCESS);

        doPerformByEngine().andExpect(status().isOk());
        verify(ghibliService, times(1)).findPersonByName(NAME);
        verify(ghibliService, times(1)).listFilmsByUrls(FILM_URLS);
        verify(ghibliService, times(1)).findSpeciesByUrl(SPECIES_URL);
    }
}
