
package com.irouter.base;

import android.app.Activity;
import android.content.Intent;
import android.os.Bundle;
import android.os.StrictMode;

import com.irouter.util.LogUtil;
import com.irouter.util.NetworkUtil;

public class BaseActivity extends Activity {

    public static final String URL_PREFIX = "http://";
    public static final String DHCP_CLIENT_LIST_URL = "/dhcp_clientlist.asp";
    public static final String WLAN_CLIENT_LIST_URL = "/wl_hostset.asp";
    public static final String LOGIN_URL = "/goform/formLogin";

    public final static String VALIDATE_REUSLT = "com.irouter.VALIDATE_REUSLT";

    static final String[] SUPPROTED_DEVICE_ABBR = {
        "TPLINK", "MERCURY", "FAST", "TENDA", "NETCORE", "PHICOMM", "GAOKE", "HIWIFI", "DLINK",
        "NETGEAR", "ZTE", "TOTOLINK"
    };
    static final String[] SUPPROTED_DEVICE_NAME = {
        "", "(水星)", "(迅捷)", "(腾达)", "(磊科)", "(斐讯)", "(高科)", "(极路由)", "", "(网件)", "(中兴)", ""
    };

    public static boolean isValidated = false;
    public static String gateway = "192.168.1.1";
    public static String routerName = "";

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        // TODO Auto-generated method stub
        super.onCreate(savedInstanceState);
        StrictMode.ThreadPolicy policy = new StrictMode.
                ThreadPolicy.Builder().permitAll().build();
        StrictMode.setThreadPolicy(policy);
        routerName = NetworkUtil.getRouterName(this);
        // TODO: need uncomment
        gateway = (null == NetworkUtil.getGateway(this)) ? "" : NetworkUtil.getGateway(this);
        LogUtil.i("routerName = " + routerName + ", gateway = " + gateway);
    }

    public void startActivity(String intentString) {
        if (!isValidated()) {
            intentString = "com.irouter.account.LoginActivity";
        }

        Intent intent = new Intent(intentString);
        this.startActivity(intent);
    }

    @Override
    protected void onResume() {
        // TODO Auto-generated method stub
        super.onResume();
    }

    @Override
    protected void onPause() {
        // TODO Auto-generated method stub
        super.onPause();
    }

    @Override
    protected void onDestroy() {
        // TODO Auto-generated method stub
        super.onDestroy();
    }

    public static boolean isValidated() {
        return isValidated;
    }

    public static void setIsValidated(boolean isValidated) {
        BaseActivity.isValidated = isValidated;
    }
}
