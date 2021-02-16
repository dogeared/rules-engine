package com.afitnerd.rulesengine.model.step;

import java.util.Map;

public abstract class BasicStep implements Step {

    private Map<String, Object> stateContainer;

    @Override
    public void setStateContainer(Map<String, Object> stateContainer) {
        this.stateContainer = stateContainer;
    }

    @Override
    public Map<String, Object> getStateContainer() {
        return this.stateContainer;
    }
}
