package trashsoftware.decimalExpr.parser;

import trashsoftware.numbers.Rational;
import trashsoftware.numbers.Real;

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

    private Real value;

    NumberToken(String value) {
        this.value = Rational.parseDecimal(value);
    }

    @Override
    public Real getValue() {
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
