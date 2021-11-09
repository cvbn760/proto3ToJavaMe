package content.io.protoinput.enums;


import net.jarlehansen.proto2javame.domain.proto.ProtoFileInput;
import net.jarlehansen.proto2javame.io.protoinput.AbstractProtoParser;

public class EnumParser extends AbstractProtoParser {
    public EnumParser() {
    }

    public void parseAndAddProtoFile(ProtoFileInput protoInput) {
        protoInput.setEnumIfAbsent(this.strings[1]);
    }
}
