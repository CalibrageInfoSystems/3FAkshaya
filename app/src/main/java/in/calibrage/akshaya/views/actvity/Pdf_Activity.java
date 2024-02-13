package in.calibrage.akshaya.views.actvity;

import android.Manifest;
import android.content.ActivityNotFoundException;
import android.content.Intent;
import android.content.pm.PackageManager;
import android.net.Uri;
import android.os.AsyncTask;
import android.os.Environment;
//import android.support.v4.app.ActivityCompat;
//import android.support.v4.content.ContextCompat;
//import android.support.v4.content.FileProvider;
//import android.support.v7.app.AppCompatActivity;
import android.os.Bundle;
//import android.support.v7.widget.Toolbar;
import android.util.Log;
import android.view.View;
import android.webkit.WebChromeClient;
import android.webkit.WebSettings;
import android.webkit.WebView;
import android.widget.Button;
import android.widget.ProgressBar;
import android.widget.TextView;
import android.widget.Toast;

import androidx.appcompat.widget.Toolbar;
import androidx.core.app.ActivityCompat;
import androidx.core.content.ContextCompat;

import java.io.File;
import java.io.IOException;

import dmax.dialog.SpotsDialog;
import in.calibrage.akshaya.BuildConfig;
import in.calibrage.akshaya.R;
import in.calibrage.akshaya.common.BaseActivity;
import in.calibrage.akshaya.common.FileDownloader;

public class Pdf_Activity extends BaseActivity {
    private String name, url;
    private SpotsDialog mdilogue;
    Button btn_download, bun_view;
WebView webview;
    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_pdf_view);
        if (ContextCompat.checkSelfPermission(this, Manifest.permission.WRITE_EXTERNAL_STORAGE) == PackageManager.PERMISSION_GRANTED) {
            // Do the file write
        } else {
            // Request permission from the user
            ActivityCompat.requestPermissions(this,
                    new String[]{Manifest.permission.WRITE_EXTERNAL_STORAGE}, 0);

        }



        Toolbar toolbar = (Toolbar) findViewById(R.id.toolbar);

        setSupportActionBar(toolbar);
        getSupportActionBar().setHomeAsUpIndicator(R.drawable.ic_left);
        getSupportActionBar().setDisplayShowHomeEnabled(true);
        getSupportActionBar().setDisplayHomeAsUpEnabled(true);

        getSupportActionBar().setDisplayShowTitleEnabled(false);

        mdilogue = (SpotsDialog) new SpotsDialog.Builder()
                .setContext(this)
                .setTheme(R.style.Custom)
                .build();
        toolbar.setNavigationOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                finish();
            }
        });

        if (getIntent() != null) {
            name = getIntent().getStringExtra("name");
            url = getIntent().getStringExtra("url");
            Log.d("PDF", "---------- file URL :" + url);
            TextView txt_name = findViewById(R.id.txt_name);
            txt_name.setText(name);
        }
//
//        btn_download.setOnClickListener(new View.OnClickListener() {
//            public void onClick(View v) {
//                mdilogue.show();
                new DownloadFile().execute(url, "3FFarmers.pdf");
                Toast.makeText(Pdf_Activity.this, "Download Completed See in Views ", Toast.LENGTH_SHORT).show();

             //   bun_view.setBackground(getResources().getDrawable(R.drawable.button_bg));
              //  btn_download.setBackground(getResources().getDrawable(R.drawable.button_bg_disable));
               // mdilogue.dismiss();

                Log.d("Download complete", "----------");
//            }
//        });
//
//        bun_view.setOnClickListener(new View.OnClickListener() {
//            public void onClick(View v) {
//                // your handler code here

 File pdfFile = new File(Environment.getExternalStorageDirectory() + "/3FOIL/" + "3FFarmers.pdf");


    //File file = new File(Environment.getExternalStorageDirectory() + "/test.pdf");

        webview = (WebView) findViewById(R.id.webView);
        WebSettings settings = webview.getSettings();
        settings.setJavaScriptEnabled(true);
        settings.setAllowFileAccessFromFileURLs(true);
        settings.setAllowUniversalAccessFromFileURLs(true);
        settings.setBuiltInZoomControls(true);
        webview.setWebChromeClient(new WebChromeClient());
        webview.loadUrl("file:///android_asset/pdfjs/web/viewer.html?file=" + pdfFile.getAbsolutePath() + "#zoom=page-width");
                // -> filename = maven.pdf
                //Uri path = Uri.fromFile(pdfFile);
//                Uri path = FileProvider.getUriForFile(Pdf_Activity.this, BuildConfig.APPLICATION_ID + ".provider", pdfFile);
//                Intent pdfIntent = new Intent(Intent.ACTION_VIEW);
//                //
//                pdfIntent.setDataAndType(path, "application/pdf");
//                pdfIntent.setFlags(Intent.FLAG_ACTIVITY_CLEAR_TOP);
//                pdfIntent.addFlags(Intent.FLAG_GRANT_READ_URI_PERMISSION);
//
//                try {
//                    startActivity(pdfIntent);
//                } catch (ActivityNotFoundException e) {
//                    Toast.makeText(Pdf_Activity.this, "No Application available to view PDF", Toast.LENGTH_SHORT).show();
//                }
          }
//        });
//    }

        private class DownloadFile extends AsyncTask<String, Void, Void> {


            @Override
            protected Void doInBackground(String... strings) {
                String fileUrl = strings[0];   // -> http://maven.apache.org/maven-1.x/maven.pdf
                String fileName = strings[1];  // -> maven.pdf
                String extStorageDirectory = Environment.getExternalStorageDirectory().toString();
                File folder = new File(extStorageDirectory, "3FOIL");
                folder.mkdir();

                File pdfFile = new File(folder, fileName);

                try {
                    pdfFile.createNewFile();
                } catch (IOException e) {
                    e.printStackTrace();
                }
                FileDownloader.downloadFile(fileUrl, pdfFile);
                return null;
            }
        }

        @Override
        public void onRequestPermissionsResult ( int requestCode, String[] permissions,
        int[] grantResults) {
            super.onRequestPermissionsResult(requestCode, permissions, grantResults);
            switch (requestCode) {
                case 0:
                    // Re-attempt file write
            }
        }
    }
