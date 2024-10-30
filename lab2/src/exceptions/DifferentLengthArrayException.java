package exceptions;

public class DifferentLengthArrayException extends RuntimeException {
    public DifferentLengthArrayException(){}

    public DifferentLengthArrayException(String message) {
        super(message);
    }
}
