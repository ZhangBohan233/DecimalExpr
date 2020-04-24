package trashsoftware.numbers;

import java.util.Arrays;

public class Vector {

    private final Real[] vector;

    private Vector(Real[] vector) {
        this.vector = vector;
    }

    public static Vector createInstance(Real[] v) {
        return new Vector(v);
    }

    public static Vector integerVector(int... array) {
        Real[] v = new Real[array.length];
        for (int i = 0; i < array.length; i++) {
            v[i] = Rational.valueOf(array[i]);
        }
        return new Vector(v);
    }

    public static Vector standardVector(int dimension, int x) {
        int[] v = new int[dimension];
        v[x] = 1;
        return integerVector(v);
    }

    public Real norm() {
        Real num = Rational.ZERO;
        for (Real r : vector) {
            num = num.add(r.power(Rational.TWO));
        }
        return num.sqrt();
    }

    public void multiplyBy(Real scalingFactor) {
        for (int r = 0; r < vector.length; r++) {
            vector[r] = vector[r].multiply(scalingFactor);
        }
    }

    public Real[] toArray() {
        return vector;
    }

    public int dimension() {
        return vector.length;
    }

    @Override
    public String toString() {
        return Arrays.toString(vector);
    }
}
