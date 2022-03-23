package com.gifting.kefi.data.shared_preference;

import android.content.Context;
import android.content.SharedPreferences;

import com.gifting.kefi.data.model.User;

public class UserSharedPreference {
    private SharedPreferences sharedPreferences;
    private SharedPreferences.Editor editor;

    Context context;
    public static String SHARED_NAME = "user";
    public static String USER_SKIN = "USER_SKIN";
    public static final int DRY = 2;
    public static final int OILY = 0;
    public static final int MIXED = 3;
    public static final int NORMAL = 1;
    public static String USER_ID = "USER_ID";
    public static String USER_NAME = "USER_NAME";
    public static String NAME = "NAME";
    public static String USER_EMAIL = "USER_EMAIL";
    public static String USER_PHONE = "USER_PHONE";
    public static String USER_IMAGE = "image";
    public static String USER_DATE_OF_BIRTH = "date_of_birth";
    public static String SKIN_TYPE = "SKIN_TYPE";
    public static String USER_GENDER = "gender";
    public static String SEARCH = "search";
    public static String STATUS = "status";
    public static String IS_LOGIN = "isLogin";
    public static String IS_QUESTIONS_ANSWERED = "IS_QUESTIONS_ANSWERED";
    public static String SELECTED_ADDRESS = "SELECTED_ADDRESS";
    public static String PAYMENT_METHOD_SELECTED = "PAYMENT_METHOD_SELECTED";
    public static String HEAD_LINE = "HEAD_LINE";
    public static String STORAGE_REFERENCE = "STORAGE_REFERENCE";
    public static String USER_LANG = "USER_LANGUAGE";
    public static String PUSH_NOTIFICATION = "PUSH_NOTIFICATION";


    public UserSharedPreference(Context context) {
        this.context = context;
        sharedPreferences = context.getSharedPreferences(SHARED_NAME, Context.MODE_PRIVATE);
        editor = sharedPreferences.edit();
    }


    public void saveUserSkin(int userSkin) {
        SharedPreferences.Editor shardEditor = sharedPreferences.edit();
        shardEditor.putInt(USER_SKIN, userSkin);
        shardEditor.apply();
    }

    public void setSkinType(String skinType) {
        editor.putString(SKIN_TYPE, skinType);

        editor.apply();
    }

    public int getUserSkin() {
        return sharedPreferences.getInt(USER_SKIN, -1);
    }

    public void addUser(User user) {
        editor.putString(USER_ID, user.getId());
        editor.putString(USER_EMAIL, user.getEmail());
        editor.putString(USER_NAME, user.getUserName());
        editor.putString(NAME, user.getName());
        editor.putString(USER_PHONE, user.getPhoneNumber());
        editor.putLong(USER_DATE_OF_BIRTH, user.getBirthDate());
        editor.putString(SEARCH, user.getSearch());
        editor.putString(SEARCH, user.getSearch());
        editor.putString(STATUS, user.getStatus());
        editor.putString(USER_IMAGE, user.getImageURL());
        editor.putString(USER_GENDER, user.getGender());
        editor.putString(SKIN_TYPE, user.getSkinType());
        editor.putBoolean(IS_QUESTIONS_ANSWERED, user.isQuestionAnswered());
        editor.putString(HEAD_LINE, user.getHeadLine());

        editor.apply();

    }

    public void setIsLogin(boolean value) {
        editor.putBoolean(IS_LOGIN, value);
        editor.apply();
    }

    public boolean getIsLogin() {
        return sharedPreferences.getBoolean(IS_LOGIN, false);
    }


    public void setStorageReference(String storageReference) {
        editor.putString(STORAGE_REFERENCE, storageReference);
        editor.apply();
    }

    public String getStorageReference() {
        return sharedPreferences.getString(STORAGE_REFERENCE, "");
    }

    public void setIsQuestionsAnswered(boolean value) {
        editor.putBoolean(IS_QUESTIONS_ANSWERED, value);
        editor.apply();
    }

    public boolean getIsQuestionsAnswered() {
        return sharedPreferences.getBoolean(IS_QUESTIONS_ANSWERED, false);
    }

    public User getUserDetails() {

        User user = new User(sharedPreferences.getString(USER_ID, ""),
                sharedPreferences.getString(USER_EMAIL, ""),
                sharedPreferences.getString(USER_NAME, ""),
                sharedPreferences.getString(NAME, ""),
                sharedPreferences.getString(USER_PHONE, ""),
                sharedPreferences.getLong(USER_DATE_OF_BIRTH, 0),
                sharedPreferences.getString(USER_IMAGE, ""),
                sharedPreferences.getString(SEARCH, ""),
                sharedPreferences.getString(STATUS, ""),
                sharedPreferences.getString(USER_GENDER, ""),
                sharedPreferences.getString(SKIN_TYPE, ""),
                sharedPreferences.getBoolean(IS_QUESTIONS_ANSWERED, false));
        return user;
    }

    public String getHeadLine() {
        return sharedPreferences.getString(HEAD_LINE, "Please add your Headline");
    }


    public void setSelectedAddress(String address) {
        editor.putString(SELECTED_ADDRESS, address);
        editor.apply();
    }

    public String getSelectedAddress() {
        return sharedPreferences.getString(SELECTED_ADDRESS, "default");
    }

    public void setSelectedPaymentMethod(String address) {
        editor.putString(PAYMENT_METHOD_SELECTED, address);
        editor.apply();
    }

    public String getSelectedPaymentMethod() {
        return sharedPreferences.getString(PAYMENT_METHOD_SELECTED, "default");
    }

    public void setGender(String gender) {
        editor.putString(USER_GENDER, gender);
        editor.apply();
    }

    public void removeData() {
        sharedPreferences.edit().clear().apply();
    }


    public void saveLanguage(String lang) {
        SharedPreferences.Editor shardEditor = sharedPreferences.edit();
        shardEditor.putString(USER_LANG, lang);
        shardEditor.apply();

    }

    public String getLanguage() {
        return sharedPreferences.getString(USER_LANG, "ar");

    }

    public void setPushNotification(String pushNotification) {
        SharedPreferences.Editor shardEditor = sharedPreferences.edit();
        shardEditor.putString(PUSH_NOTIFICATION, pushNotification);
        shardEditor.apply();

    }

    public String getPushNotification() {
        return sharedPreferences.getString(PUSH_NOTIFICATION, "first_time");

    }
}
