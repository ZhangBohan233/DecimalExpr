package trashsoftware.numbers;

import java.math.BigDecimal;
import java.math.BigInteger;
import java.math.MathContext;
import java.text.DecimalFormat;
import java.text.NumberFormat;
import java.util.HashMap;
import java.util.Map;

public class Rational implements Real, FieldAble {

    public final static Rational ZERO = valueOf(0);
    public final static Rational ONE = valueOf(1);
    public final static Rational TWO = valueOf(2);
    private static final DecimalFormat DOUBLE_FORMATTER = (DecimalFormat) NumberFormat.getInstance();

    public final static char FRONT_REPEAT_CHAR = '{';
    public final static char BACK_REPEAT_CHAR = '}';

    /**
     * The denominator of this rational number.
     * <p>
     * This value will always be a positive integer.
     */
    private final BigInteger denominator;

    /**
     * The numerator of this rational number.
     */
    private final BigInteger numerator;

    protected Rational(Rational value) {
        numerator = value.numerator;
        denominator = value.denominator;
    }

    private Rational(BigInteger value) {
        numerator = value;
        denominator = BigInteger.ONE;
    }

    private Rational(BigInteger numerator, BigInteger denominator) {
        if (denominator.equals(BigInteger.ZERO)) throw new ZeroDivisionException();
        BigInteger[] simplified = simplify(numerator, denominator);
        this.numerator = simplified[0];
        this.denominator = simplified[1];
    }

    private Rational(String decimal) {
        decimal = decimal.replace(",", "");
        int pointIndex = decimal.indexOf('.');
        if (pointIndex == -1) {  // it is an integer
            numerator = new BigInteger(decimal);
            denominator = BigInteger.ONE;
        } else if (pointIndex == decimal.length() - 1) {
            numerator = new BigInteger(decimal.substring(0, decimal.length() - 1));
            denominator = BigInteger.ONE;
        } else {
            int frontRecurIndex = decimal.indexOf(FRONT_REPEAT_CHAR);
            int backRecurIndex = decimal.indexOf(BACK_REPEAT_CHAR);
            if (frontRecurIndex == -1) {
                if (backRecurIndex == -1) {
                    int decimalPlaceCount = decimal.length() - pointIndex - 1;
                    BigInteger denom = BigInteger.TEN.pow(decimalPlaceCount);
                    String bigNumber = decimal.substring(0, pointIndex) + decimal.substring(pointIndex + 1);
                    BigInteger num = new BigInteger(bigNumber);
                    BigInteger[] simplified = simplify(num, denom);
                    numerator = simplified[0];
                    denominator = simplified[1];
                } else {
                    throw new NumberFormatException("Recurring interval not closed");
                }
            } else {
                if (backRecurIndex == -1) {
                    throw new NumberFormatException("Recurring interval not closed");
                } else {
                    String repeatPart = decimal.substring(frontRecurIndex + 1, backRecurIndex);
                    int repeatLength = repeatPart.length();
                    int nonRepeatDecimalPlaces = frontRecurIndex - pointIndex - 1;
                    String decimalNoRepeat = decimal.substring(0, frontRecurIndex) + repeatPart;
                    String bigPart = appendRepeatPart(decimalNoRepeat, repeatPart,
                            repeatLength + nonRepeatDecimalPlaces);
                    String smallPart = appendRepeatPart(decimalNoRepeat, repeatPart, nonRepeatDecimalPlaces);
                    BigDecimal bigShift = BigDecimal.TEN.pow(repeatLength + nonRepeatDecimalPlaces);
                    BigDecimal smallShift = BigDecimal.TEN.pow(nonRepeatDecimalPlaces);
                    BigDecimal bigNumber = new BigDecimal(bigPart).multiply(bigShift);
                    BigDecimal smallNumber = new BigDecimal(smallPart).multiply(smallShift);
                    BigInteger num = bigNumber.subtract(smallNumber).toBigIntegerExact();
                    BigInteger denom = bigShift.subtract(smallShift).toBigIntegerExact();
                    BigInteger[] simplified = simplify(num, denom);
                    numerator = simplified[0];
                    denominator = simplified[1];
                }
            }
        }
    }

    private static String appendRepeatPart(String original, String repeat, int appendCount) {
        StringBuilder stringBuilder = new StringBuilder(original);
        for (int i = 0; i < appendCount; i++) {
            char c = repeat.charAt(i % repeat.length());
            stringBuilder.append(c);
        }
        return stringBuilder.toString();
    }

