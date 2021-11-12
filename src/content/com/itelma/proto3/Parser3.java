package content.com.itelma.proto3;

import java.io.*;
import java.util.Vector;
/**
 *
 * @author kozenkov
 */
public class Parser3 {

    public static Pair getPair(byte[] b, int startPosition) throws ProtoParserError {
        IntData data = getVarint32(b, startPosition);                       //System.out.println("startPos = " + startPosition);
        int key = data.value;                                               //System.out.println("key = " + key);
        int fieldNumber = key >> 3;                                         //System.out.println("field = " + fieldNumber);
        byte wireType = (byte) (key & 0x07);                                //System.out.println("wire = " + wireType);
        int startContent =  startPosition + data.sizeProto;                 //System.out.println("startContent = " + startContent);
        int sizeContent;
        switch (wireType) {
            case Pair.VARINT: {sizeContent = getSizeVarint(b, startContent); break;}
            case Pair.BIT64: {sizeContent = 8; break;}
            case Pair.LENGTH_DEL: {
                IntData sizeVarint = getVarint32(b, startContent);
                startContent += sizeVarint.sizeProto;
                sizeContent = sizeVarint.value;
                break;
            }
            case Pair.BIT32: {sizeContent = 4; break;}
            default: {sizeContent = 0; break;}
        }
        int endContent = startContent + sizeContent;
        byte[] content = new byte[sizeContent];
        try {
            System.arraycopy(b, startContent, content, 0, sizeContent);
        } catch (IndexOutOfBoundsException e) {
            throw new ProtoParserError(e.getMessage());
        } catch (ArrayStoreException e) {
            throw new ProtoParserError(e.getMessage());
        } catch (NullPointerException e) {
            throw new ProtoParserError(e.getMessage());
        }
        return new Pair(fieldNumber, wireType, content, endContent);
    }

    // определение кол-ва байт, занимаемых значением типа Varint
    private static int getSizeVarint(byte[] b, int start) {
        final int MAX_LENGTH_VARINT = 11;
        int size = 0;
        int length = b.length - start; if (length > MAX_LENGTH_VARINT) length = MAX_LENGTH_VARINT;
        for (int i = start; i < start + length; i++) {
            if ((b[i] & 0x80) == 0) {
                size = 1 + i - start; break;
            }
        }
        return size;
    }

    public static boolean getBool(byte[] content) throws ProtoParserError {
        return getVarint32(content, 0).value == 1;
    }
    
    public static int getEnum(byte[] content) throws ProtoParserError {
        return getVarint32(content, 0).value;
    }

    public static int getInt32(byte[] content) throws ProtoParserError {
        return (int)getVarint64(content, 0).value;
    }

    public static int getUInt32(byte[] content) throws ProtoParserError {
        return getVarint32(content, 0).value;
    }

    public static long getUInt64(byte[] content) throws ProtoParserError {
        return getVarint64(content, 0).value;
    }

    public static long getInt64(byte[] content) throws ProtoParserError {
        return getVarint64(content, 0).value;
    }

    public static int getFixed32(byte[] b) {
        return getReverseInt32(b);
    }

    public static int getSFixed32(byte[] b) {
        return getReverseInt32(b);
    }

    private static int getReverseInt32(byte[] b) {
        if (b.length == 4) {
            int i = b[0] & 0xFF;
            i += (b[1] << 8)  & 0x0000FF00;
            i += (b[2] << 16) & 0x00FF0000;
            i += (b[3] << 24) & 0xFF000000;
            return i;
        } else return 0;
    }

    public static float getFloat(byte[] b) throws ProtoParserError {
        ByteArrayInputStream iS = new ByteArrayInputStream(getReverse(b));
        DataInputStream dIs = new DataInputStream(iS);
        float r;
        try {
            r = dIs.readFloat();
        } catch (IOException e) {
            throw new ProtoParserError("");
        }
        return r;
    }    

    public static Vector getPackedFloat(byte[] data) throws ProtoParserError {
        Vector v = new Vector();
        if (data.length == 0 || data.length % 4 != 0) throw new ProtoParserError("");
        byte[] b = new byte[4];
        for (int i=0; i < data.length; i += 4) {
             System.arraycopy(data, i, b, 0, 4);
             v.addElement(new Float(getFloat(b)));
        }
        return v;
    }
    
    public static int getSInt32(byte[] b) throws ProtoParserError {
        int raw = getVarint32(b, 0).value;
        int temp = (((raw << 31) >> 31) ^ raw) >> 1;
        return temp ^ (raw & (1 << 31));
    }
/*
    // не проверено!
    public static long getSInt64(byte[] b) throws ProtoParserError {
        long raw = getVarint64(b, 0).value;
        long temp = (((raw << 63) >> 63) ^ raw) >> 1;
        return temp ^ (raw & (1L << 63));
    }
*/
    public static byte[] getBytes(byte[] content) {return content;}

