package com.softdesign.devintensive.ui.activities;

import android.app.ProgressDialog;
import android.graphics.Color;
import android.graphics.drawable.ColorDrawable;
import android.support.v7.app.AppCompatActivity;
import android.util.Log;
import android.widget.Toast;

import com.softdesign.devintensive.BuildConfig;
import com.softdesign.devintensive.R;
import com.softdesign.devintensive.utils.ConstantManager;

public class BaseActivity extends AppCompatActivity {
    static final String TAG = ConstantManager.TAG_Prefix + "BaseActivity";
    protected ProgressDialog mProgressDialog;
    public int mCurrentEditMode = 0;

    public void showProgress() {
        if (BuildConfig.DEBUG) {
            Log.d(TAG, "showProgress");
        }
        if (mProgressDialog == null) {
            mProgressDialog = new ProgressDialog(this, R.style.custom_dialog);
            mProgressDialog.setCancelable(false);
            mProgressDialog.getWindow().setBackgroundDrawable(new ColorDrawable(Color.TRANSPARENT));
            mProgressDialog.show();
            mProgressDialog.setContentView(R.layout.progress_splash);
        } else {
            mProgressDialog.show();
            mProgressDialog.setContentView(R.layout.progress_splash);
        }
    }

    public void hideProgress() {
        if (BuildConfig.DEBUG) {
            Log.d(TAG, "hideProgress");
        }
        if (mProgressDialog != null) {
            if (mProgressDialog.isShowing()) {
                mProgressDialog.hide();
            }
        }
    }

    public void showError(String message, Exception error) {
        if (BuildConfig.DEBUG) {
            Log.e(TAG, String.valueOf(error));
        }
    }

    public void showToast(String message) {
        if (BuildConfig.DEBUG) {
            Log.d(TAG, "showToast");
        }
        Toast.makeText(this, message, Toast.LENGTH_LONG).show();

    }
}
