package org.jpatternmatch.src;

import org.jpatternmatch.src.common.PatternMatchException;
import static org.jpatternmatch.src.ArgCheck.*;

import java.util.function.Function;
import java.util.function.Supplier;

public class JPatternMatch<T> {

    private boolean matched = false;
    private T result;
    private Object ctorInstance;

    private JPatternMatch(Object ctorInstance) {
        this.ctorInstance = checkArgumentNonNullAndReturn(ctorInstance);
    }


    public static <T, R> R asTypeOf(Object injectedInstance, Class<T> clazz, Function<T, R> func) {

        requireArguemntsNonNull(injectedInstance, clazz, func);

        if (clazz.isInstance(injectedInstance)) {
            return func.apply(clazz.cast(injectedInstance));
        }
        throw new ClassCastException("타입 매칭에 실패했습니다.");
    }

    public static <T> void asTypeOf(Object injectedInstance, Class<T> clazz, Runnable func) {

        requireArguemntsNonNull(injectedInstance, clazz, func);

        if (clazz.isInstance(injectedInstance)) {
            func.run();
        }
    }

    public static <T> JPatternMatch<T> of(Object injectedInstance) {

        requireArguemntNonNull(injectedInstance);
        return new JPatternMatch<>(injectedInstance);
    }

    public JPatternMatch<T> match() {
        return this;
    }

    public <R> JPatternMatch<R> returnObject(Class<R> clazz) {
        requireArguemntNonNull(clazz);
        return new JPatternMatch<>(this.ctorInstance);
    }

    public JPatternMatch<T> with(Object condition, Runnable action) {

        requireArguemntsNonNull(condition, action);

        if (!matched && condition.equals(this.ctorInstance)) {
            action.run();
            matched = true;
        }
        return this;
    }

    public JPatternMatch<T> with(Object condition, Supplier<T> compareSupplier) {

        requireArguemntsNonNull(condition, compareSupplier);

        if (!matched && condition.equals(this.ctorInstance)) {
            matched = true;
            this.result = compareSupplier.get();
        }
        return this;
    }

    public void otherwise(Runnable action) {

        requireArguemntNonNull(action);

        if (!matched) {
            action.run();
        }
    }

    public T otherwise(Supplier<? extends T> supplier) {

        requireArguemntNonNull(supplier);

        if (this.result == null) {
            this.result = supplier.get();
        }
        return this.result;
    }


    public T exhaustive() throws PatternMatchException {
        if (this.result == null || !result.getClass().equals(ctorInstance.getClass())) {
            throw new PatternMatchException("패턴과 일치하는 타입이 없습니다.");
        }
        return this.result;
    }


}

