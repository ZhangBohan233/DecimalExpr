package trashsoftware.decimalExpr.expression;

class EvaluateException extends ArithmeticException {

    EvaluateException() {
        super();
    }

    EvaluateException(String msg) {
        super(msg);
    }
}

class NumberValueException extends EvaluateException {

    NumberValueException(String msg) {
        super(msg);
    }
}
