package com.lsdapps.uni.bookmoth_library.library.core.utils;

import android.content.Context;
import android.widget.Toast;

public class InnerToast {
    private static Toast toast;

    public static void show(Context context, String message) {
        if (toast != null) {
            toast.cancel();
        }
        toast = Toast.makeText(context, message, Toast.LENGTH_SHORT);
        toast.show();
    }
}