package com.github.imthenico.gmlib.event;

import com.github.imthenico.gmlib.ModelData;

public class DataMutateEvent {

    private final Object mutatedData;
    private final ModelData modelData;
    private final String mutationAction;

    public DataMutateEvent(Object mutatedData, ModelData modelData, String mutationAction) {
        this.mutatedData = mutatedData;
        this.modelData = modelData;
        this.mutationAction = mutationAction;
    }

    public Object getMutatedData() {
        return mutatedData;
    }

    public ModelData getModelData() {
        return modelData;
    }

    public String getMutationAction() {
        return mutationAction;
    }
}