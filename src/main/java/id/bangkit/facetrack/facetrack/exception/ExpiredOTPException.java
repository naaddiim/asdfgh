package id.bangkit.facetrack.facetrack.exception;

public class ExpiredOTPException extends RuntimeException {
    public ExpiredOTPException(String message) {
        super(message);
    }
}
