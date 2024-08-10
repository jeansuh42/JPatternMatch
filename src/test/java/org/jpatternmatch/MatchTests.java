package org.jpatternmatch;

import org.jpatternmatch.common.PatternMatchException;
import org.junit.jupiter.api.Assertions;
import org.junit.jupiter.api.DisplayName;
import org.junit.jupiter.api.Test;

import java.util.List;

@DisplayName("Match 메서드 테스트")
public class MatchTests {

    @Test
    @DisplayName("usecase 1")
    void usecase1() {
        var base = "first";
        // 1. 타입을 알고 있으면서, 같은 타입에서 값을 비교하는 usecase
        JPatternMatch.of(base)
                .with("first", System.out::println)
                .otherwise(() -> Assertions.fail("값이 first가 아니라서 실패 실패"));
    }

    @Test
    @DisplayName("usecase 2")
    void usecase2() {
        Object base = "first";
        /*
         * 2. 타입을 알지 못하면서, 타입에 따라 다른 로직을 수행하는 usecase
         *   2-1. Base 타입: Object
         *   2-2. 타입 확인 이후 타입: 확인된 타입(Generic)
         */
        JPatternMatch.of(base)
                .asTypeOf(String.class, System.out::println)
                .elseThrow(new IllegalArgumentException("타입이 일치하지 않습니다."));
    }

    @Test
    @DisplayName("usecase 3")
    void usecase3() {
        /*
         * 3. 타입을 알지 못하면서, 타입을 확인한 후 각 타입의 값을 다시 추출해서 값에 따라 다른 로직을 수행하는 usecase
         * record Member(
         *   String name,
         *   long age
         * ) { }
         *  -> member.name == "철수"
         */
        var member = new JPatternMatch.Member("철수", 20);
        JPatternMatch.of(member)
                .extract(JPatternMatch.Member.class, extract -> {
                    extract.extractByField("name", matcher -> {
                        matcher.with("철수", System.out::println);
                        matcher.otherwise(() -> Assertions.fail("name이 철수가 아니어서 실패"));
                    });
                });
    }

    @Test
    @DisplayName("exhaustive")
    void exhaustive() throws PatternMatchException {
        Object base = "first";
        var actual = JPatternMatch.of(base)
                .with("first", System.out::println)
                .otherwise(() -> Assertions.fail("값이 first가 아니라서 실패 실패"))
                .exhaustive();

        Assertions.assertEquals(base, actual);
    }

    void qa() {
        List.of(1, 2, 3, 4, 5, 6, 12);
        var component1 = 1;
        var component2 = 2;
        var component3 = 3;
        var component4 = 4;
        var component5 = 5;
        var component6 = 6;
        var component7 = 7;
        var component8 = 8;
        // distributed declaration
    }
}
