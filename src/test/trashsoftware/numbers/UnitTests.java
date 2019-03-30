package trashsoftware.numbers;

import org.junit.jupiter.api.Test;

import java.math.BigInteger;

class UnitTests {

    @Test
    void testGcd() {
        BigInteger x = BigInteger.valueOf(398);
        BigInteger y = BigInteger.valueOf(298);
        BigInteger gcd = Rational.gcd(x, y);
        assert BigInteger.valueOf(2).equals(gcd);
    }

    @Test
    void testLcm() {
        BigInteger x = BigInteger.valueOf(35);
        BigInteger y = BigInteger.valueOf(25);
        BigInteger lcm = Rational.lcm(x, y);
        assert BigInteger.valueOf(175).equals(lcm);
    }

    @Test
    void testRationalAdd() {
        Rational a = Rational.valueOf(6);
        Rational b = Rational.fromFraction(2, 7);
        Real c = a.add(b);
        assert c.equals(Rational.fromFraction(44, 7));
    }

    @Test
    void testRationalSubtract() {

    }

    @Test
    void testRationalMultiply() {
        Rational a = Rational.fromFraction(3, 4);
        Rational b = Rational.fromFraction(8, 3);
        Real c = a.multiply(b);
        System.out.println(c);
    }

    @Test
    void testDoubleRemainder() {
        double a = 10.0/3.0;
        double b = 3.0/2.0;
        double c = a % b;
        System.out.println(c);
    }

    @Test
    void testCeil() {
        double c = -3.5;
        System.out.println(Math.ceil(c));
    }

    @Test
    void testRationalRemainder() {
        Rational a = Rational.fromFraction(10, 3);
        Rational b = Rational.fromFraction(3, 2);
        Real c = a.modulo(b);
        assert c.equals(Rational.parseRational("1/3"));
    }

    @Test
    void testRationalDivide() {
        Rational a = Rational.fromFraction(3, 2);
        Rational b = Rational.fromFraction(2, 1);
        Real c = a.divide(b);
        System.out.println(c);
    }

    @Test
    void testFromFiniteDecimal() {
        Rational a = Rational.parseDecimal("0.32");
        System.out.println(a);
    }

    @Test
    void testFromPointAtEnd() {
        Rational a = Rational.parseDecimal("0.");
        System.out.println(a);
    }

    @Test
    void testFromRepeatDecimalMultipleDigitAfterZero() {
        Rational a = Rational.parseDecimal("0.{142857}");
        assert Rational.fromFraction(1, 7).equals(a);  // 1/7 == a
    }

    @Test
    void testFromRepeatDecimalMultipleDigitComplicated() {
        Rational a = Rational.parseDecimal("0.{23}");
        System.out.println(a);
    }

    @Test
    void testRationalToDecimalStringRepeat() {
        Rational a = Rational.fromFraction(2, 7);
        System.out.println(a);
        System.out.println(a.toStringDecimal());
    }

    @Test
    void testRationalZero() {

    }

    @Test
    void testComplexAddTwoReal() {
        Complex a = Complex.fromReal(Rational.fromFraction(3, 2));
        Complex b = Complex.fromReal(Rational.fromFraction(2, 3));
        Complex c = a.add(b);
        System.out.println(c);
    }

    @Test
    void testComplexRealAddComplex() {
        Complex a = Complex.fromReal(Rational.fromFraction(3, 2));
        Complex b = Complex.createComplex(Rational.valueOf(1), Rational.valueOf(2));
        Complex c = a.add(b);
        System.out.println(c);
    }
}
