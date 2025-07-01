package apps.spring.reportium.entity.exceptions;

public class AgeFilterOnNotAlivePeopleException extends RuntimeException {
    public AgeFilterOnNotAlivePeopleException(String message) {
        super(message);
    }
}
