package content.domain.proto;

import net.jarlehansen.proto2javame.domain.proto.EnumData;
import net.jarlehansen.proto2javame.domain.proto.FieldData;
import net.jarlehansen.proto2javame.io.exception.ProtoFileValidationException;

import java.util.*;

public class ProtoFileInput {
    private final List<net.jarlehansen.proto2javame.domain.proto.FieldData> variables = new ArrayList();
    private final Map<String, net.jarlehansen.proto2javame.domain.proto.EnumData> enums = new HashMap();
    private String protoClassName;
    private String packageName = "";
    private net.jarlehansen.proto2javame.domain.proto.EnumData currentEnum = null;

    public ProtoFileInput() {
    }

    public String getPackageName() {
        return this.packageName;
    }

    public void setPackageName(String packageName) {
        this.packageName = packageName;
    }

    public String getProtoClassName() {
        return this.protoClassName;
    }

    public void setProtoClassName(String protoClassName) {
        this.protoClassName = protoClassName;
    }

    public void addFieldData(net.jarlehansen.proto2javame.domain.proto.FieldData messageData) {
        boolean duplicate = false;
        Iterator i$ = this.variables.iterator();

        while (i$.hasNext()) {
            net.jarlehansen.proto2javame.domain.proto.FieldData fieldData = (net.jarlehansen.proto2javame.domain.proto.FieldData) i$.next();
            if (fieldData.getId() == messageData.getId()) {
                duplicate = true;
                break;
            }
        }

        if (duplicate) {
            throw new ProtoFileValidationException("The id numbers must be unique, duplicate id: " + messageData.getId());
        } else {
            this.variables.add(messageData);
        }
    }

    public List<FieldData> getFields() {
        return this.variables;
    }

    public void setEnumIfAbsent(String enumName) {
        if (!this.enums.containsKey(enumName)) {
            net.jarlehansen.proto2javame.domain.proto.EnumData enumData = new net.jarlehansen.proto2javame.domain.proto.EnumData();
            enumData.setName(enumName);
            this.enums.put(enumName, enumData);
            this.currentEnum = enumData;
        }

    }

    public void setCurrentEnumValue(int id, String value) {
        this.currentEnum.addEnumValue(id, value);
        this.enums.put(this.currentEnum.getName(), this.currentEnum);
    }

    public net.jarlehansen.proto2javame.domain.proto.EnumData getEnumData(String enumName) {
        return this.enums.get(enumName);
    }

    public net.jarlehansen.proto2javame.domain.proto.EnumData getCurrentEnum() {
        return this.currentEnum;
    }

    public Collection<EnumData> getEnums() {
        return this.enums.values();
    }
}
