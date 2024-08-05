package org.jpatternmatch;

import java.util.function.Function;

public class JPatternMatch {


    public static <T, R> R asTypeOf(Object instance, Class<T> clazz, Function<T, R> func) {
        if (clazz.isInstance(instance)) {
            return func.apply(clazz.cast(instance));
        }
        throw new ClassCastException("타입 매칭에 실패했습니다.");
    }

    public static <T> void asTypeOf(Object instance, Class<T> clazz, Runnable func) {
        if (clazz.isInstance(instance)) {
            func.run();
        }
    }



}