    public static Rational valueOf(long value) {
        return new Rational(BigInteger.valueOf(value));
    }

    public static Rational fromDouble(double value) {
        return new Rational(DOUBLE_FORMATTER.format(value));
    }

    public static Rational fromFraction(long numerator, long denominator) {
        return new Rational(BigInteger.valueOf(numerator), BigInteger.valueOf(denominator));
    }

    /**
     * Parses a {@code Rational} from a {@code String} representation of a decimal number.
     * <p>
     * The string must represent a decimal rational number.
     * A repeating decimal can be express an 'x.xx{rrr}', where 'rrr' is the repeating part. For example,
     * "0.{142857}" is the decimal form of 1/7.
     *
     * @param decimal the decimal representation of a rational number
     * @return the {@code Rational} instance
     */
    public static Rational parseDecimal(String decimal) {
        return new Rational(decimal);
    }

    /**
     * Parse a rational number from a {@code String} representation of a rational, which is, m/n, for integer m and n.
     *
     * @param rational the {@code String} of number
     * @return the {@code Rational} instance
     */
    @SuppressWarnings("WeakerAccess")
    public static Rational parseRational(String rational) {
        int lineIndex = rational.indexOf('/');
        if (lineIndex == -1) throw new NumberFormatException("Not a rational number");
        BigInteger num = new BigInteger(rational.substring(0, lineIndex));
        BigInteger denom = new BigInteger(rational.substring(lineIndex + 1));
        return new Rational(num, denom);
    }

    public static Rational valueOf(BigInteger value) {
        return new Rational(value);
    }

    @SuppressWarnings("WeakerAccess")
    public static Rational fromFraction(BigInteger numerator, BigInteger denominator) {
        return new Rational(numerator, denominator);
    }

    public BigInteger getDenominator() {
        return denominator;
    }

    public BigInteger getNumerator() {
        return numerator;
    }

    private BigDecimal ratio(MathContext roundingMode) {
        return new BigDecimal(numerator).divide(new BigDecimal(denominator), roundingMode);
    }

    @Override
    public boolean equals(Object obj) {
        if (obj instanceof Rational) {
            return numerator.equals(((Rational) obj).numerator)
                    && denominator.equals(((Rational) obj).denominator);
        } else {
            return false;
        }
    }

    @Override
    public String toString() {
//        return toDecimal().toString();
//        return toStringDecimal();
        return denominator.equals(BigInteger.ONE) ? numerator.toString() : numerator + "/" + denominator;
    }

    public String toStringFractional() {
        return denominator.equals(BigInteger.ONE) ? numerator.toString() : numerator + "/" + denominator;
    }

    @SuppressWarnings("WeakerAccess")
    public String toStringDecimal() {
        if (numerator.equals(BigInteger.ZERO)) return "0";

        StringBuilder resultBuilder = new StringBuilder();
        if (signum() < 0) resultBuilder.append('-');  // negative

        BigInteger denom = denominator.abs();
        BigInteger num = numerator.abs();
        BigInteger[] dr = num.divideAndRemainder(denom);  // 'dr' always contains the remainder times ten
        dr[1] = dr[1].multiply(BigInteger.TEN);
        resultBuilder.append(dr[0]);
        if (dr[1].equals(BigInteger.ZERO)) return resultBuilder.toString();

        resultBuilder.append('.');
        Map<BigInteger, Integer> map = new HashMap<>();
        map.put(dr[1], resultBuilder.length());

        while (!(dr = dr[1].divideAndRemainder(denom))[1].equals(BigInteger.ZERO)) {
            resultBuilder.append(dr[0]);
            dr[1] = dr[1].multiply(BigInteger.TEN);
            Integer beg = map.get(dr[1]);
            if (beg != null) {
                String front = resultBuilder.substring(0, beg);
                String back = resultBuilder.substring(beg);
                return front + FRONT_REPEAT_CHAR + back + BACK_REPEAT_CHAR;
            } else {
                map.put(dr[1], resultBuilder.length());
            }
        }
        if (!dr[0].equals(BigInteger.ZERO)) resultBuilder.append(dr[0]);
        return resultBuilder.toString();
    }


