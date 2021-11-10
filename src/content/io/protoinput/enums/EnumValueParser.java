package content.io.protoinput.enums;

import content.domain.proto.ProtoFileInput;
import content.io.exception.ProtoFileValidationException;
import content.io.protoinput.AbstractProtoParser;

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
