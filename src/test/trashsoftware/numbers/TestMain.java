package trashsoftware.numbers;

public class TestMain {

    public static void main(String[] args) {
        for (int i = 1; i < 7; i++) {
            Rational rational = Rational.fromFraction(i, 7);
            System.out.println(rational.toStringDecimal());
        }
    }
}
