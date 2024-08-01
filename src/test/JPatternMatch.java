package org.jpatternmatch;

import java.util.function.Function;

public class JPatternMatch {


    public static <T, R> R asTypeOf(Object instance, Class<T> clazz, Function<T, R> func) {
        if (clazz.isInstance(instance)) {
            return func.apply(clazz.cast(instance));
        }
        throw new ClassCastException("Instance is not of the expected type: " + clazz.getName());
    }

    public static <T> void asTypeOf(Object instance, Class<T> clazz, Runnable func) {
        if (clazz.isInstance(instance)) {
            func.run();
        }
    }



}

