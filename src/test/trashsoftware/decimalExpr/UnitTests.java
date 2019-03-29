package trashsoftware.decimalExpr;

import org.junit.jupiter.api.Test;
import trashsoftware.decimalExpr.expression.Operator;
import trashsoftware.decimalExpr.expression.UnaryOperator;

import java.math.BigDecimal;

class UnitTests {

    private static Operator weakNot = new UnaryOperator("!", 1, false) {
        @Override
        public BigDecimal eval(BigDecimal number) {
            return number.negate();
        }
    };

    private static Operator factorial = new UnaryOperator("!", 100, true) {
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

    @Test
    void testSingleNumber() {
        DecimalExpr expr = new DecimalExprBuilder().expression("2").build();
        assert expr.evaluate().equals(new BigDecimal(2));
    }

    @Test
    void testSingleVariable() {
        DecimalExpr expr = new DecimalExprBuilder()
                .expression("x")
                .variable("x")
                .build()
                .setVariable("x", new BigDecimal(5));
        assert expr.evaluate().equals(new BigDecimal(5));
    }

    @Test
    void testCustomUnaryOperatorRight() {
        DecimalExpr expr = new DecimalExprBuilder().expression("!2").operator(weakNot).build();
        assert expr.evaluate().equals(new BigDecimal(-2));
    }

    @Test
    void testCustomUnaryOperatorRightWithNumber() {
        DecimalExpr expr = new DecimalExprBuilder().expression("!2 + 3").operator(weakNot).build();
        assert expr.evaluate().equals(new BigDecimal(-5));
    }

    @Test
    void testCustomUnaryOperatorLeft() {
        DecimalExpr expr = new DecimalExprBuilder().expression("3!").operator(factorial).build();
        assert expr.evaluate().equals(new BigDecimal(6));
    }

    @Test
    void testCustomUnaryOperatorLeftWithSymbol() {
        DecimalExpr expr = new DecimalExprBuilder().expression("3! * 6").operator(factorial).build();
        assert expr.evaluate().equals(new BigDecimal(36));
    }

    @Test
    void testCustomUnaryOperatorLeftWithSymbolAndPar() {
        DecimalExpr expr = new DecimalExprBuilder().expression("(3 + 1)! * 6").operator(factorial).build();
        assert expr.evaluate().equals(new BigDecimal(144));
    }
}
