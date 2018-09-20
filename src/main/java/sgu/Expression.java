package sgu;

import java.util.ArrayList;
import java.util.HashMap;
import java.util.List;
import java.util.Map;

public class Expression {

    List<String> modifyExpression;
    List<Error> errorList;
    private String operations = "()+-*/^";
    private Map<String, Integer> operationPriorityMap = new HashMap<String, Integer>() {{
        put("(", 0);
        put(")", 0);
        put("+", 1);
        put("-", 1);
        put("*", 2);
        put("/", 2);
        put("^", 3);
    }};

    public Expression(String expression){
        this.errorList = new ArrayList<>();
        this.modifyExpression = modifyExpression(expression);
        errorsCheck(this.getModifyExpression());
    }

    public List<String> getModifyExpression() {
        return modifyExpression;
    }

    public List<Error> getErrorList() {
        return errorList;
    }

    public boolean isValid(){
        return this.getErrorList().isEmpty();
    }

    private boolean isOperation(String symbol) {
        return operations.contains(symbol);
    }

    private boolean isBracket(String operator) {
        return isOperation(operator) && getPriority(operator) == 0;
    }

    private int getPriority(String operation) {
        return operationPriorityMap.get(operation);
    }

    private int operatorCount(List<String> expressionList) {
        int count = 0;
        for (int i = 0; i < expressionList.size(); i++) {
            if (!isOperation(expressionList.get(i))) {
                count++;
            }
        }
        return count;
    }

    private int operationCount(List<String> expressionList) {
        int count = 0;
        for (int i = 0; i < expressionList.size(); i++) {
            if (isOperation(expressionList.get(i)) & !isBracket(expressionList.get(i))) {
                count++;
            }
        }
        return count;
    }

    private void operatorCheck(String expression) {
        for (int i = 1; i < expression.length() - 1; i++) {
            if (expression.charAt(i) == ' ') {
                if (!isOperation(String.valueOf(expression.charAt(i - 1))) & !isOperation(String.valueOf(expression.charAt(i + 1)))) {
                    errorList.add(Error.OPERATION_ERROR);
                    break;
                }
            }
        }
    }

    private void bracketsCheck(List<String> expressionList) {
        int bracketCount = 0;
        for (int i = 0; i < expressionList.size(); i++) {
            if (expressionList.get(i).equals("(")) {
                bracketCount++;
            } else if (expressionList.get(i).equals(")")) {
                if (bracketCount >= 0) {
                    bracketCount--;
                } else {
                    errorList.add(Error.BRACKET_ERROR);
                    break;
                }
            }
        }
        if (bracketCount != 0) {
            errorList.add(Error.BRACKET_ERROR);
        }
    }

    private void operationCheck(List<String> expressionList) {
        if (isOperation(expressionList.get(0)) && getPriority(expressionList.get(0)) != 0) {
            expressionList.add(0, "0");
        }
        if (operationCount(expressionList) != (operatorCount(expressionList) - 1)) {
            errorList.add(Error.OPERATION_ERROR);
        } else if (isOperation(expressionList.get(expressionList.size() - 1)) && getPriority(expressionList.get(expressionList.size() - 1)) != 0) {
            errorList.add(Error.OPERATION_ERROR);
        } else {
            for (int i = 1; i < expressionList.size(); i++) {
                if (isOperation(expressionList.get(i))) {
                    if (isOperation(expressionList.get(i - 1)) && getPriority(expressionList.get(i - 1)) != 0 && getPriority(expressionList.get(i)) != 0) {
                        errorList.add(Error.OPERATION_ERROR);
                        break;
                    }
                }
            }
        }
    }

    private void numericOperatorCheck(List<String> expressionList) {
        double operator;
        try {
            for (int i = 0; i < expressionList.size(); i++) {
                if (!isOperation(expressionList.get(i))) {
                    operator = Double.parseDouble(expressionList.get(i));
                }
            }
        } catch (NumberFormatException e) {
            errorList.add(Error.OPERATOR_ERROR);
        }
    }

    private void errorsCheck(List<String> expressionList) {
        bracketsCheck(expressionList);
        operationCheck(expressionList);
        numericOperatorCheck(expressionList);
    }

    private String makeUnaryMines(String expression) {
        String res = "";

        if (expression.charAt(0) == '-' & !isOperation(String.valueOf(expression.charAt(1)))) {
            res += "~";
        } else {
            res += expression.charAt(0);
        }

        for (int i = 1; i < expression.length(); i++) {
            if (expression.charAt(i) == '-' & isOperation(String.valueOf(expression.charAt(i - 1)))) {
                res += "~";
            } else {
                res += expression.charAt(i);
            }
        }
        return res;
    }

    private List<String> modifyExpression(String expression) {
        StringBuilder temp = new StringBuilder();
        List<String> expressionList = new ArrayList<String>();
        operatorCheck(expression);
        String newExpression = expression.replaceAll(" ", "");
        if (expression.length() > 1){
            newExpression = makeUnaryMines(newExpression);
        }

        for (int i = 0; i < newExpression.length(); i++) {
            char symbol = newExpression.charAt(i);

            if (isOperation(String.valueOf(symbol))) {
                if (!temp.toString().equals("")) {
                    expressionList.add(temp.toString());
                    temp.delete(0, temp.length());
                }
                expressionList.add(String.valueOf(symbol));
            } else {
                if (symbol == '~') {
                    temp.append('-');
                } else {
                    temp.append(symbol);
                }
            }
        }
        if (temp.length() != 0) {
            expressionList.add(temp.toString());
        }
        return expressionList;
    }
}
