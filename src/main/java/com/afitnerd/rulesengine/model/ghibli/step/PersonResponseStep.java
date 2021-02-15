package com.afitnerd.rulesengine.model.ghibli.step;

import com.afitnerd.rulesengine.model.KeyValueFieldsRequest;
import com.afitnerd.rulesengine.model.ServiceHttpResponse;
import com.afitnerd.rulesengine.model.ghibli.apiresponse.PersonResponse;
import com.afitnerd.rulesengine.model.step.Step;
import com.afitnerd.rulesengine.service.GhibliService;

import java.util.Map;

public class PersonResponseStep implements Step {

    public static final String PERSON_KEY = "person";

    private final GhibliService ghibliService;
    private Map<String, Object> stateContainer;
    private PersonResponse personResponse;

    public PersonResponseStep(GhibliService ghibliService) {
        this.ghibliService = ghibliService;
    }

    @Override
    public void setStateContainer(Map<String, Object> stateContainer) {
        this.stateContainer = stateContainer;
    }

    @Override
    public ServiceHttpResponse.Status evaluate(KeyValueFieldsRequest request) {
        personResponse = ghibliService.findPersonByName(request.getByName("name"));
        stateContainer.put(PERSON_KEY, personResponse.getPerson());
        return personResponse.getStatus();
    }

    @Override
    public ServiceHttpResponse getResponse() {
        return personResponse;
    }
}
