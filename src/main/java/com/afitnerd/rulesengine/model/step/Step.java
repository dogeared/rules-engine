package com.afitnerd.rulesengine.model.step;

import com.afitnerd.rulesengine.model.KeyValueFieldsRequest;
import com.afitnerd.rulesengine.model.ServiceHttpResponse;
import java.util.Map;

import static com.afitnerd.rulesengine.model.ServiceHttpResponse.Status;

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
        return (T) getStateContainerSafe().get(key);
    }

    default void saveState(String key, Object value) {
        getStateContainerSafe().put(key, value);
    }

    Map<String, Object> getStateContainer();
    void setStateContainer(Map<String, Object> stateContainer);
    Status evaluate(KeyValueFieldsRequest request);
    ServiceHttpResponse getResponse();

    class StateContainerException extends RuntimeException {

        public StateContainerException(String message) {
            super(message);
        }
    }
}
