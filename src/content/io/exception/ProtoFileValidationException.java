package content.io.exception;

public class ProtoFileValidationException extends Proto2JavaMeException {
    private static final long serialVersionUID = -5060943626029064004L;
    private final String message;
    private int lineNumber = -1;

    public ProtoFileValidationException(String message) {
        super(message);
        this.message = message;
    }

    public ProtoFileValidationException(String message, Throwable cause) {
        super(message, cause);
        this.message = message;
    }

    public int getLineNumber() {
        return this.lineNumber;
    }

    public void setLineNumber(int lineNumber) {
        this.lineNumber = lineNumber;
    }

    public String getMessage() {
        String msg;
        if (this.lineNumber > -1) {
            msg = this.message + ", line number: " + this.lineNumber;
        } else {
            msg = this.message;
        }

        return msg;
    }
}
