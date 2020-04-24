package trashsoftware.numbers;


import org.junit.jupiter.api.Assertions;
import org.junit.jupiter.api.Test;

import java.math.BigInteger;
import java.util.Arrays;
import java.util.Map;
import java.util.Set;

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
    void testFieldExtension() {
        Radical sqrt2 = Radical.createRadical(2, 2);

        class RationalExtendSqrt extends Field<Rational, Radical> {
            public RationalExtendSqrt(Rational rational, Rational b, Radical extension) {
                super(rational, b, extension);
            }
        }

        RationalExtendSqrt res2 = new RationalExtendSqrt(Rational.valueOf(3), Rational.valueOf(4), sqrt2);
        System.out.println(res2);
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
    void testComplexModulus() {
        Complex a = Complex.createComplex(1, 1);
        Real m = a.modulus();
        assert m.equals(Rational.valueOf(2).sqrt());
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

    @Test
    void testSquareInverse() {
        Matrix m = Matrix.createInstance(new Rational[][]{
                {Rational.valueOf(26), Rational.parseDecimal("59.56"), Rational.parseDecimal("1628.4")},
                {Rational.parseDecimal("59.56"), Rational.parseDecimal("133.43"), Rational.parseDecimal("3449.65")},
                {Rational.parseDecimal("1628.4"), Rational.parseDecimal("3449.65"), Rational.parseDecimal("95487.38")}
        });
        Matrix mInv = m.inverse();
        System.out.println(mInv);
    }

    @Test
    void testSquareREF() {
        Matrix a = Matrix.integerMatrix(new long[][]{
                {2, 1, -1},
                {-3, -1, 2},
                {-2, 1, 2}
        });
        a.toRowEchelonForm();
        System.out.println(a);
    }

    @Test
    void testNotSquareREF() {
        Matrix a = Matrix.integerMatrix(new long[][]{
                {2, 1, -1, 8},
                {-3, -1, 2, -11},
                {-2, 1, 2, -3}
        });
        a.toRowEchelonForm();
        System.out.println(a);
    }

    @Test
    void testRREF() {
        Matrix a = Matrix.integerMatrix(new long[][]{
                {2, 1, -1},
                {-3, -1, 2},
                {-2, 1, 2}
        });
        a.toReducedRowEchelonForm();
        System.out.println(a);
    }

    @Test
    void testNotSquareRREF() {
        Matrix a = Matrix.integerMatrix(new long[][]{
                {2, 1, -1, 8},
                {-3, -1, 2, -11},
                {-2, 1, 2, -3}
        });
        a.toReducedRowEchelonForm();
        System.out.println(a);
    }

    @Test
    void testRowSwap() {
        Matrix a = Matrix.integerMatrix(new long[][]{
                {0, 3, 1},
                {-3, 0, 2},
                {-2, 1, 2},
                {1, 2, 3}
        });
        a.swapRows(1, 2);
        System.out.println(a);
    }

    @Test
    void test4x3REF() {
        Matrix a = Matrix.integerMatrix(new long[][]{
                {0, 0, 1},
                {-3, 0, 2},
                {-2, 1, 2},
                {1, 2, 3}
        });
        a.toRowEchelonForm();
        System.out.println(a);
    }

    @Test
    void test4x3RREF() {
        Matrix a = Matrix.integerMatrix(new long[][]{
                {0, 0, 1},
                {-3, 0, 2},
                {-2, 1, 2},
                {1, 2, 3}
        });
        a.toReducedRowEchelonForm();
        System.out.println(a);
    }

    @Test
    void testDependentRREF() {
        Matrix a = Matrix.integerMatrix(new long[][]{
                {1, 1, 2},
                {2, 2, 4},
                {3, 3, 6}
        });
        a.toReducedRowEchelonForm();
        System.out.println(a);
    }

    @Test
    void testVectorAugment() {
        Matrix a = Matrix.integerMatrix(new long[][]{
                {2, 1, -1},
                {-3, -1, 2},
                {-2, 1, 2}
        });
        Real[] v = new Real[]{Rational.valueOf(8), Rational.valueOf(-11), Rational.valueOf(-3)};
        Matrix b = a.augment(v);
        System.out.println(b);
        b.toReducedRowEchelonForm();
        System.out.println(b);
    }

    @Test
    void testMatrixAugment() {
        Matrix a = Matrix.integerMatrix(new long[][]{
                {2, -1, 0},
                {-1, 2, -1},
                {0, -1, 2}
        });
        Matrix b = Matrix.IDENTITY_3X3;
        Matrix c = a.augment(b);
        System.out.println(c);
        c.toReducedRowEchelonForm();
        System.out.println(c);
    }

    @Test
    void testInverse() {
        Matrix a = Matrix.integerMatrix(new long[][]{
                {2, -1, 0},
                {-1, 2, -1},
                {0, -1, 2}
        });
        Matrix b = a.inverse();
        System.out.println(b);
    }

    @Test()
    void testNotInvertible() {
        Matrix a = Matrix.integerMatrix(new long[][]{
                {2, -1, 0},
                {-1, 2, -1},
                {0, 0, 0}
        });
        Assertions.assertThrows(IncompatibleMatrixException.class, a::inverse);
    }

    @Test
    void test2x2Determinant2() {
        Matrix a = Matrix.integerMatrix(new long[][]{
                {1, 2},
                {2, 1}
        });
        Real det = a.det();
        System.out.println(det);
    }

    @Test
    void test4x4Determinant2() {
        Matrix a = Matrix.integerMatrix(new long[][]{
                {1, 0, 2, -1},
                {3, 0, 0, 5},
                {2, 1, 4, -3},
                {1, 0, 5, 0}
        });
        Real det = a.det();
        System.out.println(det);
    }

    @Test
    void testRank() {
        Matrix a = Matrix.integerMatrix(new long[][]{
                {2, -1, 0},
                {-4, 2, 0},
                {0, 0, 0}
        });
        int r = a.rank();
        System.out.println(r);
    }

    @Test
    void testPerformanceDet1vsDet2() {
        Matrix a = Matrix.integerMatrix(new long[][]{
                {1, 0, 2, -1, 6, 0, 1, 0},
                {3, 0, 0, 5, 7, 0, 0, 0},
                {2, 1, 4, -3, 8, 4, 1, 0},
                {1, 0, 5, 0, 9, 7, 1, -1},
                {2, 3, 0, 4, 1, -3, 0, 0},
                {3, 0, 3, 0, 0, 0, 1, 1},
                {-1, 1, 0, 2, 0, 3, 1, -1},
                {1, 0, 3, 1, 0, -1, 2, 0}
        });
        long t1 = System.currentTimeMillis();
        Real det1 = a.determinant();
        long t2 = System.currentTimeMillis();
        Real det2 = a.det();
        long t3 = System.currentTimeMillis();
        assert det1.equals(det2);
        System.out.println(det2);
        System.out.println(t2 - t1);
        System.out.println(t3 - t2);
    }

    @Test
    void testMatrixPower() {
        Matrix a = Matrix.integerMatrix(new int[][]{
                {1, 3, 3, 1},
                {0, 2, 2, 0},
                {1, 0, 0, 0},
                {0, 0, 0, 1}
        });
        System.out.println(a.rank());
        Matrix b = a.power(3);
        System.out.println(b);
        Matrix c = a.copy();
        c.toRowEchelonForm();
        Matrix d = c.power(5);
        System.out.println(d);
    }

    @Test
    void testVector() {
        Vector v = Vector.integerVector(1, 2, 1);
        System.out.println(v.norm());
    }

    @Test
    void testPLU() {
        Matrix m = Matrix.integerMatrix(new int[][]{
                {9, 6, 0},
                {6, 5, 4},
                {3, 4, 10}
        });
        Matrix[] plu = m.PLUDecomposition();
        System.out.println(Arrays.toString(plu));
        assert m.equals(plu[0].multiply(plu[1]).multiply(plu[2]));
    }

    @Test
    void testPLUWithSwap() {
        Matrix m = Matrix.integerMatrix(new int[][]{
                {0, 5, 6},
                {1, 2, 9},
                {3, 4, 0}
        });
        Matrix b = m.copy();
        b.toRowEchelonForm();
        System.out.println(b);
        Matrix[] plu = m.PLUDecomposition();
        System.out.println(Arrays.toString(plu));
        assert m.equals(plu[0].multiply(plu[1]).multiply(plu[2]));
    }

    @Test
    void testTranspose() {
        Matrix a = Matrix.integerMatrix(new int[][]{
                {1, 2, 3},
                {1, 3, 4},
                {0, 3, 3},
                {2, 1, 0}
        });
        Matrix b = a.transpose();
        System.out.println(b);
    }
}
