package trashsoftware.decimalExpr.expression;

import java.math.BigDecimal;

public class Functions {

    public static Function ABS = new Function("abs", 1) {
        @Override
        public BigDecimal eval(BigDecimal... numbers) {
            return numbers[0].abs();
        }
    };
}
