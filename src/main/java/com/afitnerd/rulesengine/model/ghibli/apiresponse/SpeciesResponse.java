package com.afitnerd.rulesengine.model.ghibli.apiresponse;

import com.afitnerd.rulesengine.model.ServiceHttpResponse;
import com.afitnerd.rulesengine.model.ghibli.Species;

public class SpeciesResponse implements ServiceHttpResponse {

    private final Status status;
    private final Integer httpStatus;
    private final String message;
    private final Species species;

    public SpeciesResponse(Status status, Integer httpStatus, String message, Species species) {
        this.status = status;
        this.httpStatus = httpStatus;
        this.message = message;
        this.species = species;
    }

    public SpeciesResponse(Status status, Integer httpStatus, String message) {
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

    public Species getSpecies() {
        return species;
    }
}
