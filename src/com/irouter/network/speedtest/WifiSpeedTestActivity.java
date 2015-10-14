package com.irouter.network.speedtest;

import android.app.Activity;
import android.content.Context;
import android.graphics.Bitmap;
import android.graphics.BitmapFactory;
import android.net.wifi.WifiManager;
import android.os.Bundle;
import android.os.Handler;
import android.os.HandlerThread;
import android.os.Message;
import android.util.Log;
import android.widget.ImageView;
import android.widget.TextView;
import android.widget.Toast;

import com.irouter.R;
import com.irouter.network.entity.WifiSpeedInfo;
import com.irouter.util.FileUtil;
import com.irouter.util.LogUtil;

public class WifiSpeedTestActivity extends Activity {

    private final int UPDATE_SPEED = 1;
    private final static String TAG = WifiSpeedTestActivity.class.getSimpleName();
    private WifiManager mWifiManager = null;

    // speed test thread
    private Handler mSpeedTestHandler = null;
    private final static HandlerThread mSpeedTestHandlerThread = new HandlerThread(
            "WifiSpeedTestHandlerThread");

    // down load the file thread
    private Handler mFileReadHandler = null;
    private final static HandlerThread mFileReadHandlerThread = new HandlerThread(
            "FileReadtHandlerThread");

    /**
     * Handler for post message into OS
     */
    private Handler mMainHandler = null;

    private TextView mInstantNetworkSpeed;
    private TextView mWifiSpeedTestPercent = null;
    private String url = "";
    private final WifiSpeedInfo wifiSpeedInfo = null;
    private ImageView imageView = null;
    private byte[] imageData = null;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        this.setContentView(R.layout.wifi_speed_test_detail);
        mWifiManager = (WifiManager) getSystemService(Context.WIFI_SERVICE);
        mInstantNetworkSpeed = (TextView) findViewById(R.id.instant_network_speed_value);
        url = this.getString(R.string.wifi_speed_test_url);
        mWifiSpeedTestPercent = (TextView) findViewById(R.id.wifi_speedtest_progress);
        imageView = (ImageView) findViewById(R.id.wifi_speed_test_image);

        if (!mWifiManager.isWifiEnabled()) {
            Toast.makeText(this, "Wifi is not enabled", Toast.LENGTH_SHORT).show();
            return;
        }
        /*
         * WifiInfo wifiInfo = this.mWifiManager.getConnectionInfo(); int
         * linkSpeed = wifiInfo.getLinkSpeed(); LogUtil.d("link speed" +
         * linkSpeed); StringBuffer stringBuffer = new StringBuffer();
         * stringBuffer.append(linkSpeed > 0 ? linkSpeed :
         * 0).append(WifiInfo.LINK_SPEED_UNITS);
         * this.mInstantNetworkSpeed.setText(stringBuffer.toString());
         */
        mMainHandler = new Handler(getMainLooper()) {
            @Override
            public void handleMessage(Message msg) {
                int value = msg.what;
                switch (value) {
                    case UPDATE_SPEED:
                        WifiSpeedTestActivity.this.updateView();
                        break;
                    default:
                        break;
                }
            }
        };

        // 开启handlerThread，在使用handlerThread.getLooper()之前必须先调用start方法，否则取出的是空
        mFileReadHandlerThread.start();
        mFileReadHandler = new Handler(mFileReadHandlerThread.getLooper());
        mFileReadHandler.post(mFileReadRunnable);

        mSpeedTestHandlerThread.start();
        mSpeedTestHandler = new Handler(mSpeedTestHandlerThread.getLooper());
        mSpeedTestHandler.post(mSpeedTestRunnable);

        // mTotalTxBytesOnCreate = TrafficStats.getTotalTxBytes();
    }

    /**
     * Update the view method
     */
    private void updateView()
    {
        mInstantNetworkSpeed.setText(wifiSpeedInfo.speed + "bytes/s");
        mWifiSpeedTestPercent.setText(wifiSpeedInfo.downloadPercent + "%");

        if (imageData != null)
        {
            Bitmap b = BitmapFactory.decodeByteArray(imageData, 0,
                    imageData.length);
            imageView.setImageBitmap(b);
        }
    }

    private final Runnable mSpeedTestRunnable = new Runnable() {
        @Override
        public void run() {
            Log.i(TAG, "mSpeedTestRunnable is running");

            while (wifiSpeedInfo.hadFinishedBytes < wifiSpeedInfo.totalBytes) {
                wifiSpeedInfo.downloadPercent = (int) ((wifiSpeedInfo.hadFinishedBytes / (double) wifiSpeedInfo.totalBytes) * 100);
                try {
                    Thread.sleep(1500);
                } catch (InterruptedException e) {
                    LogUtil.e(Log.getStackTraceString(e));
                }

                LogUtil.i("send the message to update");
                // update view
                mSpeedTestHandler.sendEmptyMessage(UPDATE_SPEED);
            }

            // finished
            if (wifiSpeedInfo.hadFinishedBytes == wifiSpeedInfo.totalBytes) {
                wifiSpeedInfo.downloadPercent = (int) ((wifiSpeedInfo.hadFinishedBytes / (double) wifiSpeedInfo.totalBytes) * 100);
                LogUtil.i("send the message to update and stop");
                mSpeedTestHandler.sendEmptyMessage(UPDATE_SPEED);
            }
        }
    };

    private final Runnable mFileReadRunnable = new Runnable() {
        @Override
        public void run() {
            Log.i(TAG, "mFileReadRunnable is running");
            imageData = FileUtil.getFileFromUrl(url, wifiSpeedInfo);
        };
    };

    @Override
    protected void onDestroy() {
        super.onDestroy();
        mWifiManager = null;
        imageView = null;
    }

}
