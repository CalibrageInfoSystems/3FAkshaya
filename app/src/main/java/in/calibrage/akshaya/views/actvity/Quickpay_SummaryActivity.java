package in.calibrage.akshaya.views.actvity;

import android.app.Dialog;
import android.app.ProgressDialog;
import android.content.Intent;
import android.content.SharedPreferences;
import android.graphics.Bitmap;
import android.graphics.Color;
import android.media.MediaScannerConnection;
import android.os.Build;
import android.os.Bundle;
import android.os.Environment;
import android.os.Handler;
import android.support.annotation.RequiresApi;
import android.text.SpannableString;
import android.text.Spanned;
import android.text.TextPaint;
import android.text.TextUtils;
import android.text.method.LinkMovementMethod;
import android.text.style.ClickableSpan;
import android.util.Log;
import android.view.View;
import android.view.Window;
import android.webkit.WebSettings;
import android.webkit.WebView;
import android.webkit.WebViewClient;
import android.widget.Button;
import android.widget.CheckBox;
import android.widget.ImageView;
import android.widget.TextView;
import android.widget.Toast;

import com.google.gson.Gson;
import com.google.gson.JsonObject;

import java.io.ByteArrayOutputStream;
import java.io.File;
import java.io.FileOutputStream;
import java.io.IOException;
import java.text.DecimalFormat;
import java.text.SimpleDateFormat;
import java.util.ArrayList;
import java.util.Calendar;
import java.util.Date;
import java.util.List;
import java.util.Locale;

import dmax.dialog.SpotsDialog;
import in.calibrage.akshaya.R;
import in.calibrage.akshaya.common.BaseActivity;
import in.calibrage.akshaya.common.CommonUtil;

import in.calibrage.akshaya.common.SignatureView;
import in.calibrage.akshaya.localData.SharedPrefsData;
import in.calibrage.akshaya.models.GetQuickpayDetails;
import in.calibrage.akshaya.models.GetquickpayDetailsModel;
import in.calibrage.akshaya.models.MSGmodel;
import in.calibrage.akshaya.models.PostQuickpaymodel;
import in.calibrage.akshaya.models.QuickPayResponce;
import in.calibrage.akshaya.service.APIConstantURL;
import in.calibrage.akshaya.service.ApiService;
import in.calibrage.akshaya.service.ServiceFactory;
import retrofit2.adapter.rxjava.HttpException;
import rx.Subscriber;
import rx.Subscription;
import rx.android.schedulers.AndroidSchedulers;
import rx.schedulers.Schedulers;

import static in.calibrage.akshaya.common.CommonUtil.updateResources;

public class Quickpay_SummaryActivity extends BaseActivity {
    CheckBox checkbox;
    private static final String TAG = Quickpay_SummaryActivity.class.getSimpleName();
    private ProgressDialog dialog;
    String currentDate;
    String total;
    TextView terms;
    TextView ok, getTerms, Click_here ;
    TextView ffbCostTxt, convenienceChargeTxt, closingBalanceTxt, totalAmount, text_flat_charge, text_quntity, text_quickpay_fee;
    String Farmer_code;
    private Subscription mSubscription;
    ImageView backImg, home_btn;
    Button submit;
    private SpotsDialog mdilogue;
    Bitmap bitmap;
    Button save;
    private boolean isSignatured = false;
    SignatureView signatureView;
    String path;
    List<String> ids_list = new ArrayList<>();
    List<String> dates_list = new ArrayList<>();
    List<Double> netweight_list = new ArrayList<>();
    List<String> post_ids = new ArrayList<>();
    private static final String IMAGE_DIRECTORY = "/signdemo";
    String result;
    String whs_Code;
    double total_weight = 0.0;
     DecimalFormat df = new DecimalFormat("####0.00");
    DecimalFormat dff = new DecimalFormat("####0.000");
    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        final int langID = SharedPrefsData.getInstance(this).getIntFromSharedPrefs("lang");
        if (langID == 2)
            updateResources(this, "te");
        else
            updateResources(this, "en-US");
        setContentView(R.layout.activity_quickpay__summary);

        init();
        setViews();

