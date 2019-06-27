package trashsoftware.decimalExpr;

import org.junit.jupiter.api.Test;
import trashsoftware.decimalExpr.expression.Function;
import trashsoftware.decimalExpr.expression.Operator;
import trashsoftware.decimalExpr.expression.UnaryOperator;
import trashsoftware.numbers.Irrational;
import trashsoftware.numbers.Rational;
import trashsoftware.numbers.Real;


class UnitTests {

    private static Operator weakNot = new UnaryOperator("!", 1, false) {
        @Override
        public Real eval(Real number) {
            return number.negate();
        }
    };

    private static Operator factorial = new UnaryOperator("!", 100, true) {
        @Override
        public Real eval(Real number) {
            Real result = Rational.ONE;
            Real increment = Rational.ONE;
            for (Real i = Rational.ONE; i.compareTo(number) <= 0; i = i.add(increment)) {
                result = result.multiply(i);
            }
            return result;
        }
    };

    private static Function exp2 = new Function("exp2", 1) {
        @Override
        public Real eval(Real... numbers) {
            return Irrational.E.power(numbers[0]);
        }
    };

    @Test
    void testSingleNumber() {
        DecimalExpr expr = new DecimalExprBuilder("2").build();
        assert expr.evaluate().equals(Rational.valueOf(2));
    }

    @Test
    void testSingleVariable() {
        DecimalExpr expr = new DecimalExprBuilder("x")
                .variable("x")
                .build()
                .setVariable("x", Rational.valueOf(5));
        assert expr.evaluate().equals(Rational.valueOf(5));
    }

    @Test
    void testParenthesis() {
        DecimalExpr expr = new DecimalExprBuilder("4*3+2*(1+2*(1+1))-1")
                .build();
        assert expr.evaluate().equals(Rational.valueOf(21));
    }

    @Test
    void testCustomUnaryOperatorRight() {
        DecimalExpr expr = new DecimalExprBuilder("!2").operator(weakNot).build();
        assert expr.evaluate().equals(Rational.valueOf(-2));
    }

    @Test
    void testCustomUnaryOperatorRightWithNumber() {
        DecimalExpr expr = new DecimalExprBuilder("!2 + 3").operator(weakNot).build();
        assert expr.evaluate().equals(Rational.valueOf(-5));
    }

    @Test
    void testCustomUnaryOperatorLeft() {
        DecimalExpr expr = new DecimalExprBuilder("3!").operator(factorial).build();
        assert expr.evaluate().equals(Rational.valueOf(6));
    }

    @Test
    void testCustomUnaryOperatorLeftWithSymbol() {
        DecimalExpr expr = new DecimalExprBuilder("3! * 6").operator(factorial).build();
        assert expr.evaluate().equals(Rational.valueOf(36));
    }

    @Test
    void testCustomUnaryOperatorLeftWithSymbolAndPar() {
        DecimalExpr expr = new DecimalExprBuilder("(3 + 1)! * 6").operator(factorial).build();
        assert expr.evaluate().equals(Rational.valueOf(144));
    }

    @Test
    void testCeil() {
        DecimalExpr expr = new DecimalExprBuilder("ceil(2.33333)").build();
        assert expr.evaluate().equals(Rational.valueOf(3));
    }

    @Test
    void testFloor() {
        DecimalExpr expr = new DecimalExprBuilder("floor(2.33333)").build();
        assert expr.evaluate().equals(Rational.valueOf(2));
    }

    @Test
    void testExp() {
        DecimalExpr expr = new DecimalExprBuilder("exp2(x)")
                .variable("x")
                .function(exp2)
                .build()
                .setVariable("x", Rational.valueOf(2));
        Real result = expr.evaluate();
        assert result.lessThan(Rational.fromFraction(15, 2)) && result.greaterThan(Rational.valueOf(7));
    }

    @Test
    void testRepeatingRational() {
        DecimalExpr expr = new DecimalExprBuilder("3.{3}").build();
        assert expr.evaluate().equals(Rational.fromFraction(10, 3));
    }

    @Test
    void testFractionRational() {
        DecimalExpr expr = new DecimalExprBuilder("1/3").build();
        assert expr.evaluate().equals(Rational.fromFraction(1, 3));
    }

    @Test
    void testRationalIntegerPower() {
        DecimalExpr expr = new DecimalExprBuilder("3^(-2)").build();
        assert expr.evaluate().equals(Rational.fromFraction(1, 9));
    }

    @Test
    void testParenthesises() {
        DecimalExpr expr = new DecimalExprBuilder("(1+2)").build();
        assert Rational.valueOf(3).equals(expr.evaluate());
    }

    @Test
    void testMacro() {
        DecimalExpr expr = new DecimalExprBuilder("5 + m + x")
                .variable("x")
                .macro("m")
                .build()
                .setVariable("x", Rational.valueOf(4))
                .setMacro("m", "(x+2)");
        Real result = expr.evaluate();
        assert Rational.valueOf(15).equals(result);
        expr.setMacro("m", "1");
        assert Rational.valueOf(10).equals(expr.evaluate());
    }

    @Test
    void testDoubleEqualLong() {
        double d = Math.log(0);
        long l = 1;
//        System.out.println(d);
//        System.out.println(d == l);
    }

    @Test
    void testLogs() {
//        DecimalExpr expr = new DecimalExprBuilder("ln(e())").build();
//        Real res = expr.evaluate();
//        System.out.println(res);
    }
}
