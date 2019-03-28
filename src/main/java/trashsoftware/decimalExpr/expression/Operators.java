package trashsoftware.decimalExpr.expression;

import java.math.BigDecimal;
import java.math.RoundingMode;

public class Operators {

    public final static int PRECEDENCE_ADDITION = 50;
    public final static int PRECEDENCE_SUBTRACTION = 50;
    public final static int PRECEDENCE_MULTIPLICATION = 100;
    public final static int PRECEDENCE_DIVISION = 100;
    public final static int PRECEDENCE_NEGATION = 200;


    public final static String[] POSSIBLE_OPERATORS = {"+", "-", "*", "/", "\\", "%", "^", "$", "#", "@", "!", "&",
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

    public static Operator NEGATION = new UnaryOperator("-", PRECEDENCE_NEGATION, true) {
        @Override
        public BigDecimal eval(BigDecimal number) {
            return number.negate();
        }
    };
}
