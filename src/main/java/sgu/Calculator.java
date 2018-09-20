package sgu;

import java.util.ArrayList;
import java.util.HashMap;
import java.util.List;
import java.util.Map;

public class Calculator {

    private Map<String, Integer> operationPriorityMap = new HashMap<String, Integer>() {{
        put("(", 0);
        put(")", 0);
        put("+", 1);
        put("-", 1);
        put("*", 2);
        put("/", 2);
        put("^", 3);
    }};
    private String operations = "()+-*/^";

    private int getPriority(String operation) {
        return operationPriorityMap.get(operation);
    }

    private boolean comparePriority(String operation1, String operation2) {
        return getPriority(operation1) >= getPriority(operation2);
    }

    private boolean isOperation(String symbol) {
        return operations.contains(symbol);
    }

    private String createRPN(List<String> expression) {
        List<String> output = new ArrayList<>();
        List<String> operatorStack = new ArrayList<>();
        StringBuilder builder = new StringBuilder();

        for (int i = 0; i < expression.size(); i++) {
            String operator = expression.get(i);

            if (!isOperation(operator)) {
                output.add(operator);
            } else if (operator.equals("(")) {
                operatorStack.add(operator);
            } else if (operator.equals(")")) {
                int j = operatorStack.size() - 1;
                while (!operatorStack.get(j).equals("(")) {
                    output.add(operatorStack.get(j));
                    operatorStack.remove(j);
                    j = operatorStack.size() - 1;
                }
                operatorStack.remove(j);
            } else if (operatorStack.isEmpty() || !comparePriority(operatorStack.get(operatorStack.size() - 1), operator)) {
                operatorStack.add(operator);
            } else if (comparePriority(operatorStack.get(operatorStack.size() - 1), operator)) {
                int j = operatorStack.size();
                while (!operatorStack.isEmpty() && comparePriority(operatorStack.get(operatorStack.size() - 1), operator)) {
                    output.add(operatorStack.get(j - 1));
                    operatorStack.remove(j - 1);
                    j--;
                }
                operatorStack.add(operator);
            }
        }

        for (int j = operatorStack.size() - 1; j >= 0; j--) {
            output.add(operatorStack.get(j));
        }

        for (int i = 0; i < output.size(); i++) {
            builder.append(output.get(i) + " ");
        }
        return builder.toString();
    }

    public double solve(Expression expression){
            String RPN = createRPN(expression.getModifyExpression());
            String[] operators = RPN.split(" ");
            List<Double> stack = new ArrayList<Double>();

            for (int i = 0; i < operators.length; i++) {
                if (!operators[i].equals("")) {
                    if (!operations.contains(operators[i])) {
                        stack.add(Double.parseDouble(operators[i]));
                    } else {
                        double a = stack.get(stack.size() - 2);
                        double b = stack.get(stack.size() - 1);
                        double c = 0;
                        switch (operators[i].charAt(0)) {
                            case '+':
                                c = a + b;
                                break;
                            case '-':
                                c = a - b;
                                break;
                            case '*':
                                c = a * b;
                                break;
                            case '/':
                                c = a / b;
                                break;
                            case '^':
                                c = Math.pow(a, b);
                                break;
                            default:
                                throw new NumberFormatException();
                        }
                        stack.remove(stack.size() - 1);
                        stack.remove(stack.size() - 1);
                        stack.add(c);
                    }
                }
            }
            return stack.get(0);
    }
}