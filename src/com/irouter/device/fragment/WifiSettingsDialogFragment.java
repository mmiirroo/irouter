
package com.irouter.device.fragment;

import android.app.Activity;
import android.app.DialogFragment;
import android.os.Bundle;
import android.text.Editable;
import android.text.TextUtils;
import android.view.LayoutInflater;
import android.view.View;
import android.view.View.OnClickListener;
import android.view.ViewGroup;
import android.widget.Button;
import android.widget.EditText;
import android.widget.TextView;
import android.widget.Toast;

import com.irouter.R;
import com.irouter.util.LogUtil;

public class WifiSettingsDialogFragment extends DialogFragment implements OnClickListener {

    public static final String TAG = WifiSettingsDialogFragment.class.getSimpleName();

    private EditText mWifiPassword;
    private TextView mWifiName;
    private Button btnOK;
    private Button btnCancel;

    public WifiSettingsDialogFragment() {
        // Empty constructor required for DialogFragment
    }

    @Override
    public void onAttach(Activity activity) {
        super.onAttach(activity);
    }

    @Override
    public View onCreateView(LayoutInflater inflater, ViewGroup container, Bundle savedInstanceState) {
        View view = inflater.inflate(R.layout.fragment_wifi_setting, container);
        mWifiName = (TextView) view.findViewById(R.id.wifi_name_value);
        mWifiPassword = (EditText) view.findViewById(R.id.wifi_password_value);
        btnOK = (Button) view.findViewById(R.id.btn_ok);

        // Set the dialog's title
        getDialog().setTitle(getString(R.string.wifi_setting));

        btnOK.setOnClickListener(this);

        return view;
    }

    @Override
    public void onClick(View v) {
        switch (v.getId()) {
            case R.id.btn_ok:
                Editable password = mWifiPassword.getText();
                if (!TextUtils.isEmpty(password)) {
                    // TODO: set passwor to router
                    LogUtil.d("set wifi password");
                }
                else
                    Toast.makeText(getActivity(), "You should enter password !", Toast.LENGTH_LONG)
                    .show();
                break;
            case R.id.btn_cancel:
                break;

        }
        dismiss();
    }
}
