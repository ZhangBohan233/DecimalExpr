package trashsoftware.decimalExpr.expression;

public class ArgumentNumberException extends EvaluateTimeException {

    public ArgumentNumberException() {
        super();
    }

    public ArgumentNumberException(String msg) {
        super(msg);
    }

    static ArgumentNumberException getByNameExpectActual(String functionName, int expect, int actual) {
        return new ArgumentNumberException(String.format("Improper arguments number for function '%s': " +
                "expected: %d, actual: %d", functionName, expect, actual));
    }

    static ArgumentNumberException getByNameExpectActual(String functionName, int expectLeast,
                                                                int expectMost, int actual) {
        return new ArgumentNumberException(String.format("Improper arguments number for function '%s': " +
                "expected: %d to %d, actual: %d", functionName, expectLeast, expectMost, actual));
    }
}
