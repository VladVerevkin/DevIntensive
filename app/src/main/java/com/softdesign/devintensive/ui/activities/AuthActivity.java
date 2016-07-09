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
import com.softdesign.devintensive.data.managers.DataManager;
import com.softdesign.devintensive.data.network.req.UserLoginReq;
import com.softdesign.devintensive.data.network.res.UserModelRes;
import com.softdesign.devintensive.utils.ConstantManager;
import com.softdesign.devintensive.utils.NetworkStatusChecker;

import retrofit2.Call;
import retrofit2.Callback;
import retrofit2.Response;

public class AuthActivity extends BaseActivity implements View.OnClickListener {
    private static final String TAG = ConstantManager.TAG_Prefix + "login Activity";

    private Button mSignIn;
    private TextView mRememberPassword;
    private EditText mLogin, mPassword;
    private CoordinatorLayout mCoordinatorLayout;
    private DataManager mDataManager;


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

        mDataManager = DataManager.getInstance();

        mCoordinatorLayout = (CoordinatorLayout) findViewById(R.id.main_coordinator_container);
        mSignIn = (Button) findViewById(R.id.login_btn);
        mRememberPassword = (TextView) findViewById(R.id.remember_txt);
        mLogin = (EditText) findViewById(R.id.login_email_et);
        mPassword = (EditText) findViewById(R.id.login_password_et);

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
                signIn();
                break;

            case R.id.remember_txt:
                rememberPassword();
                break;
        }
    }

    private void showSnackBar(String message) {
        Snackbar.make(mCoordinatorLayout, message, Snackbar.LENGTH_LONG);
    }

    private void rememberPassword() {
        Intent rememberIntent = new Intent(Intent.ACTION_VIEW, Uri.parse("http://devintensive.softdesign-apps.ru/forgotpass"));
        startActivity(rememberIntent);
    }

    private void loginSuccess(Response<UserModelRes> response) {
        mDataManager.getPreferenceManager().saveAuthToken(response.body().getData().getToken());
        mDataManager.getPreferenceManager().saveUserId(response.body().getData().getUser().getId());
        Intent loginIntent = new Intent(this, MainActivity.class);
        startActivity(loginIntent);
    }

    private void signIn() {
        if (NetworkStatusChecker.isNetworkAvailable(this)) {
            Call<UserModelRes> call = mDataManager.loginUser(new UserLoginReq(mLogin.getText().toString(), mPassword.getText().toString()));
            call.enqueue(new Callback<UserModelRes>() {
                @Override
                public void onResponse(Call<UserModelRes> call, Response<UserModelRes> response) {
                    if (response.code() == 200) {
                        loginSuccess(response);
                    } else if (response.code() == 404) {
                        showSnackBar("неверный логин или пароль");
                    } else {
                        showSnackBar("Всё совсем плохо!");
                    }
                }

                @Override
                public void onFailure(Call<UserModelRes> call, Throwable t) {
// TODO: 10.07.2016 обработать ошибки ретрофита 
                }
            });
        } else {
            showSnackBar("Сеть недоступна, попробуйте позже");
        }
    }
}