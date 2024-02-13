package in.calibrage.akshaya.views.actvity;

import android.Manifest;
import android.content.Context;
import android.content.pm.PackageManager;
import android.graphics.Bitmap;
import android.graphics.BitmapFactory;
import android.os.Build;
import android.os.Bundle;
import android.os.Environment;
//import android.support.annotation.RequiresApi;
//import android.support.v4.app.ActivityCompat;
//import android.support.v7.app.AppCompatActivity;
import android.util.Base64;
import android.util.Log;
import android.widget.ImageView;


import androidx.annotation.RequiresApi;
import androidx.appcompat.app.AppCompatActivity;
import androidx.core.app.ActivityCompat;

import com.itextpdf.text.Utilities;

import java.io.ByteArrayOutputStream;
import java.io.File;
import java.io.FileInputStream;
import java.io.FileNotFoundException;
import java.io.FileOutputStream;
import java.io.IOException;
import java.util.Arrays;


import javax.xml.transform.stream.StreamResult;

import in.calibrage.akshaya.R;

public class test extends AppCompatActivity {
    public static String TAG = "test";
    private String filepath = null;
    @RequiresApi(api = Build.VERSION_CODES.KITKAT)
    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_test);
        if (Build.VERSION.SDK_INT >= Build.VERSION_CODES.M) {
            isStoragePermissionGranted();
        }
        String base64String = "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";
        byte[] decoded = Base64.decode(base64String, 0);
        Log.e("~~~~~~~~ Decoded: ", Arrays.toString(decoded));

        try
        {
            File file2 = new File(Environment.getExternalStorageDirectory() + "/payment.xlsx");
            Log.e("~~~~~~~~ Decoded: ", file2+"");
            FileOutputStream os = new FileOutputStream(file2, true);
            Log.e("~~~~~~~~ Decoded: ", os+"");
            os.write(decoded);
            Log.e("~~~~~~~~ Decoded: ", os+"");
            os.close();
        }
        catch (Exception e)
        {
            e.printStackTrace();
        }


    }
    public  boolean isStoragePermissionGranted() {
        if (Build.VERSION.SDK_INT >= Build.VERSION_CODES.M) {
            if (checkSelfPermission(android.Manifest.permission.WRITE_EXTERNAL_STORAGE)
                    == PackageManager.PERMISSION_GRANTED) {
                Log.v(TAG,"Permission is granted");
                return true;
            } else {

                Log.v(TAG,"Permission is revoked");
                ActivityCompat.requestPermissions(this, new String[]{Manifest.permission.WRITE_EXTERNAL_STORAGE}, 1);
                return false;
            }
        }
        else { //permission is automatically granted on sdk<23 upon installation
            Log.v(TAG,"Permission is granted");
            return true;
        }
    }
    @Override
    public void onRequestPermissionsResult(int requestCode, String[] permissions, int[] grantResults) {
        super.onRequestPermissionsResult(requestCode, permissions, grantResults);
        if(grantResults.length > 0 && grantResults[0] == PackageManager.PERMISSION_GRANTED){
            Log.v(TAG,"Permission: "+permissions[0]+ "was "+grantResults[0]);
            //resume tasks needing this permission
        }
    }
}