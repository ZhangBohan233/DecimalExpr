package trashsoftware.decimalExpr;

import static org.junit.jupiter.api.Assertions.*;
import org.junit.jupiter.api.Test;
import trashsoftware.decimalExpr.expression.Operator;
import trashsoftware.decimalExpr.expression.UnaryOperator;

import java.math.BigDecimal;

class UnitTests {

    @Test
    void testSingleNumber() {
        DecimalExpr expr = new DecimalExprBuilder().expression("2").build();
        assert expr.evaluate().equals(new BigDecimal(2));
    }

    @Test
    void testCustomUnaryOperatorLeft() {
        Operator not = new UnaryOperator("!", 1, true) {
            @Override
            public BigDecimal eval(BigDecimal number) {
                return number.negate();
            }
        };
        DecimalExpr expr = new DecimalExprBuilder().expression("!2").operator(not).build();
        assert expr.evaluate().equals(new BigDecimal(-2));
    }

    @Test
    void testCustomUnaryOperatorRight() {
        Operator factorial = new UnaryOperator("!", 1, false) {
            @Override
            public BigDecimal eval(BigDecimal number) {
                BigDecimal result = new BigDecimal(1);
                BigDecimal increment = new BigDecimal(1);
                for (BigDecimal i = new BigDecimal(1); i.compareTo(number) <= 0; i = i.add(increment)) {
                    result = result.multiply(i);
                }
                return result;
            }
        };
        DecimalExpr expr = new DecimalExprBuilder().expression("3!").operator(factorial).build();
        assert expr.evaluate().equals(new BigDecimal(6));
    }
}
