package com.lo.sisyphuser.fish.util;

import android.content.Context;
import android.widget.Toast;

import java.util.HashMap;

/**
 * Created by xx on 2016/9/13.
 */
public class ToastUtil {
    private static HashMap<Context, Toast> toasts = new HashMap<Context, Toast>();

    public synchronized static void showToast(Context context, int content) {
        Toast toast = toasts.get(context);
        if (null == toast) {
            toast = Toast.makeText(context, context.getText(content).toString(), Toast.LENGTH_SHORT);
            toasts.put(context, toast);
        }
        toast.setText(content);
        toast.show();
    }

    public synchronized static void showToast(Context context, String content) {
        Toast toast = toasts.get(context);
        if (null == toast) {
            toast = Toast.makeText(context, content, Toast.LENGTH_SHORT);
            toasts.put(context, toast);
        }
        toast.setText(content);
        toast.show();
    }
}
