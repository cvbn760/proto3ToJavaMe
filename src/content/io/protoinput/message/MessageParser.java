package content.io.protoinput.message;

import net.jarlehansen.proto2javame.domain.proto.ProtoFileInput;
import net.jarlehansen.proto2javame.io.protoinput.AbstractProtoParser;

public final class MessageParser extends AbstractProtoParser {
    public MessageParser() {
    }

    public void parseAndAddProtoFile(ProtoFileInput protoInput) {
        protoInput.setProtoClassName(this.strings[1]);
    }
}
