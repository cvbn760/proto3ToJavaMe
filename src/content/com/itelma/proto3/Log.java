package content.com.itelma.proto3;

public class Log {
    private static final String MARK = "";

    public static void i(String message) {
        StringBuffer sB = getSB(MARK);
        if (message == null) message = "-";
        print(sB.append(message).toString());
    }

    private static StringBuffer getSB(String pre) {
        return new StringBuffer(getTime() + getMess(pre) + "");
    }

    private static void print(String message) {
        System.out.println(getMess(message));
    }

    private static String getMess(String message) {
        return message == null ? "null" : message;
    }

    private static String getTime() {
        return Time.get().getStringTime(System.currentTimeMillis(), true) + " ";
    }

}
