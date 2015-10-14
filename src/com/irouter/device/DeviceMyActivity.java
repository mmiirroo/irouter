
package com.irouter.device;

import android.os.Bundle;
import android.view.View;
import android.view.View.OnClickListener;
import android.widget.Button;
import android.widget.ImageButton;
import android.widget.TextView;

import com.irouter.R;
import com.irouter.base.BaseActivity;

public class DeviceMyActivity extends BaseActivity implements OnClickListener {
    private TextView mRouterName;
    private Button mLoginButton;
    private ImageButton mDeviceCheckListButton;
    private ImageButton mMyRouterButton;
    private ImageButton mRateControlButton;
    private ImageButton mChildLockButton;
    private ImageButton mFileShareButton;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        this.setContentView(R.layout.device_my);

        mRouterName = (TextView) findViewById(R.id.TextViewDeviceMyRouterName);
        mRouterName.setText(routerName);

        mLoginButton = (Button) findViewById(R.id.ButtonDeviceMyLogin);
        mLoginButton.setOnClickListener(this);
        if (isValidated()) {
            mLoginButton.setText(R.string.already_login);
            mLoginButton.setClickable(false);
        }

        mDeviceCheckListButton = (ImageButton) findViewById(R.id.ImageButtonDeviceCheckList);
        mDeviceCheckListButton.setOnClickListener(this);

        mMyRouterButton = (ImageButton) findViewById(R.id.ImageButtonMyRouter);
        mMyRouterButton.setOnClickListener(this);

        mRateControlButton = (ImageButton) findViewById(R.id.ImageButtonRateControl);
        mRateControlButton.setOnClickListener(this);

        mChildLockButton = (ImageButton) findViewById(R.id.ImageButtonChildLock);
        mChildLockButton.setOnClickListener(this);

        mFileShareButton = (ImageButton) findViewById(R.id.ImageButtonFileShare);
        mFileShareButton.setOnClickListener(this);
    }

    @Override
    protected void onDestroy() {
        super.onDestroy();
    }

    @Override
    public void onClick(View v) {

        String intent = "";
        switch (v.getId()) {
            case R.id.ButtonDeviceMyLogin:
                intent = "com.irouter.account.LoginActivity";
                break;
            case R.id.ImageButtonDeviceCheckList:
                intent = "com.irouter.device.DeviceListActivity";
                break;
            case R.id.ImageButtonMyRouter:
                intent = "com.irouter.device.MyRouterActivity";
            default:
                break;
        }

        this.startActivity(intent);

    }

}