    /**
     * Returns the greatest common divisor of two {@code BigInteger}s.
     * <p>
     * This method performs the classic Euclidean Algorithm.
     *
     * @param x a number
     * @param y another number
     * @return the greatest common divisor
     */
    @SuppressWarnings("WeakerAccess")
    public static BigInteger gcd(BigInteger x, BigInteger y) {
        BigInteger q, b;
        if (x.abs().compareTo(y.abs()) < 0) {  // a < b
            q = y;
            b = x;
        } else {
            q = x;
            b = y;
        }
        if (b.equals(BigInteger.ZERO)) return q;
        BigInteger[] dr;  // divisor and remainder
        while (!(dr = q.divideAndRemainder(b))[1].equals(BigInteger.ZERO)) {
            q = b;
            b = dr[1];
        }
        return b;
    }

    /**
     * Returns the least common multiplier of two {@code BigInteger}s.
     * <p>
     * This method uses the fact that lcm(a, b) = ab / gcd(a, b)
     *
     * @param a a number
     * @param b another number
     * @return the least common multiplier
     */
    @SuppressWarnings("WeakerAccess")
    public static BigInteger lcm(BigInteger a, BigInteger b) {
        return a.multiply(b).divide(gcd(a, b));
    }

    public Rational copy() {
        return Rational.fromFraction(numerator, denominator);
    }

    private Rational negateRational() {
        return Rational.fromFraction(numerator.negate(), denominator);
    }

    private Rational absRational() {
        if (signum() < 0) return negateRational();
        else return copy();
    }

    public boolean isInteger() {
        return denominator.equals(BigInteger.ONE);
    }

    /**
     * This method simplifies the numerator and denominator.
     * <p>
     * After this simplification, the gcd(numerator, denominator) = 1, and the denominator will be a natural number.
     *
     * @param numerator   the numerator
     * @param denominator the denominator
     * @return the array of numerator, denominator
     */
    private static BigInteger[] simplify(BigInteger numerator, BigInteger denominator) {
        int sig = numerator.signum() * denominator.signum();
        BigInteger gcd = gcd(numerator, denominator);
        if (sig < 0) {
            return new BigInteger[]{numerator.divide(gcd).abs().negate(), denominator.divide(gcd).abs()};
        } else {
            return new BigInteger[]{numerator.divide(gcd).abs(), denominator.divide(gcd).abs()};
        }
    }

    private Rational addRational(Rational val) {
        BigInteger denom = denominator.multiply(val.denominator);
        BigInteger num = numerator.multiply(val.denominator).add(val.numerator.multiply(denominator));
        return new Rational(num, denom);
    }

    private Rational subtractRational(Rational b) {
        Rational negativeB = b.negateRational();
        return addRational(negativeB);
    }

    private Rational multiplyRational(Rational multiplier) {
        BigInteger denom = denominator.multiply(multiplier.denominator);
        BigInteger num = numerator.multiply(multiplier.numerator);
        return new Rational(num, denom);
    }

    private Rational divideRational(Rational divisor) {
        BigInteger denom = denominator.multiply(divisor.numerator);
        BigInteger num = numerator.multiply(divisor.denominator);
        return new Rational(num, denom);
    }

    private Rational moduloRational(Rational divisor) {
        BigInteger denom = denominator.multiply(divisor.denominator);
        BigInteger thisNum = numerator.multiply(divisor.denominator);
        BigInteger otherNum = divisor.numerator.multiply(denominator);
        BigInteger rem = thisNum.remainder(otherNum);
        return new Rational(rem, denom);
    }

    @SuppressWarnings("WeakerAccess")
    public Rational inverse() {
        return new Rational(denominator, numerator);
    }

    @Override
    public Real add(Real val) {
        if (val.isRational()) {
            return addRational((Rational) val);
        } else {
            assert val instanceof Irrational;
            return Irrational.valueOf(toDecimal()).add(val);
        }
    }

    @Override
    public Real subtract(Real val) {
        if (val.isRational()) {
            return subtractRational((Rational) val);
        } else {
            assert val instanceof Irrational;
            return Irrational.valueOf(toDecimal()).subtract(val);
        }
    }

    @Override
    public Real multiply(Real val) {
        if (val.isRational()) {
            return multiplyRational((Rational) val);
        } else {
            assert val instanceof Irrational;
            return Irrational.valueOf(toDecimal()).multiply(val);
        }
    }

    @Override
    public Real divide(Real val) {
        if (val.isRational()) {
            return divideRational((Rational) val);
        } else {
            assert val instanceof Irrational;
            return Irrational.valueOf(toDecimal()).divide(val);
        }
    }

