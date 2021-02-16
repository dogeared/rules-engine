package com.afitnerd.rulesengine.model.step;

import com.afitnerd.rulesengine.model.KeyValueFieldsRequest;
import com.afitnerd.rulesengine.model.ServiceHttpResponse;

import java.util.ArrayList;
import java.util.HashMap;
import java.util.List;
import java.util.Map;

public class StepsEngine {

    private final Map<String, Object> stateContainer = new HashMap<>();
    private final List<Step> steps = new ArrayList<>();

    public void addStep(Step step) {
        step.setStateContainer(stateContainer);
        steps.add(step);
    }

    public ServiceHttpResponse process(KeyValueFieldsRequest request) {
        Step step =  steps.stream()
            .filter(s -> s.evaluate(request) == ServiceHttpResponse.Status.FAILURE)
            .findFirst()
            // if nothing fails, last step must be success
            .orElse(steps.get(steps.size()-1));
        return step.getResponse();
    }
}