        // Saving string data of your e

    }

    private void init() {
        Bundle extras = getIntent().getExtras();
        if (extras != null) {
           // include_gst_amount = extras.getString("Total_amount");

            ids_list = (ArrayList<String>) getIntent().getSerializableExtra("collection_ids");
            dates_list = (ArrayList<String>) getIntent().getSerializableExtra("collection_dates");
            netweight_list = (ArrayList<Double>) getIntent().getSerializableExtra("collection_weight");
            whs_Code =extras.getString("whsCode");
            Log.e("whs_Code==",whs_Code);
        }
        for (int i = 0; i < ids_list.size(); i++) {

            String id = ids_list.get(i);
            String date = dates_list.get(i);
            double weight = netweight_list.get(i);
            post_ids.add(id + "|" + weight + "|" + date + "");



            total_weight = total_weight + weight;
            Log.e("total_weight===",total_weight+"");

            Log.e("post_ids==", String.valueOf(post_ids));
        }


        backImg = (ImageView) findViewById(R.id.back);
        signatureView = (SignatureView) findViewById(R.id.signature_view);
        Click_here = (TextView) findViewById(R.id.clear);
        save = (Button) findViewById(R.id.save);
        ffbCostTxt = (TextView) findViewById(R.id.tvtext_item_five);
        convenienceChargeTxt = (TextView) findViewById(R.id.tvtext_item_seven);
        closingBalanceTxt = (TextView) findViewById(R.id.tvtext_item_nine);
        totalAmount = (TextView) findViewById(R.id.tvtext_item_fifteen);
        text_flat_charge = (TextView) findViewById(R.id.text_flat_charge);
        text_quntity = (TextView) findViewById(R.id.text_quntity);
        text_quickpay_fee = (TextView) findViewById(R.id.text_quickpay_fee);
        Button confirmBtn = (Button) findViewById(R.id.buttonConfirm);
        checkbox = (CheckBox) findViewById(R.id.checkBox);
        home_btn = (ImageView) findViewById(R.id.home_btn);
        submit = (Button) findViewById(R.id.buttonConfirm);
        mdilogue = (SpotsDialog) new SpotsDialog.Builder()
                .setContext(this)
                .setTheme(R.style.Custom)
                .build();
        SharedPreferences pref = getSharedPreferences("FARMER", MODE_PRIVATE);
        Farmer_code = pref.getString("farmerid", "");       // Saving string data of your editext

    }

    private void setViews() {
        currentDate = new SimpleDateFormat("yyyy-MM-dd", Locale.getDefault()).format(new Date());
        home_btn.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View view) {
                Intent intent = new Intent(getApplicationContext(), HomeActivity.class);
                startActivity(intent);
            }
        });
        backImg.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View view) {
                finish();
            }
        });

        save.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                bitmap = signatureView.getSignatureBitmap();
                path = saveImage(bitmap);
            }
        });

        submit.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View view) {
                if (validations()) {
                    if (isOnline()) {
                     //   submitReq();
                    } else {
                        showDialog(Quickpay_SummaryActivity.this, getResources().getString(R.string.Internet));
                    } }
            }
        });
        if(isOnline()) {
            GetQuckPaySummary();
        }
        else {
            showDialog(Quickpay_SummaryActivity.this, getResources().getString(R.string.Internet));
        }
    }
    private void signature_popup() {
        final Dialog dialog = new Dialog(this);
        dialog.setContentView(R.layout.signature_view);
        dialog.setTitle("Title...");

        // set the custom dialog components - text, image and button
      TextView clear = (TextView) dialog.findViewById(R.id.clear);
        signatureView = (SignatureView) dialog.findViewById(R.id.signature_view);
      //  text.setText("Android custom dialog example!");
      //  ImageView image = (ImageView) dialog.findViewById(R.id.image);
       // image.setImageResource(R.drawable.ic_launcher);
        clear.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {

              signatureView.clearCanvas();

            }
        });
        Button dialogButton = (Button) dialog.findViewById(R.id.dialogButtonOK);
        // if button is clicked, close the custom dialog
        dialogButton.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                if (signatureView.isBitmapEmpty()) {
                    showDialog(Quickpay_SummaryActivity.this, getResources().getString(R.string.signature));
                    return ;
                }
                if (isOnline()) {
                    submitReq();
                } else {
                    showDialog(Quickpay_SummaryActivity.this, getResources().getString(R.string.Internet));
                }
                dialog.dismiss();
            }
        });

        dialog.show();
    }




    private boolean validations() {
        if (checkbox.isChecked()) {
            checkbox.setChecked(true);
        } else {
            showDialog(Quickpay_SummaryActivity.this, getResources().getString(R.string.terms_agree));
            return false;
        }

        if (signatureView.getSignatureBitmap() == null){
            signature_popup();
          //  showDialog(Quickpay_SummaryActivity.this, getResources().getString(R.string.signature));
            return false;
            // myBitmap is empty/blank
        }


        return true;
    }

    public String saveImage(Bitmap myBitmap) {
        ByteArrayOutputStream bytes = new ByteArrayOutputStream();
        myBitmap.compress(Bitmap.CompressFormat.JPEG, 90, bytes);
        File wallpaperDirectory = new File(Environment.getExternalStorageDirectory() + IMAGE_DIRECTORY /*iDyme folder*/);
        // have the object build the directory structure, if needed.
        if (!wallpaperDirectory.exists()) {
            wallpaperDirectory.mkdirs();
            Log.d("hhhhh", wallpaperDirectory.toString());
        }

        try {
            File f = new File(wallpaperDirectory, Calendar.getInstance()
                    .getTimeInMillis() + ".jpg");
            f.createNewFile();
            FileOutputStream fo = new FileOutputStream(f);
            fo.write(bytes.toByteArray());
            MediaScannerConnection.scanFile(Quickpay_SummaryActivity.this,
                    new String[]{f.getPath()},
                    new String[]{"image/jpeg"}, null);
            fo.close();
            Log.d("TAG", "File Saved::--->" + f.getAbsolutePath());

            return f.getAbsolutePath();
        } catch (IOException e1) {
            e1.printStackTrace();
        }
        return "";

    }

    private void GetQuckPaySummary() {
        mdilogue.show();
        JsonObject object = GetReuestobject();
        ApiService service = ServiceFactory.createRetrofitService(this, ApiService.class);
        mSubscription = service.post_details(object)
                .subscribeOn(Schedulers.newThread())
                .observeOn(AndroidSchedulers.mainThread())
                .subscribe(new Subscriber<GetquickpayDetailsModel>() {
//        ApiService service = ServiceFactory.createRetrofitService(this, ApiService.class);
//        mSubscription = service.getquickpaydetails(APIConstantURL.GetQuickpayDetails + Farmer_code)
//                .subscribeOn(Schedulers.newThread())
//                .observeOn(AndroidSchedulers.mainThread())
//                .subscribe(new Subscriber<GetquickpayDetailsModel>() {
                    @Override
                    public void onCompleted() {
                        mdilogue.dismiss();
                    }

                    @Override
                    public void onError(Throwable e) {
                        if (e instanceof HttpException) {
                            ((HttpException) e).code();
                            ((HttpException) e).message();
                            ((HttpException) e).response().errorBody();
                            try {
                                ((HttpException) e).response().errorBody().string();
                            } catch (IOException e1) {
                                e1.printStackTrace();
                            }
                            e.printStackTrace();
                        }
                        mdilogue.dismiss();
                        showDialog(Quickpay_SummaryActivity.this, getString(R.string.server_error));
                    }

                    @Override
                    public void onNext(GetquickpayDetailsModel getquickpayDetailsModel) {
                        if (getquickpayDetailsModel.getListResult() != null) {

                            Log.e("nodada====", "nodata===custom2");
                            text_quntity.setText(" "+dff.format(getquickpayDetailsModel.getListResult().get(0).getQuantity()));

                            if (getquickpayDetailsModel.getListResult().get(0).getFfbFlatCharge() == null) {
                                text_flat_charge.setText(" 0.00");

                            } else {
                                text_flat_charge.setText(" "+df.format(getquickpayDetailsModel.getListResult().get(0).getFfbFlatCharge()));
                            }
                            ffbCostTxt.setText(" "+df.format(getquickpayDetailsModel.getListResult().get(0).getFfbCost()));

                            if (getquickpayDetailsModel.getListResult().get(0).getConvenienceCharge() == null) {
                                convenienceChargeTxt.setText(" 0.00");

                            } else {

                                convenienceChargeTxt.setText("-" + df.format(getquickpayDetailsModel.getListResult().get(0).getConvenienceCharge()));
                            }
                            closingBalanceTxt.setText(" "+df.format(getquickpayDetailsModel.getListResult().get(0).getClosingBalance()));
                         //   totalAmount.setText(df.format(getquickpayDetailsModel.getListResult().get(0).getTotal()));

                            if (getquickpayDetailsModel.getListResult().get(0).getTotal() > 0.0 ) {
                                totalAmount.setText(" "+String.valueOf(getquickpayDetailsModel.getListResult().get(0).getTotal()));
                               // totalAmount.setText("0");

                            } else {
                                totalAmount.setText(" 0.00");
                               // totalAmount.setText(String.valueOf(getquickpayDetailsModel.getListResult().get(0).getTotal()));
                            }
                            text_quickpay_fee.setText("-" + df.format(getquickpayDetailsModel.getListResult().get(0).getQuickPay()));

                        } else {

                        }

                    }


                });
    }

    private JsonObject GetReuestobject() {
        GetQuickpayDetails requestModel = new GetQuickpayDetails();
        requestModel.setFarmerCode(Farmer_code);
        requestModel.setQuantity(total_weight);
        requestModel.setWhsCode(whs_Code);
            return new Gson().toJsonTree(requestModel).getAsJsonObject();

        }



    private void submitReq() {
        Double d1 = Double.valueOf(totalAmount.getText().toString());

        if (d1 > 0.0) {
            mdilogue.show();
            JsonObject object = quickReuestobject();
            ApiService service = ServiceFactory.createRetrofitService(this, ApiService.class);
            mSubscription = service.postquickpay(object)
                    .subscribeOn(Schedulers.newThread())
                    .observeOn(AndroidSchedulers.mainThread())
                    .subscribe(new Subscriber<QuickPayResponce>() {
                        @Override
                        public void onCompleted() {
                            mdilogue.dismiss();
                        }

                        @Override
                        public void onError(Throwable e) {
                            if (e instanceof HttpException) {
                                ((HttpException) e).code();
                                ((HttpException) e).message();
                                ((HttpException) e).response().errorBody();
                                try {
                                    ((HttpException) e).response().errorBody().string();
                                } catch (IOException e1) {
                                    e1.printStackTrace();
                                }
                                e.printStackTrace();
                            }
                            mdilogue.cancel();
                        }

                        @Override
                        public void onNext(final QuickPayResponce quickPayResponce) {
                            if (quickPayResponce.getIsSuccess()) {
                                new Handler().postDelayed(new Runnable() {
                                    @RequiresApi(api = Build.VERSION_CODES.M)
                                    @Override
                                    public void run() {

                                        result = quickPayResponce.getResult();

                                        showSuccesspdf();
                                    }
                                }, 300);
                            } else {
                                showDialog(Quickpay_SummaryActivity.this, quickPayResponce.getEndUserMessage());
                            }

                        }


                    });
        } else {
            showDialog(Quickpay_SummaryActivity.this,getString(R.string.enter_loan_amount));
           // Toast.makeText(this, "unable to process request now", Toast.LENGTH_SHORT).show();
        }

    }

    private void showSuccesspdf() {
        mdilogue.show();
        final Dialog dialog = new Dialog(this);
        dialog.requestWindowFeature(Window.FEATURE_NO_TITLE);
        dialog.setCancelable(false);
        dialog.setContentView(R.layout.pdf_dialog);
        final WebView webView = dialog.findViewById(R.id.webView);
        Button btn_dialog = dialog.findViewById(R.id.btn_dialog);

       // webView.getSettings().setPluginState(WebSettings.PluginState.ON);
        webView.getSettings().setJavaScriptEnabled(true);
       webView.getSettings().setLoadWithOverviewMode(true);
       webView.getSettings().setUseWideViewPort(true);

        //---you need this to prevent the webview from
        // launching another browser when a url
        // redirection occurs---
        webView.setWebViewClient(new Quickpay_SummaryActivity.Callback());
        webView.setWebViewClient(new WebViewClient() {
            @Override
            public void onPageStarted(WebView view, String url, Bitmap favicon) {
                super.onPageStarted(view, url, favicon);

                webView.loadUrl("javascript:(function() { " +
                        "document.querySelector('[role=\"toolbar\"]').remove();})()");
                mdilogue.show();
            }
            @Override
            public void onPageFinished(WebView view, String url) {
                super.onPageFinished(view, url);
                webView.loadUrl("javascript:(function() { " +
                        "document.querySelector('[role=\"toolbar\"]').remove();})()");
                mdilogue.dismiss();
            }
        });
        webView.loadUrl("http://docs.google.com/gview?embedded=true&url=" + result);
       // mdilogue.cancel();
        btn_dialog.setOnClickListener(new View.OnClickListener() {
            @RequiresApi(api = Build.VERSION_CODES.M)
            @Override
            public void onClick(View v) {
                dialog.dismiss();
                List<MSGmodel> displayList = new ArrayList<>();

                //   displayList.add(new MSGmodel(getString(R.string.loan_amount), Amount));
                showSuccessDialog(displayList, getResources().getString(R.string.qucick_success));
//                Intent intent = new Intent(Quickpay_SummaryActivity.this, HomeActivity.class);
//                intent.setFlags(Intent.FLAG_ACTIVITY_NEW_TASK | Intent.FLAG_ACTIVITY_CLEAR_TOP);
//                startActivity(intent);
//                finish();
            }
        });
        dialog.show();

    }

    private JsonObject quickReuestobject() {
        PostQuickpaymodel requestModel = new PostQuickpaymodel();

        requestModel.setFarmername(SharedPrefsData.getusername(this));
        requestModel.setFarmerCode(Farmer_code);
        requestModel.setIsFarmerRequest(true);
        requestModel.setCreatedByUserId(null);
        requestModel.setReqCreatedDate(currentDate);
        requestModel.setCreatedDate(currentDate);
        requestModel.setUpdatedByUserId(null);
        requestModel.setUpdatedDate(currentDate);
        requestModel.setWhsCode(whs_Code);
        requestModel.setFileLocation("");
        if(null != closingBalanceTxt.getText() & !TextUtils.isEmpty(closingBalanceTxt.getText()))
          requestModel.setClosingBalance(Double.parseDouble(closingBalanceTxt.getText().toString()));
        else {
            requestModel.setClosingBalance(0.0);
        }
        //TODO make dynamic

        String val = arrayTOstring(post_ids);
        Log.d(TAG, "------ analysis ------ >> get values in String(): " + val);


        requestModel.setCollectionIds(val);
      //  requestModel.setCost(Double.parseDouble(ffbCostTxt.getText().toString()));
        requestModel.setNetWeight(Double.parseDouble(text_quntity.getText().toString()));
        requestModel.setSignatureExtension(".png");
        requestModel.setSignatureName(CommonUtil.bitMaptoBase64(signatureView.getSignatureBitmap()));
        return new Gson().toJsonTree(requestModel).getAsJsonObject();

    }

    public String arrayTOstring(List<String> arrayList) {
        StringBuilder string = new StringBuilder();
        if (arrayList.size() > 0) {
            for (int i = 0; i < arrayList.size(); i++) {
                if (i == 0)
                    string.append("" + arrayList.get(i));
                else
                    string.append("," + arrayList.get(i));
            }
        }
        return string.toString();
    }

    private void showCustomDialog() {
        final Dialog dialog = new Dialog(this);
        // Include dialog.xml file
        dialog.setContentView(R.layout.loan_dialog);
        // Set dialog title
        dialog.setTitle("Custom Dialog");

        ok = (TextView) dialog.findViewById(R.id.ok);

        getTerms = (TextView) dialog.findViewById(R.id.txtclose);
        //  image.setImageResource(R.drawable.ic_action_duration);
        dialog.show();
        ok.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                dialog.dismiss();
            }
        });
        getTerms.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                dialog.dismiss();
            }
        });
    }

    @Override
    public void onBackPressed() {
        super.onBackPressed();
        this.finish();
    }


    private class Callback extends WebViewClient {
        @Override
        public boolean shouldOverrideUrlLoading(
                WebView view, String url) {
            return (false);
        }
    }
}


