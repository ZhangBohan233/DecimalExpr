package trashsoftware.decimalExpr.util;

import trashsoftware.numbers.Irrational;
import trashsoftware.numbers.Rational;
import trashsoftware.numbers.Real;

import java.math.BigDecimal;
import java.math.RoundingMode;
import java.text.DecimalFormat;

public class Util {

    public static boolean isInteger(BigDecimal number) {
        return number.stripTrailingZeros().scale() <= 0;
    }

    public static boolean approximateInteger(double value, int decimalDigit) {
        return (long) value == Math.round(0);
    }

    public static Real trigonometryResultToReal(double origDoubleResult) {
        long round = 1_000_000_000_000L;
        double doubleResult = (double) Math.round(origDoubleResult * round) / round;
        if (doubleResult == 0) {
            return Rational.ZERO;
        } else if (doubleResult == 1) {
            return Rational.ONE;
        } else if (doubleResult == -1) {
            return Rational.ONE.negate();
        } else if (doubleResult == 0.5) {
            return Rational.fromFraction(1, 2);
        } else if (doubleResult == -0.5) {
            return Rational.fromFraction(1, 2).negate();
        } else {
            return Irrational.valueOf(doubleResult);
        }
    }
}
