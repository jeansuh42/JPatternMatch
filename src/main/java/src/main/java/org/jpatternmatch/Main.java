package src.main.java.org.jpatternmatch;

public class Main {
    public static void main(String[] args) {

//        Parent parentInstance = new Parent();
//        Child childInstance = new Child();
//
//        JPatternMatch.asTypeOfWithClass(childInstance, parentInstance.getClass(), () -> {
//            System.out.println("childInstance is a type of Parent.");
//            parentInstance.print();
//        });
//
//        JPatternMatch.asTypeOfWithClass(parentInstance, parentInstance.getClass(), () -> {
//            System.out.println("parentInstance is a type of Parent.");
//            parentInstance.print();
//        });
//
//        JPatternMatch.asTypeOfWithClass("", parentInstance.getClass(), () -> {
//            System.out.println("This will not be printed.");
//        });


        JPatternMatch.asTypeOfWithClass(2, Integer.class, () -> {
            System.out.println("This is a number.");
        });

        JPatternMatch.asTypeOfWithClass("2", Integer.class, () -> {
            System.out.println("This is a number.");
        });

        String[] a = {"str"};
        Main mainInstance = new Main();

//         반환값을 받는 예시
        String result = JPatternMatch.asTypeOfWithClass("2", a[0].getClass(), instance -> {
            String out = mainInstance.exampleMethod("go");
            System.out.println("out!!!!!!!!!!" + out);
            return "changed";
        });

        JPatternMatch.asTypeOfWithClass("2", a[0].getClass(), () -> {
            System.out.println("This is a String.");
            a[0] = "chang222e";
        });

        // 결과 출력
        System.out.println("changed: " + result);
        System.out.println("Updated value: " + a[0]);

        System.out.println("요");


    }

    public String exampleMethod(String input) {
        return "Hello, " + input;
    }

}
