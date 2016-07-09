package com.softdesign.devintensive.ui.activities;

import android.content.Intent;
import android.net.Uri;
import android.os.Bundle;
import android.support.design.widget.CoordinatorLayout;
import android.support.design.widget.Snackbar;
import android.util.Log;
import android.view.View;
import android.widget.Button;
import android.widget.EditText;
import android.widget.TextView;

import com.softdesign.devintensive.BuildConfig;
import com.softdesign.devintensive.R;
import com.softdesign.devintensive.utils.ConstantManager;

public class AuthActivity extends BaseActivity implements View.OnClickListener {
    private static final String TAG = ConstantManager.TAG_Prefix + "login Activity";

    private Button mSignIn;
    private TextView mRememberPassword;
    private EditText mLogin, mPassword;
    private CoordinatorLayout mCoordinatorLayout;

    /**
     * инициализация страницы логина и основных параметров
     *
     * @param savedInstanceState
     */
    @Override
    public void onCreate(Bundle savedInstanceState) {
        if (BuildConfig.DEBUG) {
            Log.d(TAG, "onCreate");
        }
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_auth);

        mCoordinatorLayout = (CoordinatorLayout)findViewById(R.id.main_coordinator_container);
        mSignIn = (Button) findViewById(R.id.login_btn);
        mRememberPassword = (TextView)findViewById(R.id.remember_txt);
        mLogin = (EditText)findViewById(R.id.login_email_et);
        mPassword = (EditText)findViewById(R.id.login_password_et);

        mRememberPassword.setOnClickListener(this);
        mSignIn.setOnClickListener(this);
    }



    /**
     * ожидание нажатия кнопок
     *
     * @param v
     */
    @Override
    public void onClick(View v) {
        if (BuildConfig.DEBUG) {
            Log.d(TAG, "onClick");
        }
        switch (v.getId()) {
            case R.id.login_btn:
                loginSuccess();
                break;

            case R.id.remember_txt:
                rememberPassword();
                break;
        }
    }

    private void showSnackBar(String message){
        Snackbar.make(mCoordinatorLayout, message, Snackbar.LENGTH_LONG);
    }
    private void rememberPassword(){
    Intent rememberIntent = new Intent(Intent.ACTION_VIEW, Uri.parse("http://devintensive.softdesign-apps/forgotpass"));
        startActivity(rememberIntent);
    }

    private void loginSuccess(){
       // Intent intent = new Intent(this, MainActivity.class);
       // startActivity(intent);
        showSnackBar("Вход");
    }

}