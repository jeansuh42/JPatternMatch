package org.jpatternmatch;

import org.jpatternmatch.src.ArgCheck;
import org.junit.jupiter.api.DisplayName;
import org.junit.jupiter.api.Test;

import static org.junit.jupiter.api.Assertions.*;

public class ArgCheckTest {


    @Test
    @DisplayName("checkArgumentNonNullAndReturn이 null 입력 시 IllegalArgumentException을 던지는지 확인한다.")
    void testCheckAndEnsureArgumentNonNullWithNull() {
        assertThrows(IllegalArgumentException.class, () -> {
            ArgCheck.checkArgumentNonNullAndReturn(null);
        });
    }

    @Test
    @DisplayName("checkArgumentNonNullAndReturn이 null이 아닌 입력 시 동일 객체를 반환하는지 확인한다.")
    void testCheckAndEnsureArgumentNonNullWithNonNull() {
        String testString = "test";
        assertEquals(testString, ArgCheck.checkArgumentNonNullAndReturn(testString));
    }

    @Test
    @DisplayName("requireArguemntNonNull이 null이 아닌 입력 시 true를 반환하는지 확인한다.")
    void testRequireArguemntNonNullWithNonNull() {
        assertTrue(ArgCheck.requireArguemntNonNull("Not Null"));
    }

    @Test
    @DisplayName("requireArguemntNonNull이 null 입력 시 IllegalArgumentException을 던지는지 확인한다.")
    void testRequireArguemntNonNullWithNull() {
        assertThrows(IllegalArgumentException.class, () -> {
            ArgCheck.requireArguemntNonNull(null);
        });
    }

    @Test
    @DisplayName("requireArguemntsNonNull이 null이 아닌 여러 객체 입력 시 true를 반환하는지 확인한다.")
    void testRequireArguemntsNonNullWithNonNull() {
        assertTrue(ArgCheck.requireArguemntsNonNull("Not Null", "Not Null"));
        assertTrue(ArgCheck.requireArguemntsNonNull("Not Null", "Not Null", "Not Null"));
        assertTrue(ArgCheck.requireArguemntsNonNull("Not Null", "Not Null", "Not Null", "Not Null"));
        assertTrue(ArgCheck.requireArguemntsNonNull("Not Null", "Not Null", "Not Null", "Not Null", "Not Null"));
        assertTrue(ArgCheck.requireArguemntsNonNull("Not Null", "Not Null", "Not Null", "Not Null", "Not Null", "Not Null"));
    }

    @Test
    @DisplayName("requireArguemntsNonNull이 null 객체가 포함된 입력 시 IllegalArgumentException을 던지는지 확인한다.")
    void testRequireArguemntsNonNullWithNull() {
        assertThrows(IllegalArgumentException.class, () -> {
            ArgCheck.requireArguemntsNonNull("Not Null", null, "Not Null");
        });
    }

    @Test
    @DisplayName("requireArguemntsNonNull이 0개 / 1개의 객체 전달 시 IllegalArgumentException을 던지는지 확인한다.")
    void testRequireArguemntsisSmallerThan2() {
        assertThrows(IllegalArgumentException.class, () -> {
            ArgCheck.requireArguemntsNonNull();
            ArgCheck.requireArguemntsNonNull("Not Null");
        });
    }

}
