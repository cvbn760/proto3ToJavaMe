package content.itelma;

import java.io.*;
import java.util.Vector;

// https://habrahabr.ru/post/225901/
// https://github.com/addthis/stream-lib/blob/master/src/main/java/com/clearspring/analytics/util/Varint.java
public class Serializer3 {

    public static byte[] getInt32(int fieldNumber, int data) {
        return getType0_64(fieldNumber, data); // да, это не ошибка, 64
    }
    public static byte[] getInt32(int data) {
        return getVarint64(data);
    }

    public static byte[] getInt64(int fieldNumber, long data) {
        return getType0_64(fieldNumber, data);
    }
    public static byte[] getInt64(long data) {
        return getVarint64(data);
    }

    public static byte[] getUInt32(int fieldNumber, int data) {
        if (data == 0) return new byte[0];
        else return getType0_32(fieldNumber, data);
    }
    public static byte[] getUInt32(int data) {
        return getVarint32(data);
    }

    public static byte[] getUInt64(int fieldNumber, long data) {
        return getType0_64(fieldNumber, data);
    }
    public static byte[] getUInt64(long data) {
        return getVarint64(data);
    }

    // https://github.com/addthis/stream-lib/blob/master/src/main/java/com/clearspring/analytics/util/Varint.java
    public static byte[] getSInt32(int fieldNumber, int data) {
        return getType0_32(fieldNumber, (data << 1) ^ (data >> 31));
    }
    public static byte[] getSInt32(int data) {
        return getVarint32((data << 1) ^ (data >> 31));
    }

    public static byte[] getSInt64(int fieldNumber, long data) {
        return getType0_64(fieldNumber, (data << 1) ^ (data >> 63));
    }
    public static byte[] getSInt64(long data) {
        return getVarint64((data << 1) ^ (data >> 63));
    }

    public static byte[] getBool(int fieldNumber, boolean data) {
        if (data == false) return new byte[0]; // по умолчанию если data = false, то ничего не передаётся
        else return getType0_32(fieldNumber, 1);
    }
    public static byte[] getBool(boolean data) {
        return getVarint32(data == true? 1 : 0);
    }

    public static byte[] getEnum(int fieldNumber, int enumNumber) {
        return getType0_64(fieldNumber, enumNumber);
    }

    public static byte[] getFixed32(int fieldNumber, int data) {
        return getType5(fieldNumber, data);
    }
    public static byte[] getFixed32(int data) {
        return get4Bytes(data);
    }

    public static byte[] getSFixed32(int fieldNumber, int data) {
        return getType5(fieldNumber, data);
    }
    public static byte[] getSFixed32(int data) {
        return get4Bytes(data);
    }

    public static byte[] getFloat(int fieldNumber, float data) {
        return getType5(fieldNumber, Float.floatToIntBits(data));
    }
    public static byte[] getFloat(float data) {
        return get4Bytes(Float.floatToIntBits(data));
    }

    public static byte[] getFixed64(int fieldNumber, long data) {
        return getType1(fieldNumber, data);
    }
    public static byte[] getFixed64(long data) {
        return get8Bytes(data);
    }

    public static byte[] getSFixed64(int fieldNumber, long data) {
        return getType1(fieldNumber, data);
    }
    public static byte[] getSFixed64(long data) {
        return get8Bytes(data);
    }

    /**
     * Одинаково для прото 2 и 3
     */
    public static byte[] getDouble(int fieldNumber, double data) {
        return getType1(fieldNumber, Double.doubleToLongBits(data));
    }

    /**
     * Одинаково для прото 2 и 3
     */
    public static byte[] getDouble(long data) {
        return get8Bytes(Double.doubleToLongBits(data));
    }

    /**
     * Одинаково для прото 2 и 3
     */
    public static byte[] getString(int fieldNumber, String s) {
        if (s.length() == 0) return new byte[0];
        else return getNestedMessage(fieldNumber,s.getBytes());
    }

    /**
     * Одинаково для прото 2 и 3
     */
    public static byte[] getString(String s) {
        if (s.length() == 0) return new byte[0];
        else return s.getBytes();
    }


    /**
     * Одинаково для прото 2 и 3
     */
    public static byte[] getBytes(int fieldNumber, byte[] b) {
        if (b.length == 0) return new byte[0];
        else return getNestedMessage(fieldNumber, b);
    }


    /**
     * Различается
     * */
    /* Для полей repeated. ВАЖНО - не использовать для элементарных типов protobuf: varint, 32-bit, or 64-bit!
     * Vector v должен содержать не исходные данные, а массивы байт, полученные методами вида getSInt32(int i)!
     */
    public static byte[] getRepeated(int fieldNumber, Vector v) {
        byte type = 2;
        byte[] result = new byte[0];
        if (v.size() == 0) return result;
        else {
            try {
                ByteArrayOutputStream bout = new ByteArrayOutputStream();
                for(int i=0; i < v.size(); i++) {
                    bout.write(getKey(fieldNumber, type));
                    ByteArrayOutputStream bData = new ByteArrayOutputStream();
                    bData.write((byte[]) v.elementAt(i));
                    byte[] data = bData.toByteArray();
                    bout.write(getVarint32(data.length));
                    bout.write(data);
                }
                result = bout.toByteArray();
            } catch (IOException e) {}
            return result;
        }
    }

