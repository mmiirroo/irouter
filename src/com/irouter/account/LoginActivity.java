
package com.irouter.account;

import java.io.IOException;

import org.jsoup.Jsoup;
import org.jsoup.nodes.Document;

import android.content.Context;
import android.content.Intent;
import android.content.SharedPreferences;
import android.os.Bundle;
import android.util.Base64;
import android.util.Log;
import android.view.View;
import android.view.View.OnClickListener;
import android.widget.Button;
import android.widget.CheckBox;
import android.widget.EditText;
import android.widget.Toast;

import com.irouter.MainActivity;
import com.irouter.R;
import com.irouter.base.BaseActivity;
import com.irouter.util.LogUtil;

/**
 * Created by whole on 2015-05-12.
 */
public class LoginActivity extends BaseActivity implements OnClickListener
{
    private EditText mUserName;
    private EditText mPassword;
    private Button mLoginButton;
    private SharedPreferences mSharedPreferences;
    private CheckBox mAutoLogin;

    @Override
    public void onClick(View v) {
        switch (v.getId()) {
            case R.id.ButtonLogin:
                login(true);
                break;
            default:
                break;
        }
    }

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        this.setContentView(R.layout.router_login);

        mUserName = (EditText) findViewById(R.id.EditTextUserName);
        mUserName.setText("admin");

        mPassword = (EditText) findViewById(R.id.EditTextPassword);
        mLoginButton = (Button) findViewById(R.id.ButtonLogin);
        mLoginButton.setOnClickListener(this);

        mSharedPreferences = getSharedPreferences("account", Context.MODE_PRIVATE);
        mAutoLogin = (CheckBox) findViewById(R.id.CheckBoxAutoLogin);

        String username = mSharedPreferences.getString("username", "");
        String password = mSharedPreferences.getString("password", "");
        boolean autoLogin = mSharedPreferences.getBoolean("autologin", false);

        if (autoLogin) {
            mUserName.setText(username);
            mPassword.setText(password);
            mAutoLogin.setChecked(true);
            login(false);
        }

    }

    @Override
    protected void onDestroy() {
        super.onDestroy();
    }

    private void login(boolean needEncrypted) {
        String username = mUserName.getText().toString();
        String password = mPassword.getText().toString();

        if (0 == username.length()) {
            isValidated = false;
            Toast.makeText(this, "please enter username", Toast.LENGTH_SHORT).show();
            return;
        }
        if (0 == password.length()) {
            isValidated = false;
            Toast.makeText(this, "please enter password", Toast.LENGTH_SHORT).show();
            return;
        }

        String passwordEncode = "";
        if (needEncrypted) {
            passwordEncode = Base64.encodeToString(password.getBytes(), Base64.DEFAULT);
            LogUtil.d("needEncrypted = " + needEncrypted + ", passwordEncode = " + passwordEncode);

        } else {
            passwordEncode = password;
        }
        // TODO: to be removed before release
        gateway = "192.168.1.1";
        // TODO: need to put in xml or properites for different router
        String loginUrl = "http://" + gateway + LOGIN_URL;
        LogUtil.d("loginUrl = " + loginUrl);

        try {
            Document loginResultDoc = Jsoup.connect(loginUrl)
                    .data("Language", "Chinese")
                    .data("Language_set", "Chinese")
                    .data("login", "登录")
                    .data("password", passwordEncode)
                    .data("username", username).post();
            String loginResultString = loginResultDoc.outerHtml();
            LogUtil.d("loginResultString = " + loginResultString);

            if (-1 == loginResultString.indexOf("alert(") || -1 != loginResultString.indexOf("退出")) {
                // save username, password and autologin
                SharedPreferences.Editor editor = mSharedPreferences.edit();
                editor.putString("username", username);
                editor.putString("password", passwordEncode);
                if (mAutoLogin.isChecked()) {
                    editor.putBoolean("autologin", true);
                } else {
                    editor.putBoolean("autologin", false);
                }
                editor.commit();

                // start the next activity
                Intent intent = new Intent();
                intent.setClass(this, MainActivity.class);
                isValidated = true;

                this.startActivity(intent);
            } else {
                Toast.makeText(this, "username not exist or password wrong", Toast.LENGTH_SHORT)
                .show();
            }
        } catch (IOException e) {
            LogUtil.e(Log.getStackTraceString(e));
        }
    }
}