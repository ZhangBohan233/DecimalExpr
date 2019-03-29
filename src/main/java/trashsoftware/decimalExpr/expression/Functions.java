package trashsoftware.decimalExpr.expression;

import java.math.BigDecimal;
import java.math.MathContext;

public class Functions {

    public static Function ABS = new Function("abs", 1) {
        @Override
        public BigDecimal eval(BigDecimal... numbers) {
            return numbers[0].abs();
        }
    };

    public static Function SQRT = new Function("sqrt", 1) {
        @Override
        public BigDecimal eval(BigDecimal... numbers) {
            return numbers[0].sqrt(MathContext.DECIMAL128);
        }
    };

    public static Function E = new Function("e", 0) {
        @Override
        public BigDecimal eval(BigDecimal... numbers) {
            return new BigDecimal("2.718281828459045235360287471352662497757247093699959" +
                    "5749669676277240766303535475945713821785251664274");
        }
    };

    public static Function PI = new Function("pi", 0) {
        @Override
        public BigDecimal eval(BigDecimal... numbers) {
            return new BigDecimal("3.14159265358979323846264338327950288419716939937510" +
                    "5820974944592307816406286208998628034825342117068");
        }
    };
}
