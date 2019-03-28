package trashsoftware.decimalExpr.expression;

import java.math.BigDecimal;

public abstract class BinaryOperator extends Operator {

    public BinaryOperator(String symbol, int precedence) {
        this.symbol = symbol;
        this.precedence = precedence;
    }

    public abstract BigDecimal eval(BigDecimal left, BigDecimal right);
}
