package com.github.imthenico.eventbus;

import com.github.imthenico.eventbus.key.Key;
import com.github.imthenico.eventbus.listener.EventHandler;
import com.github.imthenico.eventbus.listener.ListenerClass;
import com.github.imthenico.eventbus.priority.Priority;
import org.jetbrains.annotations.NotNull;
import org.jetbrains.annotations.Nullable;

import java.util.List;
import java.util.Map;
import java.util.concurrent.Executor;
import java.util.concurrent.Executors;

public interface EventBus<E> extends EventPublisher<E> {

    <T extends E> @NotNull Subscription<T> subscribe(
            @NotNull Key key,
            @NotNull Class<T> eventClass,
            @NotNull EventHandler<T> eventHandler,
            @NotNull Priority priority
    );

    <T extends E> @NotNull List<Subscription<T>> subscribeHandlerImpl(
             @NotNull Key key, @NotNull EventHandler<T> eventHandler
    );
    
    @NotNull Map<Class<? extends E>, Map<Key, Subscription<? extends E>>> subscribeAll(
            @NotNull Key key, @NotNull ListenerClass listenerClass
    );
    
    <T extends E> @Nullable Map<Key, Subscription<T>> unsubscribeAll(@NotNull Class<T> eventClass);

    @SuppressWarnings("UnusedReturnValue")
    <T extends E> @Nullable Subscription<T> unsubscribe(
            @NotNull Class<T> eventClass, @NotNull Key key
    );
    
    <T extends E> void cancelSubscription(@NotNull Subscription<T> subscription);

    <T extends E> @NotNull EventSubscriber<T> getSubscriber(@NotNull Class<T> eventClass);
    
    <T extends E> void addEventValidator(@NotNull Class<T> eventClass, @NotNull EventValidator<T> eventValidator);
    
    @Nullable EventValidator<E> getDefaultValidator();

    @NotNull Class<E> getEventClass();

    static <E> EventBus<E> create(Class<E> eventClass, EventValidator<E> eventValidator, Executor executor) {
        return new SimpleEventBus<>(eventClass, eventValidator, executor);
    }
    
    static <E> EventBus<E> create(Class<E> eventClass, EventValidator<E> eventHandler) {
        return create(eventClass, eventHandler, Executors.newSingleThreadExecutor());
    }
    
    static <E> EventBus<E> create(Class<E> eventClass) {
        return create(eventClass, null);
    }
}
