package trashsoftware.decimalExpr.expression;

import trashsoftware.numbers.Irrational;
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
            return Irrational.valueOf("2.718281828459045235360287471352662497757247093699959" +
                    "5749669676277240766303535475945713821785251664274");
        }
    };

    public final static Function FLOOR = new Function("floor", 1) {
        @Override
        public Real eval(Real... numbers) {
            return numbers[0].floor();
        }
    };

    public final static Function PI = new Function("pi", 0) {
        @Override
        public Real eval(Real... numbers) {
            return Irrational.valueOf("3.14159265358979323846264338327950288419716939937510" +
                    "5820974944592307816406286208998628034825342117068");
        }
    };

    public final static Function SQRT = new Function("sqrt", 1) {
        @Override
        public Real eval(Real... numbers) {
            return numbers[0].sqrt();
        }
    };
}
