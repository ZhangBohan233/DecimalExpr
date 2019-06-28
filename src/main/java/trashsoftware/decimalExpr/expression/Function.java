package trashsoftware.decimalExpr.expression;


import trashsoftware.numbers.Real;

public abstract class Function extends AbstractFunction {

    protected Function(String name, int numArgument) {
        super(name, numArgument);
    }

    protected Function(String name, int leastNumArgument, int mostNumArgument) {
        super(name, leastNumArgument, mostNumArgument);
    }

    public abstract Real eval(Real... numbers);

    @Override
    public int hashCode() {
        return super.hashCode();
    }
}
