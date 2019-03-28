package trashsoftware.decimalExpr;

import trashsoftware.decimalExpr.expression.Function;

import java.math.BigDecimal;

public class TestMain {

    public static void main(String[] args) {
        Function f = new Function("log", 2) {
            @Override
            public BigDecimal eval(BigDecimal... numbers) {
                return new BigDecimal(1);
            }
        };

        Function g = new Function("g", 2, 3) {
            @Override
            public BigDecimal eval(BigDecimal... numbers) {
                if (numbers.length == 2) {
                    return new BigDecimal(2);
                } else if (numbers.length == 3) {
                    return new BigDecimal(99);
                } else {
                    return null;
                }
            }
        };
        DecimalExprBuilder deb = new DecimalExprBuilder()
                .expression("x + 3 + abs(-(4 + 2)) + log(2, 3) + g(1, 2)")
                .variable("x")
                .function(g)
                .function(f);
        DecimalExpr expr = deb.build();
        expr.setVariable("x", new BigDecimal(2));
        System.out.println(expr.evaluate());
    }
}
