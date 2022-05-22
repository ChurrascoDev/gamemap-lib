package com.github.imthenico.gmlib;

import com.github.imthenico.eventbus.EventBus;
import com.github.imthenico.eventbus.Subscription;
import com.github.imthenico.eventbus.key.Key;
import com.github.imthenico.eventbus.listener.EventHandler;
import com.github.imthenico.eventbus.listener.ListenerClass;
import com.github.imthenico.eventbus.priority.Priority;
import com.github.imthenico.gmlib.event.DataMutateEvent;
import com.google.gson.JsonObject;
import org.jetbrains.annotations.NotNull;

import java.util.Objects;

public class ModelData {

    private final JsonObject extraData;
    private final transient EventBus<DataMutateEvent> eventEventBus = EventBus.create(DataMutateEvent.class);

    public ModelData() {
        this(new JsonObject());
    }

    public ModelData(JsonObject extraData) {
        this.extraData = Objects.requireNonNull(extraData);
    }

    @NotNull
    public JsonObject getExtraData() {
        return extraData;
    }

    protected void handleDataMutation(DataMutateEvent event) {
        this.eventEventBus.dispatch(Objects.requireNonNull(event));
    }

    public void subscribe(Key key, EventHandler<DataMutateEvent> eventHandler) {
        eventEventBus.subscribe(key, DataMutateEvent.class, eventHandler, Priority.NORMAL);
    }

    public void subscribe(Key key, ListenerClass listenerClass) {
        eventEventBus.subscribeAll(key, listenerClass);
    }

    public void unsubscribe(Subscription<DataMutateEvent> subscription) {
        eventEventBus.cancelSubscription(subscription);
    }
}