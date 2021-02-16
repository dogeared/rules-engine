package com.afitnerd.rulesengine.model.ghibli.step;

import com.afitnerd.rulesengine.model.KeyValueFieldsRequest;
import com.afitnerd.rulesengine.model.ServiceHttpResponse;
import com.afitnerd.rulesengine.model.ghibli.apiresponse.PersonResponse;
import com.afitnerd.rulesengine.model.step.BasicStep;
import com.afitnerd.rulesengine.service.GhibliService;

public class PersonResponseStep extends BasicStep {

    public static final String PERSON_KEY = "person";

    private final GhibliService ghibliService;
    private PersonResponse personResponse;

    public PersonResponseStep(GhibliService ghibliService) {
        this.ghibliService = ghibliService;
    }

    @Override
    public ServiceHttpResponse.Status evaluate(KeyValueFieldsRequest request) {
        personResponse = ghibliService.findPersonByName(request.getByName("name"));
        saveState(PERSON_KEY, personResponse.getPerson());
        return personResponse.getStatus();
    }

    @Override
    public ServiceHttpResponse getResponse() {
        return personResponse;
    }
}
