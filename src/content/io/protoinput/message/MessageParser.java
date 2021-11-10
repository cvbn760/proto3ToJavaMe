package content.io.protoinput.message;

import content.domain.proto.ProtoFileInput;
import content.io.protoinput.AbstractProtoParser;

public final class MessageParser extends AbstractProtoParser {
    public MessageParser() {
    }

    public void parseAndAddProtoFile(ProtoFileInput protoInput) {
        protoInput.setProtoClassName(this.strings[1]);
    }
}
