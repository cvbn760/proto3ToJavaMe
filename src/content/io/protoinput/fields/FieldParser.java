package content.io.protoinput.fields;

import content.domain.metadata.DataType;
import content.domain.proto.*;
import content.io.exception.ProtoFileValidationException;
import content.io.protoinput.AbstractProtoParser;
import content.io.validator.ProtoFileValues;

public final class FieldParser extends AbstractProtoParser {
    ProtoFileInput protoInput = new ProtoFileInput();

    public FieldParser() {
        this.protoInput.setProtoClassName("");
    }

    public void parseAndAddProtoFile(ProtoFileInput protoInput) {
        this.protoInput = protoInput;
        FieldData fieldData = new FieldData();
        fieldData.setScope(this.getScopeTag(this.strings[0]));
        if (fieldData.getScope() == ValidScopes.REPEATED) {
            fieldData.setListImpl(ListImplementation.VECTOR);
        }

        fieldData.setType(this.getTypeTag(this.strings[1]));
        fieldData.setName(this.strings[2]);
        fieldData.setId(this.getIdTag(this.strings[4]));
        protoInput.addFieldData(fieldData);
    }

    ValidScopes getScopeTag(String token) {
        ValidScopes scope;
        if (ProtoFileValues.REQUIRED_TAG.getTag().equals(token)) {
            scope = ValidScopes.REQUIRED;
        } else if (ProtoFileValues.OPTIONAL_TAG.getTag().equals(token)) {
            scope = ValidScopes.OPTIONAL;
        } else {
            if (!ProtoFileValues.REPEATED_TAG.getTag().equals(token)) {
                throw new ProtoFileValidationException("The tag required, optional or repeated are mandatory for all variables, content: " + this.line);
            }

            scope = ValidScopes.REPEATED;
        }

        return scope;
    }

    DataType getTypeTag(String token) {
        DataType type = null;
        ValidTypes[] arr$ = ValidTypes.values();
        int len$ = arr$.length;

        for (int i$ = 0; i$ < len$; ++i$) {
            ValidTypes typeObj = arr$[i$];
            if (typeObj.getProtoType().equals(token)) {
                type = typeObj;
                break;
            }
        }

        if (type == null) {
            if (this.protoInput.getEnumData(token) != null) {
                type = ValidTypes.ENUM_VALUE;
            } else {
                type = new CustomType(token);
            }
        }

        return type;
    }

    int getIdTag(String idString) {
        String tempId = idString.substring(0, idString.length() - 1);
        if (tempId.matches("[0-9]++")) {
            int id = Integer.parseInt(tempId);
            return id;
        } else {
            throw new ProtoFileValidationException("The field id is not a valid number, id: " + idString + ", content: " + this.line);
        }
    }
}
