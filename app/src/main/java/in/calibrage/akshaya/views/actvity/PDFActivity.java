package in.calibrage.akshaya.views.actvity;

import android.net.Uri;
import android.os.Handler;
import android.support.v7.app.AppCompatActivity;
import android.os.Bundle;
import android.support.v7.widget.Toolbar;
import android.view.View;
import android.webkit.WebSettings;
import android.webkit.WebView;
import android.webkit.WebViewClient;
import android.widget.TextView;

import dmax.dialog.SpotsDialog;
import in.calibrage.akshaya.R;

public class PDFActivity extends AppCompatActivity {

    private String name, url;
    WebView webView;
    private SpotsDialog mdilogue;
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
           TextView txt_name = findViewById(R.id.txt_name);
           txt_name.setText(name);
        }
        webView = findViewById(R.id.webView);

               mdilogue = (SpotsDialog) new SpotsDialog.Builder()
                .setContext(this)
                .setTheme(R.style.Custom)
                .build();

        mdilogue.show();
        pdfOpen(url);

        new Handler().postDelayed(new Runnable() {
            @Override
            public void run() {
                mdilogue.cancel();
            }
        },2000);
    }

    private void pdfOpen(String fileUrl) {

        webView.getSettings().setJavaScriptEnabled(true);
        webView.getSettings().setPluginState(WebSettings.PluginState.ON);

        //---you need this to prevent the webview from
        // launching another browser when a url
        // redirection occurs---
        webView.setWebViewClient(new Callback());

        webView.loadUrl(
                "http://docs.google.com/gview?embedded=true&url=" + fileUrl);

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
