package content.io.protoinput.util;

import net.jarlehansen.proto2javame.io.exception.ProtoFileValidationException;

public enum ProtoTagUtil {
    private static final String PROTO_FILE = ".proto";

    ProtoTagUtil() {
    }

    public static void isValidProtoFile(String protoLocation) {
        if (!protoLocation.endsWith(".proto")) {
            throw new ProtoFileValidationException("The input file must end with .proto");
        }
    }
}