package com.afitnerd.rulesengine.model.step;

import com.afitnerd.rulesengine.model.KeyValueFieldsRequest;
import com.afitnerd.rulesengine.model.ServiceHttpResponse;

import java.util.Map;

public interface Step {

    default <T> T fetchState(String key) {
        T ret = (T) getStateContainer().get(key);
        if (ret == null) {
            throw new RuntimeException(key + " not found in stateContainer");
        }
        return ret;
    }

    default void saveState(String key, Object value) {
        getStateContainer().put(key, value);
    }

    Map<String, Object> getStateContainer();
    void setStateContainer(Map<String, Object> stateContainer);
    ServiceHttpResponse.Status evaluate(KeyValueFieldsRequest request);
    ServiceHttpResponse getResponse();
}
