package trashsoftware.decimalExpr.expression;

public abstract class Operator {

    String symbol;
    int precedence;

    public int getPrecedence() {
        return precedence;
    }

    public String getSymbol() {
        return symbol;
    }
}
