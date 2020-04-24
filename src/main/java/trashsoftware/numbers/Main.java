package trashsoftware.numbers;

import java.util.Arrays;

public class Main {

    public static void main(String[] args) {
        Vector y = Vector.integerVector(4, 5, 4, 3, 2, 4, 3, 4, 4, 6, 8, 4, 5, 4, 6, 5, 8, 6, 6, 7, 6, 6, 7, 5, 6, 5, 5);

        Real[] yArray = y.toArray();
        Real yBar = Utilities.arrayAvg(yArray);
        System.out.println(yBar);

        Real sst = Rational.ZERO;
        for (int i = 0; i < 27; i++) {
            Real sqr = yArray[i].subtract(yBar).power(Rational.valueOf(2));
            sst = sst.add(sqr);
        }
        System.out.println(sst.doubleValue());

        Matrix xTr = Matrix.integerMatrix(new long[][]{
                {1, 1, 1, 1, 1, 1, 1, 1, 1, 1, 1, 1, 1, 1, 1, 1, 1, 1, 1, 1, 1, 1, 1, 1, 1, 1, 1},
                {1, 1, 1, 1, 1, 1, 1, 1, 1, 0, 0, 0, 0, 0, 0, 0, 0, 0, 0, 0, 0, 0, 0, 0, 0, 0, 0},
                {0, 0, 0, 0, 0, 0, 0, 0, 0, 1, 1, 1, 1, 1, 1, 1, 1, 1, 0, 0, 0, 0, 0, 0, 0, 0, 0}
        });
//        System.out.println(Arrays.toString(xTr.dimension()));
        Vector xTrY = xTr.multiply(y);
//        System.out.println(xTrY);

        Matrix yTr = Matrix.ofVectors(y).transpose();
        Matrix iMin1nJ = Matrix.identityMatrix(27)
                .subtract(Matrix.oneMatrix(27).multiply(Rational.fromFraction(1, 27)));
        System.out.println(yTr.multiply(iMin1nJ).multiply(y));

        Matrix xty = Matrix.integerMatrix(new long[][]{
                {27, 9, 9},
                {9, 9, 0},
                {9, 0, 9}
        });
        Matrix xtyInv = xty.inverse();
        System.out.println(xtyInv);

        Vector beta = xtyInv.multiply(xTrY);
        System.out.println(beta);

        Matrix m2 = xtyInv.multiply(xTr);
        System.out.println(m2.multiply(y));

//        Matrix m = Matrix.integerMatrix(new long[][]{
//                {1, 1, 0, 0},
//                {1, 0, 1, 0},
//                {1, 0, 0, 1}
//        });
//        m.toRowEchelonForm();
//        System.out.println(m);
    }
}
