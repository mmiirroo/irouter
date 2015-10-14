
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
import android.widget.Toast;

import com.irouter.R;
import com.irouter.util.LogUtil;

public class AdminPasswordModifyFragment extends DialogFragment implements OnClickListener {

    public static final String TAG = AdminPasswordModifyFragment.class.getSimpleName();

    private EditText mNewAdminPassword;
    private EditText mNewAdminPasswordConfirm;
    private Button btnCommit;

    public AdminPasswordModifyFragment() {
        // Empty constructor required for DialogFragment
    }

    @Override
    public void onAttach(Activity activity) {
        super.onAttach(activity);
    }

    @Override
    public View onCreateView(LayoutInflater inflater, ViewGroup container, Bundle savedInstanceState) {
        View view = inflater.inflate(R.layout.fragment_admin_password_modify, container);
        mNewAdminPassword = (EditText) view.findViewById(R.id.new_admin_password);
        mNewAdminPasswordConfirm = (EditText) view.findViewById(R.id.new_admin_password_confirm);
        btnCommit = (Button) view.findViewById(R.id.btn_commit);

        // Set the dialog's title
        getDialog().setTitle(getString(R.string.admin_password_modify));

        btnCommit.setOnClickListener(this);

        return view;
    }

    @Override
    public void onClick(View v) {
        Editable newAdminpassword = mNewAdminPassword.getText();
        Editable newAdminpasswordConfirm = mNewAdminPasswordConfirm.getText();

        if (!TextUtils.isEmpty(newAdminpassword) && !TextUtils.isEmpty(newAdminpasswordConfirm)) {
            // TODO: set passwor to router
            LogUtil.d("set new admin password");

            if (newAdminpassword.equals(newAdminpasswordConfirm)) {
                // TODO:commit admin password

            } else {
                Toast.makeText(getActivity(), "You should enter new password twice !",
                        Toast.LENGTH_LONG)
                        .show();
            }
        }
        else {
            Toast.makeText(getActivity(), "You should enter new password twice !",
                    Toast.LENGTH_LONG)
                    .show();
        }
        dismiss();
    }
}
