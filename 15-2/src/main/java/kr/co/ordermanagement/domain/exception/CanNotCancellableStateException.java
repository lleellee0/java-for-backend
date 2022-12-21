package kr.co.ordermanagement.domain.exception;

public class CanNotCancellableStateException extends RuntimeException {
    public CanNotCancellableStateException(String message) {
        super(message);
    }
}
