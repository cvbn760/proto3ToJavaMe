package content.domain.proto;

import content.domain.metadata.DataType;

import java.io.IOException;

public class FieldData {
    private int syntax = 3;
    private int id;
    private ValidScopes scope;
    private DataType type;
    private String name;
    private boolean isList = false;
    private ListImplementation listImpl;

    public FieldData() {
    }

    public int getSyntax() {
        return syntax;
    }

    public void setSyntax(int syntax) throws IOException {
        if (syntax < 2 || syntax > 3){
            throw new IOException("Non-existent syntax version specified. Only 2 or 3 possible...");
        }
        this.syntax = syntax;
    }

    public ValidScopes getScope() {
        return this.scope;
    }

    public void setScope(ValidScopes scope) {
        this.scope = scope;
    }

    public int getId() {
        return this.id;
    }

    public void setId(int id) {
        this.id = id;
        System.out.println(this);
    }

    public String getName() {
        return this.name;
    }

    public void setName(String name) {
        this.name = name;
    }

    public DataType getType() {
        return this.type;
    }

    public void setType(DataType type) {
        this.type = type;
    }

    public ListImplementation getListImpl() {
        return this.listImpl;
    }

    public void setListImpl(ListImplementation listImpl) {
        this.listImpl = listImpl;
        this.isList = true;
    }

    public boolean isList() {
        return this.isList;
    }

    public String toString() {
        StringBuilder sb = new StringBuilder();
        sb.append("FieldData");
        sb.append("{id=").append(this.id);
        sb.append(", syntax=").append(this.syntax);
        sb.append(", scope=").append(this.scope);
        sb.append(", type=").append(this.type);
        sb.append(", name='").append(this.name).append('\'');
        sb.append(", isList=").append(this.isList);
        sb.append(", listImpl=").append(this.listImpl);
        sb.append('}');
        return sb.toString();
    }
}
