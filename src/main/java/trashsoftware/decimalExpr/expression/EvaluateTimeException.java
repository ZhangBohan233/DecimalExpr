package trashsoftware.decimalExpr.expression;

public class EvaluateTimeException extends ArithmeticException {

    public EvaluateTimeException() {
        super();
    }

    public EvaluateTimeException(String msg) {
        super(msg);
    }
}

class NumberValueException extends EvaluateTimeException {

    NumberValueException(String msg) {
        super(msg);
    }
}
