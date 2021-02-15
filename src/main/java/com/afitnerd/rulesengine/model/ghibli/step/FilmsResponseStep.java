package com.afitnerd.rulesengine.model.ghibli.step;

import com.afitnerd.rulesengine.model.KeyValueFieldsRequest;
import com.afitnerd.rulesengine.model.ServiceHttpResponse;
import com.afitnerd.rulesengine.model.ghibli.Person;
import com.afitnerd.rulesengine.model.ghibli.apiresponse.FilmsResponse;
import com.afitnerd.rulesengine.model.step.Step;
import com.afitnerd.rulesengine.service.GhibliService;

import java.util.Map;

import static com.afitnerd.rulesengine.model.ghibli.step.PersonResponseStep.PERSON_KEY;

public class FilmsResponseStep implements Step {

    public static final String FILMS_KEY = "films";

    private final GhibliService ghibliService;
    private Map<String, Object> stateContainer;
    private FilmsResponse filmsResponse;

    public FilmsResponseStep(GhibliService ghibliService) {
        this.ghibliService = ghibliService;
    }

    @Override
    public void setStateContainer(Map<String, Object> stateContainer) {
        this.stateContainer = stateContainer;
    }

    @Override
    public ServiceHttpResponse.Status evaluate(KeyValueFieldsRequest request) {
        Person person = (Person)stateContainer.get(PERSON_KEY);
        filmsResponse = ghibliService.listMoviesByUrls(person.getFilmUrls());
        stateContainer.put(FILMS_KEY, filmsResponse.getFilms());
        return filmsResponse.getStatus();
    }

    @Override
    public ServiceHttpResponse getResponse() {
        return filmsResponse;
    }
}
