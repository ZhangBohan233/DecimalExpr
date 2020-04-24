package trashsoftware.numbers;

public class Utilities {

    public static Real arraySum(Real[] arr) {
        Real res = Rational.ZERO;
        for (Real r: arr) res = res.add(r);
        return res;
    }

    public static Real arrayAvg(Real[] arr) {
        return arraySum(arr).divide(Rational.valueOf(arr.length));
    }
}
