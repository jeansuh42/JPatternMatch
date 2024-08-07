package org.jpatternmatch.examples;

import java.util.Arrays;
import java.util.List;

import static org.jpatternmatch.src.JPatternMatch.asTypeOf;

public class AsTypeOfExample {

    public static void main(String[] args) {

        /*
         * You can use an expression lambda as well,
         * but sometimes a statement lambda is much more readable
         * when using this library.
         */
        asTypeOf(1, Integer.class, () -> System.out.println("expression lambda"));

        // 예제 1: null 값 매칭
        try {
            asTypeOf(null, String.class, () -> {
                System.out.println("null 주입");
            });
        } catch (IllegalArgumentException e) {
            System.out.println("IllegalArgumentException 발생");
        }

        // 예제 2: Double 타입 매칭
        asTypeOf(2.0, Double.class, () -> {
            System.out.println("Double 타입 매칭");
        });

        // 예제 3: Boolean 타입 매칭
        asTypeOf(true, Boolean.class, () -> {
            System.out.println("Boolean 타입 매칭");
        });

        // 예제 4: List 타입 매칭
        asTypeOf(Arrays.asList(1, 2, 3), List.class, () -> {
            System.out.println("List 타입 매칭");
        });

        // 예제 5: 타입 불일치
        asTypeOf(2, String.class, () -> {
            System.out.println("String 타입 매칭 실패");
        });

        // 예제 7: 유효한 타입 변환
        String stringValue = "123";
        Integer integerResult = asTypeOf(stringValue, String.class, Integer::parseInt);
        System.out.println("String을 Integer로 변환 완료:" + integerResult);

        // 예제 8: 유효하지 않은 타입 변환
        try {
            asTypeOf(123, String.class, Integer::parseInt);
        } catch (ClassCastException e) {
            System.out.println("ClassCastException 발생");
        }

        // 예제 9: Double 타입 변환
        Double doubleValue = 3.14;
        String doubleResult = asTypeOf(doubleValue, Double.class, String::valueOf);
        System.out.println("Double을 String으로 변환 완료: " + doubleResult);

        // 예제 10: Float 타입 변환
        Float floatValue = 3.14f;
        String floatResult = asTypeOf(floatValue, Float.class, String::valueOf);
        System.out.println("Float을 String으로 변환 완료: " + floatResult);


        // 예제 11: 사용자 정의 객체 타입 매칭
        class UserDefinedClass {
            String value;
            UserDefinedClass(String value) {
                this.value = value;
            }
        }

        UserDefinedClass UserDefinedClass = new UserDefinedClass("value");
        asTypeOf(UserDefinedClass, UserDefinedClass.class, () -> {
            System.out.println("UserDefinedClass 타입 매칭");
        });


        // 예제 12: 사용자 정의 객체 타입 매칭
        // Nested Custom Object for example
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


        TestClass testObject = new TestClass("testValue", null);
        NestedTestClass nestedObject = new NestedTestClass("nestedValue", null);
        nestedObject.deeperTestClass = new DeeperTestClass("deepValue");
        testObject.nestedTestClass = nestedObject;

        asTypeOf(testObject, TestClass.class, () -> {
            System.out.println("TestClass 타입 매칭");
        });

        asTypeOf(testObject.nestedTestClass, NestedTestClass.class, () -> {
            System.out.println("NestedTestClass 타입 매칭");
        });

        asTypeOf(testObject.nestedTestClass.deeperTestClass, DeeperTestClass.class, () -> {
            System.out.println("DepperTestClass 타입 매칭");
        });

    }

}
