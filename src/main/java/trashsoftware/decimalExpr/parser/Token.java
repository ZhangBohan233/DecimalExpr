package trashsoftware.decimalExpr.parser;

import java.math.BigDecimal;

public abstract class Token {

    public boolean isIdentifier() {
        return false;
    }

    public boolean isNumber() {
        return false;
    }

    public abstract Object getValue();
}

class IdToken extends Token {

    private String value;

    IdToken(String value) {
        this.value = value;
    }

    @Override
    public String getValue() {
        return value;
    }

    @Override
    public boolean isIdentifier() {
        return true;
    }

    @Override
    public String toString() {
        return "Id(" + value + ")";
    }
}

class NumberToken extends Token {

    private BigDecimal value;

    NumberToken(String value) {
        this.value = new BigDecimal(value);
    }

    @Override
    public BigDecimal getValue() {
        return value;
    }

    @Override
    public boolean isNumber() {
        return true;
    }

    @Override
    public String toString() {
        return "Num(" + value + ")";
    }
}
