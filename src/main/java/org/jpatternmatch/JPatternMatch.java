package org.jpatternmatch;

import org.jpatternmatch.common.PatternMatchException;
import static org.jpatternmatch.ArgCheck.*;

import java.util.function.Function;
import java.util.function.Supplier;

public class JPatternMatch<T> {

    private boolean matched = false;
    private T result;
    private Object ctorInstance;

    private JPatternMatch(Object ctorInstance) {
        this.ctorInstance = checkArgumentNonNullAndReturn(ctorInstance);
    }

    /**
     * Check the type of parameter instance
     *
     * @param injectedInstance the object to check the type of
     * @param clazz the class type to compare against
     * @param func a function for return
     * @return the result of applying the function
     * @throws IllegalArgumentException if any argument is null
     * @throws ClassCastException if the type does not match
     */
    public static <T, R> R asTypeOf(Object injectedInstance, Class<T> clazz, Function<T, R> func) {

        requireArguemntsNonNull(injectedInstance, clazz, func);

        if (clazz.isInstance(injectedInstance)) {
            return func.apply(clazz.cast(injectedInstance));
        }
        throw new ClassCastException("타입 매칭에 실패했습니다.");
    }

    /**
     * Check the type of parameter instance
     *
     * @param injectedInstance the object to check for type
     * @param clazz the class type to compare against
     * @param action a runnable action to execute
     * @throws IllegalArgumentException if any argument is null
     */
    public static <T> void asTypeOf(Object injectedInstance, Class<T> clazz, Runnable action) {

        requireArguemntsNonNull(injectedInstance, clazz, action);

        if (clazz.isInstance(injectedInstance)) {
            action.run();
        }
    }

    /**
     * Init JPatternMatch for pattern matching
     * @param injectedInstance the instance want to check for pattern matching
     * @throws IllegalArgumentException if any argument is null
     */
    public static <T> JPatternMatch<T> of(Object injectedInstance) {

        requireArguemntNonNull(injectedInstance);
        return new JPatternMatch<>(injectedInstance);
    }

    /**
     * Explicitly starting pattern matching
     */
    public JPatternMatch<T> match() {
        return this;
    }

    /**
     * Defined return type class
     * @param clazz the return class
     * @throws IllegalArgumentException if any argument is null
     */
    public <R> JPatternMatch<R> returnObject(Class<R> clazz) {
        requireArguemntNonNull(clazz);
        return new JPatternMatch<>(this.ctorInstance);
    }

    /**
     * Defined compare condition with a runnable
     *
     * @param condition the condtion you want to compare with control instance
     * @param action a runnable action to execute
     * @throws IllegalArgumentException if any argument is null
     */
    public JPatternMatch<T> with(Object condition, Runnable action) {

        requireArguemntsNonNull(condition, action);

        if (!matched && condition.equals(this.ctorInstance)) {
            action.run();
            matched = true;
        }
        return this;
    }

    /**
     * Defined return type class with a supplier
     *
     * @param condition the condtion you want to compare with control instance
     * @param compareSupplier a supplier to provide the return value
     * @throws IllegalArgumentException if any argument is null
     */
    public JPatternMatch<T> with(Object condition, Supplier<T> compareSupplier) {

        requireArguemntsNonNull(condition, compareSupplier);

        if (!matched && condition.equals(this.ctorInstance)) {
            matched = true;
            this.result = compareSupplier.get();
        }
        return this;
    }

    /**
     * End Pattern Matching with default executor
     *
     * @param action a runnable action to execute
     * @throws IllegalArgumentException if any argument is null
     */
    public void otherwise(Runnable action) {

        requireArguemntNonNull(action);

        if (!matched) {
            action.run();
        }
    }

    /**
     * End Pattern Matching with default return value
     *
     * @param supplier a supplier to provide the return value if no match was found
     * @throws IllegalArgumentException if supplier is null
     */
    public T otherwise(Supplier<? extends T> supplier) {

        requireArguemntNonNull(supplier);

        if (this.result == null) {
            this.result = supplier.get();
        }
        return this.result;
    }

    /**
     * End Pattern Matching with accurate result matching
     *
     * @return the result of pattern matching
     * @throws PatternMatchException if no matching type was found
     */
    public T exhaustive() throws PatternMatchException {
        if (this.result == null || !result.getClass().equals(ctorInstance.getClass())) {
            throw new PatternMatchException("패턴과 일치하는 타입이 없습니다.");
        }
        return this.result;
    }


}

