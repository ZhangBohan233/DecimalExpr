package trashsoftware.decimalExpr.expression;

import trashsoftware.decimalExpr.parser.Node;
import trashsoftware.numbers.Rational;
import trashsoftware.numbers.Real;

public class LoopFunctions {

    /**
     * Summing an expression.
     *
     * Arguments:
     * (expression, invariant name, start value, stop value)
     *
     * Example:
     * sum(x+1, x, 0, 5)
     */
    public static final LoopFunction SUMMATION = new LoopFunction("sum", 4) {

        @Override
        public Real eval(Node expr, String invariant, Real... numbers) {
            Real stop = numbers[1];
            Real current = numbers[0];
            Real sum = Rational.ZERO;
            while (hasNext(current, stop)) {
                getValuesBundle().putVariable(invariant, current);
                Real loopResult = expr.eval(getValuesBundle());
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

    /**
     * Arguments:
     * (expression, invariant name, point)
     */
    public static final LoopFunction DERIVATIVE = new LoopFunction("ddx", 3) {
        private final Rational dx = Rational.fromFraction(1, 10000);
        private final Rational halfDx = (Rational) dx.divide(Rational.valueOf(2));

        @Override
        public Real eval(Node expr, String invariant, Real... numbers) {
            Real pos = numbers[0];
            getValuesBundle().putVariable(invariant, pos.subtract(halfDx));
            Real firstVal = expr.eval(getValuesBundle());
            getValuesBundle().putVariable(invariant, pos.add(halfDx));
            Real secondVal = expr.eval(getValuesBundle());
            return secondVal.subtract(firstVal).divide(dx);
        }

        @Override
        protected Real nextStep(Real current) {
            return null;
        }

        @Override
        protected boolean hasNext(Real current, Real stopValue) {
            return false;
        }
    };

    /**
     * Arguments:
     * (expression, invariant, lower bound, upper bound)
     */
    public static final LoopFunction INTEGRAL = new LoopFunction("integral", 4) {
        private final Rational dx = Rational.fromFraction(1, 100);
        private final Rational halfDx = (Rational) dx.divide(Rational.valueOf(2));

        @Override
        public Real eval(Node expr, String invariant, Real... numbers) {
            Real stop = numbers[1];
            Real x = numbers[0].add(halfDx);
            Real sum = Rational.ZERO;
            while (hasNext(x, stop)) {
                getValuesBundle().putVariable(invariant, x);
                Real y = expr.eval(getValuesBundle());
                Real area = y.multiply(dx);
                sum = sum.add(area);
                x = nextStep(x);
            }
            return sum;
        }

        @Override
        protected Real nextStep(Real current) {
            return current.add(dx);
        }

        @Override
        protected boolean hasNext(Real current, Real stopValue) {
            return current.lessThan(stopValue);
        }
    };
}
