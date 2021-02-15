package com.afitnerd.rulesengine.model.ghibli.apiresponse;

import com.afitnerd.rulesengine.model.ServiceHttpResponse;
import com.afitnerd.rulesengine.model.ghibli.Film;
import com.afitnerd.rulesengine.model.ghibli.Person;
import com.afitnerd.rulesengine.model.ghibli.Species;

import java.util.List;

public class CompositeResponse implements ServiceHttpResponse {

    private final Status status;
    private final Integer httpStatus;
    private final String message;
    private final Person person;
    private final List<Film> films;
    private final Species species;

    public CompositeResponse(
        Status status, Integer httpStatus, String message, Person person, List<Film> films, Species species
    ) {
        this.status = status;
        this.httpStatus = httpStatus;
        this.message = message;
        this.person = person;
        this.films = films;
        this.species = species;
    }

    @Override
    public Status getStatus() {
        return status;
    }

    @Override
    public String getMessage() {
        return message;
    }

    @Override
    public Integer getHttpStatus() {
        return httpStatus;
    }

    public Person getPerson() {
        return person;
    }

    public List<Film> getFilms() {
        return films;
    }

    public Species getSpecies() {
        return species;
    }
}
