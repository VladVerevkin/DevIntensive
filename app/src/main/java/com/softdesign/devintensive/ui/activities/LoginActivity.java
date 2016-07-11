package com.softdesign.devintensive.ui.activities;

import android.content.Intent;
import android.os.Bundle;
import android.util.Log;
import android.view.View;
import android.widget.Button;

import com.softdesign.devintensive.BuildConfig;
import com.softdesign.devintensive.R;
import com.softdesign.devintensive.utils.ConstantManager;

public class LoginActivity extends BaseActivity implements View.OnClickListener {
    private static final String TAG = ConstantManager.TAG_Prefix + "login Activity";

    private Button mBtnLogin;

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
        setContentView(R.layout.login_activity);
        mBtnLogin = (Button) findViewById(R.id.btn_login);

        mBtnLogin.setOnClickListener(this);
    }

    @Override
    protected void onStop() {
        if (BuildConfig.DEBUG) {
            Log.d(TAG, "onStop");
        }
        super.onStop();
    }

    @Override
    protected void onStart() {
        if (BuildConfig.DEBUG) {
            Log.d(TAG, "onStart");
        }
        super.onStart();
    }

    @Override
    protected void onSaveInstanceState(Bundle outState) {
        if (BuildConfig.DEBUG) {
            Log.d(TAG, "onSaveInstanceState");
        }
        super.onSaveInstanceState(outState);
        Log.d(TAG, "onSaveInstanceState");
        outState.putInt(ConstantManager.EDIT_MODE_KEY, mCurrentEditMode);

    }

    @Override
    protected void onPause() {
        if (BuildConfig.DEBUG) {
            Log.d(TAG, "onPause");
        }
        super.onPause();
    }

    @Override
    protected void onResume() {
        if (BuildConfig.DEBUG) {
            Log.d(TAG, "onResume");
        }
        super.onResume();
    }

    @Override
    protected void onDestroy() {
        if (BuildConfig.DEBUG) {
            Log.d(TAG, "onDestroy");
        }
        super.onDestroy();
    }

    @Override
    protected void onRestart() {
        if (BuildConfig.DEBUG) {
            Log.d(TAG, "onRestart");
        }
        super.onRestart();
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
            case R.id.btn_login:
                Intent intent = new Intent(this, MainActivity.class);
                startActivity(intent);
                break;
        }
    }
}