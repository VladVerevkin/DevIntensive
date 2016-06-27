package com.softdesign.devintensive.ui.activities;

import android.content.Context;
import android.graphics.Bitmap;
import android.graphics.drawable.Drawable;
import android.os.Handler;
import android.support.design.widget.CoordinatorLayout;
import android.support.design.widget.FloatingActionButton;
import android.support.design.widget.NavigationView;
import android.support.design.widget.Snackbar;
import android.support.v4.view.GravityCompat;
import android.support.v4.widget.DrawerLayout;
import android.support.v7.app.ActionBar;
import android.support.v7.app.AppCompatActivity;
import android.os.Bundle;
import android.support.v7.widget.Toolbar;
import android.util.Log;
import android.view.MenuItem;
import android.view.View;
import android.view.inputmethod.InputMethodManager;
import android.widget.EditText;
import android.widget.ImageView;

import com.softdesign.devintensive.R;
import com.softdesign.devintensive.data.managers.DataManager;
import com.softdesign.devintensive.utils.ConstantManager;
import com.softdesign.devintensive.utils.RoundedAvatarDrawable;

import java.util.ArrayList;
import java.util.List;

public class MainActivity extends BaseActivity implements View.OnClickListener {

    private static final String TAG = ConstantManager.TAG_Prefix + "Main Activity";

    private DataManager mDataManager;
    private int mCurrentEditMode = 0;

    private CoordinatorLayout mCoordinatorLayout;
    private Toolbar mToolbar;
    private DrawerLayout mNavigationDrawer;

    private FloatingActionButton mFab;
    private EditText mUserPhone, mUserMail, mUserVK, mUserGit, mUserBio;
    private List<EditText> mUserInfoViews;
    private Boolean mFlag=false;

    private RoundedAvatarDrawable mAvatar;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_main);
        mDataManager = DataManager.getInstance();
        mCoordinatorLayout = (CoordinatorLayout) findViewById(R.id.main_coordinator_container);
        mToolbar = (Toolbar) findViewById(R.id.toolbar);
        mFab = (FloatingActionButton) findViewById(R.id.fab);

        mNavigationDrawer = (DrawerLayout) findViewById(R.id.navigation_drawer);
        mUserPhone = (EditText) findViewById(R.id.phone_et);
        mUserMail = (EditText) findViewById(R.id.email_et);
        mUserVK = (EditText) findViewById(R.id.vkcom_et);
        mUserGit = (EditText) findViewById(R.id.github_et);
        mUserBio = (EditText) findViewById(R.id.about_et);
        mUserInfoViews = new ArrayList<>();
        mUserInfoViews.add(mUserPhone);
        mUserInfoViews.add(mUserMail);
        mUserInfoViews.add(mUserVK);
        mUserInfoViews.add(mUserGit);
        mUserInfoViews.add(mUserBio);

        mFab.setOnClickListener(this);



        setupToolbar();
        setupDrawer();

        loadUserInfoValue();

        if (savedInstanceState == null) {

        } else {
            mCurrentEditMode = savedInstanceState.getInt(ConstantManager.EDIT_MODE_KEY, 0);
            changeEditMode(mCurrentEditMode);
        }

        Log.d(TAG, "onCreate");
    }




    @Override
    protected void onStart() {
        super.onStart();
        Log.d(TAG, "onStart");
    }

    @Override
    protected void onResume() {
        super.onResume();
        Log.d(TAG, "onResume");
    }

    @Override
    protected void onPause() {
        super.onPause();
        Log.d(TAG, "onPause");
        saveUserInfoValue();
    }

    @Override
    protected void onStop() {
        super.onStop();
        Log.d(TAG, "onStop");
    }

    @Override
    protected void onDestroy() {
        super.onDestroy();
        Log.d(TAG, "onDestroy");
    }

    @Override
    protected void onRestart() {
        super.onRestart();
        Log.d(TAG, "onRestart");
    }

    @Override
    public void onClick(View v) {
        switch (v.getId()) {
            case R.id.fab:
                if (mCurrentEditMode == 0) {
                    changeEditMode(1);
                    mCurrentEditMode = 1;
                } else {
                    changeEditMode(0);
                    mCurrentEditMode = 0;
                }

                break;

        }

    }

    @Override
    protected void onSaveInstanceState(Bundle outState) {
        super.onSaveInstanceState(outState);
        Log.d(TAG, "onSaveInstanceState");
        outState.putInt(ConstantManager.EDIT_MODE_KEY, mCurrentEditMode);

    }


    private void showSnackbar(String message) {
        Log.d(TAG, "showSnackbar");
        Snackbar.make(mCoordinatorLayout, message, Snackbar.LENGTH_LONG).show();


    }

    private void setupToolbar() {
        setSupportActionBar(mToolbar);
          ActionBar actionbar = getSupportActionBar();
        if (actionbar != null) {
            actionbar.setHomeAsUpIndicator(R.drawable.ic_menu_black_24dp);
            actionbar.setDisplayHomeAsUpEnabled(true);

        }
    }

    private void changeEditMode(int mode) {
        mFab.setImageResource(R.drawable.ic_done_black_24dp);
        if (mode == 1) {
            for (EditText userValue : mUserInfoViews) {
                userValue.setEnabled(true);
                userValue.setFocusable(true);
                userValue.setFocusableInTouchMode(true);
            }
        } else {
            mFab.setImageResource(R.drawable.ic_create_black_24dp);
            for (EditText userValue : mUserInfoViews) {
                userValue.setEnabled(false);
                userValue.setFocusable(false);
                userValue.setFocusableInTouchMode(false);
                saveUserInfoValue();
            }
        }

    }

    private void loadUserInfoValue() {
        List<String> userData = mDataManager.getPreferenceManager().loadUserProfileData();
        for (int i = 0; i < userData.size(); i++) {
            mUserInfoViews.get(i).setText(userData.get(i));
        }
    }

    private void saveUserInfoValue() {
        List<String> userData = new ArrayList<>();
        for (EditText userFieldView : mUserInfoViews) {
            userData.add(userFieldView.getText().toString());
        }
        mDataManager.getPreferenceManager().saveUserProfileData(userData);
    }


    private void setupDrawer() {
        NavigationView navigationView = (NavigationView) findViewById(R.id.navigation_view);
        Log.d(TAG, "SetupDrawer");
        navigationView.setNavigationItemSelectedListener(new NavigationView.OnNavigationItemSelectedListener() {
            @Override
            public boolean onNavigationItemSelected(MenuItem item) {
                showSnackbar(item.getTitle().toString());
                item.setChecked(true);
                mNavigationDrawer.closeDrawer(GravityCompat.START);
                return false;
            }

        });


    }

    @Override
    public boolean onOptionsItemSelected(MenuItem item) {
        Log.d(TAG, "onOptionsItemSelected");
        if (item.getItemId() == android.R.id.home) {
            mNavigationDrawer.openDrawer(GravityCompat.START);
            mFlag=true;
        }

        return super.onOptionsItemSelected(item);
    }

    @Override
    public void onBackPressed() {
        if (mFlag == true) {
            Log.d(TAG, "true");
            mFlag=false;
            mNavigationDrawer.closeDrawer(GravityCompat.START);

        }
        else {

            Log.d(TAG, "false");
            super.onBackPressed();
        }
    }



}