    /**
     * Различается
     * */
    /* ВАЖНО: использовать обязательно с полями types varint, 32-bit, or 64-bit и также с полями с опцией [packed=true]
     * Vector v должен содержать не исходные данные, а массивы байт, полученные методами вида getSInt32(int i)!
     */
    public static byte[] getRepeatedPacked(int fieldNumber, Vector v) {
        byte type = 2;
        byte[] result = new byte[0];
        if (v.size() == 0) return result;
        else {
            ByteArrayOutputStream bout = new ByteArrayOutputStream();
            try {
                bout.write(getKey(fieldNumber, type));
                ByteArrayOutputStream bData = new ByteArrayOutputStream();
                for(int i=0; i < v.size(); i++) bData.write((byte[]) v.elementAt(i));
                byte[] data = bData.toByteArray();
                bout.write(getVarint32(data.length));
                bout.write(data);
                result = bout.toByteArray();
            } catch (IOException e) {}
            return result;
        }
    }


    /**
     * Различается
     * */
    private static byte[] getType0_32(int fieldNumber, int data) {
        byte type = 0; // Varint	int32, uint32, sint32, bool, enum
        byte[] result = new byte[0];
        if (data == 0) return result;
        else {
            ByteArrayOutputStream bout = new ByteArrayOutputStream();
            try {
                bout.write(getKey(fieldNumber, type));//(fieldNumber << 3) | type);
                bout.write(getVarint32(data));
                result = bout.toByteArray();
            } catch (IOException e) {
                e.printStackTrace();
            }
            return result;
        }
    }


    /**
     * Различается
     * */
    private static byte[] getType0_64(int fieldNumber, long data) {
        byte type = 0; // Varint	int64, uint64, sint64
        byte[] result = new byte[0];
        if (data == 0) return result;
        else {
            ByteArrayOutputStream bout = new ByteArrayOutputStream();
            try {
                bout.write(getKey(fieldNumber, type));
                bout.write(getVarint64(data));
                result = bout.toByteArray();
            } catch (IOException e) {
                e.printStackTrace();
            }
            return result;
        }
    }


    /**
     * Одинаково для прото 2 и 3
     */
    private static byte[] getType1(int fieldNumber, long data) {
        byte type = 1;
        byte[] result = new byte[0];
        if (data == 0) return result;
        else {
            ByteArrayOutputStream bout = new ByteArrayOutputStream();
            try {
                bout.write(getKey(fieldNumber, type));
                bout.write(get8Bytes(data)); //for (int i = 0; i < 64; i += 8) bout.write((byte) (0xFF & (data >> i)));
                result = bout.toByteArray();
            } catch (IOException ignored) {}
            return result;
        }
    }





    /**
     * Одинаково для прото 2 и 3
     */
    private static byte[] get8Bytes(long data) {
        ByteArrayOutputStream bout = new ByteArrayOutputStream();
        for (int i = 0; i < 64; i += 8) bout.write((byte) (0xFF & (data >> i)));
        return bout.toByteArray();
    }

    /**
     * Одинаково для прото 2 и 3
     */
    private static byte[] getType5(int fieldNumber, int data) {
        byte type = 5;
        byte[] result = new byte[0];
        if (data == 0) return result;
        else {
            ByteArrayOutputStream bout = new ByteArrayOutputStream();
            try {
                bout.write(getKey(fieldNumber, type));
                bout.write(get4Bytes(data));// for (int i = 0; i < 32; i += 8) bout.write((byte) (0xFF & (data >> i)));
                result = bout.toByteArray();
            } catch (IOException ignored) {
            }
            return result;
        }
    }

    /**
     * Одинаково для прото 2 и 3
     */
    private static byte[] get4Bytes(int data) {
        ByteArrayOutputStream bout = new ByteArrayOutputStream();
        for (int i = 0; i < 32; i += 8) bout.write((byte) (0xFF & (data >> i)));
        return bout.toByteArray();
    }

    /**
     * Одинаково для прото 2 и 3
     */
    public static byte[] getNestedMessage(int fieldNumber, byte[] data) {
        byte type = 2; // Length-delimited	string, bytes, embedded messages, packed repeated fields
        byte[] result = new byte[] {0};
        ByteArrayOutputStream bout = new ByteArrayOutputStream();
        //bout.write((byte) data.length);
        try {
            bout.write(getVarint32((fieldNumber << 3) | type));
            bout.write(getVarint32(data.length));
            bout.write(data);
            result = bout.toByteArray();
        } catch (IOException e) {}
        return result;      
        
    }

    /**
     * Одинаково для прото 2 и 3
     */
    private static byte[] getKey(int fieldNumber, byte type) {
        fieldNumber <<= 3;
        if ((fieldNumber >>> 7) == 0) return new byte[] {(byte) ((byte) fieldNumber | type)};
        else {
            byte[] b = getVarint32(fieldNumber);
            b[0] |= type;
            return b;
        }
    }

    /**
     * Одинаково для прото 2 и 3
     */
    private static byte[] getVarint32(int data) {
        ByteArrayOutputStream bout = new ByteArrayOutputStream();
        while(true) {
            byte elem = (byte) (data & 0xFF);
            data >>>= 7;
            if (data == 0) {
                bout.write(elem);
                break;
            } else bout.write(elem | 0x80);
        }
        //System.out.println("Varint: ");Utils.printMassive(b);
        return bout.toByteArray();
    }

    /**
     * Одинаково для прото 2 и 3
     */
    private static byte[] getVarint64(long data) {
        ByteArrayOutputStream bout = new ByteArrayOutputStream();
        while(true) {
            byte elem = (byte) (data & 0xFF);
            data >>>= 7;
            if (data == 0) {
                bout.write(elem);
                break;
            } else bout.write(elem | 0x80);
        }
        //System.out.println("Varint: "); Utils.printMassive(b);
        return bout.toByteArray();
    }

}
