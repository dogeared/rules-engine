package com.afitnerd.rulesengine.model.step;

import com.afitnerd.rulesengine.model.KeyValueFieldsRequest;
import com.afitnerd.rulesengine.model.ServiceHttpResponse;

import java.util.Map;

public interface Step {

    void setStateContainer(Map<String, Object> stateContainer);
    ServiceHttpResponse.Status evaluate(KeyValueFieldsRequest request);
    ServiceHttpResponse getResponse();
}
