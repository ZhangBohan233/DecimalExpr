package trashsoftware.decimalExpr.expression;

public abstract class Operator {

    protected String symbol;
    protected int precedence;

    public int getPrecedence() {
        return precedence;
    }

    public String getSymbol() {
        return symbol;
    }
}
