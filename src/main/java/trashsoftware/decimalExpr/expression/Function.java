package trashsoftware.decimalExpr.expression;


import java.math.BigDecimal;

public abstract class Function {

    private final String name;
    private final int leastNumArgument;
    private final int mostNumArgument;

    @SuppressWarnings("WeakerAccess")
    public Function(String name, int numArgument) {
        this.name = name;
        this.leastNumArgument = numArgument;
        this.mostNumArgument = numArgument;
    }

    @SuppressWarnings("WeakerAccess")
    public Function(String name, int leastNumArgument, int mostNumArgument) {
        this.name = name;
        this.leastNumArgument = leastNumArgument;
        this.mostNumArgument = mostNumArgument;
    }

    public int getLeastNumArgument() {
        return leastNumArgument;
    }

    public int getMostNumArgument() {
        return mostNumArgument;
    }

    public String getName() {
        return name;
    }

    public abstract BigDecimal eval(BigDecimal... numbers);

    @Override
    public int hashCode() {
        return super.hashCode();
    }
}
