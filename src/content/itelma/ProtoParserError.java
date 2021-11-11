package content.itelma;

import java.io.IOException;

public class ProtoParserError extends IOException {
    public ProtoParserError(String description) {
        super(description);
    }

    public String toString() {
        return "Proto parsing error";
    }    
}
