package in.calibrage.akshaya.views.actvity;

import android.app.Dialog;
import android.app.ProgressDialog;
import android.content.Intent;
import android.content.SharedPreferences;
import android.graphics.Bitmap;
import android.media.MediaScannerConnection;
import android.os.Build;
import android.os.Environment;
import android.os.Handler;
import android.support.annotation.RequiresApi;
import android.support.v7.app.AppCompatActivity;
import android.os.Bundle;
import android.util.Log;
import android.view.View;
import android.widget.Button;
import android.widget.CheckBox;
import android.widget.ImageView;
import android.widget.TextView;
import android.widget.Toast;

import com.google.gson.Gson;
import com.google.gson.JsonObject;
import com.kyanogen.signatureview.SignatureView;

import java.io.ByteArrayOutputStream;
import java.io.File;
import java.io.FileOutputStream;
import java.io.IOException;
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
import in.calibrage.akshaya.common.Constants;
import in.calibrage.akshaya.common.PdfUtil;
import in.calibrage.akshaya.localData.SharedPrefsData;
import in.calibrage.akshaya.models.AddLabourRequestHeader;
import in.calibrage.akshaya.models.GetquickpayDetailsModel;
import in.calibrage.akshaya.models.LobourResponse;
import in.calibrage.akshaya.models.MSGmodel;
import in.calibrage.akshaya.models.PostQuickpaymodel;
import in.calibrage.akshaya.models.QuickPayModel;
import in.calibrage.akshaya.models.QuickPayResponce;
import in.calibrage.akshaya.service.APIConstantURL;
import in.calibrage.akshaya.service.ApiService;
import in.calibrage.akshaya.service.ServiceFactory;
import in.calibrage.akshaya.views.Adapter.Collection_Adapter;
import retrofit2.adapter.rxjava.HttpException;
import rx.Subscriber;
import rx.Subscription;
import rx.android.schedulers.AndroidSchedulers;
import rx.schedulers.Schedulers;

