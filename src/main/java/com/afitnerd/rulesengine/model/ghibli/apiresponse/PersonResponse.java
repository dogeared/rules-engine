package com.afitnerd.rulesengine.model.ghibli.apiresponse;

import com.afitnerd.rulesengine.model.ServiceHttpResponse;
import com.afitnerd.rulesengine.model.ghibli.Person;

public class PersonResponse implements ServiceHttpResponse {

    private final Status status;
    private final Integer httpStatus;
    private final String message;
    private final Person person;

    public PersonResponse(Status status, Integer httpStatus, String message, Person person) {
        this.status = status;
        this.httpStatus = httpStatus;
        this.message = message;
        this.person = person;
    }

    public PersonResponse(Status status, Integer httpStatus, String message) {
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

    public Person getPerson() {
        return person;
    }
}
