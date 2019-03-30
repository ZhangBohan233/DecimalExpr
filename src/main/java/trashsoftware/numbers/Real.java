package trashsoftware.numbers;

import java.math.BigDecimal;

public interface Real extends Comparable<Real> {

    Real add(Real val);

    Real subtract(Real val);

    Real multiply(Real val);

    Real divide(Real val);

    Real modulo(Real val);

    Real power(Real val);

    Real sqrt();

    Real negate();

    Real abs();

    Real floor();

    Real ceiling();

    int signum();

    BigDecimal toDecimal();

    boolean isRational();

    @Override
    default int compareTo(Real o) {
        Real result = this.subtract(o);
        return result.signum();
    }
}
