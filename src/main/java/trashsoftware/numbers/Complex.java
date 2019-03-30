package trashsoftware.numbers;

import java.math.BigDecimal;
import java.math.BigInteger;

public class Complex {

    private final Real real;
    private final Real imaginary;

    private Complex(Real real, Real imaginary) {
        this.real = real;
        this.imaginary = imaginary;
    }

    public static Complex fromReal(Real realPart) {
        return new Complex(realPart, Rational.ZERO);
    }

    public static Complex createComplex(Real realPart, Real imaginaryPart) {
        return new Complex(realPart, imaginaryPart);
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

    public boolean isReal() {
        return imaginary.equals(Rational.ZERO);
    }

    public Real getReal() {
        return real;
    }

    public Real getImaginary() {
        return imaginary;
    }

    public Complex add(Complex other) {
        return new Complex(real.add(other.real), imaginary.add(other.imaginary));
    }

    public Complex subtract(Complex other) {
        return new Complex(real.subtract(other.real), imaginary.subtract(other.imaginary));
    }

    public Complex multiply(Complex other) {
        Real realPart = real.multiply(other.real).subtract(imaginary.multiply(other.imaginary));  // ac - bd
        Real imPart = imaginary.multiply(other.real).add(real.multiply(other.imaginary));  // bc + ad
        return new Complex(realPart, imPart);
    }

    public Complex divide(Complex other) {
        Real denom = other.real.power(Rational.valueOf(2)).add(other.imaginary.power(Rational.valueOf(2)));
        // c^2 + d^2
        Real realPartNum = real.multiply(other.real).add(imaginary.multiply(other.imaginary));  // ac + bd
        Real imPartNum = imaginary.multiply(other.real).subtract(real.multiply(other.imaginary));  // bc - ad
        return new Complex(realPartNum.divide(denom), imPartNum.divide(denom));
    }

    public Complex power(Complex other) {
        return null;
    }

    @Override
    public String toString() {
        if (isReal()) {
            return real.toString();
        } else {
            return real + "+" + imaginary + "i";
        }
    }
}
