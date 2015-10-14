package com.irouter;

import android.os.Bundle;
import android.view.View;
import android.view.View.OnClickListener;
import android.widget.Button;
import android.widget.TextView;

import com.irouter.base.BaseActivity;

public class MainActivity extends BaseActivity implements OnClickListener {
    private Button mWifiSpeedTest;
    private Button mLoginButton;
    private Button mMyDeviceButton;
    private TextView mRouterName;

    @Override
    public void onClick(View v) {
        String intent = "";
        switch (v.getId()) {
            case R.id.main_wifi_speed_test:
                intent = "com.irouter.network.speedtest.WifiSpeedTestActivity";
                break;
            case R.id.main_loginButton:
                intent = "com.irouter.account.LoginActivity";
                break;
            case R.id.main_mydevice:
                intent = "com.irouter.device.DeviceMyActivity";
                break;
            default:
                break;
        }

        super.startActivity(intent);
    }


    /**
     * Called when the activity is first created.
     */
    @Override
    public void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        this.setContentView(R.layout.network);

        mWifiSpeedTest = (Button) findViewById(R.id.main_wifi_speed_test);
        mWifiSpeedTest.setOnClickListener(this);

        mLoginButton = (Button) findViewById(R.id.main_loginButton);
        mLoginButton.setOnClickListener(this);

        mMyDeviceButton = (Button) findViewById(R.id.main_mydevice);
        mMyDeviceButton.setOnClickListener(this);

        if (isValidated()) {
            mLoginButton.setText(R.string.already_login);
            mLoginButton.setClickable(false);
        }

        mRouterName = (TextView) findViewById(R.id.TextViewRouterName);
        mRouterName.setText(routerName);
    }
}
