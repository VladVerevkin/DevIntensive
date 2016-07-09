package com.softdesign.devintensive.data.managers;

import android.content.SharedPreferences;
import android.net.Uri;
import android.util.Log;
import android.util.Patterns;

import com.softdesign.devintensive.ui.activities.BaseActivity;
import com.softdesign.devintensive.ui.activities.MainActivity;
import com.softdesign.devintensive.utils.ConstantManager;
import com.softdesign.devintensive.utils.DevIntensiveApplication;

import java.util.ArrayList;
import java.util.List;
import java.util.regex.Matcher;
import java.util.regex.Pattern;

public class PreferenceManager extends MainActivity {

    private SharedPreferences mSharedPreferences;
    private static final String[] USER_FIELDS = {ConstantManager.USER_PHONE_KEY,
            ConstantManager.USER_MAIL_KEY, ConstantManager.USER_VK_KEY, ConstantManager.USER_GIT_KEY, ConstantManager.USER_BIO_KEY};

    public PreferenceManager() {
        this.mSharedPreferences = DevIntensiveApplication.getSharedPreferences();

    }

    public void saveUserProfileData(List<String> userFields) {
        SharedPreferences.Editor editor = mSharedPreferences.edit();

        //
        for (int i = 0; i < USER_FIELDS.length; i++) {
            switch (USER_FIELDS[i]) {
                case ConstantManager.USER_PHONE_KEY:

                    if (checkPhoneUserInput(userFields.get(i)) == true) {
                        editor.putString(USER_FIELDS[i], userFields.get(i));
                    } else {
                        showSnackbar("Формат номера +x xxx xxx-xx-xx");

                    }
                    break;
                case ConstantManager.USER_MAIL_KEY:

                    if (checkMailUserInput(userFields.get(i)) == true) {
                        editor.putString(USER_FIELDS[i], userFields.get(i));
                    } else {
                        showSnackbar("Формат адреса aaa@aaa.aa");
                    }
                    break;

                case ConstantManager.USER_VK_KEY:

                    if (checkVKUserInput(userFields.get(i)) == true) {
                        editor.putString(USER_FIELDS[i], userFields.get(i));
                    } else {
                        showSnackbar("Формат номера адреса VK.com/aaaaa");
                    }
                    break;

                case ConstantManager.USER_GIT_KEY:

                    if (checkGITUserInput(userFields.get(i)) == true) {
                        editor.putString(USER_FIELDS[i], userFields.get(i));
                    } else {
                        showSnackbar("Формат номера адреса github.com/aaaa");
                    }
                    break;
            }
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

    public static boolean checkPhoneUserInput(String userPhone) {
        Pattern p = Pattern.compile("^\\+[0-9]{1}\\s[0-9]{3}\\s[0-9]{3}-[0-9]{2}-[0-9]{2}$");
        Matcher m = p.matcher(userPhone);
        return m.matches();
    }

    public static boolean checkMailUserInput(String userMail) {
        Pattern p = Pattern.compile("^[_A-Za-z0-9-\\+]+(\\.[_A-Za-z0-9-]+)*@[A-Za-z0-9-]+(\\.[A-Za-z0-9]+)*(\\.[A-Za-z]{2,})$");
        Matcher m = p.matcher(userMail);
        return m.matches();
    }


    public static boolean checkVKUserInput(String userVK) {

        return android.util.Patterns.WEB_URL.matcher(userVK).matches();
    }

    public static boolean checkGITUserInput(String userGIT) {
        /*Pattern p = Pattern.compile("^[0-9]$");
        Matcher m = p.matcher(userGIT);
        return m.matches();*/

        return android.util.Patterns.WEB_URL.matcher(userGIT).matches();
    }

    public void saveAuthToken(String AuthToken){
        SharedPreferences.Editor editor = mSharedPreferences.edit();
        editor.putString(ConstantManager.AUTH_TOKEN_KEY, AuthToken);
        editor.apply();
    }

    public String getAuthToken(){
        return mSharedPreferences.getString(ConstantManager.AUTH_TOKEN_KEY, "null");
    }

    public void saveUserId(String userId){
        SharedPreferences.Editor editor = mSharedPreferences.edit();
        editor.putString(ConstantManager.USER_ID_KEY, userId);
        editor.apply();
    }

    public String getUserId(){
        return mSharedPreferences.getString(ConstantManager.USER_ID_KEY, "null");
    }

}