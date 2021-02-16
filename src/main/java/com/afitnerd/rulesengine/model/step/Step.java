package com.afitnerd.rulesengine.model.step;

import com.afitnerd.rulesengine.model.KeyValueFieldsRequest;
import com.afitnerd.rulesengine.model.ServiceHttpResponse;

import java.util.Map;

public interface Step {

    default Map<String, Object> getStateContainerSafe() {
        Map<String, Object> stateContainer = getStateContainer();
        if (stateContainer == null) {
            throw new StateContainerException("stateContainer is null");
        }
        return stateContainer;
    }

    @SuppressWarnings("unchecked")
    default <T> T fetchState(String key) {
        T ret = (T) getStateContainerSafe().get(key);
        if (ret == null) {
            throw new StateContainerException(key + " not found in stateContainer");
        }
        return ret;
    }

    default void saveState(String key, Object value) {
        getStateContainerSafe().put(key, value);
    }

    Map<String, Object> getStateContainer();
    void setStateContainer(Map<String, Object> stateContainer);
    ServiceHttpResponse.Status evaluate(KeyValueFieldsRequest request);
    ServiceHttpResponse getResponse();

    class StateContainerException extends RuntimeException {

        public StateContainerException(String message) {
            super(message);
        }
    }
}
