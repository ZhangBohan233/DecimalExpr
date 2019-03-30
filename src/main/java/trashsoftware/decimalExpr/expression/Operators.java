package trashsoftware.decimalExpr.expression;

import trashsoftware.numbers.Real;

@SuppressWarnings("WeakerAccess")
public class Operators {

    public final static int PRECEDENCE_ADDITION = 10;
    public final static int PRECEDENCE_SUBTRACTION = 10;
    public final static int PRECEDENCE_NEGATION = 11;
    public final static int PRECEDENCE_POSITIVE_SIGN = 11;
    public final static int PRECEDENCE_MULTIPLICATION = 20;
    public final static int PRECEDENCE_DIVISION = 20;
    public final static int PRECEDENCE_MODULO = 20;
    public final static int PRECEDENCE_POWER = 30;

    private final static String[] POSSIBLE_OPERATORS = {"+", "-", "*", "/", "\\", "%", "^", "$", "#", "@", "!", "&",
            "?", "<", ">", "|"};

    public static boolean isPossibleOperator(String s) {
        for (String op: POSSIBLE_OPERATORS) {
            if (s.equals(op)) return true;
        }
        return false;
    }

    public final static Operator ADDITION = new BinaryOperator("+", PRECEDENCE_ADDITION) {
        @Override
        public Real eval(Real left, Real right) {
            return left.add(right);
        }
    };

    public final static Operator SUBTRACTION = new BinaryOperator("-", PRECEDENCE_SUBTRACTION) {
        @Override
        public Real eval(Real left, Real right) {
            return left.subtract(right);
        }
    };

    public final static Operator MULTIPLICATION = new BinaryOperator("*", PRECEDENCE_MULTIPLICATION) {
        @Override
        public Real eval(Real left, Real right) {
            return left.multiply(right);
        }
    };

    public final static Operator DIVISION = new BinaryOperator("/", PRECEDENCE_DIVISION) {
        @Override
        public Real eval(Real left, Real right) {
            return left.divide(right);
        }
    };

    public final static Operator MODULO = new BinaryOperator("%", PRECEDENCE_MODULO) {
        @Override
        public Real eval(Real left, Real right) {
            return left.modulo(right);
        }
    };

    public final static Operator POWER = new BinaryOperator("^", PRECEDENCE_POWER) {
        @Override
        public Real eval(Real left, Real right) {
            return left.power(right);
        }
    };

    public final static Operator NEGATION =
            new UnaryOperator("-", PRECEDENCE_NEGATION, false) {
        @Override
        public Real eval(Real number) {
            return number.negate();
        }
    };

    public final static Operator POSITIVE_SIGN =
            new UnaryOperator("+", PRECEDENCE_POSITIVE_SIGN, false) {
        @Override
        public Real eval(Real number) {
            return number;
        }
    };
}
