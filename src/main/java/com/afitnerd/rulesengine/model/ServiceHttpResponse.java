package com.afitnerd.rulesengine.model;

import com.fasterxml.jackson.annotation.JsonIgnore;
import com.fasterxml.jackson.annotation.JsonInclude;

@JsonInclude(JsonInclude.Include.NON_NULL)
public interface ServiceHttpResponse {

    enum Status {
        SUCCESS("SUCCESS"), FAILURE("FAILURE");

        private String value;

        Status(String value) {
            this.value = value;
        }
    }

    Status getStatus();
    String getMessage();

    @JsonIgnore
    Integer getHttpStatus();

}
