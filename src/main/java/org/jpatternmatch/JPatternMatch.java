package org.jpatternmatch;

import java.util.function.Function;
import java.util.function.Supplier;

public class JPatternMatch<T> {

    private boolean matched = false;
    private T result;
    private Object ctorInstance;

    private JPatternMatch(Object ctorInstance) {
        this.ctorInstance = ctorInstance;
    }


    public static <T, R> R asTypeOf(Object injectedInstance, Class<T> clazz, Function<T, R> func) {
        if (clazz.isInstance(injectedInstance)) {
            return func.apply(clazz.cast(injectedInstance));
        }
        throw new ClassCastException("타입 매칭에 실패했습니다.");
    }

    public static <T> void asTypeOf(Object injectedInstance, Class<T> clazz, Runnable func) {
        if (clazz.isInstance(injectedInstance)) {
            func.run();
        }
    }

    public static <T> JPatternMatch<T> of(Object injectedInstance) {
        return new JPatternMatch<>(injectedInstance);
    }

    public JPatternMatch<T> match() {
        return this;
    }

    public <R> JPatternMatch<R> returnObject(Class<R> clazz) {
        return new JPatternMatch<>(this.ctorInstance);
    }

    public JPatternMatch<T> with(Object condition, Runnable action) {
        if (!matched && condition.equals(this.ctorInstance)) {
            action.run();
            matched = true;
        }
        return this;
    }

    public JPatternMatch<T> with(Object condition, Supplier<T> compareSupplier) {
        if (!matched && condition.equals(this.ctorInstance)) {
            matched = true;
            this.result = compareSupplier.get();
        }
        return this;
    }

    public T otherwise(Runnable action) {
        if (!matched) {
            action.run();
        }
        return this.result;
    }

    public T otherwise(Supplier<? extends T> supplier) {
        if (this.result == null) {
            this.result = supplier.get();
        }
        return this.result;
    }
    
    


}

