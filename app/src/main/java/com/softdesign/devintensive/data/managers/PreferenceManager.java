package com.softdesign.devintensive.data.managers;

import android.content.SharedPreferences;
import android.net.Uri;
import android.util.Log;

import com.softdesign.devintensive.utils.ConstantManager;
import com.softdesign.devintensive.utils.DevIntensiveApplication;

import java.util.ArrayList;
import java.util.List;

public class PreferenceManager {
    private SharedPreferences mSharedPreferences;
    private static final String[] USER_FIELDS = {ConstantManager.USER_PHONE_KEY,
            ConstantManager.USER_MAIL_KEY, ConstantManager.USER_VK_KEY, ConstantManager.USER_GIT_KEY, ConstantManager.USER_BIO_KEY};

    public PreferenceManager() {
        this.mSharedPreferences = DevIntensiveApplication.getSharedPreferences();

    }

    public void saveUserProfileData(List<String> userFields) {
        SharedPreferences.Editor editor = mSharedPreferences.edit();
        for (int i = 0; i < USER_FIELDS.length; i++) {
            editor.putString(USER_FIELDS[i], userFields.get(i));
        }
        editor.apply();
    }

    public List<String> loadUserProfileData() {
        List<String> userFields = new ArrayList<>();
        userFields.add(mSharedPreferences.getString(ConstantManager.USER_PHONE_KEY, "null"));
        userFields.add(mSharedPreferences.getString(ConstantManager.USER_MAIL_KEY, "null"));
        userFields.add(mSharedPreferences.getString(ConstantManager.USER_VK_KEY, "null"));
        userFields.add(mSharedPreferences.getString(ConstantManager.USER_GIT_KEY, "null"));
        userFields.add(mSharedPreferences.getString(ConstantManager.USER_BIO_KEY, "null"));
        return userFields;
    }

    public void saveUserPhoto(Uri uri) {
        SharedPreferences.Editor editor = mSharedPreferences.edit();
        editor.putString(ConstantManager.USER_PHOTO_KEY, uri.toString());
        editor.apply();
    }

    public Uri loadUserPhoto() {
        return Uri.parse(mSharedPreferences.getString(ConstantManager.USER_PHOTO_KEY,
                "android.resource://com.softdesign.devintensive.drawable/user_bg"));
    }

    /**
     * получение номера телефона для интента
     *
     * @return
     */
    public String getUserPhone() {
        return mSharedPreferences.getString(ConstantManager.USER_PHONE_KEY, "");
    }

    /**
     * получение почты для интента
     *
     * @return
     */
    public String getUserMail() {
        return mSharedPreferences.getString(ConstantManager.USER_MAIL_KEY, "");
    }

    /**
     * получение ссылки на страницу VK для интента
     *
     * @return
     */
    public String getUserVK() {
        return mSharedPreferences.getString(ConstantManager.USER_VK_KEY, "");
    }

    /**
     * получение ссылки на страницу gitа для интента
     *
     * @return
     */
    public String getUserGit() {
        return mSharedPreferences.getString(ConstantManager.USER_GIT_KEY, "");
    }
}
