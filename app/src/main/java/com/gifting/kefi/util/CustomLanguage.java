package com.gifting.kefi.util;

import android.content.Context;

import com.gifting.kefi.data.shared_preference.UserSharedPreference;

public class CustomLanguage {

    public static void checkLanguage(Context context){
        UserSharedPreference userSharedPreference = new UserSharedPreference(context);
        if(userSharedPreference.getLanguage().equals("ar")){
            Language.changeLang("ar",context);
        }else{
            Language.changeLang("en",context);
        }
    }
}
