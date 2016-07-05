package com.softdesign.devintensive.ui.activities;

import android.app.Activity;
import android.content.Intent;
import android.os.Bundle;
import android.os.PersistableBundle;
import android.util.Log;
import android.view.View;
import android.widget.Button;

import com.softdesign.devintensive.R;
import com.softdesign.devintensive.utils.ConstantManager;

public class LoginActivity extends BaseActivity implements View.OnClickListener {
    private static final String TAG = ConstantManager.TAG_Prefix + "login Activity";


    private Button mBtnLogin;

    @Override
    public void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        Log.d(TAG,"onCreate1");
        setContentView(R.layout.login_activity);
        Log.d(TAG,"onCreate2");
        mBtnLogin = (Button) findViewById(R.id.btn_login);
        Log.d(TAG,"onCreate3");
        mBtnLogin.setOnClickListener(this);


    }

    @Override
    protected void onStop() {
        super.onStop();
    }

    @Override
    protected void onStart() {
        super.onStart();
    }

  /*  @Override
    protected void onSaveInstanceState(Bundle outState) {
        super.onSaveInstanceState(outState);
        Log.d(TAG, "onSaveInstanceState");
        outState.putInt(ConstantManager.EDIT_MODE_KEY, );

    }
*/
    @Override
    protected void onPause() {
        super.onPause();
    }

    @Override
    protected void onResume() {
        super.onResume();
    }

    @Override
    protected void onDestroy() {
        super.onDestroy();
    }

    @Override
    protected void onRestart() {
        super.onRestart();
    }

    @Override
    public void onClick(View v) {
        Log.d(TAG,"onClick1");
        switch (v.getId()) {

            case R.id.btn_login:
                Log.d(TAG,"onClick2");
                Intent intent = new Intent(this, MainActivity.class);
                startActivity(intent);
                break;
        }
    }
}