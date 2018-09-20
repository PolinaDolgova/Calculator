package sgu;


public enum Error {
    BRACKET_ERROR {
        public String getErrorMessage() {
            return "Неверно расставлены скобки";
        }
    },
    OPERATOR_ERROR {
        public String getErrorMessage() {
            return "Выражение содержит не числа";
        }
    },
    OPERATION_ERROR {
        public String getErrorMessage() {
            return "Неверно расставлены знаки операций";
        }
    };

    public abstract String getErrorMessage();
}
