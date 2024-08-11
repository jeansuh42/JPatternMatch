package org.example;

import org.jpatternmatch.src.JPatternMatch;
import org.jpatternmatch.src.common.PatternMatchException;

import java.util.Arrays;

public class MatchExample {

    public static void main(String[] args) {

        /*
         * You can use an expression lambda as well,
         * but sometimes a statement lambda is much more readable,
         * especially when the condition is too long.
         */
        JPatternMatch.of(2.0)
                .match()
                .with(Double.valueOf(1.2) + Double.valueOf(3.8) + Double.valueOf(7.2), () -> {
                    System.out.println("DeeperTestClass 타입 매칭");
                })
                .otherwise(() -> System.out.println("Double 타입 매칭 실패"));


        // 예제 1: null 값 매칭
        try{
            Object nullObject = null;
            JPatternMatch.of(nullObject)
                         .match()
                         .with(null, () -> System.out.println("null 값 매칭"))
                         .otherwise(() -> System.out.println("null 값 매칭 실패"));
        }catch (IllegalArgumentException ex){
            System.out.println("IllegalArgumentException 발생");
        }

        // 예제 2: Double 타입 매칭
        JPatternMatch.of(2.0)
                     .match()
                     .with(2.0, () -> System.out.println("Double 타입 매칭"))
                     .otherwise(() -> System.out.println("Double 타입 매칭 실패"));

        // 예제 3: Boolean 타입 매칭
        JPatternMatch.of(true)
                     .match()
                     .with(true, () -> System.out.println("Boolean 타입 매칭"))
                     .otherwise(() -> System.out.println("Boolean 타입 매칭 실패"));

        // 예제 4: List 타입 매칭
        JPatternMatch.of(Arrays.asList(1, 2, 3))
                     .match()
                     .with(Arrays.asList(1, 2, 3), () -> System.out.println("List 타입 매칭"))
                     .otherwise(() -> System.out.println("List 타입 매칭 실패"));

        // 예제 5: 타입 불일치
        JPatternMatch.of(2)
                     .match()
                     .with("2", () -> System.out.println("String 타입 매칭"))
                     .otherwise(() -> System.out.println("String 타입 매칭 실패"));

        // 예제 6: 유효한 타입 변환
        String stringValue = "123";
        Integer integerResult = JPatternMatch.of(stringValue)
                                             .match()
                                             .returnObject(Integer.class)
                                             .with("123", () -> Integer.parseInt(stringValue))
                                             .otherwise(() -> -1);

        System.out.println("문자열을 정수로 변환: " + integerResult);

        // 예제 7: 유효하지 않은 타입 변환 ; ClassCastException
        try {
            JPatternMatch.of(123)
                         .match()
                         .with("123", () -> Integer.parseInt("123"))
                         .otherwise(() -> {
                            throw new ClassCastException();
                        });
        } catch (ClassCastException e) {
            System.out.println("ClassCastException 발생");
        }

        // 예제 8: Double 타입 변환
        Double doubleValue2 = 3.14;
        String doubleResult = JPatternMatch.of(doubleValue2)
                                           .match()
                                           .returnObject(String.class)
                                           .with(3.14, () -> String.valueOf(doubleValue2))
                                           .otherwise(() -> "default");

        System.out.println("Double을 문자열로 변환: " + doubleResult);

        // 예제 10: exhaustive() - 반환 매핑 객체가 있을 경우 ; PatternMatchException
        Object exhaustiveCheckValue = 2;
        try {
            JPatternMatch.of(exhaustiveCheckValue)
                    .match()
                    .returnObject(String.class)
                    .with("2", () -> System.out.println("String 2 타입 매칭"))
                    .exhaustive();
        } catch (PatternMatchException ex){
            System.out.println("PatternMatchException 발생");
        }


        // 예제 10: exhaustive() - 반환 매핑 객체가 없을 경우 ; PatternMatchException
        Object exhaustiveExceptionCheckValue = 2;
        try {
            JPatternMatch.of(exhaustiveExceptionCheckValue)
                    .match()
                    .returnObject(String.class)
                    .with("3", () -> System.out.println("String 3 타입 매칭"))
                    .exhaustive();
        } catch (PatternMatchException ex){
            System.out.println("PatternMatchException 발생");
        }

        // 예제 11: 사용자 정의 객체 타입 매칭
        class UserDefinedClass {
            String value;
            UserDefinedClass(String value) {
                this.value = value;
            }
        }
        UserDefinedClass userDefinedObject = new UserDefinedClass("value");


        JPatternMatch.of(userDefinedObject)
                     .match()
                     .with(new UserDefinedClass("value"), () -> System.out.println("UserDefinedClass 타입 매칭"))
                     .otherwise(() -> System.out.println("UserDefinedClass 타입 매칭 실패"));


        // 예제 12: 중첩된 사용자 정의 객체 매칭
        class DeeperTestClass {
            String deeperNestedValue;
            DeeperTestClass(String deeperNestedValue) {
                this.deeperNestedValue = deeperNestedValue;
            }
        }

        class NestedTestClass {
            String nestedValue;
            DeeperTestClass deeperTestClass;
            NestedTestClass(String nestedValue, DeeperTestClass deeperTestClass) {
                this.nestedValue = nestedValue;
                this.deeperTestClass = deeperTestClass;
            }
        }

        class TestClass {
            String value;
            NestedTestClass nestedTestClass;
            TestClass(String value, NestedTestClass nestedTestClass) {
                this.value = value;
                this.nestedTestClass = nestedTestClass;
            }
        }

        TestClass testObject = new TestClass("testValue",
                               new NestedTestClass("nestedValue",
                               new DeeperTestClass("deepValue")));

        JPatternMatch.of(testObject)
                     .match()
                     .with(new TestClass("testValue", null), () -> {
                         System.out.println("TestClass 타입 매칭");
                     })
                     .otherwise(() -> {
                         System.out.println("TestClass 타입 매칭 실패");
                     });

        JPatternMatch.of(testObject.nestedTestClass)
                     .match()
                     .with(new NestedTestClass("nestedValue", null), () -> {
                         System.out.println("NestedTestClass 타입 매칭");
                     })
                     .otherwise(() -> {
                        System.out.println("NestedTestClass 타입 매칭 실패");
                     });

        JPatternMatch.of(testObject.nestedTestClass.deeperTestClass)
                     .match()
                     .with(new DeeperTestClass("deepValue"), () -> {
                         System.out.println("DeeperTestClass 타입 매칭");
                     })
                     .otherwise(() -> {
                         System.out.println("DeeperTestClass 타입 매칭 실패");
                     });
    }

}
