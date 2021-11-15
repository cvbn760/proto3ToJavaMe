package content.domain.proto;

import content.domain.metadata.DataType;

public enum ValidTypes implements DataType {
    DOUBLE("double", "double", "DATA_TYPE_DOUBLE", true, "Double", "Double"), // +
    FLOAT("float", "float", "DATA_TYPE_FLOAT", true, "Float", "Float"), // +
    INT32("int32", "int", "DATA_TYPE_INT", true, "Integer", "Int32"), // +
    INT64("int64", "long", "DATA_TYPE_LONG", true, "Long","Int64"), // +
    UINT32("uint32", "int", "DATA_TYPE_INT", true, "Integer","UInt32"), // [0,4294967295] int ????????
    UINT64("uint64","long", "DATA_TYPE_LONG", true, "Long", "UInt64"),// uint64 - [0,18446744073709551615] long ???????????????????
    SINT32("sint32","int", "DATA_TYPE_INT", true, "Integer","SInt32"),// sint32 - [-2147483648,2147483647] int ???????????????????
    SINT64("sint64","long", "DATA_TYPE_LONG", true, "Long", "SInt64"),  // sint64 - [-9223372036854775808,9223372036854775807] long
    FIXED32("fixed32","int", "DATA_TYPE_INT", true, "Integer", "Fixed32"),// fixed32 - [] int ???????????????????
    FIXED64("fixed64","long", "DATA_TYPE_LONG", true, "Long", "Fixed64"),// fixed64 - [] long ???????????????????
    SFIXED32("sfixed32","int", "DATA_TYPE_INT", true, "Integer", "SFixed32"),// sfixed32 - [] int ???????????????????
    SFIXED64("sfixed64","long", "DATA_TYPE_LONG", true, "Long", "SFixed64"),// sfixed64 - [] long ???????????????????
    BOOL("bool", "boolean", "DATA_TYPE_BOOLEAN", true, "Boolean", "Bool"), //
    STRING("string", "String", "DATA_TYPE_STRING", false, "String"), //
    ENUM("enum", "enum", "DATA_TYPE_ENUM", true, "Integer", "Enum"), //
    ENUM_VALUE("", "int", "DATA_TYPE_INT", true, "Integer",""), //
    BYTES("bytes", "byte[]", "DATA_TYPE_BYTESTRING", false, "Bytes"); //

    private final String implementationType;
    private final String protoType;
    private final String dataTypeConstant;
    private final boolean primitiveType;
    private final String javaObjectType;
    private final String name;

    ValidTypes(String protoType, String implementationType, String dataTypeConstant, boolean primitiveType, String name) {
        this.protoType = protoType;
        this.implementationType = implementationType;
        this.dataTypeConstant = dataTypeConstant;
        this.primitiveType = primitiveType;
        this.javaObjectType = "";
        this.name = name;
    }

    ValidTypes(String protoType, String implementationType, String dataTypeConstant, boolean primitiveType, String javaObjectType, String name) {
        this.protoType = protoType;
        this.implementationType = implementationType;
        this.dataTypeConstant = dataTypeConstant;
        this.primitiveType = primitiveType;
        this.javaObjectType = javaObjectType;
        this.name = name;
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

    @Override
    public String getName() {
        return this.name;
    }
}