    private static IntData getVarint32(byte[] b, int start) throws ProtoParserError {
        int size = getSizeVarint(b, start);
        if (size == 1) return new IntData(b[start], size);
        else {
            int step = 0, res = 0;
            try {
                for (int i = start; i < start + size; i++) {
                    res |= (b[i] & 0x7F) << step;
                    step += 7;
                }
            } catch (ArrayIndexOutOfBoundsException e) {
                throw new ProtoParserError(e.getMessage());
            }
            return new IntData(res, size);
        }
    }

    private static LongData getVarint64(byte[] b, int start) throws ProtoParserError {
        int size = getSizeVarint(b, start);
        if (size == 1) return new LongData(b[start], size);
        else {
            int step = 0, resL = 0, resH=0;
            long res;
            try {
                for (int i = start; i < start + size; i++) {
                    if (step < 28) resL |= (b[i] & 0x7F) << step;
                    else {
                        if (step == 28) {
                            resL |= (b[i] & 0x0F) << 28;
                            resH |= (b[i] & 0x7F) >> 4;
                        } else resH |= (b[i] & 0x7F) << (step - 32);
                    }
                    step += 7;
                }
                res = resH; res <<= 32;
                res |= (resL & 0xFFFFFFFFL);
            } catch (ArrayIndexOutOfBoundsException e) {
                throw new ProtoParserError(e.getMessage());
            }
            return new LongData(res, size);
        }
    }

    public static String getStringIntVector(String caption, Vector data) {
        String r = "";
        for (int i=0; i < data.size(); i++) {
            r += caption + ": " + ((Integer) data.elementAt(i)) + "\r\n";
        }
        return r;
    }

    public static String getStringBytesVector(String caption, Vector data) {
        String r = "";
        for (int i = 0; i < data.size(); i++) {
            String s = new ByteService().byteToHexString((Byte) data.elementAt(i));
            r += caption + ": " + s + "\r\n";
        }
        return r;
    }

    public static String getString(byte[] data) {
        String res = "";
        try {
            res = new String(data, "UTF-8");
        } catch (UnsupportedEncodingException e) {}
        return res;
    }

    public static Vector getPackedUInt32(byte[] data) {
        return getPacketVarint(data);
    }

    public static Vector getPackedInt32(byte[] data) { return getPacketVarint(data); }

    private static Vector getPacketVarint(byte[] data) {
        Vector v = new Vector();
        try {
            for (int i=0; i < data.length;) {
                int size = getSizeVarint(data, i);
                if (size == 0) break;
                int r = getVarint32(data, i).value;
                v.addElement(new Integer(r));
                i += size;
            }
        } catch (ProtoParserError protoParserError) {}
        return v;
    }

    public static Vector getPackedSInt32(byte[] data) {
        if (data == null) return new Vector();
        Vector v = new Vector();
        try {
            for (int i=0; i < data.length;) {
                int size = getSizeVarint(data, i);
                if (size == 0) break;
                int r = getVarint32(data, i).value;
                int temp = (((r << 31) >> 31) ^ r) >> 1;
                v.addElement(new Integer(temp ^ (r & (1 << 31))));
                i += size;
            }
        } catch (ProtoParserError protoParserError) {}
        return v;
    }
    
    public static double getDouble(byte[] b) throws ProtoParserError {
        if (b == null || b.length != 8) throw new ProtoParserError("");
        ByteArrayInputStream iS = new ByteArrayInputStream(getReverse(b));
        DataInputStream diS = new DataInputStream(iS);
        double r;
        try {
            r = diS.readDouble();
        } catch (IOException ex) {
            throw new ProtoParserError("");
        }
        return r;
    }

    public static byte[] getReverse(byte[] b) {
        if (b == null || b.length == 0) return new byte[0];
        byte[] r = new byte[b.length];
        for (int i=0; i < b.length; i++) {
            r[b.length - i - 1] = b[i];
        }
        return r;
    }

    public static int[] getUInts(Vector v) {
        if (v == null) return new int[0];
        int[] r = new int[v.size()];
        for(int i=0; i < v.size(); i++) {
            try {
                r[i] = Parser3.getUInt32((byte[]) v.elementAt(i));
            } catch (ProtoParserError e) {
                Log.i("ppe");}
        }
        return r;
    }
    public static int[] getInts(Vector v) {
        if (v == null) return new int[0];
        int[] r = new int[v.size()];
        for(int i=0; i < v.size(); i++) {
            try {
                r[i] = Parser3.getInt32((byte[]) v.elementAt(i));
            } catch (ProtoParserError e) {
                Log.i("ppe");}
        }
        return r;
    }
    
}
