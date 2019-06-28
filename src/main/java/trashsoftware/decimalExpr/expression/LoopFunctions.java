package trashsoftware.decimalExpr.expression;

import trashsoftware.decimalExpr.parser.Node;
import trashsoftware.numbers.Rational;
import trashsoftware.numbers.Real;

public class LoopFunctions {

    public static final LoopFunction SUMMATION = new LoopFunction("sum", 4) {

        @Override
        public Real eval(Node node, String invariant, Real... numbers) {
            Real stop = numbers[1];
            Real current = numbers[0];
            Real sum = Rational.ZERO;
            while (hasNext(current, stop)) {
                valuesBundle.putVariable(invariant, current);
                Real loopResult = node.eval(valuesBundle);
                sum = sum.add(loopResult);
                current = nextStep(current);
            }
            return sum;
        }

        @Override
        protected Real nextStep(Real current) {
            return current.add(Rational.valueOf(1));
        }

        @Override
        protected boolean hasNext(Real current, Real stopValue) {
            return !current.greaterThan(stopValue);
        }
    };
}