    @Override
    public Real modulo(Real val) {
        if (val.isRational()) {
            return moduloRational((Rational) val);
        } else {
            assert val instanceof Irrational;
            return Irrational.valueOf(toDecimal()).modulo(val);
        }
    }

    @Override
    public Real sqrt() {
        if (signum() < 0) {
            throw new ImaginaryInRealException();
        }
        BigInteger[] numSqrt = numerator.sqrtAndRemainder();
        BigInteger[] denomSqrt = denominator.sqrtAndRemainder();
        if (numSqrt[1].equals(BigInteger.ZERO) && denomSqrt[1].equals(BigInteger.ZERO)) {  // both are perfect squares
            return new Rational(numSqrt[0], denomSqrt[0]);
        } else {
            return Irrational.valueOf(toDecimal().sqrt(Irrational.DEFAULT_CONTEXT));
        }
    }

    @Override
    public Real root(Rational power) {
        if (power.isInteger() && power.signum() > 0) {
            // TODO: Uncertainty
            if (isInteger()) {
                BigInteger[] rootAndRemainder = nThRootAndRemainder(numerator, power.numerator);
                if (rootAndRemainder[1].equals(BigInteger.ZERO)) {
                    return Rational.valueOf(rootAndRemainder[0]);
                }
            } else {
                BigInteger[] denomRootRemainder = nThRootAndRemainder(denominator, power.numerator);
                BigInteger[] numRootRemainder = nThRootAndRemainder(numerator, power.numerator);
                if (denomRootRemainder[1].equals(BigInteger.ZERO) && numRootRemainder[1].equals(BigInteger.ZERO)) {
                    return Rational.fromFraction(numRootRemainder[0], denomRootRemainder[0]);
                }
            }
            return Irrational.valueOf(Math.pow(doubleValue(), power.inverse().doubleValue()));
        } else {
            throw new NumberException("Power must be positive integer");
        }
    }

    static BigInteger[] nThRootAndRemainder(BigInteger number, BigInteger n) {
        int intN = n.intValue();
        BigInteger root = BigInteger.ONE;
        BigInteger pow;
        while ((pow = root.pow(intN)).compareTo(number) < 0) {
            root = root.multiply(BigInteger.TWO);
        }
        if (pow.equals(number)) {
            return new BigInteger[]{root, BigInteger.ZERO};
        }

        BigInteger left = root.divide(BigInteger.TWO);
        BigInteger right = root;
        while (right.compareTo(left.add(BigInteger.ONE)) > 0) {
            BigInteger mid = right.subtract(left).divide(BigInteger.TWO).add(left);
            int cmp = mid.pow(intN).compareTo(number);
            if (cmp > 0) {
                right = mid;
            } else if (cmp < 0) {
                left = mid;
            } else {
                return new BigInteger[]{mid, BigInteger.ZERO};
            }
        }
        return new BigInteger[]{left, number.subtract(left.pow(intN))};
    }

    @Override
    public Real power(Real val) {
        if (val.isRational()) {
            Rational p = (Rational) val;
            if (p.isInteger()) {
                Rational pow = Rational.ONE;
                for (int i = 0; i < p.numerator.abs().longValueExact(); i++) {
                    pow = pow.multiplyRational(this);
                }
                if (p.signum() < 0) {
                    return pow.inverse();
                } else {
                    return pow;
                }
            } else {
                Real inner = power(Rational.valueOf(p.numerator));
                return inner.root(Rational.valueOf(p.denominator));
            }
        } else {
            return Irrational.valueOf(toDecimal()).power(val);
        }
    }

    @Override
    public Real negate() {
        return negateRational();
    }

    @Override
    public BigDecimal toDecimal() {
        return ratio(Irrational.DEFAULT_CONTEXT);
    }

    @Override
    public boolean isRational() {
        return true;
    }

    @Override
    public Real abs() {
        return absRational();
    }

    @Override
    public int signum() {
        return numerator.signum();
    }

    @Override
    public Real ceiling() {
        return denominator.equals(BigInteger.ONE)
                ? copy()
                : Rational.valueOf(numerator.divide(denominator).add(BigInteger.ONE));
    }

    @Override
    public Real floor() {
        return denominator.equals(BigInteger.ONE)
                ? copy()
                : Rational.valueOf(numerator.divide(denominator));
    }
}
