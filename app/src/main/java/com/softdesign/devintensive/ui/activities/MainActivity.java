package com.softdesign.devintensive.ui.activities;

import android.app.Dialog;
import android.content.ContentValues;
import android.content.DialogInterface;
import android.content.Intent;
import android.content.pm.PackageManager;
import android.graphics.Bitmap;
import android.graphics.Color;
import android.graphics.drawable.BitmapDrawable;
import android.net.Uri;
import android.os.Environment;
import android.provider.MediaStore;
import android.provider.Settings;
import android.support.annotation.NonNull;
import android.support.design.widget.AppBarLayout;
import android.support.design.widget.CollapsingToolbarLayout;
import android.support.design.widget.CoordinatorLayout;
import android.support.design.widget.FloatingActionButton;
import android.support.design.widget.NavigationView;
import android.support.design.widget.Snackbar;
import android.support.v4.app.ActivityCompat;
import android.support.v4.content.ContextCompat;
import android.support.v4.view.GravityCompat;
import android.support.v4.widget.DrawerLayout;
import android.support.v7.app.ActionBar;
import android.os.Bundle;
import android.support.v7.app.AlertDialog;
import android.support.v7.widget.Toolbar;
import android.util.Log;
import android.view.MenuItem;
import android.view.View;
import android.widget.EditText;
import android.widget.ImageView;
import android.widget.RelativeLayout;

import com.softdesign.devintensive.BuildConfig;
import com.softdesign.devintensive.R;
import com.softdesign.devintensive.data.managers.DataManager;
import com.softdesign.devintensive.data.managers.PreferenceManager;
import com.softdesign.devintensive.utils.ConstantManager;
import com.softdesign.devintensive.utils.RoundedAvatarDrawable;
import com.squareup.picasso.Picasso;

import java.io.File;
import java.io.IOException;
import java.text.SimpleDateFormat;
import java.util.ArrayList;
import java.util.Date;
import java.util.List;

public class MainActivity extends BaseActivity implements View.OnClickListener {

    private static final String TAG = ConstantManager.TAG_Prefix + "Main Activity";

    private DataManager mDataManager;


    private CoordinatorLayout mCoordinatorLayout;
    private Toolbar mToolbar;
    private DrawerLayout mNavigationDrawer;
    private RelativeLayout mProfilePlaceholder;
    private CollapsingToolbarLayout mCollapsingToolbar;
    private AppBarLayout.LayoutParams mAppBarParams = null;
    private AppBarLayout mAppBarLayout;

    private ImageView mCall;
    private ImageView mEmail;
    private ImageView mVK;
    private ImageView mGitHub;

    private FloatingActionButton mFab;
    private EditText mUserPhone, mUserMail, mUserVK, mUserGit, mUserBio;
    private List<EditText> mUserInfoViews;
    private Boolean mFlag = false;
    private File mPhotoFile = null;
    private Uri mSelectedImage = null;
    private ImageView mProfileImage;


    @Override
    protected void onCreate(Bundle savedInstanceState) {
        if (BuildConfig.DEBUG) {
            Log.d(TAG, "onCreate");
        }
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_main);
        mDataManager = DataManager.getInstance();
        mCoordinatorLayout = (CoordinatorLayout) findViewById(R.id.main_coordinator_container);
        mToolbar = (Toolbar) findViewById(R.id.toolbar);
        mFab = (FloatingActionButton) findViewById(R.id.fab);
        mProfilePlaceholder = (RelativeLayout) findViewById(R.id.profile_placeholder);
        mCollapsingToolbar = (CollapsingToolbarLayout) findViewById(R.id.collapsing_toolbar);
        mAppBarLayout = (AppBarLayout) findViewById(R.id.appbar_layout);
        mProfileImage = (ImageView) findViewById(R.id.user_photo_img);
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
        mProfilePlaceholder.setOnClickListener(this);

        setupToolbar();
        setupDrawer();


        loadUserInfoValue();
        mCall = (ImageView) findViewById(R.id.call_img);
        mCall.setOnClickListener(this);
        mEmail = (ImageView) findViewById(R.id.send_img);
        mEmail.setOnClickListener(this);
        mVK = (ImageView) findViewById(R.id.vk_view);
        mVK.setOnClickListener(this);
        mGitHub = (ImageView) findViewById(R.id.github_view);
        mGitHub.setOnClickListener(this);

