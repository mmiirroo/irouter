
package com.irouter.util;

import android.content.Context;
import android.net.ConnectivityManager;
import android.net.DhcpInfo;
import android.net.NetworkInfo;
import android.net.wifi.WifiInfo;
import android.net.wifi.WifiManager;

/**
 * Created by whole on 15-3-29.
 */
public class NetworkUtil {
    public  static String getGateway(Context context) {
        WifiManager wifiManager = (WifiManager) context.getSystemService(Context.WIFI_SERVICE);
        if(wifiManager.isWifiEnabled()) {
            wifiManager.setWifiEnabled(true);
        }

        DhcpInfo dhcpInfo = wifiManager.getDhcpInfo();
        return intToIp( dhcpInfo.gateway);
    }

    public static String getLocalIp(Context context) {
        WifiManager wifiManager = (WifiManager) context.getSystemService(Context.WIFI_SERVICE);
        if(wifiManager.isWifiEnabled()) {
            wifiManager.setWifiEnabled(true);
        }

        WifiInfo wifiInfo = wifiManager.getConnectionInfo();
        int ipAddress = wifiInfo.getIpAddress();
        String localIp = intToIp(ipAddress);
        return localIp;
    }


    public static String getRouterMac(Context context){
        WifiManager wifiManager = (WifiManager) context
                .getSystemService(Context.WIFI_SERVICE);
        return wifiManager.getConnectionInfo().getBSSID();
    }

    public static String getWifiMacAddress(Context context){
        WifiManager wifiManager = (WifiManager) context
                .getSystemService(Context.WIFI_SERVICE);
        return wifiManager.getConnectionInfo().getMacAddress();
    }

    public static String getWifiName(Context context) {
        WifiManager wifiManager = (WifiManager) context
                .getSystemService(Context.WIFI_SERVICE);
        return wifiManager.getConnectionInfo().getSSID();
    }

    public static String getRouterName(Context context) {
        WifiManager wifiManager = (WifiManager) context.getSystemService(Context.WIFI_SERVICE);
        WifiInfo wifiInfo = wifiManager.getConnectionInfo();
        return wifiInfo.getSSID();
    }

    private static String intToIp(int ipAddress) {
        return (ipAddress & 0xFF) + "." + ((ipAddress >> 8) & 0xFF) + "."
                + ((ipAddress >> 16) & 0xFF) + "." + ((ipAddress >> 24) & 0xFF);
    }

    /**
     * is wifi connecting or connected
     * 
     * @param context
     *            application context
     * @return boolean
     */
    @SuppressWarnings("deprecation")
    public static boolean isWifi(Context context) {
        ConnectivityManager connectivityManager = (ConnectivityManager) context.getSystemService(Context.CONNECTIVITY_SERVICE);
        NetworkInfo networkInfo = connectivityManager.getActiveNetworkInfo();
        if(null ==networkInfo || !connectivityManager.getBackgroundDataSetting()){
            return false;
        }

        int netType = networkInfo.getType();
        if(ConnectivityManager.TYPE_WIFI == netType){
            return networkInfo.isConnectedOrConnecting();
        }
        return false;
    }
}
