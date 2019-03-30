package trashsoftware.decimalExpr.expression;

import trashsoftware.numbers.Real;

@SuppressWarnings("WeakerAccess")
public abstract class UnaryOperator extends Operator {

    private final boolean leftAssociative;

    /**
     * Creates an instance of a {@code UnaryOperator}.
     * <p>
     * An example of left-associative operator is factorial '!', e.g. "3!" evaluates the left side of the symbol '!'.
     *
     * @param symbol          the operator symbol
     * @param precedence      the precedence of evaluation. The higher the precedence is, the earlier the operator would
     *                        be evaluated
     * @param leftAssociative whether the operator evaluates its left side.
     */
    public UnaryOperator(String symbol, int precedence, boolean leftAssociative) {
        this.symbol = symbol;
        this.precedence = precedence;
        this.leftAssociative = leftAssociative;
    }

    public boolean isLeftAssociative() {
        return leftAssociative;
    }

    public abstract Real eval(Real number);
}
