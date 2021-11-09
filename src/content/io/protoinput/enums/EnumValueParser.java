package content.io.protoinput.enums;

import net.jarlehansen.proto2javame.domain.proto.ProtoFileInput;
import net.jarlehansen.proto2javame.io.exception.ProtoFileValidationException;
import net.jarlehansen.proto2javame.io.protoinput.AbstractProtoParser;

public class EnumValueParser extends AbstractProtoParser {
    public EnumValueParser() {
    }

    public void parseAndAddProtoFile(ProtoFileInput protoInput) {
        String idString = this.strings[2];
        idString = idString.substring(0, idString.length() - 1);
        if (idString.matches("[0-9]++")) {
            protoInput.setCurrentEnumValue(new Integer(idString), this.strings[0]);
        } else {
            throw new ProtoFileValidationException("The enum id is not a valid number, content: " + this.line);
        }
    }
}
