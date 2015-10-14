package com.irouter.util;

import java.net.InetAddress;

public class Ping {

    private static final String TAG = Ping.class.getSimpleName();
    private static final String CMD = "/system/bin/ping -q -n -w 1 -c 1 %s";
    private static final int TIMEOUT = 1000;

    public static void doPing(String host) {
        try {
            // TODO: Use ProcessBuilder ?
            Runtime.getRuntime().exec(String.format(CMD, host));
        } catch (Exception e) {
            LogUtil.e(TAG, "Can't use native ping: " + e.getMessage());
            try {
                if (InetAddress.getByName(host).isReachable(TIMEOUT)) {
                    LogUtil.i(TAG, "Using Java ICMP request instead ...");
                }
            } catch (Exception e1) {
                LogUtil.e(TAG, e1.getMessage());
            }
        }
    }
}
