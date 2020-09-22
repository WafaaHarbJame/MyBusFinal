package com.elaj.patient.Utils;

import android.app.Activity;
import android.content.Context;
import android.content.Intent;
import android.location.Address;
import android.location.Geocoder;
import android.net.Uri;
import android.util.Log;


import com.elaj.patient.RootApplication;
import com.elaj.patient.classes.Constants;
import com.elaj.patient.classes.UtilityApp;
import com.elaj.patient.R;

import java.util.List;
import java.util.Locale;

/**
 * Created by wokman on 4/2/2017.
 */

public class MapHandler {

    public static double[] getGPSData(String gps) {
        double[] data = new double[2];
        if (gps != null) {
            String[] gpsSplit = gps.split(",");
            for (int i = 0; i < gpsSplit.length; i++)
                data[i] = NumberHandler.INSTANCE.getDouble(gpsSplit[i]);
        }

        return data;
    }

    public static String getGpsAddress(Context c, double latitude, double longitude) {
        try {
            Geocoder geocoder;
            List<Address> addresses;
            String lang = UtilityApp.INSTANCE.getLanguage() != null ? UtilityApp.INSTANCE.getLanguage() : Constants.English;
            geocoder = new Geocoder(c, new Locale(lang));

            addresses = geocoder.getFromLocation(latitude, longitude, 1); // Here 1 represent max location result to returned, by documents it recommended 1 to 5
            String address;
            Address allAddress = addresses.get(0);
//            if (addresses.get(0) != null)
            Log.i("MapHandler", "Log getAdminArea " + allAddress.getAdminArea());
            Log.i("MapHandler", "Log getSubAdminArea " + allAddress.getSubAdminArea());
            Log.i("MapHandler", "Log getCountryName " + allAddress.getCountryName());
            Log.i("MapHandler", "Log getFeatureName " + allAddress.getFeatureName());
            Log.i("MapHandler", "Log getLocality " + allAddress.getLocality());
            Log.i("MapHandler", "Log getSubLocality " + allAddress.getSubLocality());
            Log.i("MapHandler", "Log getPremises " + allAddress.getPremises());
            Log.i("MapHandler", "Log getThoroughfare " + allAddress.getThoroughfare());

            address = allAddress.getAddressLine(0); // If any additional address line present than only, check with max available address lines by getMaxAddressLineIndex()
//            else
//                address = c.getResources().getString(R.string.no_address);
            return address;
        } catch (Exception e) {
            e.printStackTrace();
            return c.getResources().getString(R.string.no_address);
        }
//        return c.getResources().getString(R.string.no_address);
    }

//    public static String GetMapImage(String size, double latitude, double longitude) {
//        return "https://maps.googleapis.com/maps/api/staticmap?center=" + latitude + "," + longitude + "&size=" + size + "&sensor=false&zoom=17&scale=2&markers=color:red|label:|" + latitude + "," + longitude;
//    }

//    public static String GetMapImage(String size, double latitude, double longitude) {
//        return "https://maps.googleapis.com/maps/api/staticmap?center=" + latitude + "," + longitude + "&size=" + size + "&sensor=false&zoom=17&scale=2&markers=color:red|label:|" + latitude + "," + longitude
//                + "&key=" + RootApplication.Companion.getInstance().getString(R.string.mapKey) /*"AIzaSyDQ4Z76ProBcbDfVMX315ztGL61Q21mBQ0"*/;
//    }

    public static void OpenGoogleMapIntent(Activity activity, String advTitle, double lat, double lng) {

        double latitude = lat;
        double longitude = lng;
        String label = advTitle + "";
        String uriBegin = "geo:" + latitude + "," + longitude;
        String query = latitude + "," + longitude + "(" + label + ")";
        String encodedQuery = Uri.encode(query);
        String uriString = uriBegin + "?q=" + encodedQuery + "&z=16";
        Uri uri = Uri.parse(uriString);
        Intent intent = new Intent(Intent.ACTION_VIEW, uri);
        activity.startActivity(intent);

    }

}
