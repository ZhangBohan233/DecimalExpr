package trashsoftware.numbers;

import java.math.BigDecimal;

/**
 * The interface of the mathematical real number.
 * <p>
 * This interface will only have two implementation. If the number can be express as the ratio of two integers,
 * it is a {@code Rational}. Otherwise it is an {@code Irrational}.
 *
 * @author zbh
 * @see java.lang.Comparable
 * @see Rational
 * @see Irrational
 * @since 0.2
 */
public interface Real extends Comparable<Real> {

    /**
     * Returns a new instance of a subclass of {@code Real}, consisting of the sum of this instance and
     * the {@code Real} {@code val}
     *
     * @param val the adder
     * @return the sum
     */
    Real add(Real val);

    /**
     * Returns a new instance of a subclass of {@code Real}, consisting of the difference of this instance and
     * the {@code Real} {@code val}
     *
     * @param val the subtractor
     * @return the difference
     */
    Real subtract(Real val);

    Real multiply(Real val);

    Real divide(Real val);

    Real modulo(Real val);

    Real power(Real val);

    /**
     * Returns the square root of this.
     *
     * @return the square root of this
     */
    Real sqrt();

    /**
     * Returns the {@code power}'s root of this.
     * <p>
     * {@code power} must be positive integer.
     *
     * @param power the power
     * @return the {@code power}'s root of this
     */
    Real root(Rational power);

    Real negate();

    /**
     * Returns the absolute value of this.
     * <p>
     * If the value of this is less than 0, returns the negation of this. Otherwise, return a copy of this.
     *
     * @return the absolute value
     */
    Real abs();

    Real floor();

    Real ceiling();

    /**
     * Returns an integer representing the sign of this.
     *
     * @return {@code -1} if this number is negative, {@code 0} if 0, {@code 1} if positive
     */
    int signum();

    BigDecimal toDecimal();

    boolean isRational();

    Real copy();

    @Override
    default int compareTo(Real o) {
        Real result = this.subtract(o);
        return result.signum();
    }

    default boolean greaterThan(Real other) {
        Real subtract = this.subtract(other);
        return subtract.signum() > 0;
    }

    default boolean lessThan(Real other) {
        Real subtract = this.subtract(other);
        return subtract.signum() < 0;
    }

    default double doubleValue() {
        return toDecimal().doubleValue();
    }
}
