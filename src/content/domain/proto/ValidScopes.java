package content.domain.proto;

public enum ValidScopes {
    ENUM_NONE(""),
    OPTIONAL("optional"),
    REQUIRED("required"),
    REPEATED("repeated");

    private final String scope;

    ValidScopes(String scope) {
        this.scope = scope;
    }

    public String getScopeValue() {
        return this.scope;
    }
}
