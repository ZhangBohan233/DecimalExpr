package trashsoftware.decimalExpr.util;

import java.math.BigDecimal;

public class Util {

    public static boolean isInteger(BigDecimal number) {
        return number.stripTrailingZeros().scale() <= 0;
    }

    public static boolean approximateInteger(double value, int decimalDigit) {
        return (long) value == Math.round(0);
    }
}
