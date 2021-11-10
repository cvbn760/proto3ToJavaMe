package content.io.protoinput.enums;


import content.domain.proto.ProtoFileInput;
import content.io.protoinput.AbstractProtoParser;

public class EnumParser extends AbstractProtoParser {
    public EnumParser() {
    }

    public void parseAndAddProtoFile(ProtoFileInput protoInput) {
        protoInput.setEnumIfAbsent(this.strings[1]);
    }
}
