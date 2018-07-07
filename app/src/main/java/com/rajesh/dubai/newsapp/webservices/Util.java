package com.rajesh.dubai.newsapp.webservices;

import android.app.ProgressDialog;
import android.content.Context;
import android.graphics.PorterDuff;
import android.graphics.drawable.Drawable;
import android.net.ConnectivityManager;
import android.net.NetworkInfo;
import android.support.v4.content.ContextCompat;
import android.widget.ProgressBar;
import android.widget.Toast;

import com.rajesh.dubai.newsapp.R;

public class Util {

    private static ProgressDialog mProgressDialog;
    private static boolean isLoadingVisible;

    /**
     * method to check active internet connection on device.
     *
     * @param context
     * @return true, if device connected to the internet. if it is false, also shows a error dialog.
     */
    public static boolean networkStatus(Context context) {
        if (context != null) {
            if (Util.isWifiAvailable(context) || Util.isMobileNetworkAvailable(context)) {
                return true;
            } else {
                Toast.makeText(context, "Internet is connected, Please try after some time.", Toast.LENGTH_LONG).show();
                return false;
            }
        } else {
            return false;
        }
    }

    /**
     * method to check if device has enabled mobile data.
     *
     * @param context
     * @return true, if mobile data is enabled in mobile.
     */
    public static boolean isMobileNetworkAvailable(Context context) {

        ConnectivityManager connectManager = (ConnectivityManager) context.getSystemService(Context.CONNECTIVITY_SERVICE);
        NetworkInfo activeNetworkInfo = connectManager.getActiveNetworkInfo();

        return activeNetworkInfo != null
                && activeNetworkInfo.getType() == ConnectivityManager.TYPE_MOBILE
                && activeNetworkInfo.isConnected();
    }

    /**
     * method to check device has connected with active Wifi connection.
     *
     * @param context
     * @return true, if device connected with any Wifi network.
     */
    public static boolean isWifiAvailable(Context context) {

        ConnectivityManager connectManager = (ConnectivityManager) context.getSystemService(Context.CONNECTIVITY_SERVICE);
        NetworkInfo activeNetworkInfo = connectManager.getActiveNetworkInfo();

        return activeNetworkInfo != null
                && activeNetworkInfo.getType() == ConnectivityManager.TYPE_WIFI
                && activeNetworkInfo.isConnected();
    }

    /**
     * method to show Progress.
     *
     * @param mContext Context to get resources and device specific display metrics
     */
    public static void showProgress(Context mContext) {

        if (isLoadingVisible) {
            hideProgress();
        }
        isLoadingVisible = true;
        mProgressDialog = new ProgressDialog(mContext);
        mProgressDialog.setMessage("Loading, Please wait...");
        mProgressDialog.setIndeterminate(false);
        mProgressDialog.setCanceledOnTouchOutside(false);
        mProgressDialog.setCancelable(false);

        Drawable drawable = new ProgressBar(mContext).getIndeterminateDrawable().mutate();
        drawable.setColorFilter(ContextCompat.getColor(mContext, R.color.colorAccent), PorterDuff.Mode.SRC_IN);
        mProgressDialog.setIndeterminateDrawable(drawable);
        mProgressDialog.show();
    }

    /**
     * method to hide progress
     */
    public static void hideProgress() {
        try {
            if (mProgressDialog != null && mProgressDialog.isShowing()) {
                mProgressDialog.dismiss();
                isLoadingVisible = false;
            }
        } catch (Exception e) {
            e.printStackTrace();
        }
    }
}