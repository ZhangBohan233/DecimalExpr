package trashsoftware.decimalExpr.expression;

import trashsoftware.decimalExpr.util.Util;

import java.math.BigDecimal;
import java.math.RoundingMode;

public class Operators {

    public final static int PRECEDENCE_ADDITION = 10;
    public final static int PRECEDENCE_SUBTRACTION = 10;
    public final static int PRECEDENCE_NEGATION = 11;
    public final static int PRECEDENCE_MULTIPLICATION = 20;
    public final static int PRECEDENCE_DIVISION = 20;
    public final static int PRECEDENCE_MODULO = 20;
    public final static int PRECEDENCE_POWER = 30;

    private static String[] POSSIBLE_OPERATORS = {"+", "-", "*", "/", "\\", "%", "^", "$", "#", "@", "!", "&",
            "?", "<", ">", "|"};

    public static boolean isPossibleOperator(String s) {
        for (String op: POSSIBLE_OPERATORS) {
            if (s.equals(op)) return true;
        }
        return false;
    }

    public static Operator ADDITION = new BinaryOperator("+", PRECEDENCE_ADDITION) {
        @Override
        public BigDecimal eval(BigDecimal left, BigDecimal right) {
            return left.add(right);
        }
    };

    public static Operator SUBTRACTION = new BinaryOperator("-", PRECEDENCE_SUBTRACTION) {
        @Override
        public BigDecimal eval(BigDecimal left, BigDecimal right) {
            return left.subtract(right);
        }
    };

    public static Operator MULTIPLICATION = new BinaryOperator("*", PRECEDENCE_MULTIPLICATION) {
        @Override
        public BigDecimal eval(BigDecimal left, BigDecimal right) {
            return left.multiply(right);
        }
    };

    public static Operator DIVISION = new BinaryOperator("/", PRECEDENCE_DIVISION) {
        @Override
        public BigDecimal eval(BigDecimal left, BigDecimal right) {
            return left.divide(right, RoundingMode.HALF_UP);
        }
    };

    public static Operator MODULO = new BinaryOperator("%", PRECEDENCE_MODULO) {
        @Override
        public BigDecimal eval(BigDecimal left, BigDecimal right) {
            return left.remainder(right);
        }
    };

    public static Operator POWER = new BinaryOperator("^", PRECEDENCE_POWER) {
        @Override
        public BigDecimal eval(BigDecimal left, BigDecimal right) {
            if (!Util.isInteger(right)) throw new NumberValueException("Non-integer power is not allowed");
            return left.pow(right.intValue());
        }
    };

    public static Operator NEGATION = new UnaryOperator("-", PRECEDENCE_NEGATION, false) {
        @Override
        public BigDecimal eval(BigDecimal number) {
            return number.negate();
        }
    };
}
