package trashsoftware.decimalExpr.expression;

import trashsoftware.numbers.Real;

public abstract class AbstractFunction {
    private final String name;
    private final int leastNumArgument;
    private final int mostNumArgument;

    AbstractFunction(String name, int numArgument) {
        this.name = name;
        this.leastNumArgument = numArgument;
        this.mostNumArgument = numArgument;
    }

    AbstractFunction(String name, int leastNumArgument, int mostNumArgument) {
        this.name = name;
        this.leastNumArgument = leastNumArgument;
        this.mostNumArgument = mostNumArgument;
    }

    public String getName() {
        return name;
    }

    public int getLeastNumArgument() {
        return leastNumArgument;
    }

    public int getMostNumArgument() {
        return mostNumArgument;
    }

    ArgumentNumberException createArgumentNumberException(int actualNumArgument) {
        if (leastNumArgument == mostNumArgument) {
            return ArgumentNumberException.getByNameExpectActual(
                    name, leastNumArgument, actualNumArgument);
        } else {
            return ArgumentNumberException.getByNameExpectActual(
                    name, leastNumArgument, mostNumArgument, actualNumArgument);
        }
    }
}
