package trashsoftware.decimalExpr.expression;

import trashsoftware.decimalExpr.parser.Node;
import trashsoftware.decimalExpr.parser.ValuesBundle;
import trashsoftware.numbers.Real;


public abstract class LoopFunction extends AbstractFunction {

    private ValuesBundle valuesBundle;

    protected LoopFunction(String name, int paramCount) {
        super(name, paramCount);
    }

    public void setValuesBundle(ValuesBundle valuesBundle) {
        this.valuesBundle = valuesBundle;
    }

    protected ValuesBundle getValuesBundle() {
        return valuesBundle;
    }

    public abstract Real eval(Node expr, String invariant, Real... numbers);

    protected abstract Real nextStep(Real current);

    protected abstract boolean hasNext(Real current, Real stopValue);
}
