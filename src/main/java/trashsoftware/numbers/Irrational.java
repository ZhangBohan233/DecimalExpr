package trashsoftware.numbers;

import java.math.BigDecimal;
import java.math.MathContext;
import java.math.RoundingMode;

public class Irrational implements Real {

    /**
     * The Euler's number.
     */
    public final static Irrational E = Irrational.valueOf("2.718281828459045235360287471352662497757247093699959" +
            "5749669676277240766303535475945713821785251664274");

    /**
     * The pi.
     */
    public final static Irrational PI = Irrational.valueOf("3.14159265358979323846264338327950288419716939937510" +
            "5820974944592307816406286208998628034825342117068");

    final static MathContext DEFAULT_CONTEXT = new MathContext(64, RoundingMode.HALF_UP);

    private final BigDecimal value;

    private Irrational(BigDecimal value) {
        this.value = value;
    }

    public static Irrational valueOf(double value) {
        return new Irrational(BigDecimal.valueOf(value));
    }

    public static Irrational valueOf(BigDecimal value) {
        return new Irrational(value);
    }

    public static Irrational valueOf(String value) {
        return new Irrational(new BigDecimal(value));
    }

    @Override
    public String toString() {
        return value.toString();
    }

    @Override
    public Real add(Real val) {
        return Irrational.valueOf(value.add(val.toDecimal()));
    }

    @Override
    public Real subtract(Real val) {
        return Irrational.valueOf(value.subtract(val.toDecimal()));
    }

    @Override
    public Real multiply(Real val) {
        return Irrational.valueOf(value.multiply(val.toDecimal()));
    }

    @Override
    public Real divide(Real val) {
        return Irrational.valueOf(value.divide(val.toDecimal(), DEFAULT_CONTEXT));
    }

    @Override
    public Real modulo(Real val) {
        return Irrational.valueOf(value.remainder(val.toDecimal(), DEFAULT_CONTEXT));
    }

    @Override
    public Real power(Real val) {
        if (val.isRational() && ((Rational) val).isInteger()) {
            Rational p = (Rational) val;
            return Irrational.valueOf(value.pow(p.toDecimal().intValueExact(), DEFAULT_CONTEXT));
        } else {
            System.err.println("Irrational powers a non-integer number does not guarantee precision");
            return Irrational.valueOf(Math.pow(value.doubleValue(), val.toDecimal().doubleValue()));
        }
    }

    @Override
    public Real sqrt() {
        return Irrational.valueOf(value.sqrt(DEFAULT_CONTEXT));
    }

    @Override
    public Real negate() {
        return Irrational.valueOf(value.negate());
    }

    @Override
    public BigDecimal toDecimal() {
        return value;
    }

    @Override
    public boolean isRational() {
        return false;
    }

    @Override
    public Real abs() {
        return Irrational.valueOf(value.abs());
    }

    @Override
    public int signum() {
        return value.signum();
    }

    @Override
    public Real ceiling() {
        return Rational.valueOf(value.setScale(0, RoundingMode.CEILING).toBigIntegerExact());
    }

    @Override
    public Real floor() {
        return Rational.valueOf(value.setScale(0, RoundingMode.FLOOR).toBigIntegerExact());
    }

    @Override
    public Real copy() {
        return Irrational.valueOf(value);
    }
}
