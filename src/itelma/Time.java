package itelma;

import java.util.Calendar;
import java.util.Date;
import java.util.TimeZone;

public class Time {
    private static final Object LOCK = new Object();
    private static Time localInstance = null;

    private Time() {
    }

    public static Time get() {
        synchronized (LOCK) {
            if (localInstance == null) {
                localInstance = new Time();
            }
        }
        return localInstance;
    }

    public String getStringTime(long timeMillis, boolean mS) {
        return getStringTime(timeMillis, mS, ":");
    }

    public String getStringTime(long timeMillis, boolean mS, String separator) {
        Date date = new Date(timeMillis);
        Calendar c = Calendar.getInstance(TimeZone.getTimeZone("UTC"));
        c.setTime(date);
        StringBuffer sB = new StringBuffer();
        try {
            String s = String.valueOf(c.get(Calendar.HOUR_OF_DAY));
            if (s.length() == 1) sB.append("0");
            sB.append(s).append(separator);
            s = String.valueOf(c.get(Calendar.MINUTE));
            if (s.length() == 1) sB.append("0");
            sB.append(s).append(separator);
            s = String.valueOf(c.get(Calendar.SECOND));
            if (s.length() == 1) sB.append("0");
            sB.append(s);
            if (mS) {
                sB.append(":");
                s = String.valueOf(c.get(Calendar.MILLISECOND));
                if (s.length() == 1) sB.append("00");
                else if (s.length() == 2) sB.append("0");
                sB.append(s);
            }
        } catch (Exception e) {
        }
        return sB.toString();
    }

}
