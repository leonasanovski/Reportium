package apps.spring.reportium.entity.exceptions;

public class NoExistingCredentialsException extends RuntimeException {
    public NoExistingCredentialsException(String message) {
        super(message);
    }
}
