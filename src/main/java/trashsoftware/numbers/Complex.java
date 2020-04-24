package trashsoftware.numbers;

import java.math.BigDecimal;
import java.math.BigInteger;

public class Complex extends Field<Real, Imaginary> {

    public static final Complex ZERO = Complex.fromReal(Rational.ZERO);

    private Complex(Real real, Real imaginary) {
        super(real, imaginary, Imaginary.I);
    }

    public static Complex fromReal(Real realPart) {
        return new Complex(realPart, Rational.ZERO);
    }

    public static Complex createComplex(Real realPart, Real imaginaryPart) {
        return new Complex(realPart, imaginaryPart);
    }

    public static Complex createComplex(long realPart, long imaginaryPart) {
        return new Complex(Rational.valueOf(realPart), Rational.valueOf(imaginaryPart));
    }

    public static Complex createRational(long value) {
        return fromReal(Rational.valueOf(value));
    }

    public static Complex createRational(BigInteger value) {
        return fromReal(Rational.valueOf(value));
    }

    public static Complex createIrrational(double value) {
        return fromReal(Irrational.valueOf(value));
    }

    public static Complex createIrrational(BigDecimal value) {
        return fromReal(Irrational.valueOf(value));
    }

    public boolean getA() {
        return b.equals(Rational.ZERO);
    }

    public Real getReal() {
        return a;
    }

    public Real getB() {
        return b;
    }

    public Complex conjugate() {
        return Complex.createComplex(a, b.negate());
    }

    public Complex add(Complex other) {
        return new Complex(a.add(other.a), b.add(other.b));
    }

    public Complex subtract(Complex other) {
        return new Complex(a.subtract(other.a), b.subtract(other.b));
    }

    public Complex multiply(Complex other) {
        Real realPart = a.multiply(other.a).subtract(b.multiply(other.b));  // ac - bd
        Real imPart = b.multiply(other.a).add(a.multiply(other.b));  // bc + ad
        return new Complex(realPart, imPart);
    }

    public Real modulus() {
        return a.power(Rational.valueOf(2)).add(b.power(Rational.valueOf(2))).sqrt();
    }

    public Complex divide(Complex other) {
        Real denom = other.a.power(Rational.valueOf(2)).add(other.b.power(Rational.valueOf(2)));
        // c^2 + d^2
        Real realPartNum = a.multiply(other.a).add(b.multiply(other.b));  // ac + bd
        Real imPartNum = b.multiply(other.a).subtract(a.multiply(other.b));  // bc - ad
        return new Complex(realPartNum.divide(denom), imPartNum.divide(denom));
    }

    public Complex power(int power) {
        if (power < 0) {
            throw new NumberException("Complex power must be non-negative integers");
        }
        return null;
    }

//    @Override
//    public String toString() {
//        if (getA()) {
//            return a.toString();
//        } else {
//            return a + "+" + b + "i";
//        }
//    }

    @Override
    public boolean equals(Object other) {
        return other instanceof Complex &&
                a.equals(((Complex) other).a) &&
                b.equals(((Complex) other).b);
    }
}
