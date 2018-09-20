package sgu;

import java.util.Scanner;

public class Main {
    public static void main(String[] args) {

        String input;
        Calculator calculator = new Calculator();
        Expression expression;

        System.out.println("Enter an expression:");
        Scanner in = new Scanner(System.in);
        input = in.nextLine();

        while (!input.equals("exit")){
            expression = new Expression(input);
            if (expression.isValid()){
                System.out.println(calculator.solve(expression));
            } else {
                for (Error error : expression.getErrorList()){
                    System.out.println(error.getErrorMessage());
                }
            }

            System.out.println("Enter an expression:");
            input = in.nextLine();
        }
    }
}
