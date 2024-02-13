package in.calibrage.akshaya.views.actvity;

import android.graphics.Bitmap;
import android.net.Uri;
import android.os.Handler;
//import android.support.v7.app.AppCompatActivity;
import android.os.Bundle;
//import android.support.v7.widget.Toolbar;
import android.util.Log;
import android.view.View;
import android.webkit.WebSettings;
import android.webkit.WebView;
import android.webkit.WebViewClient;
import android.widget.ProgressBar;
import android.widget.TextView;

import androidx.appcompat.app.AppCompatActivity;
import androidx.appcompat.widget.Toolbar;

import dmax.dialog.SpotsDialog;
import in.calibrage.akshaya.R;

public class PDFActivity extends AppCompatActivity {

    private String name, url;
    WebView webView;
    private SpotsDialog mdilogue;
    ProgressBar progressbar;
    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);

        setContentView(R.layout.activity_pdf);
        init();
    }

    private void init() {
        Toolbar toolbar = (Toolbar) findViewById(R.id.toolbar);
        setSupportActionBar(toolbar);
        getSupportActionBar().setHomeAsUpIndicator(R.drawable.ic_left);
        getSupportActionBar().setDisplayShowHomeEnabled(true);
        getSupportActionBar().setDisplayHomeAsUpEnabled(true);

        getSupportActionBar().setDisplayShowTitleEnabled(false);
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
        webView = findViewById(R.id.webView);

        mdilogue = (SpotsDialog) new SpotsDialog.Builder()
                .setContext(this)
                .setTheme(R.style.Custom)
                .build();


        pdfOpen(url);

        new Handler().postDelayed(new Runnable() {
            @Override
            public void run() {
                mdilogue.cancel();
            }
        }, 2000);
    }

    private void pdfOpen(String fileUrl) {
        mdilogue.show();
        webView.getSettings().setJavaScriptEnabled(true);
        webView.getSettings().setLoadWithOverviewMode(true);
        webView.getSettings().setUseWideViewPort(true);
        webView.getSettings().setPluginState(WebSettings.PluginState.ON);
        webView.getSettings().setBuiltInZoomControls(true);
        webView.getSettings().setSupportZoom(true);
        //---you need this to prevent the webview from
        // launching another browser when a url
        // redirection occurs---



        webView.setWebViewClient(new Callback());

        webView.setWebViewClient(new WebViewClient() {
            @Override
            public void onPageStarted(WebView view, String url, Bitmap favicon) {
                super.onPageStarted(view, url, favicon);
                mdilogue.show();
            }

            @Override
            public void onPageFinished(WebView view, String url) {
                super.onPageFinished(view, url);
                mdilogue.dismiss();
            }
        });
        String furl = "http://docs.google.com/gview?embedded=true&url=" + fileUrl;
        Log.d("PDF", "final URL :" + furl);
        webView.loadUrl(furl);



    }

    private class Callback extends WebViewClient {
        @Override
        public boolean shouldOverrideUrlLoading(
                WebView view, String url) {
            return (false);
        }
    }

    @Override
    public void onBackPressed() {
        super.onBackPressed();
        finish();
    }
}
