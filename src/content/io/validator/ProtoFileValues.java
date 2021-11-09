package content.io.validator;

public enum ProtoFileValues {
    REQUIRED_TAG("required"),
    OPTIONAL_TAG("optional"),
    REPEATED_TAG("repeated"),
    END_FIELD_TAG(";");

    private final String tag;

    ProtoFileValues(String tag) {
        this.tag = tag;
    }

    public String getTag() {
        return this.tag;
    }
}
