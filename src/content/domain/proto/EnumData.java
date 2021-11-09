package content.domain.proto;

import net.jarlehansen.proto2javame.domain.proto.ValidScopes;
import net.jarlehansen.proto2javame.io.exception.ProtoFileValidationException;

import java.util.HashMap;
import java.util.Map;

public class EnumData {
    private final Map<Integer, String> enumValues = new HashMap();
    private int id;
    private ValidScopes scope;
    private String name;

    public EnumData() {
    }

    public int getId() {
        return this.id;
    }

    public void setId(int id) {
        this.id = id;
    }

    public ValidScopes getScope() {
        return this.scope;
    }

    public void setScope(ValidScopes scope) {
        this.scope = scope;
    }

    public String getName() {
        return this.name;
    }

    public void setName(String name) {
        this.name = name;
    }

    public void addEnumValue(int id, String value) {
        if (this.enumValues.containsKey(id)) {
            throw new ProtoFileValidationException("Enum field id must be unique, field: " + id + " - " + value);
        } else {
            this.enumValues.put(id, value);
        }
    }

    public Map<Integer, String> getEnumValues() {
        return this.enumValues;
    }

    public String toString() {
        StringBuilder sb = new StringBuilder();
        sb.append("EnumData");
        sb.append("{id=").append(this.id);
        sb.append(", scope=").append(this.scope);
        sb.append(", name='").append(this.name).append('\'');
        sb.append(", enumValues=").append(this.enumValues);
        sb.append('}');
        return sb.toString();
    }
}
