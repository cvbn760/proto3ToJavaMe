package content.io.protoinput.util;

import java.util.ArrayList;
import java.util.List;

public enum LineSplitterUtil {
    LineSplitterUtil() {
    };

    public static String[] split(String line) {
        String[] tempStrings = line.split("[\\s]++");
        List<String> stringsList = new ArrayList();
        String[] arr$ = tempStrings;
        int len$ = tempStrings.length;

        for (int i$ = 0; i$ < len$; ++i$) {
            String string = arr$[i$];
            if (string != null) {
                String tempString;
                if (string.contains("=") && string.length() > 1) {
                    tempString = string.substring(0, string.indexOf(61));
                    String string2 = string.substring(string.indexOf(61) + 1);
                    stringsList.add(tempString);
                    stringsList.add("=");
                    stringsList.add(string2);
                } else {
                    tempString = string.trim();
                    if (tempString.length() > 0) {
                        stringsList.add(tempString);
                    }
                }
            }
        }

        System.out.println(stringsList);
        return stringsList.toArray(new String[0]);
    }
}
