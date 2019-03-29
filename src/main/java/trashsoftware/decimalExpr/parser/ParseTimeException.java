package trashsoftware.decimalExpr.parser;

public class ParseTimeException extends RuntimeException {

    public ParseTimeException(String msg) {
        super(msg);
    }
}


class NoSuchVariableException extends ParseTimeException {

    NoSuchVariableException(String msg) {
        super(msg);
    }
}
