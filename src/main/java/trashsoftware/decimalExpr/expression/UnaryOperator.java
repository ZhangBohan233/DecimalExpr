package trashsoftware.decimalExpr.expression;

import java.math.BigDecimal;

public abstract class UnaryOperator extends Operator {

    private final boolean leftAssociative;

    public UnaryOperator(String symbol, int precedence, boolean leftAssociative) {
        this.symbol = symbol;
        this.precedence = precedence;
        this.leftAssociative = leftAssociative;
    }

    public boolean isLeftAssociative() {
        return leftAssociative;
    }

    public abstract BigDecimal eval(BigDecimal number);
}
