package apps.spring.reportium.entity.exceptions;

public class NotAuthenticatedUserException extends RuntimeException {
    public NotAuthenticatedUserException(String message) {
        super(message);
    }
}
