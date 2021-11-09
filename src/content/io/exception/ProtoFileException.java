package content.io.exception;

public class ProtoFileException extends Proto2JavaMeException {
    private static final long serialVersionUID = 7040685528096171294L;

    public ProtoFileException(String message) {
        super(message);
    }

    public ProtoFileException(String message, Throwable cause) {
        super(message, cause);
    }
}