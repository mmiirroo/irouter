
package com.irouter.device;

import java.util.ArrayList;
import java.util.List;

import android.app.AlertDialog;
import android.app.AlertDialog.Builder;
import android.content.DialogInterface;
import android.os.Bundle;
import android.view.View;
import android.view.View.OnClickListener;
import android.widget.AdapterView;
import android.widget.AdapterView.OnItemClickListener;
import android.widget.ArrayAdapter;
import android.widget.ListView;

import com.irouter.R;
import com.irouter.base.BaseActivity;
import com.irouter.device.fragment.AdminPasswordModifyFragment;
import com.irouter.device.fragment.RouterDetailInfoFragment;
import com.irouter.device.fragment.RouterRestoreBackupFragment;
import com.irouter.device.fragment.RouterSafeCheckFragment;
import com.irouter.device.fragment.WifiSettingsDialogFragment;
import com.irouter.util.LogUtil;

public class MyRouterActivity extends BaseActivity implements OnClickListener {
    private ListView myRouterListView;
    private ArrayAdapter<String> myRouterArrayAdapter;
    public final static String EXTRA_MESSAGE = "com.irouter.device.MyRouterActivity.MESSAGE";

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        this.setContentView(R.layout.my_router_new);

        List<String> options = new ArrayList<String>();
        options.add(getString(R.string.wifi_setting));
        options.add(getString(R.string.admin_password_modify));
        options.add(getString(R.string.router_detail_info));
        options.add(getString(R.string.safe_check));
        options.add(getString(R.string.router_restore_backup));
        options.add(getString(R.string.restart_router));
        myRouterArrayAdapter = new ArrayAdapter<String>(this, android.R.layout.simple_list_item_1,
                options);
        myRouterListView = (ListView) findViewById(R.id.tweetList);
        myRouterListView.setAdapter(myRouterArrayAdapter);

        myRouterListView.setOnItemClickListener(new OnItemClickListener() {
            @Override
            public void onItemClick(AdapterView<?> parent, View view, int position,
                    long id) {
                String entry = (String) parent.getAdapter().getItem(position);
                LogUtil.d("entry = " + entry + ", id = " + id);

                if (entry.endsWith(getString(R.string.wifi_setting))) {
                    showWifiSettings();

                } else if (entry.endsWith(getString(R.string.admin_password_modify))) {
                    showAdminPasswordModify();
                } else if (entry.endsWith(getString(R.string.router_detail_info))) {
                    showRouterDetailInfo();
                } else if (entry.endsWith(getString(R.string.safe_check))) {
                    showSafeCheck();
                } else if (entry.endsWith(getString(R.string.router_restore_backup))) {
                    showRouterRestoreBackup();
                } else if (entry.endsWith(getString(R.string.restart_router))) {
                    showRestartRouter();
                }

            }

            private void showRestartRouter() {
                AlertDialog.Builder builder = new Builder(MyRouterActivity.this);
                builder.setMessage("确认重启路由起吗？");
                builder.setTitle("提示");
                builder.setPositiveButton("确认", new DialogInterface.OnClickListener() {
                    @Override
                    public void onClick(DialogInterface dialog, int which) {
                        dialog.dismiss();
                        MyRouterActivity.this.restartRouter();
                    }
                });
                builder.setNegativeButton("取消", new DialogInterface.OnClickListener() {
                    @Override
                    public void onClick(DialogInterface dialog, int which) {
                        dialog.dismiss();
                    }
                });
                builder.create().show();
            }

            private void showRouterRestoreBackup() {
                RouterRestoreBackupFragment dialog = new RouterRestoreBackupFragment();
                dialog.show(getFragmentManager(), RouterRestoreBackupFragment.TAG);
            }

            private void showSafeCheck() {
                RouterSafeCheckFragment dialog = new RouterSafeCheckFragment();
                dialog.show(getFragmentManager(), RouterSafeCheckFragment.TAG);
            }

            private void showRouterDetailInfo() {
                RouterDetailInfoFragment dialog = new RouterDetailInfoFragment();
                dialog.show(getFragmentManager(), RouterDetailInfoFragment.TAG);
            }
        });
    }

    protected void restartRouter() {
        // TODO Auto-generated method stub

    }

    private void showWifiSettings() {
        WifiSettingsDialogFragment dialog = new WifiSettingsDialogFragment();
        dialog.show(getFragmentManager(), WifiSettingsDialogFragment.TAG);
    }

    private void showAdminPasswordModify() {
        AdminPasswordModifyFragment dialog = new AdminPasswordModifyFragment();
        dialog.show(getFragmentManager(), AdminPasswordModifyFragment.TAG);
    }

    @Override
    protected void onDestroy() {
        super.onDestroy();
    }

    @Override
    public void onClick(View v) {

    }
}
