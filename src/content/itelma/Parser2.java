package content.itelma;

import java.io.UnsupportedEncodingException;
import java.util.Vector;
public class Parser2 {

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
        System.arraycopy(b, startContent, content, 0, sizeContent);
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

    public static int getEnum(byte[] content) throws ProtoParserError {
        return getVarint32(content, 0).value;
    }

    public static int getInt32(byte[] content) throws ProtoParserError {
        return getVarint32(content, 0).value;
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
    public static String getString(byte[] data) {
        String res = "";
        try {
            res = new String(data, "UTF-8");
        } catch (UnsupportedEncodingException e) {}
        return res;
    }    
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
    
    public static String getStringBytesVector(String caption, Vector data) {
        String r = "";
        for (int i = 0; i < data.size(); i++) {
            String s = new ByteService().byteToHexString((Byte) data.elementAt(i));
            r += caption + ": " + s + "\r\n";
        }
        return r;
    }    
}