public class Quickpay_SummaryActivity extends BaseActivity {
    CheckBox checkbox;
    private static final String TAG = Quickpay_SummaryActivity.class.getSimpleName();
    private ProgressDialog dialog;
    String currentDate;
    String total;
    TextView terms;
    TextView ok, getTerms;
    TextView ffbCostTxt, convenienceChargeTxt, closingBalanceTxt, totalAmount, text_flat_charge, text_quntity, text_quickpay_fee;
    String Farmer_code;
    private Subscription mSubscription;
    ImageView backImg, home_btn;
    Button submit;
    private SpotsDialog mdilogue;
    Bitmap bitmap;
    Button clear, save;
    SignatureView signatureView;
    String path;
    private static final String IMAGE_DIRECTORY = "/signdemo";

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_quickpay__summary);

        init();
        setViews();

        // Saving string data of your e

    }

    private void init() {

        backImg = (ImageView) findViewById(R.id.back);

        signatureView = (SignatureView) findViewById(R.id.signature_view);
        clear = (Button) findViewById(R.id.clear);
        save = (Button) findViewById(R.id.save);


        terms = (TextView) findViewById(R.id.terms);

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
        clear.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                signatureView.clearCanvas();
            }
        });

        save.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                bitmap = signatureView.getSignatureBitmap();
                path = saveImage(bitmap);
            }
        });

        terms.setOnClickListener(new View.OnClickListener() {

            @Override
            public void onClick(View v) {
                // TODO Auto-generated method stub
                showCustomDialog();
            }
        });

        submit.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View view) {
                if (checkbox.isChecked()) {
                    PdfUtil.createPDF(Quickpay_SummaryActivity.this, CommonUtil.scaleDown(signatureView.getSignatureBitmap(), 200, true), text_quntity.getText().toString(), ffbCostTxt.getText().toString(), ffbCostTxt.getText().toString(), convenienceChargeTxt.getText().toString(), text_quickpay_fee.getText().toString(), closingBalanceTxt.getText().toString(), totalAmount.getText().toString());
                    submitReq();
                } else {
                    showDialog(Quickpay_SummaryActivity.this, "Please Agree Terms &amp;\\n Conditions");
                }

            }
        });
        GetQuckPaySummary();
    }

    public String saveImage(Bitmap myBitmap) {
        ByteArrayOutputStream bytes = new ByteArrayOutputStream();
        myBitmap.compress(Bitmap.CompressFormat.JPEG, 90, bytes);
        File wallpaperDirectory = new File(
                Environment.getExternalStorageDirectory() + IMAGE_DIRECTORY /*iDyme folder*/);
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
        ApiService service = ServiceFactory.createRetrofitService(this, ApiService.class);
        mSubscription = service.getquickpaydetails(APIConstantURL.GetQuickpayDetails + Farmer_code + "/" + 13)
                .subscribeOn(Schedulers.newThread())
                .observeOn(AndroidSchedulers.mainThread())
                .subscribe(new Subscriber<GetquickpayDetailsModel>() {
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
                    }

                    @Override
                    public void onNext(GetquickpayDetailsModel getquickpayDetailsModel) {
                        if (getquickpayDetailsModel.getListResult() != null) {

                            Log.e("nodada====", "nodata===custom2");
                            text_quntity.setText(String.valueOf(getquickpayDetailsModel.getListResult().get(0).getQuantity()));

                            if (getquickpayDetailsModel.getListResult().get(0).getFfbFlatCharge() == null) {
                                text_flat_charge.setText("0");

                            } else {
                                text_flat_charge.setText(String.valueOf(getquickpayDetailsModel.getListResult().get(0).getFfbFlatCharge()));
                            }
                            ffbCostTxt.setText(String.valueOf(getquickpayDetailsModel.getListResult().get(0).getFfbCost()));

                            if (getquickpayDetailsModel.getListResult().get(0).getConvenienceCharge() == null) {
                                convenienceChargeTxt.setText("0");

                            } else {

                                convenienceChargeTxt.setText("-" + String.valueOf(getquickpayDetailsModel.getListResult().get(0).getConvenienceCharge()));
                            }
                            closingBalanceTxt.setText(String.valueOf(getquickpayDetailsModel.getListResult().get(0).getClosingBalance()));
                            totalAmount.setText(String.valueOf(getquickpayDetailsModel.getListResult().get(0).getTotal()));

                            if (getquickpayDetailsModel.getListResult().get(0).getTotal() == null) {
                                totalAmount.setText("0");

                            } else {

                                totalAmount.setText(String.valueOf(getquickpayDetailsModel.getListResult().get(0).getTotal()));
                            }
                            text_quickpay_fee.setText("-" + String.valueOf(getquickpayDetailsModel.getListResult().get(0).getQuickPay()));

                        } else {

                            //  noRecords.setVisibility(View.VISIBLE);

                        }

                    }


                });
    }


    private void submitReq() {
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
                    public void onNext(QuickPayResponce quickPayResponce) {


                        if (quickPayResponce.getIsSuccess()) {
                            new Handler().postDelayed(new Runnable() {
                                @RequiresApi(api = Build.VERSION_CODES.M)
                                @Override
                                public void run() {
//                                    String selected_name = arrayyTOstring(selected_labour);
//                                    String Amount = amount.getText().toString();
//                                    String date = edittext.getText().toString();
//                                    List<MSGmodel> displayList = new ArrayList<>();
//
//                                    displayList.add(new MSGmodel(getString(R.string.select_labour_type), selected_name));
//                                    displayList.add(new MSGmodel(getResources().getString(R.string.labour_duration), seleced_Duration));
//                                    displayList.add(new MSGmodel(getResources().getString(R.string.amount), Amount));
//                                    displayList.add(new MSGmodel(getResources().getString(R.string.date), date));
//
//
////
//                                    Log.d(TAG, "------ analysis ------ >> get selected_name in String(): " + selected_name);
//
//                                    showSuccessDialog(displayList);
                                }
                            }, 300);
                        } else {
                            showDialog(Quickpay_SummaryActivity.this, quickPayResponce.getEndUserMessage());
                        }

                    }


                });


    }

    private JsonObject quickReuestobject() {
        PostQuickpaymodel requestModel = new PostQuickpaymodel();
        requestModel.setFarmerCode(Farmer_code);
        requestModel.setIsFarmerRequest(true);
        requestModel.setCreatedByUserId(null);
        requestModel.setReqCreatedDate(currentDate);
        requestModel.setCreatedDate(currentDate);
        requestModel.setUpdatedByUserId(null);
        requestModel.setUpdatedDate(currentDate);
        requestModel.setClosingBalance(Double.parseDouble(closingBalanceTxt.getText().toString()));
        requestModel.setCollectionIds("COL2019TAB027CCBDL01117-94,COL2019TAB140CCBDL01131-21");
        requestModel.setCost(Double.parseDouble(ffbCostTxt.getText().toString()));
        requestModel.setNetWeight(Double.parseDouble(text_quntity.getText().toString()));
        requestModel.setFileName(null);
        requestModel.setFileLocation("");
        requestModel.setFileExtension("png");

        return new Gson().toJsonTree(requestModel).getAsJsonObject();


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
}
