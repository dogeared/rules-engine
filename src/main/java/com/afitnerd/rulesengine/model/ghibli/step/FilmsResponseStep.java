package com.afitnerd.rulesengine.model.ghibli.step;

import com.afitnerd.rulesengine.model.KeyValueFieldsRequest;
import com.afitnerd.rulesengine.model.ServiceHttpResponse;
import com.afitnerd.rulesengine.model.ghibli.Person;
import com.afitnerd.rulesengine.model.ghibli.apiresponse.FilmsResponse;
import com.afitnerd.rulesengine.model.step.BasicStep;
import com.afitnerd.rulesengine.service.GhibliService;

import static com.afitnerd.rulesengine.model.ghibli.step.PersonResponseStep.PERSON_KEY;

public class FilmsResponseStep extends BasicStep {

    public static final String FILMS_KEY = "films";

    private final GhibliService ghibliService;
    private FilmsResponse filmsResponse;

    public FilmsResponseStep(GhibliService ghibliService) {
        this.ghibliService = ghibliService;
    }

    @Override
    public ServiceHttpResponse.Status evaluate(KeyValueFieldsRequest request) {
        Person person = fetchState(PERSON_KEY);
        filmsResponse = ghibliService.listMoviesByUrls(person.getFilmUrls());
        saveState(FILMS_KEY, filmsResponse.getFilms());
        return filmsResponse.getStatus();
    }

    @Override
    public ServiceHttpResponse getResponse() {
        return filmsResponse;
    }
}
