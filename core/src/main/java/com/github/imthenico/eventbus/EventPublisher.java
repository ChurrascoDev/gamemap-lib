package com.github.imthenico.eventbus;

import com.github.imthenico.eventbus.result.PublishResult;
import org.jetbrains.annotations.NotNull;

import java.util.concurrent.CompletableFuture;

public interface EventPublisher<E> {
     <T extends E> @NotNull PublishResult dispatch(@NotNull T event);
    
     <T extends E> @NotNull CompletableFuture<PublishResult> dispatchAsync(@NotNull T event);
}
