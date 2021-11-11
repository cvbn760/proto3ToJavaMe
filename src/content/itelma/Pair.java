package content.itelma;


public class Pair {
    // Wire types
    public static final byte VARINT = 0;     // Varint	int32, int64, uint32, uint64, sint32, sint64, bool, enum
    public static final byte BIT64 = 1;      // 64-bit	fixed64, sfixed64, double
    public static final byte LENGTH_DEL = 2; // Length-delimited	string, bytes, embedded messages, packed repeated fields
    public static final byte BIT32 = 5;      // 32-bit	fixed32, sfixed32, float

    public final int fieldNumber;     // номер поля в потоке protobuf
    public final byte wireType;       // wire type
    public final byte[] content;      // массив байт, содержащий значение пары (Varint, 64-bit, etc)
    public final int endContent;      // позиция в исходном массиве, указывающая на следующий байт данных настоящей пары key-value

    public Pair(int fieldNumber, byte wireType, byte[] content, int endContent) {
        this.fieldNumber = fieldNumber;
        this.wireType = wireType;
        this.content = content;
        this.endContent = endContent;
    }

    public String getWireType() {
        switch (wireType) {
            case VARINT: return "Varint";
            case BIT64: return "64-bit (fixed), double";
            case LENGTH_DEL: return "Length-delimited";
            case BIT32: return "32-bit (fixed), float";
            default: return "UNKNOWN";
        }
    }

    public String toString() {
        return "Proto fieldNumber: " + fieldNumber + ", wireType: " + getWireType() + "\r\n" +
                "content: " + new ByteService().byteArrayToHexString(content, content.length);
    }    
}
