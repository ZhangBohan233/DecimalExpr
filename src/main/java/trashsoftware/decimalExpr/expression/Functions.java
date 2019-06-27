package trashsoftware.decimalExpr.expression;

import trashsoftware.numbers.InfinityException;
import trashsoftware.numbers.Irrational;
import trashsoftware.numbers.Rational;
import trashsoftware.numbers.Real;

public class Functions {

    public static int LOG_ACCURACY = 32;

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

    public final static Function EXP = new Function("exp", 0, 1) {
        @Override
        public Real eval(Real... numbers) {
            if (numbers.length == 0) {
                return Irrational.E;
            } else if (numbers.length == 1) {
                return Irrational.E.power(numbers[0]);
            } else {
                throw createArgumentNumberException(numbers.length);
            }
        }
    };

    public final static Function FLOOR = new Function("floor", 1) {
        @Override
        public Real eval(Real... numbers) {
            return numbers[0].floor();
        }
    };

    public final static Function LN = new Function("ln", 1) {
        @Override
        public Real eval(Real... numbers) {
            return logE(numbers[0]);
        }
    };

    public final static Function LOG = new Function("log", 1, 2) {
        @Override
        public Real eval(Real... numbers) {
            if (numbers.length == 1) {
                return log2(numbers[0]);
            } else if (numbers.length == 2) {
                Real base = numbers[0];
                Real exp = numbers[1];
                return logE(exp).divide(logE(base));
            } else {
                throw createArgumentNumberException(numbers.length);
            }
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

    public final static Function COS = new Function("cos", 1) {
        @Override
        public Real eval(Real... numbers) {
            return null;
        }
    };

    /* Helper functions */
    private static Real log2(Real exp) {
        if (exp.signum() < 0) {
            throw new NumberValueException("Exponent must be positive");
        } else if (exp.signum() == 0) {
            throw new InfinityException(-1);  // negative infinity
        }
        if (exp.isRational() && ((Rational) exp).isInteger()) {
            return null;
        } else {
            return logE(exp).divide(logE(Rational.valueOf(2)));
        }
    }

    private static Real logE(Real exp) {
        if (exp.signum() < 0) {
            throw new NumberValueException("Exponent must be positive");
        } else if (exp.signum() == 0) {
            throw new InfinityException(-1);  // negative infinity
        }
        double result = Math.log(exp.toDecimal().doubleValue());
        if ((long) result == result) {
            return Rational.valueOf((long) result);
        } else {
            return Irrational.valueOf(result);
        }
    }
}
