package content.io.exception;

public class Proto2JavaMeException extends RuntimeException {
    private static final long serialVersionUID = 1993647030982430429L;

    Proto2JavaMeException(String message) {
        super(message);
    }

    Proto2JavaMeException(String message, Throwable cause) {
        super(message, cause);
    }
}
