package org.jpatternmatch;

import org.junit.jupiter.api.DisplayName;
import org.junit.jupiter.api.Test;

import java.util.Arrays;
import java.util.List;

import static org.jpatternmatch.JPatternMatch.asTypeOf;
import static org.junit.jupiter.api.Assertions.*;

@DisplayName("asTypeOf 메서드 테스트")
class AsTypeOfTests {

    @Test
    @DisplayName("주입된 값이 null일 때 테스트한다.")
    void testNullValue() {

        // given / when
        boolean[] nullMatched = {false};
        asTypeOf(null, String.class, () -> {
            nullMatched[0] = true;
        });

        // then
        assertFalse(nullMatched[0]);

    }

    @Test
    @DisplayName("주입된 Double 타입이 매칭되는지 테스트한다.")
    void testDoubleTypeMatching() {

        // given / when
        boolean[] doubleMatched = {false};
        asTypeOf(2.0, Double.class, () -> {
            doubleMatched[0] = true;
        });

        // then
        assertTrue(doubleMatched[0]);
    }

    @Test
    @DisplayName("주입된 Boolean 타입이 매칭되는지 테스트한다.")
    void testBooleanTypeMatching() {

        // given / when
        boolean[] booleanMatched = {false};
        asTypeOf(true, Boolean.class, () -> {
            booleanMatched[0] = true;
        });

        // then
        assertTrue(booleanMatched[0]);

    }

    @Test
    @DisplayName("주입된 List 타입이 매칭되는지 테스트한다.")
    void testListTypeMatching() {

        // given / when
        boolean[] listMatched = {false};
        asTypeOf(Arrays.asList(1, 2, 3), List.class, () -> {
            listMatched[0] = true;
        });

        // then
        assertTrue(listMatched[0]);

    }

    @Test
    @DisplayName("타입 불일치 시 콜백이 실행되지 않는지 테스트한다.")
    void testTypeMismatch() {

        // given / when
        boolean[] callbackExecuted = {false};
        asTypeOf(2, String.class, () -> {
            callbackExecuted[0] = true;
        });

        // then
        assertFalse(callbackExecuted[0]);

    }

    @Test
    @DisplayName("복잡한 객체 타입이 매칭되는지 테스트한다.")
    void testComplexObjectTypeMatching() {

        // given / when
        class CustomObject {
            String value;
            CustomObject(String value) { this.value = value; }
        }

        boolean[] objectMatched = {false};
        asTypeOf(new CustomObject("test"), CustomObject.class, () -> {
            objectMatched[0] = true;
        });

        // then
        assertTrue(objectMatched[0]);

    }

    @Test
    @DisplayName("중첩된 asTypeOf 호출이 올바르게 동작하는지 테스트한다.")
    void testNestedAsTypeOf() {

        // given / when
        boolean[] nestedMatched = {false};
        asTypeOf(2, Integer.class, () -> {
            asTypeOf("string", String.class, () -> {
                nestedMatched[0] = true;
            });
        });

        // then
        assertTrue(nestedMatched[0]);

    }

    @Test
    @DisplayName("asTypeOf 인자의 함수가 잘못된 타입을 반환할 경우 예외가 발생하는지 테스트한다.")
    public void testAsTypeOfWithInvalidType() {

        Integer input = 123;
        ClassCastException exception = assertThrows(ClassCastException.class, () -> {
            asTypeOf(input, String.class, Integer::parseInt);
        });

        assertTrue(exception.getMessage().contains("타입 매칭에 실패했습니다."));
    }

    @Test
    @DisplayName("Integer 타입 변환 시 asTypeOf 수행 및 반환값이 올바른지 테스트한다.")
    public void testAsTypeOfWithValidType() {

        String input = "123";
        Integer result = asTypeOf(input, String.class, Integer::parseInt);

        assertEquals(123, result);
    }

    @Test
    @DisplayName("Double 타입 변환 시 asTypeOf 수행 및 반환값이 올바른지 테스트한다.")
    public void testAsTypeOfWithDoubleType() {

        Double input = 3.14;
        String result = asTypeOf(input, Double.class, String::valueOf);

        assertEquals("3.14", result);
    }

    @Test
    @DisplayName("Double 타입 변환 시 asTypeOf 수행 및 반환값이 올바른지 테스트한다.")
    public void testAsTypeOfWithFloatType() {

        Float input = 3.14f;
        String result = asTypeOf(input, Float.class, String::valueOf);

        assertEquals("3.14", result);
    }
}


