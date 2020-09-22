package com.elaj.patient.Utils;

import android.content.Context;
import android.graphics.Bitmap;


import java.io.ByteArrayOutputStream;
import java.io.File;
import java.util.HashMap;
import java.util.Map;

/**
 * Created by iSoft4is on 8/30/2016.
 */
public class UploadImg {

    public UploadImg() {
    }

    
    public static byte[] getImgData(Bitmap bitmap){
        ByteArrayOutputStream stream = new ByteArrayOutputStream();
        bitmap.compress(Bitmap.CompressFormat.JPEG, 90, stream);
        return stream.toByteArray();
    }

    public void upload(Context context, String urlTxt, String message, Bitmap bitmap) {

        String url = urlTxt;

        Map<String, Object> params = new HashMap<String, Object>();
        params.put("message", message);

//        if (!useFile) {
            //Simply put a byte[] to the params, AQuery will detect it and treat it as a multi-part post
            byte[] data = getImageData(bitmap);
        System.out.println(data);
            params.put("source", data);
//        }
//        else {
//                //Alternatively, put a File or InputStream instead of byte[]
//                File file = getImageFile();
//                params.put("source", file);
//        }


//        AQuery aq = new AQuery(context);
//        aq.ajax(url, params, JSONObject.class, this, "photoUpload");


    }

    private byte[] getImageData(Bitmap bitmap) {
        ByteArrayOutputStream stream = new ByteArrayOutputStream();
        bitmap.compress(Bitmap.CompressFormat.JPEG, 100, stream);
        return stream.toByteArray();
    }

    private File getImageFile() {

        return null;
    }

//    // convert from bitmap to byte array
//    private byte[] getBytesFromBitmap(Bitmap bitmap) {
//        ByteArrayOutputStream stream = new ByteArrayOutputStream();
//        bitmap.compress(Bitmap.CompressFormat.JPEG, 70, stream);
//        return stream.toByteArray();
//    }

}
