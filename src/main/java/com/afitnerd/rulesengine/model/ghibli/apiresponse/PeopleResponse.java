package com.afitnerd.rulesengine.model.ghibli.apiresponse;

import com.afitnerd.rulesengine.model.ServiceHttpResponse;
import com.afitnerd.rulesengine.model.ghibli.Person;

import java.util.List;

public class PeopleResponse implements ServiceHttpResponse {

    private final Status status;
    private final Integer httpStatus;
    private final String message;
    private final List<Person> people;

    public PeopleResponse(Status status, Integer httpStatus, String message, List<Person> people) {
        this.status = status;
        this.httpStatus = httpStatus;
        this.message = message;
        this.people = people;
    }

    public PeopleResponse(Status status, Integer httpStatus, String message) {
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

    public List<Person> getPeople() {
        return people;
    }
}