        Picasso.with(this)
                .load(mDataManager.getPreferenceManager().loadUserPhoto())
                .placeholder(R.drawable.user_bg)// TODO: 02.07.2016 01:38:04
                // сделать ПЛЭЙСХОЛДЕТ ТРАНСПАРЕНТ+CROP
                .into(mProfileImage);


        if (savedInstanceState == null) {

        } else {
            mCurrentEditMode = savedInstanceState.getInt(ConstantManager.EDIT_MODE_KEY, 0);
            changeEditMode(mCurrentEditMode);
        }


    }


    @Override
    protected void onStart() {
        super.onStart();
        if (BuildConfig.DEBUG) {
            Log.d(TAG, "onStart");
        }

    }

    @Override
    protected void onResume() {
        super.onResume();
        if (BuildConfig.DEBUG) {
            Log.d(TAG, "onResume");
        }
    }

    @Override
    protected void onPause() {
        if (BuildConfig.DEBUG) {
            Log.d(TAG, "onPause");
        }
        super.onPause();
        saveUserInfoValue();

    }

    @Override
    protected void onStop() {
        if (BuildConfig.DEBUG) {
            Log.d(TAG, "onStop");
        }
        super.onStop();
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

    @Override
    public void onClick(View v) {
        if (BuildConfig.DEBUG) {
            Log.d(TAG, "onClick");
        }
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

            case R.id.profile_placeholder:
                // TODO: 02.07.2016  сделать выбор откуда загружать фото
                showDialog(ConstantManager.LOAD_PROFILE_PHOTO);
                break;
            case R.id.call_img:
                // TODO: 02.07.2016 Набор номера Неявный интент
                call();
                break;
            case R.id.send_img:
                // TODO: 02.07.2016 Набор номера Неявный интент
                sendEmail();
                break;

            case R.id.vk_view:
                // TODO: 02.07.2016 Набор номера Неявный интент
                vkView();
                break;

            case R.id.github_view:
                // TODO: 02.07.2016 Набор номера Неявный интент
                gitHubView();
                break;
        }

    }

    @Override
    protected void onSaveInstanceState(Bundle outState) {
        if (BuildConfig.DEBUG) {
            Log.d(TAG, "onSaveInstanceState");
        }
        super.onSaveInstanceState(outState);
        outState.putInt(ConstantManager.EDIT_MODE_KEY, mCurrentEditMode);

    }

    private void showSnackbar(String message) {
        if (BuildConfig.DEBUG) {
            Log.d(TAG, "showSnackbar");
        }
        Snackbar.make(mCoordinatorLayout, message, Snackbar.LENGTH_LONG).show();


    }

    private void setupToolbar() {
        if (BuildConfig.DEBUG) {
            Log.d(TAG, "setupToolbar");
        }
        setSupportActionBar(mToolbar);
        ActionBar actionbar = getSupportActionBar();

        mAppBarParams = (AppBarLayout.LayoutParams) mCollapsingToolbar.getLayoutParams();
        if (actionbar != null) {
            actionbar.setHomeAsUpIndicator(R.drawable.ic_menu_black_24dp);
            actionbar.setDisplayHomeAsUpEnabled(true);

        }
    }

    private void changeEditMode(int mode) {
        if (BuildConfig.DEBUG) {
            Log.d(TAG, "changeEditMode");
        }
        mFab.setImageResource(R.drawable.ic_done_black_24dp);
        if (mode == 1) {
            for (EditText userValue : mUserInfoViews) {
                userValue.setEnabled(true);
                userValue.setFocusable(true);
                userValue.setFocusableInTouchMode(true);
                showProfilePlaceholder();
                lockToolbar();
                mCollapsingToolbar.setExpandedTitleColor(Color.TRANSPARENT);
            }
        } else {
            mFab.setImageResource(R.drawable.ic_create_black_24dp);
            for (EditText userValue : mUserInfoViews) {
                userValue.setEnabled(false);
                userValue.setFocusable(false);
                userValue.setFocusableInTouchMode(false);
                hideProfilePlaceholder();
                unlockToolbar();
                mCollapsingToolbar.setExpandedTitleColor(getResources().getColor(R.color.white));
                saveUserInfoValue();


            }
        }


    }

    private void loadUserInfoValue() {
        if (BuildConfig.DEBUG) {
            Log.d(TAG, "loadUserInfoValue");
        }
        List<String> userData = mDataManager.getPreferenceManager().loadUserProfileData();
        for (int i = 0; i < userData.size(); i++) {
            mUserInfoViews.get(i).setText(userData.get(i));
        }
    }

    private void saveUserInfoValue() {
        if (BuildConfig.DEBUG) {
            Log.d(TAG, "saveUserInfoValue");
        }
        List<String> userData = new ArrayList<>();
        for (EditText userFieldView : mUserInfoViews) {
            userData.add(userFieldView.getText().toString());
        }
        mDataManager.getPreferenceManager().saveUserProfileData(userData);
    }

    private void setupDrawer() {
        if (BuildConfig.DEBUG) {
            Log.d(TAG, "setupDrawer");
        }
        NavigationView navigationView = (NavigationView) findViewById(R.id.navigation_view);
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

    /**
     * Получение результатов из другой Activity (фото из камеры или галереии)
     *
     * @param requestCode
     * @param resultCode
     * @param data
     */
    @Override
    protected void onActivityResult(int requestCode, int resultCode, Intent data) {
        if (BuildConfig.DEBUG) {
            Log.d(TAG, "onActivityResult");
        }
        switch (requestCode) {
            case ConstantManager.REQUEST_GALLERY_PICTURE:
                if (resultCode == RESULT_OK && data != null) {
                    mSelectedImage = data.getData();
                    insertProfileImage(mSelectedImage);
                }
                break;
            case ConstantManager.REQUEST_CAMERA_PICTURE:
                if (resultCode == RESULT_OK && mPhotoFile != null) {
                    mSelectedImage = Uri.fromFile(mPhotoFile);
                    insertProfileImage(mSelectedImage);
                }
                break;
        }
    }

    @Override
    public boolean onOptionsItemSelected(MenuItem item) {
        if (BuildConfig.DEBUG) {
            Log.d(TAG, "onOptionsItemSelected");
        }
        if (item.getItemId() == android.R.id.home) {
            mNavigationDrawer.openDrawer(GravityCompat.START);
            mFlag = true;
        }

        circleDraw();


        return super.onOptionsItemSelected(item);
    }

    @Override
    public void onBackPressed() {
        if (BuildConfig.DEBUG) {
            Log.d(TAG, "onBackPressed");
        }
        if (mFlag == true) {
            mFlag = false;
            mNavigationDrawer.closeDrawer(GravityCompat.START);

        } else {
            super.onBackPressed();
        }
    }

    private void circleDraw() {
        if (BuildConfig.DEBUG) {
            Log.d(TAG, "circleDraw");
        }
        BitmapDrawable bImage = (BitmapDrawable) getResources().getDrawable(R.drawable.ava);
        RoundedAvatarDrawable RondedAvatarImg = new RoundedAvatarDrawable(bImage.getBitmap());
        Bitmap bImageRwonded = RondedAvatarImg.getBitmap();
        ImageView mImg = (ImageView) findViewById(R.id.avatar);
        mImg.setImageDrawable(new RoundedAvatarDrawable(bImage.getBitmap()));
    }

    private void loadPhotoFromGallery() {
        if (BuildConfig.DEBUG) {
            Log.d(TAG, "loadPhotoFromGallery");
        }
        Intent takeGalleryIntent = new Intent(Intent.ACTION_PICK,
                MediaStore.Images.Media.EXTERNAL_CONTENT_URI);
        takeGalleryIntent.setType("image/*");
        startActivityForResult(Intent.createChooser(takeGalleryIntent,
                getString(R.string.user_profile_chose_message)),
                ConstantManager.REQUEST_GALLERY_PICTURE);
    }

    private void loadPhotoFromCamera() {
        if (BuildConfig.DEBUG) {
            Log.d(TAG, "loadPhotoFromCamera");
        }
        if (ContextCompat.checkSelfPermission(this, android.Manifest.permission.CAMERA) == PackageManager.PERMISSION_GRANTED
                && ContextCompat.checkSelfPermission(this, android.Manifest.permission.WRITE_EXTERNAL_STORAGE) == PackageManager.PERMISSION_GRANTED) {

            Intent takeCaptureIntent = new Intent(MediaStore.ACTION_IMAGE_CAPTURE);
            try {
                mPhotoFile = createImageFile();
            } catch (IOException e) {
                e.printStackTrace();
                showSnackbar("Устройство не готово, повторите попытку");

                Picasso.with(this)
                        .load(mDataManager.getPreferenceManager().loadUserPhoto())
                        .placeholder(R.drawable.user_bg)
                        .into(mProfileImage);
                // TODO: 02.07.2016 обработать ошибку при чтении/записи файла
            }
            if (mPhotoFile != null) {
                takeCaptureIntent.putExtra(MediaStore.EXTRA_OUTPUT,
                        Uri.fromFile(mPhotoFile));
                startActivityForResult(takeCaptureIntent,
                        ConstantManager.REQUEST_CAMERA_PICTURE);
            }
        } else {
            ActivityCompat.requestPermissions(this, new String[]{
                    android.Manifest.permission.CAMERA,
                    android.Manifest.permission.WRITE_EXTERNAL_STORAGE
            }, ConstantManager.CAMERA_REQUEST_PERMISSION_CODE);

            Snackbar.make(mCoordinatorLayout, "Для корректной работы приложения необходимо дать требуемые разрешения", Snackbar.LENGTH_LONG)
                    .setAction("Разрешить", new View.OnClickListener() {
                        @Override
                        public void onClick(View v) {
                            openApplicationSettings();
                        }
                    }).show();
        }
    }

    @Override
    public void onRequestPermissionsResult(int requestCode, @NonNull String[] permissions, @NonNull int[] grantResults) {
        if (BuildConfig.DEBUG) {
            Log.d(TAG, "onRequestPermissionsResult");
        }
        if (requestCode == ConstantManager.CAMERA_REQUEST_PERMISSION_CODE && grantResults.length == 2) {
            if (grantResults[0] == PackageManager.PERMISSION_GRANTED) {
                // TODO: 02.07.2016 Тут обрабатываем разрешение(разрешение получено) например
                // вывести сообщение или обработать какой-то логикой если нужно
                // например сообщение - разрешение получено

            }
        }
        if (grantResults[1] == PackageManager.PERMISSION_GRANTED) {
            // TODO: 02.07.2016 Тут обрабатываем разрешение(разрешение получено) например
            // вывести сообщение или обработать какой-то логикой если нужно
            // например сообщение - разрешение получено 01:33:10
        }
    }

    private void hideProfilePlaceholder() {
        if (BuildConfig.DEBUG) {
            Log.d(TAG, "hideProfilePlaceholder");
        }
        mProfilePlaceholder.setVisibility(View.GONE);
    }

    private void showProfilePlaceholder() {
        if (BuildConfig.DEBUG) {
            Log.d(TAG, "showProfilePlaceholder");
        }
        mProfilePlaceholder.setVisibility(View.VISIBLE);
    }

    private void lockToolbar() {
        if (BuildConfig.DEBUG) {
            Log.d(TAG, "lockToolbar");
        }
        mAppBarLayout.setExpanded(true, true);
        mAppBarParams.setScrollFlags(0);
        mCollapsingToolbar.setLayoutParams(mAppBarParams);
    }

    private void unlockToolbar() {
        if (BuildConfig.DEBUG) {
            Log.d(TAG, "unlockToolbar");
        }
        mAppBarParams.setScrollFlags(AppBarLayout.LayoutParams.SCROLL_FLAG_SCROLL | AppBarLayout.LayoutParams.SCROLL_FLAG_EXIT_UNTIL_COLLAPSED);
        mCollapsingToolbar.setLayoutParams(mAppBarParams);
    }

    @Override
    protected Dialog onCreateDialog(int id) {
        if (BuildConfig.DEBUG) {
            Log.d(TAG, "onCreateDialog");
        }
        switch (id) {
            case ConstantManager.LOAD_PROFILE_PHOTO:
                String[] selectItem = {getString(R.string.user_profile_dialog_gallery), getString(R.string.user_profile_dialog_camera), getString(R.string.user_profile_dialog_cancel)};
                AlertDialog.Builder builder = new AlertDialog.Builder(this);
                builder.setTitle(getString(R.string.user_profile_dialog_title));
                builder.setItems(selectItem, new DialogInterface.OnClickListener() {
                    @Override
                    public void onClick(DialogInterface dialog, int choiceItem) {
                        switch (choiceItem) {
                            case 0:
                                // TODO: 02.07.2016 загрузить из галереии
                                loadPhotoFromGallery();
                                break;
                            case 1:
                                // TODO: 02.07.2016 загрузить из камеры
                                loadPhotoFromCamera();
                                break;
                            case 2:
                                // TODO: 02.07.2016 cancel
                                dialog.cancel();
                        }
                    }
                });
                return builder.create();
            default:
                return null;
        }
    }

    /**
     * формирование фото файла полученного с фотоаппатара и его сохранение в каталог
     *
     * @return фозвращается ссылка на файл
     */
    private File createImageFile() throws IOException {

        if (BuildConfig.DEBUG) {
            Log.d(TAG, "createImageFile");
        }
        String timeStamp = new SimpleDateFormat("yyyyMMdd_HHmmss").format(new Date());
        String imageFileName = "JPEG_" + timeStamp + "_";
        File storageDir = Environment.getExternalStoragePublicDirectory(Environment.DIRECTORY_PICTURES);
        File image = File.createTempFile(imageFileName, ".jpg", storageDir);


        ContentValues values = new ContentValues();
        values.put(MediaStore.Images.Media.DATE_TAKEN, System.currentTimeMillis());
        values.put(MediaStore.Images.Media.MIME_TYPE, "image/jpeg");
        values.put(MediaStore.MediaColumns.DATA, image.getAbsolutePath());

        this.getContentResolver().insert(MediaStore.Images.Media.EXTERNAL_CONTENT_URI, values);

        return image;
    }

    /**
     * установка фото в профиль
     *
     * @param selectedImage содержит устанавливаемую фото
     */
    private void insertProfileImage(Uri selectedImage) {
        if (BuildConfig.DEBUG) {
            Log.d(TAG, "insertProfileImage");
        }
        Picasso.with(this)
                .load(selectedImage)
                .into(mProfileImage);
        // сделать ПЛЭЙСХОЛДЕР ТРАНСПАРЕНТ+CROP
        // TODO: 02.07.2016 01:38:04
        mDataManager.getPreferenceManager().saveUserPhoto(selectedImage);
    }

    /**
     * открытие окна настроек разрешений
     */
    private void openApplicationSettings() {
        if (BuildConfig.DEBUG) {
            Log.d(TAG, "openApplicationSettings");
        }
        Intent appSettingsIntent = new Intent(Settings.ACTION_APPLICATION_DETAILS_SETTINGS,
                Uri.parse("package:" + getPackageName()));

        startActivityForResult(appSettingsIntent,
                ConstantManager.PERMISSION_REQUEST_SETTINGS_CODE);
    }

    /**
     * вызов интента для набора номера телефона
     */
    private void call() {
        if (BuildConfig.DEBUG) {
            Log.d(TAG, "call");
        }

        Uri number = Uri.parse("tel:" + mDataManager.getPreferenceManager().getUserPhone());
        Intent callIntent = new Intent(Intent.ACTION_DIAL, number);
        startActivity(callIntent);
    }


    /**
     * Вызов интента для формированияписьма на отправку
     */
    private void sendEmail() {
        if (BuildConfig.DEBUG) {
            Log.d(TAG, "sendEmail");
        }

        Intent emailIntent = new Intent(Intent.ACTION_SEND);
        emailIntent.setType("text/plain");
        emailIntent.putExtra(Intent.EXTRA_EMAIL, new String[]{mDataManager.getPreferenceManager().getUserMail()});
        emailIntent.putExtra(Intent.EXTRA_SUBJECT, "DevIntensive automatic send");
        emailIntent.putExtra(Intent.EXTRA_TEXT, "Hi! This test message from new APP");
        startActivity(emailIntent);
    }

    /**
     * Вызов интента для отображения странички с vk
     */
    private void vkView() {
        if (BuildConfig.DEBUG) {
            Log.d(TAG, "vkView");
        }

        Intent webIntent = new Intent(Intent.ACTION_VIEW, Uri.parse("http://" + mDataManager.getPreferenceManager().getUserVK()));
        startActivity(webIntent);
    }

    /**
     * Вызов интента для отображения странички с github
     */
    private void gitHubView() {
        if (BuildConfig.DEBUG) {
            Log.d(TAG, "gitHubView");
        }

        Intent webIntent = new Intent(Intent.ACTION_VIEW, Uri.parse("http://" + mDataManager.getPreferenceManager().getUserGit()));
        startActivity(webIntent);
    }


}


