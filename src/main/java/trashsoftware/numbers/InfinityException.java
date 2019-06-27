package trashsoftware.numbers;

public class InfinityException extends NumberException {

    private final int signum;

    public InfinityException(int signum) {
        this.signum = signum;
    }
}
