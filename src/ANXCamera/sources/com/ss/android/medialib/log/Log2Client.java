package com.ss.android.medialib.log;

public class Log2Client {
    private static ILog2Client sLog2Client;

    public static void init() {
        Log2ClientInvoker.nativeInit();
    }

    public static void logToLocal(int i, String str) {
        if (sLog2Client != null) {
            sLog2Client.logToLocal(i, str);
        }
    }

    public static void register(ILog2Client iLog2Client) {
        init();
        sLog2Client = iLog2Client;
    }
}
