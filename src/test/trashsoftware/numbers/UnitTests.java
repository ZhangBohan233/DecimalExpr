package trashsoftware.numbers;

import org.junit.jupiter.api.Test;

import java.math.BigInteger;
import java.util.Arrays;

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
        double a = 10.0 / 3.0;
        double b = 3.0 / 2.0;
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
        assert Rational.fromFraction(3, 4).equals(c);
    }

    @Test
    void testFromFiniteDecimal() {
        Rational a = Rational.parseDecimal("0.32");
        assert Rational.fromFraction(8, 25).equals(a);
    }

    @Test
    void testFromPointAtEnd() {
        Rational a = Rational.parseDecimal("0.");
        assert Rational.ZERO.equals(a);
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

    @Test
    void testRationalNegative() {
        Rational a = Rational.fromFraction(2, -3);
        assert Rational.fromFraction(-2, 3).equals(a);
    }

    @Test
    void testIrrationalRoot() {
        Real r = Irrational.valueOf(2.3213154352346).root(Rational.valueOf(2));
        System.out.println(r);
    }

    @Test
    void testRootAndRemainder() {
        BigInteger[] pres = Rational.nThRootAndRemainder(BigInteger.valueOf(216), BigInteger.valueOf(3));
        assert pres[0].intValue() == 6 && pres[1].intValue() == 0;
    }

    @Test
    void testFractionPower() {
        Rational r = Rational.valueOf(3);
        Real p = r.power(Rational.fromFraction(-2, 3));
        System.out.println(p);
    }

    @Test
    void testRationalRootInteger() {
        Rational r = Rational.valueOf(34);
        Real root = r.root(Rational.valueOf(3));
        System.out.println(root);
    }

    @Test
    void testRationalRootNonInteger() {
        Rational r = Rational.fromFraction(8, 27);
        Real root = r.root(Rational.valueOf(3));
        System.out.println(root);
    }

    @Test
    void testMatrixCreation() {
        Matrix matrix = Matrix.IDENTITY_3X3;
        System.out.println(matrix);
    }

    @Test
    void testMatrixMultiplication() {
        Matrix a = Matrix.integerMatrix(new long[][]{
                {1, 0, 2},
                {-1, 3, 1}
        });
        Matrix b = Matrix.integerMatrix(new long[][]{
                {3, 1},
                {2, 1},
                {1, 0}
        });
        Matrix c = a.multiply(b);
        System.out.println(c);
    }

    @Test
    void testIJMinor() {
        Matrix a = Matrix.integerMatrix(new long[][]{
                {1, 2, 3, 4, 5, 6},
                {2, 3, 4, 5, 6, 7},
                {3, 4, 5, 6, 7, 8},
                {4, 5, 6, 7, 8, 9},
                {5, 6, 7, 8, 9, 0},
                {6, 7, 8, 9, 0, 1}
        });
        Matrix b = a.minor(1, 0);
        System.out.println(b);
    }

    @Test
    void testDet2X2() {
        Matrix a = Matrix.integerMatrix(new long[][]{
                {1, 2},
                {2, 1}
        });
        Real det = a.determinant();
        System.out.println(det);
    }

    @Test
    void testDet3X3() {
        Matrix a = Matrix.integerMatrix(new long[][]{
                {1, 0, 2},
                {0, 1, 3},
                {2, 3, 1}
        });
        Real det = a.determinant();
        System.out.println(det);
    }

    @Test
    void testDet6X6() {
        Matrix a = Matrix.integerMatrix(new long[][]{
                {1, 2, 3, 4, 5, 6},
                {2, 3, 4, 5, 6, 7},
                {3, 4, 5, 6, 7, 8},
                {4, 5, 6, 7, 8, 9},
                {5, 6, 7, 8, 9, 0},
                {6, 7, 8, 9, 0, 1}
        });
        Real det = a.determinant();
        System.out.println(det);
    }
}
