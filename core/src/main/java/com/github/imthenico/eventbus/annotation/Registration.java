package com.github.imthenico.eventbus.annotation;

import org.jetbrains.annotations.NotNull;
import org.jetbrains.annotations.Nullable;

import java.lang.annotation.ElementType;
import java.lang.annotation.Retention;
import java.lang.annotation.RetentionPolicy;
import java.lang.annotation.Target;

@Retention(RetentionPolicy.RUNTIME)
@Target({ElementType.METHOD, ElementType.TYPE})
public @interface Registration {
    @NotNull
    Class<?> value();
    
    @Nullable
    Class<?>[] acceptTo() default {};
    
    int priority() default 0;
    
    boolean onlyChildren() default false;
}
