package com.irouter.device;

import java.io.IOException;
import java.util.HashMap;
import java.util.Map;

import org.jsoup.Jsoup;
import org.jsoup.nodes.Document;
import org.jsoup.nodes.Element;
import org.jsoup.select.Elements;

import android.os.Bundle;
import android.util.Log;
import android.view.Gravity;
import android.widget.GridLayout;
import android.widget.ImageView;
import android.widget.TableRow;
import android.widget.TextView;

import com.irouter.R;
import com.irouter.base.*;
import com.irouter.device.entity.*;
import com.irouter.util.LogUtil;
import com.irouter.util.NetworkUtil;

public class DeviceListActivity extends BaseActivity {

    private Map<String, DhcpClient> mDhcpClientHashMap = null;
    private GridLayout mGridLayout;
    private TextView mRouterNameAndIP;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.device_list);

        mGridLayout = (GridLayout) findViewById(R.id.GridLayoutTerminalDeviceList);
        mRouterNameAndIP = (TextView) findViewById(R.id.TextViewRouterNameAndIP);
        StringBuffer routerNameIP = new StringBuffer();
        routerNameIP.append(NetworkUtil.getRouterName(this)).append("\n")
                .append(NetworkUtil.getGateway(this));
        mRouterNameAndIP.setText(routerNameIP.toString());

        mDhcpClientHashMap = new HashMap<String, DhcpClient>();

        String dhcpClientListUrl = URL_PREFIX + gateway + DHCP_CLIENT_LIST_URL;

        try {
            Document dhcpDoc = Jsoup.connect(dhcpClientListUrl).get();
            Element dhcpTable = dhcpDoc.select("table.table01").last();
            Elements dhcpLinks = dhcpTable.select("tr");
            int rowIndex = 0;
            for (int i = 2; i < dhcpLinks.size(); i++) {
                Elements tds = dhcpLinks.get(i).select("td");

                DhcpClient client = new DhcpClient(tds.get(0).text(), tds.get(1).text(), tds.get(2)
                        .text());
                LogUtil.d(client.toString());
                mDhcpClientHashMap.put(client.getMac(), client);

                ImageView imageView = new ImageView(this);
                imageView.setImageResource(R.drawable.ic_launcher);
                // 指定该组件所占的行
                GridLayout.Spec rowSpec = GridLayout.spec(rowIndex);
                // 指定该组件所占的列
                GridLayout.Spec columnSpec = GridLayout.spec(0);
                // 按照设置好的行和列来建立对象
                GridLayout.LayoutParams params = new GridLayout.LayoutParams(rowSpec, columnSpec);
                // 指定该组件占满父控件
                params.setGravity(Gravity.FILL_VERTICAL);
                mGridLayout.addView(imageView, params);

                TextView deviceNameValueView = new TextView(this);
                deviceNameValueView.setText(client.getDeviceName());
                // 指定该组件所占的行
                GridLayout.Spec deviceNameRowSpec = GridLayout.spec(rowIndex);
                // 指定该组件所占的列
                GridLayout.Spec deviceNameColumnSpec = GridLayout.spec(1);
                // 按照设置好的行和列来建立对象
                GridLayout.LayoutParams deviceNameParams = new GridLayout.LayoutParams(
                        deviceNameRowSpec, deviceNameColumnSpec);
                // 指定该组件占满父控件
                deviceNameParams.setGravity(Gravity.FILL_VERTICAL);
                mGridLayout.addView(deviceNameValueView, deviceNameParams);

                TextView ipValueView = new TextView(this);
                ipValueView.setText(client.getIp());
                ipValueView.setLayoutParams(new TableRow.LayoutParams(3));
                // 指定该组件所占的行
                GridLayout.Spec ipRowSpec = GridLayout.spec(rowIndex);
                // 指定该组件所占的列
                GridLayout.Spec ipColumnSpec = GridLayout.spec(2);
                // 按照设置好的行和列来建立对象
                GridLayout.LayoutParams ipParams = new GridLayout.LayoutParams(ipRowSpec,
                        ipColumnSpec);
                // 指定该组件占满父控件
                ipParams.setGravity(Gravity.FILL_VERTICAL);
                mGridLayout.addView(ipValueView, ipParams);
                rowIndex = rowIndex + 1;
            }

        } catch (IOException e) {
            LogUtil.e(Log.getStackTraceString(e));
        }

    }

    @Override
    protected void onDestroy() {
        // TODO Auto-generated method stub
        super.onDestroy();
    }

}
