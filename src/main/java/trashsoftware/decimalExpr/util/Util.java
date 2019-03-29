package trashsoftware.decimalExpr.util;

import java.math.BigDecimal;

public class Util {

    public static boolean isInteger(BigDecimal number) {
        return number.stripTrailingZeros().scale() <= 0;
    }
}
