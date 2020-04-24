package trashsoftware.numbers;

public class Radical implements FieldExtendable {

    private Rational number;
    private Rational nthRoot;

    private Radical(Rational number, Rational nthRoot) {
        this.number = number;
        this.nthRoot = nthRoot;
    }

    public static Radical createRadical(Rational number, Rational nthRoot) {
        return new Radical(number, nthRoot);
    }

    public static Radical createRadical(long number, long nthRoot) {
        return new Radical(Rational.valueOf(number), Rational.valueOf(nthRoot));
    }

    @Override
    public String toString() {
        return "(" + nthRoot + "√" + number + ")";
    }
}
