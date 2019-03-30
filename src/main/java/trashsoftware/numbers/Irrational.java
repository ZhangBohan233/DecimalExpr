package trashsoftware.numbers;

import java.math.BigDecimal;
import java.math.MathContext;
import java.math.RoundingMode;

public class Irrational implements Real {

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
        return Irrational.valueOf(value.divide(val.toDecimal(), MathContext.DECIMAL128));
    }

    @Override
    public Real modulo(Real val) {
        return Irrational.valueOf(value.remainder(val.toDecimal(), MathContext.DECIMAL128));
    }

    @Override
    public Real power(Real val) {
        return null;
    }

    @Override
    public Real sqrt() {
        return Irrational.valueOf(value.sqrt(MathContext.DECIMAL128));
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
}
