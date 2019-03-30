package trashsoftware.decimalExpr.expression;

import trashsoftware.numbers.Real;

import java.math.BigDecimal;

public abstract class BinaryOperator extends Operator {

    public BinaryOperator(String symbol, int precedence) {
        this.symbol = symbol;
        this.precedence = precedence;
    }

    public abstract Real eval(Real left, Real right);
}
