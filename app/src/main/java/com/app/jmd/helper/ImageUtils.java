package com.app.jmd.helper;

import android.content.ContentResolver;
import android.graphics.Bitmap;
import android.graphics.BitmapFactory;
import android.graphics.Canvas;
import android.graphics.Matrix;
import android.net.Uri;
import android.util.Base64;

import java.io.ByteArrayOutputStream;
import java.io.IOException;
import java.io.InputStream;

public class ImageUtils {
    private static final int A4_WIDTH = 992;  // width in pixels2480/1240
    private static final int A4_HEIGHT = 1403;//3508/1754

    public static String uriToBase64(Uri uri, ContentResolver contentResolver) {
        try {
            InputStream inputStream = contentResolver.openInputStream(uri);
            if (inputStream == null) {
                return null;
            }
            Bitmap bitmap = BitmapFactory.decodeStream(inputStream);
            inputStream.close();
//          Bitmap updatedBitmap=  resizeImageWithAspectRatio(bitmap,bitmap.getWidth(),bitmap.getHeight());
            Bitmap updatedBitmap = resizeToA4(bitmap);
            ByteArrayOutputStream byteArrayOutputStream = new ByteArrayOutputStream();
            updatedBitmap.compress(Bitmap.CompressFormat.JPEG, 100, byteArrayOutputStream);
            byte[] byteArray = byteArrayOutputStream.toByteArray();
            return Base64.encodeToString(byteArray, Base64.DEFAULT);
        } catch (IOException e) {
            e.printStackTrace();
            return null;
        }
    }



    public static Bitmap resizeToA4(Bitmap originalBitmap) {
        int originalWidth = originalBitmap.getWidth();
        int originalHeight = originalBitmap.getHeight();
        float scaleWidth = (float) A4_WIDTH / originalWidth;
        float scaleHeight = (float) A4_HEIGHT / originalHeight;
        float scaleFactor = Math.min(scaleWidth, scaleHeight);
        Matrix matrix = new Matrix();
        matrix.postScale(scaleFactor, scaleFactor);
        Bitmap scaledBitmap = Bitmap.createBitmap(
                originalBitmap, 0, 0, originalWidth, originalHeight, matrix, true);
        Bitmap a4Bitmap = Bitmap.createBitmap(A4_WIDTH, A4_HEIGHT, Bitmap.Config.ARGB_8888);
        Canvas canvas = new Canvas(a4Bitmap);
        float left = (A4_WIDTH - scaledBitmap.getWidth()) / 2;
        float top = (A4_HEIGHT - scaledBitmap.getHeight()) / 2;
        canvas.drawBitmap(scaledBitmap, left, top, null);
        return a4Bitmap;
    }
}
