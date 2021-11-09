package content.io.exception;

import net.jarlehansen.proto2javame.io.exception.Proto2JavaMeException;

public class InvalidInputException extends Proto2JavaMeException {
    private static final long serialVersionUID = -6176893558158162823L;

    public InvalidInputException(String message) {
        super(message);
    }

    public InvalidInputException(String message, Throwable cause) {
        super(message, cause);
    }
}
