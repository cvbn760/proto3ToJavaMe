package content.com.itelma.proto3;

/**
 * @author klokov
 */
public class ByteService {

    public synchronized String intToHexString(int val) {
        return Integer.toHexString(val);
    }

    public synchronized int hexStringToInt(String hex) {
        return Integer.parseInt(hex, 16);
    }

    public synchronized String byteArrayToHexString(byte[] b, int size) {
        String ret = "";
        for (int i = 0; i < size; i++) {
            String hex = Integer.toHexString(b[i] & 0xFF);
            if (hex.length() == 1) {
                hex = '0' + hex;
            }
            ret += hex.toUpperCase();
        }
        return ret;
    }

    public synchronized String byteToHexString(byte b) {
        String ret = "";
        String hex = Integer.toHexString(b & 0xFF);
        if (hex.length() == 1) {
            hex = '0' + hex;
        }
        ret += hex.toUpperCase();
        return ret;
    }

    public synchronized byte[] intToByteArray(int val) {
        byte[] result = new byte[4];
        result[0] = (byte) ((val & 0xFF000000) >> 24);
        result[1] = (byte) ((val & 0x00FF0000) >> 16);
        result[2] = (byte) ((val & 0x0000FF00) >> 8);
        result[3] = (byte) ((val & 0x000000FF) >> 0);
        return result;
    }

    public synchronized byte[] hexStringToByteArray(String hex) {
        byte[] b = new byte[hex.length() / 2];
        for (int i = 0; i < b.length; i++) {
            int index = i * 2;
            int v = Integer.parseInt(hex.substring(index, index + 2), 16);
            b[i] = (byte) v;

        }
        return b;
    }

    public synchronized String addZeroInString(String hex, int before, int after) {
        for (int i = 0; i < before; i++) {
            hex = "0" + hex;
        }
        for (int j = 0; j < after; j++) {
            hex = hex + "0";
        }
        return hex;
    }

    public synchronized boolean compareByteArray(byte[] expected, byte[] actual) {
        if (expected == null || actual == null) {
            return false;
        }
        if (expected.length != actual.length) {
            return false;
        }
        for (int i = 0; i < expected.length; i++) {
            if (expected[i] != actual[i]) {
                return false;
            }
        }
        return true;
    }

    public synchronized int byteArrayToInt(byte[] bytes) {
        int value = 0;
        for (int i = 0; i < bytes.length; i++) {
            int shift = (bytes.length - 1 - i) * 8;
            value += (bytes[i] & 0x000000FF) << shift;
        }
        return value;
    }

    public synchronized void changeBitInByte(byte[] array, int index, boolean value) {
        int val = value ? 1 : 0;
        if (index < (array.length * 8)) {
            byte need = array[index / 8];
            int posInByte = 8 - (index % 8);
            if (val == 0) {
                need = (byte) (need & ~(1 << (posInByte - 1)));
            } else {
                need = (byte) (need | (1 << (posInByte - 1)));
            }
            array[index / 8] = need;
        }
    }

    public synchronized byte[] longToByteArray(long val) {
        byte[] result = new byte[8];
        for (int i = 7; i >= 0; i--) {
            result[i] = (byte) (val & 0xFF);
            val >>= 8;
        }
        return result;
    }

    public synchronized long byteArrayToLong(byte[] array) {
        long result = 0;
        for (int i = 0; i < 8; i++) {
            result <<= 8;
            result |= (array[i] & 0xFF);
        }
        return result;
    }

    public synchronized byte[] floatToByteArray(float val){
        int intBits = Float.floatToIntBits(val);
        byte[] bs = new byte[]{
                (byte) (intBits >> 24),
                (byte) (intBits >> 16),
                (byte) (intBits >> 8),
                (byte) (intBits)
        };
        return bs;
    }

    public synchronized float byteArrayToFloat(byte[] array) {
        int intBits = array[0] << 24 | (array[1] & 0xFF) << 16 | (array[2] & 0xFF) << 8 | (array[3] & 0xFF);
        return Float.intBitsToFloat(intBits);
    }

    public synchronized byte[] doubleToByteArray(double val) {
        final byte[] bytes = new byte[8];
        long double64Long = Double.doubleToLongBits(val);
        bytes[0] = (byte) ((double64Long >> 56) & 0xff);
        bytes[1] = (byte) ((double64Long >> 48) & 0xff);
        bytes[2] = (byte) ((double64Long >> 40) & 0xff);
        bytes[3] = (byte) ((double64Long >> 32) & 0xff);
        bytes[4] = (byte) ((double64Long >> 24) & 0xff);
        bytes[5] = (byte) ((double64Long >> 16) & 0xff);
        bytes[6] = (byte) ((double64Long >> 8) & 0xff);
        bytes[7] = (byte) ((double64Long >> 0) & 0xff);
        return bytes;
    }

    public synchronized double byteArrayToDouble(byte[] bytes) {
        return Double.longBitsToDouble(((bytes[0] & 0xFFL) << 56)
                | ((bytes[1] & 0xFFL) << 48)
                | ((bytes[2] & 0xFFL) << 40)
                | ((bytes[3] & 0xFFL) << 32)
                | ((bytes[4] & 0xFFL) << 24)
                | ((bytes[5] & 0xFFL) << 16)
                | ((bytes[6] & 0xFFL) << 8)
                | ((bytes[7] & 0xFFL) << 0));
    }

    public synchronized byte[] shortToByteArray(short val) {
        return new byte[]{(byte) ((val & 0xFF00) >> 8), (byte) (val & 0x00FF)};
    }

    public synchronized short byteArrayToShort(byte[] array) {
        int r = array[0] & 0xFF;
        r = (r << 8) | (array[1] & 0xFF);
        short s = (short) r;
        return s;
    }
}
