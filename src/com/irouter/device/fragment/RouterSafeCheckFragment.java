package com.irouter.device.fragment;

import android.app.DialogFragment;
import android.os.Bundle;
import android.view.LayoutInflater;
import android.view.View;
import android.view.View.OnClickListener;
import android.view.ViewGroup;
import android.widget.Button;
import android.widget.Toast;

import com.irouter.R;

public class RouterSafeCheckFragment extends DialogFragment implements OnClickListener {
    public static final String TAG = RouterSafeCheckFragment.class.getSimpleName();
    private int up = 0, low = 0, no = 0, spl = 0, xtra = 0;
    private final int len = 0;
    private final int points = 0;
    private final int max = 8;
    private char c;

    private static boolean isFinished = false;
    private Button cancelSafeCheck;
    @Override
    public View onCreateView(LayoutInflater inflater, ViewGroup container, Bundle savedInstanceState) {
        View view = inflater.inflate(R.layout.fragment_safe_check, container);
        cancelSafeCheck = (Button) view.findViewById(R.id.btn_cancel_safe_check);
        cancelSafeCheck.setOnClickListener(this);
        return view;
    }

    @Override
    public void onClick(View v) {
        isFinished = false;
    }

    private void showMessage(CharSequence text) {
        int duration = Toast.LENGTH_SHORT;
        Toast toast = Toast.makeText(getActivity().getApplicationContext(), text, duration);
        toast.show();
    }

    private void calcStr(String pass)
    {
        int len = pass.length();
        int points = 0;
        if (len == 0)
        {
            showMessage("Invalid Input ");
        }
        if (len <= 5)
            points++;
        else if (len <= 10)
            points += 2;
        else
            points += 3;
        for (int i = 0; i < len; i++)
        {
            c = pass.charAt(i);
            if (c >= 'a' && c <= 'z') {
                if (low == 0)
                    points++;
                low = 1;
            }
            else
            {
                if (c >= 'A' && c <= 'Z') {
                    if (up == 0)
                        points++;
                    up = 1;
                }
                else
                {
                    if (c >= '0' && c <= '9') {
                        if (no == 0)
                            points++;
                        no = 1;
                    }
                    else
                    {
                        if (c == '_' || c == '@') {
                            if (spl == 0)
                                points += 1;
                            spl = 1;
                        }
                        else
                        {
                            if (xtra == 0)
                                points += 2;
                            xtra = 1;
                        }
                    }
                }
            }
        }
        if (points <= 3)
            showMessage("Password Strength : LOW ");
        else if (points <= 6)
            showMessage("Password Strength : MEDIUM ");
        else if (points <= 9)
            showMessage("Password Strength : HIGH ");
        points = 0;
        len = 0;
        up = 0;
        low = 0;
        no = 0;
        xtra = 0;
        spl = 0;
    }
}
