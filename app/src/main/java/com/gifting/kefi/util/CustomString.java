package com.gifting.kefi.util;

import android.os.Build;
import android.text.Html;
import android.text.Spanned;

public class CustomString {
    public static Spanned getSpannedText(String text) {
        if (Build.VERSION.SDK_INT >= Build.VERSION_CODES.N) {
            return Html.fromHtml(text, Html.FROM_HTML_MODE_COMPACT);
        } else {
            return Html.fromHtml(text);
        }
    }


}
