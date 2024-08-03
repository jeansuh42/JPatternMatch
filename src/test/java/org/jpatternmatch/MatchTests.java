package org.jpatternmatch;

import org.junit.jupiter.api.DisplayName;
import org.junit.jupiter.api.Test;

import java.io.ByteArrayOutputStream;
import java.io.PrintStream;
import java.util.Arrays;
import java.util.HashMap;
import java.util.List;
import java.util.Map;

import static org.junit.jupiter.api.Assertions.*;

@DisplayName("Match 메서드 테스트")
public class MatchTests {

    @Test
    @DisplayName("Map 객체 매칭 로직이 적절한 조건의 호출 여부를 확인한다")
    void testMapMatching() {

        // given
        Map<String, String> keyValueObject = new HashMap<>();
        keyValueObject.put("key1", "type");
        keyValueObject.put("key2", "type2");

        List<Boolean> executionFlags = Arrays.asList(false, false, false, false);

        // when
        Map result = JPatternMatch.of(keyValueObject)
                .returnObject(Map.class)
                .with(createKeyValueMap("key1", "type", "key2", "type3"), () -> {
                    executionFlags.set(0, true);
                    return createKeyValueMap("key1", "type", "key2", "type3");
                })
                .with(createKeyValueMap("key1", "type", "key2", "type2"), () -> {
                    executionFlags.set(1, true);
                    return createKeyValueMap("key1", "type", "key2", "type2");
                })
                .with(keyValueObject, () -> {
                    executionFlags.set(2, true);
                    return keyValueObject;
                })
                .otherwise(() -> {
                    executionFlags.set(3, true);
                    return createKeyValueMap("default", "default", "default", "default");
                })
                .end();

        // then
        assertFalse(executionFlags.get(0));
        assertTrue(executionFlags.get(1));
        assertFalse(executionFlags.get(2));
        assertFalse(executionFlags.get(3));

        assertEquals(result, keyValueObject);
        assertEquals(result, createKeyValueMap("key1", "type", "key2", "type2"));
    }


    @Test
    @DisplayName("동일 객체 주입 시 System 함수 호출 여부를 확인한다.")
    void testSysoutMatchingPatterns() {
        // given
        Map<String, String> keyValueObject = new HashMap<>();
        keyValueObject.put("key1", "type");
        keyValueObject.put("key2", "type2");

        ByteArrayOutputStream outContent = new ByteArrayOutputStream();
        PrintStream originalOut = System.out;
        System.setOut(new PrintStream(outContent));

        // when
        try {
            JPatternMatch.of(keyValueObject)
                    .match()
                    .with(createKeyValueMap("key1", "type", "key2", "type3"), () -> {
                        System.out.println("첫 번째 매치 실행");
                    })
                    .with(createKeyValueMap("key1", "type", "key2", "type4"), () -> {
                        System.out.println("두 번째 매치 실행");
                    })
                    .with(createKeyValueMap("key1", "type", "key2", "type5"), () -> {
                        System.out.println("세 번째 매치 실행");
                    })
                    .otherwise(() -> {
                        System.out.println("기본 매치 실행");
                    })
                    .end();

            // then
            String output = outContent.toString();
            assertFalse(output.contains("첫 번째 매치 실행"));
            assertFalse(output.contains("두 번째 매치 실행"));
            assertFalse(output.contains("세 번째 매치 실행"));
            assertTrue(output.contains("기본 매치 실행"));
        } finally {
            System.setOut(originalOut);
        }
    }


    @Test
    @DisplayName("Map 동일 객체 주입 시 적합한 조건의 호출 여부를 확인한다.")
    void testMapMatchingPatterns() {
        // given
        Map<String, String> keyValueObject = new HashMap<>();
        keyValueObject.put("key1", "type");
        keyValueObject.put("key2", "type2");

        List<Boolean> executionFlags = Arrays.asList(false, false, false, false);

        // when
        JPatternMatch.of(keyValueObject)
                .match()
                .with(createKeyValueMap("key1", "type", "key2", "type3"), () -> executionFlags.set(0, true))
                .with(createKeyValueMap("key1", "type", "key2", "type4"), () -> executionFlags.set(1, true))
                .with(createKeyValueMap("key1", "type", "key2", "type5"), () -> executionFlags.set(2, true))
                .otherwise(() -> executionFlags.set(3, true))
                .end();

        // then
        assertFalse(executionFlags.get(0));
        assertFalse(executionFlags.get(1));
        assertFalse(executionFlags.get(2));
        assertTrue(executionFlags.get(3));
    }



    @Test
    @DisplayName("String 동일 객체 주입 시 적합한 조건의 호출 여부를 확인한다.")
    void testStringMatchingPatterns() {
        // given
        Map<String, String> keyValueObject = new HashMap<>();
        keyValueObject.put("key1", "type");
        keyValueObject.put("key2", "type2");

        List<Boolean> executionFlags = Arrays.asList(false, false, false, false);

        // when
        JPatternMatch.of(keyValueObject)
                .match()
                .with(createKeyValueMap("key1", "type", "key2", "type3"), () -> executionFlags.set(0, true))
                .with(createKeyValueMap("key1", "type", "key2", "type4"), () -> executionFlags.set(1, true))
                .with(createKeyValueMap("key1", "type", "key2", "type5"), () -> executionFlags.set(2, true))
                .otherwise(() -> executionFlags.set(3, true))
                .end();

        // then
        assertFalse(executionFlags.get(0));
        assertFalse(executionFlags.get(1));
        assertFalse(executionFlags.get(2));
        assertTrue(executionFlags.get(3));
    }

    private Map<String, String> createKeyValueMap(String key1, String value1, String key2, String value2) {
        Map<String, String> map = new HashMap<>();
        map.put(key1, value1);
        map.put(key2, value2);
        return map;
    }

}
