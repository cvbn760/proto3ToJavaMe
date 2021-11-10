package content.domain.proto;

import content.domain.metadata.DataType;

public enum ValidTypes implements DataType {
    DOUBLE("double", "double", "DATA_TYPE_DOUBLE", true, "Double"),
    FLOAT("float", "float", "DATA_TYPE_FLOAT", true, "Float"),
    INT32("int32", "int", "DATA_TYPE_INT", true, "Integer"),
    INT64("int64", "long", "DATA_TYPE_LONG", true, "Long"),
    BOOL("bool", "boolean", "DATA_TYPE_BOOLEAN", true, "Boolean"),
    STRING("string", "String", "DATA_TYPE_STRING", false),
    ENUM("enum", "enum", "DATA_TYPE_ENUM", true, "Integer"),
    ENUM_VALUE("", "int", "DATA_TYPE_INT", true, "Integer"),
    BYTES("bytes", "net.jarlehansen.protobuf.javame.ByteString", "DATA_TYPE_BYTESTRING", false);

    private final String implementationType;
    private final String protoType;
    private final String dataTypeConstant;
    private final boolean primitiveType;
    private final String javaObjectType;

    ValidTypes(String protoType, String implementationType, String dataTypeConstant, boolean primitiveType) {
        this.protoType = protoType;
        this.implementationType = implementationType;
        this.dataTypeConstant = dataTypeConstant;
        this.primitiveType = primitiveType;
        this.javaObjectType = "";
    }

    ValidTypes(String protoType, String implementationType, String dataTypeConstant, boolean primitiveType, String javaObjectType) {
        this.protoType = protoType;
        this.implementationType = implementationType;
        this.dataTypeConstant = dataTypeConstant;
        this.primitiveType = primitiveType;
        this.javaObjectType = javaObjectType;
    }

    public String getImplementationType() {
        return this.implementationType;
    }

    public String getProtoType() {
        return this.protoType;
    }

    public String getDataTypeConstant() {
        return this.dataTypeConstant;
    }

    public boolean isPrimitiveType() {
        return this.primitiveType;
    }

    public String getJavaObjectType() {
        return this.javaObjectType;
    }
}
