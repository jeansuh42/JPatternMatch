package org.jpatternmatch;

import org.jpatternmatch.common.PatternMatchException;

import java.lang.reflect.Field;
import java.util.function.Consumer;
import java.util.function.Function;

import static org.jpatternmatch.ArgCheck.requireArguemntNonNull;
import static org.jpatternmatch.ArgCheck.requireArguemntsNonNull;

public class JPatternMatch<T> {

    private final T result;
    private boolean matched = false;

    private JPatternMatch(T ctorInstance) {
        this.result = ctorInstance;
    }

    public static <T, R> R asTypeOf(Object injectedInstance, Class<T> clazz, Function<T, R> func) {

        requireArguemntsNonNull(injectedInstance, clazz, func);

        if (clazz.isInstance(injectedInstance)) {
            return func.apply(clazz.cast(injectedInstance));
        }
        throw new ClassCastException("타입 매칭에 실패했습니다.");
    }

    public static <T> void asTypeOf(Object injectedInstance, Class<T> clazz, Runnable action) {

        requireArguemntsNonNull(injectedInstance, clazz, action);

        if (clazz.isInstance(injectedInstance)) {
            action.run();
        }
    }

    public static <T> JPatternMatch<T> of(T injectedInstance) {
        requireArguemntNonNull(injectedInstance);
        return new JPatternMatch<>(injectedInstance);
    }

    // 1. 타입을 알고 있으면서, 같은 타입에서 값을 비교하는 usecase
    public JPatternMatch<T> with(T condition, Consumer<T> action) {
        if (!matched && condition.equals(this.result)) {
            action.accept(condition);
            matched = true;
        }
        return this;
    }

    /*
     * 2. 타입을 알지 못하면서, 타입에 따라 다른 로직을 수행하는 usecase
     *   2-1. Base 타입: Object
     *   2-2. 타입 확인 이후 타입: 확인된 타입(Generic)
     */
    public <R> JPatternMatch<T> asTypeOf(Class<R> clazz, Consumer<R> action) {
        if (clazz.isInstance(this.result)) {
            action.accept(clazz.cast(this.result));
            this.matched = true;
        }
        return this;
    }

    /*
     * 3. 타입을 알지 못하면서, 타입을 확인한 후 각 타입의 값을 다시 추출해서 값에 따라 다른 로직을 수행하는 usecase
     * record Member(
     *   String name,
     *   long age
     * ) { }
     *  -> member.name == "철수"
     */
    public <R> void extract(Class<R> clazz, Consumer<JPatternMatchExtractExpression<R>> func) {
        func.accept(new JPatternMatchExtractExpression<>(this.result, clazz));
    }

    public JPatternMatch<T> otherwise(Runnable action) {

        requireArguemntNonNull(action);

        if (!matched) {
            action.run();
        }

        return this;
    }

    public void elseThrow(Throwable throwable) {
        try {
            if (!matched) {
                throw throwable;
            }
        } catch (Throwable e) {
            throw new RuntimeException(e);
        }
    }

    public T exhaustive() throws PatternMatchException {
        if (this.matched) {
            return this.result;
        }
        throw new PatternMatchException("패턴과 일치하는 타입이 없습니다.");
    }


    static class Member {
        String name;
        long age;

        public Member(String name, long age) {
            this.name = name;
            this.age = age;
        }
    }

    public static class JPatternMatchExtractExpression<R> {
        private final Object result;
        private final Class<R> clazz;

        JPatternMatchExtractExpression(Object result, Class<R> clazz) {
            this.result = result;
            this.clazz = clazz;
        }

        public void extractByField(String fieldName, Consumer<JPatternMatch<Object>> func) {
            if (!clazz.equals(result.getClass())) {
                throw new IllegalArgumentException("타입이 일치하지 않습니다.");
            }

            // 1. 어떤 필드에 접근할지 받은 후, 해당 필드의 값 추출
            Field field = null;
            try {
                field = clazz.getDeclaredField(fieldName);
                field.setAccessible(true);
            } catch (NoSuchFieldException e) {
                throw new RuntimeException(e);
            }
            Object fieldValue = null;
            try {
                fieldValue = field.get(this.result);
            } catch (IllegalAccessException e) {
                throw new RuntimeException(e);
            }
            func.accept(new JPatternMatch<>(fieldValue));
        }
    }
}

