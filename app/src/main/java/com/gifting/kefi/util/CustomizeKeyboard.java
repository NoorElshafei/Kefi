package com.gifting.kefi.util;

import android.app.Activity;
import android.content.Context;
import android.view.View;
import android.view.inputmethod.EditorInfo;
import android.view.inputmethod.InputMethodManager;
import android.widget.EditText;

import androidx.core.widget.NestedScrollView;

public class CustomizeKeyboard {
    private static boolean name = true, email = true, pass = true;

    public static boolean isName() {
        return name;
    }

    public static void setName(boolean name) {
        CustomizeKeyboard.name = name;
    }

    public static boolean isEmail() {
        return email;
    }

    public static void setEmail(boolean email) {
        CustomizeKeyboard.email = email;
    }

    public static boolean isPass() {
        return pass;
    }

    public static void setPass(boolean pass) {
        CustomizeKeyboard.pass = pass;
    }

    public static void editKeyboardName(EditText editText, NestedScrollView scrollView) {

        editText.setOnEditorActionListener((v, actionId, event) -> {
            if (actionId == EditorInfo.IME_ACTION_DONE) {
                //Clear focus here from edittext
                editText.clearFocus();
                CustomizeKeyboard.setName(true);
                CustomizeKeyboard.setEmail(true);
                CustomizeKeyboard.setPass(true);

            }
            return false;
        });
        editText.setOnFocusChangeListener((v, hasFocus) -> {

            if (hasFocus && name) {

                v.postDelayed(new Runnable() {
                    @Override
                    public void run() {
                        scrollView.smoothScrollBy(0, 80);
                    }
                }, 350);
                name = false;
            }


        });
    }

    public static void editKeyboardEmail(EditText editText, NestedScrollView scrollView) {

        editText.setOnEditorActionListener((v, actionId, event) -> {
            if (actionId == EditorInfo.IME_ACTION_DONE) {
                //Clear focus here from edittext
                editText.clearFocus();
                CustomizeKeyboard.setName(true);
                CustomizeKeyboard.setEmail(true);
                CustomizeKeyboard.setPass(true);

            }
            return false;
        });
        editText.setOnFocusChangeListener((v, hasFocus) -> {

            if (hasFocus && email) {
                v.postDelayed(new Runnable() {
                    @Override
                    public void run() {
                        scrollView.smoothScrollBy(0, 80);
                    }
                }, 350);
                name = false;
                email = false;
            }

        });
    }

    public static void editKeyboardPassword(EditText editText, NestedScrollView scrollView, View view,Activity activity) {
        editText.setOnEditorActionListener((v, actionId, event) -> {
            if (actionId == EditorInfo.IME_ACTION_DONE) {
                //Clear focus here from edittext
                editText.clearFocus();
                final InputMethodManager imm = (InputMethodManager) activity.getSystemService(Context.INPUT_METHOD_SERVICE);
                imm.hideSoftInputFromWindow(view.getWindowToken(), 0);
                CustomizeKeyboard.setName(true);
                CustomizeKeyboard.setEmail(true);
                CustomizeKeyboard.setPass(true);
            }
            return false;
        });
        editText.setOnFocusChangeListener((v, hasFocus) -> {

            if (hasFocus && pass) {
                v.postDelayed(new Runnable() {
                    @Override
                    public void run() {
                        scrollView.smoothScrollBy(0, 80);
                    }
                }, 350);
                name = false;
                email = false;
                pass = false;
            }

        });
    }
}
