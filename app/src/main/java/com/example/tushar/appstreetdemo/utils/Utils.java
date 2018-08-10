package com.example.tushar.appstreetdemo.utils;

import android.app.Activity;
import android.app.ProgressDialog;
import android.content.Context;
import android.view.inputmethod.InputMethodManager;

public class Utils {

    private static ProgressDialog mProgressDialog;

    public static void showProgressDialog(Context context, String message) {
        try {
            if (null == mProgressDialog || !mProgressDialog.isShowing()) {
                mProgressDialog = new ProgressDialog(context);
                if (message == null) {
                    message = "Please wait";
                }
                mProgressDialog.setMessage(message);
                mProgressDialog.setCancelable(false);
                mProgressDialog.show();
            }
        } catch (Exception e) {
            e.printStackTrace();
        }
    }

    public static void hideProgressDialog() {
        try {
            if (null != mProgressDialog && mProgressDialog.isShowing()) {
                mProgressDialog.dismiss();
            }
        } catch (IllegalArgumentException ex) {
            ex.printStackTrace();
        } finally {
            mProgressDialog = null;
        }
    }

    public static void hideSoftKeyboard(Activity activity) {
        InputMethodManager inputMethodManager =
                (InputMethodManager) activity.getSystemService(Activity.INPUT_METHOD_SERVICE);
        if(inputMethodManager != null && activity.getCurrentFocus() != null) {
            inputMethodManager.hideSoftInputFromWindow(activity.getCurrentFocus().getWindowToken(), 0);
        }
    }
}
