
package com.irouter.network.optimization;

import java.util.ArrayList;
import java.util.HashMap;
import java.util.List;
import java.util.Map;

import android.annotation.SuppressLint;
import android.app.Activity;
import android.content.Context;
import android.net.wifi.ScanResult;
import android.net.wifi.WifiInfo;
import android.net.wifi.WifiManager;
import android.os.Bundle;
import android.os.StrictMode;

public class NetworkOptimizationActivity extends Activity {

    private WifiManager wifiManager = null;
    private WifiInfo wifiInfo = null;
    private String currentSSID = "";

    private Map<Integer, List<ScanResult>> scanResultsMapByChannel;

    @SuppressLint("UseSparseArrays")
    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        scanResultsMapByChannel = new HashMap<Integer, List<ScanResult>>();
        wifiManager = (WifiManager) getSystemService(Context.WIFI_SERVICE);
        wifiInfo = wifiManager.getConnectionInfo();
        currentSSID = wifiInfo.getSSID();
        splitScanResultByChannel();

        StrictMode.ThreadPolicy policy = new StrictMode.
                ThreadPolicy.Builder().permitAll().build();
        StrictMode.setThreadPolicy(policy);

        int minDeviceCounts = Integer.MAX_VALUE;
        int channelOnMinDeviceCounts = -1;

        for (Integer channel : scanResultsMapByChannel.keySet()) {
            List<ScanResult> singleScanResultList = scanResultsMapByChannel.get(channel);
            int size = singleScanResultList.size();
            if (minDeviceCounts > size) {
                minDeviceCounts = size;
                channelOnMinDeviceCounts = channel;
            }
        }

        // TODO:post change the router to channelOnMinDeviceCounts
    }

    @Override
    protected void onDestroy() {
        super.onDestroy();
    }

    public void splitScanResultByChannel() {
        List<ScanResult> scanResults = wifiManager.getScanResults();
        for (ScanResult result : scanResults) {
            int channel = getChannelByFrequency(result.frequency);
            List<ScanResult> scanResultList = scanResultsMapByChannel.get(channel);
            if (null == scanResultList) {
                scanResultList = new ArrayList<ScanResult>();
            }
            scanResultList.add(result);
            scanResultsMapByChannel.put(channel, scanResultList);
        }
    }

    public int getCurrentChannel() {
        List<ScanResult> scanResults = wifiManager.getScanResults();
        for (ScanResult result : scanResults) {
            if (result.BSSID.equalsIgnoreCase(wifiInfo.getBSSID())
                    && result.SSID.equalsIgnoreCase(currentSSID.substring(1,
                            currentSSID.length() - 1))) {
                return getChannelByFrequency(result.frequency);
            }
        }
        return -1;
    }

    private int getChannelByFrequency(int frequency) {
        int channel = -1;
        switch (frequency) {
            case 2412:
                channel = 1;
                break;
            case 2417:
                channel = 2;
                break;
            case 2422:
                channel = 3;
                break;
            case 2427:
                channel = 4;
                break;
            case 2432:
                channel = 5;
                break;
            case 2437:
                channel = 6;
                break;
            case 2442:
                channel = 7;
                break;
            case 2447:
                channel = 8;
                break;
            case 2452:
                channel = 9;
                break;
            case 2457:
                channel = 10;
                break;
            case 2462:
                channel = 11;
                break;
            case 2467:
                channel = 12;
                break;
            case 2472:
                channel = 13;
                break;
            case 2484:
                channel = 14;
                break;
            case 5745:
                channel = 149;
                break;
            case 5765:
                channel = 153;
                break;
            case 5785:
                channel = 157;
                break;
            case 5805:
                channel = 161;
                break;
            case 5825:
                channel = 165;
                break;
        }
        return channel;
    }
}
