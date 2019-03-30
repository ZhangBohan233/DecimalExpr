package trashsoftware.decimalExpr.expression;

import trashsoftware.numbers.Irrational;
import trashsoftware.numbers.Rational;
import trashsoftware.numbers.Real;

public class Functions {

    public final static Function ABS = new Function("abs", 1) {
        @Override
        public Real eval(Real... numbers) {
            return numbers[0].abs();
        }
    };

    public final static Function CEIL = new Function("ceil", 1) {
        @Override
        public Real eval(Real... numbers) {
            return numbers[0].ceiling();
        }
    };

    public final static Function E = new Function("e", 0) {
        @Override
        public Real eval(Real... numbers) {
            return Irrational.E;
        }
    };

    public final static Function FLOOR = new Function("floor", 1) {
        @Override
        public Real eval(Real... numbers) {
            return numbers[0].floor();
        }
    };

    public final static Function LOG = new Function("log", 1, 2) {
        @Override
        public Real eval(Real... numbers) {
            return null;
        }
    };

    public final static Function PI = new Function("pi", 0) {
        @Override
        public Real eval(Real... numbers) {
            return Irrational.PI;
        }
    };

    public final static Function SQRT = new Function("sqrt", 1) {
        @Override
        public Real eval(Real... numbers) {
            return numbers[0].sqrt();
        }
    };

    /* Helper functions */
    private static Real log2(Rational exp) {
        if (exp.signum() <= 0) {
            throw new NumberValueException("Exponent must be positive");
        }
        return null;
    }
}
