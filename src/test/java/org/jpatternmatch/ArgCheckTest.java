package org.jpatternmatch;

import org.jpatternmatch.src.ArgCheck;
import org.junit.jupiter.api.DisplayName;
import org.junit.jupiter.api.Test;

import static org.junit.jupiter.api.Assertions.*;

public class ArgCheckTest {


    @Test
    @DisplayName("checkArgumentNonNullAndReturn이 null 입력 시 IllegalArgumentException을 던지는지 확인한다.")
    void testCheckAndEnsureArgumentNonNullWithNull() {

        // given
        String input = null;

        // then
        assertThrows(IllegalArgumentException.class, () -> {
            // when
            ArgCheck.checkArgumentNonNullAndReturn(input);
        });

    }

    @Test
    @DisplayName("checkArgumentNonNullAndReturn이 null이 아닌 입력 시 동일 객체를 반환하는지 확인한다.")
    void testCheckAndEnsureArgumentNonNullWithNonNull() {

        // given
        String input = "test";

        // then / when
        assertEquals(input, ArgCheck.checkArgumentNonNullAndReturn(input));

    }

    @Test
    @DisplayName("requireArguemntNonNull이 null이 아닌 입력 시 true를 반환하는지 확인한다.")
    void testRequireArguemntNonNullWithNonNull() {
        assertTrue(ArgCheck.requireArguemntNonNull("Not Null"));
    }

    @Test
    @DisplayName("requireArguemntNonNull이 null 입력 시 IllegalArgumentException을 던지는지 확인한다.")
    void testRequireArguemntNonNullWithNull() {

        // given
        String input = null;

        // then
        assertThrows(IllegalArgumentException.class, () -> {
            // when
            ArgCheck.requireArguemntNonNull(input);
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

        // given
        Object[] input = new Object[]{"Not Null", null, "Not Null"};

        // then / when
        assertThrows(IllegalArgumentException.class, () -> {
            ArgCheck.requireArguemntsNonNull(input);
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
