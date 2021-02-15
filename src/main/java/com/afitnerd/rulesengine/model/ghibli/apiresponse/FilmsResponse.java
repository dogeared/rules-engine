package com.afitnerd.rulesengine.model.ghibli.apiresponse;

import com.afitnerd.rulesengine.model.ServiceHttpResponse;
import com.afitnerd.rulesengine.model.ghibli.Film;

import java.util.List;

public class FilmsResponse implements ServiceHttpResponse {

    private final Status status;
    private final Integer httpStatus;
    private final String message;
    private final List<Film> films;

    public FilmsResponse(Status status, Integer httpStatus, String message, List<Film> films) {
        this.status = status;
        this.httpStatus = httpStatus;
        this.message = message;
        this.films = films;
    }

    public FilmsResponse(Status status, Integer httpStatus, String message) {
        this(status, httpStatus, message, null);
    }


    @Override
    public Status getStatus() {
        return status;
    }

    @Override
    public Integer getHttpStatus() {
        return httpStatus;
    }

    @Override
    public String getMessage() {
        return message;
    }

    public List<Film> getFilms() {
        return films;
    }
}
