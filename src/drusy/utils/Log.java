package drusy.utils;

import java.util.Date;

public class Log {
    public static void Debug(String context, String message) {
        System.out.println("[ " + new Date().toString() + " ] [ " + context + "] " + message);
    }
}
