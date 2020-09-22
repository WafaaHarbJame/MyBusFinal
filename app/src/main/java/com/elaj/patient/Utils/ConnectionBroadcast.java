package com.elaj.patient.Utils;

import android.content.BroadcastReceiver;
import android.content.Context;
import android.content.Intent;
import android.util.Log;

/**
 * Created by ameer on 9/17/2017.
 */

public class ConnectionBroadcast extends BroadcastReceiver {

    private static final String LOG_TAG = "NetworkChangeReceiver";
    private boolean isConnected = false;

    @Override
    public void onReceive(final Context context, final Intent intent) {

        Log.v(LOG_TAG, "Receieved notification about network status");
//        isNetworkAvailable(context);

//        final ConnectivityManager connMgr = (ConnectivityManager) context
//                .getSystemService(Context.CONNECTIVITY_SERVICE);
//
//        final android.net.NetworkInfo wifi = connMgr
//                .getActiveNetworkInfo();
//
//        if (wifi != null && wifi.isAvailable()) {
//            // Do something
//            GlobalData.Toast(context, "connection available");
//            GlobalData.CustomeDialog(context, false);
////            Log.d("Network Available ", "Flag No 1");
//        } else {
//            GlobalData.Toast(context, "connection not available");
//            GlobalData.CustomeDialog(context, true);
//        }

    }

//    private boolean isNetworkAvailable(Context context) {
//        ConnectivityManager connectivity = (ConnectivityManager) context.getSystemService(Context.CONNECTIVITY_SERVICE);
//        if (connectivity != null) {
//            NetworkInfo[] info = connectivity.getAllNetworkInfo();
//            if (info != null) {
//                for (int i = 0; i < info.length; i++) {
//                    if (info[i].getState() == NetworkInfo.State.CONNECTED) {
//                        if (!isConnected) {
//                            Log.v(LOG_TAG, "Now you are connected to Internet!");
//                            GlobalData.Toast(context, "connection available");
////
////                            Toast.makeText(context, "Internet availablle via Broadcast receiver", Toast.LENGTH_SHORT).show();
//                            isConnected = true;
////                            GlobalData.CustomeDialog(context.getApplicationContext(), false);
//                            // do your processing here ---
//                            // if you need to post any data to the server or get
//                            // status
//                            // update from the server
//                        }
//                        return true;
//                    }
//                }
//            }
//        }
//        Log.v(LOG_TAG, "You are not connected to Internet!");
////
////        Toast.makeText(context, "Internet NOT availablle via Broadcast receiver", Toast.LENGTH_SHORT).show();
//        isConnected = false;
//        GlobalData.Toast(context.getApplicationContext(), "connection not available");
////        GlobalData.CustomeDialog(context.getApplicationContext(), true);
//        return false;
//    }

}
