package org.jpatternmatch;

import java.util.ArrayList;
import java.util.Collections;
import java.util.List;

public class ArgCheck {


    /**
     * Ensures that the provided objects is not null.
     *
     * @param obj the object to check for null
     * @return boolean
     * @throws IllegalArgumentException if object is null
     */
    public static boolean checkAndEnsureArgumentNonNull(Object obj) {
        if (obj == null) {
            throw new IllegalArgumentException("요청 값이 잘못되었습니다.");
        }
        return true;
    }

    /**
     * Return Object that the provided objects is not null.
     *
     * @param obj the object to check for null
     * @param <T> the type of the object
     * @return the non-null object
     * @throws IllegalArgumentException if object is null
     */
    public static <T> T checkArgumentNonNullAndReturn(T obj) {
        checkAndEnsureArgumentNonNull(obj);
        return obj;
    }

    /**
     * Ensures that the provided objects is not null.
     *
     * @param obj the object to check for null
     * @param <T> the type of the object
     * @return true if the object is non-null
     * @throws IllegalArgumentException if the object is null
     */
    public static <T> boolean requireArguemntNonNull(T obj) {
        checkAndEnsureArgumentNonNull(obj);
        return true;
    }


    /**
     * Ensures that none of the provided objects are null.
     *
     * @param objects the objects to check for null
     * @param <T> the type of the objects
     * @return boolean
     * @throws IllegalArgumentException if any of the objects are null
     */
    @SafeVarargs
    public static <T> boolean requireArguemntsNonNull(T... objects) {
        List<Object> argList = makeListOf(objects);
        for (Object o : argList) {
            checkAndEnsureArgumentNonNull(o);
        }
        return true;
    }

    /**
     * Creates an unmodifiable list from the provided objects.
     * This method converts the provided varargs into a list similar to
     * List.of(java 9)
     *
     * @param objects the objects to be added to the list
     * @return List<Object>
     */
    private static List<Object> makeListOf(Object[] objects) {

        if(objects.length < 1){
            throw new IllegalArgumentException("인자는 2개 이상 포함되어야 합니다.");
        }

        ArrayList<Object> arrayList = new ArrayList<>();
        Collections.addAll(arrayList, objects);

        return Collections.unmodifiableList(arrayList);
    }

}
