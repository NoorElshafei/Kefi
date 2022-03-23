package com.gifting.kefi.util;

import android.app.Dialog;
import android.content.Context;
import android.view.Window;
import android.widget.ImageView;

import com.bumptech.glide.Glide;
import com.gifting.kefi.R;

public class DialogCustom {
    private Dialog dialog;
    private Context context;
    public DialogCustom(Context context) {
        dialog = new Dialog(context, android.R.style.Theme_Translucent_NoTitleBar_Fullscreen);
        this.context = context;
    }

    public void showDialog() {
        dialog.requestWindowFeature(Window.FEATURE_NO_TITLE);
        dialog.setCancelable(true);
        dialog.setContentView(R.layout.layout_bbi_loading_dialog);
        ImageView imageView = dialog.findViewById(R.id.loading_image);
        Glide.with(context).load(R.drawable.loading).into(imageView);
        dialog.show();

    }

    public void dismissDialog(){

        dialog.dismiss();

    }

    public Dialog getDialog() {
        return dialog;
    }
}
