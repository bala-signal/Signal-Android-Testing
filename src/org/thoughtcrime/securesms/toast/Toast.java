package org.thoughtcrime.securesms.toast;

import android.content.Context;
import android.content.Intent;

public class Toast {

    String text;

    Context context;

    public static final int LENGTH_LONG = 1;
    public static final int LENGTH_SHORT = 0;

    public Toast(Context context, String text) {
        this.context = context;
        this.text = text;
    }

    public static Toast makeText(Context context, String text, int duration) {
        Toast toast = new Toast(context, text);
        return toast;
    }

    public static Toast makeText(Context context, int resId, int duration) {
        String text = context.getString(resId);
        Toast toast = new Toast(context, text);
        return toast;
    }

    public static Toast makeText(Context context, CharSequence charSequence, int duration) {
        String text = charSequence.toString();
        Toast toast = new Toast(context, text);
        return toast;
    }


    public void show() {
        Intent intent = new Intent(context, ToastActivity.class);
        intent.putExtra(Intent.EXTRA_TEXT, text);
        intent.setFlags(Intent.FLAG_ACTIVITY_NEW_TASK);
        context.startActivity(intent);
    }
}
