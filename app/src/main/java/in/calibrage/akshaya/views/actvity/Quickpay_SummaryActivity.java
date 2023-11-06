package in.calibrage.akshaya.views.actvity;

import android.app.Activity;
import android.app.Dialog;
import android.app.ProgressDialog;
import android.content.Intent;
import android.content.SharedPreferences;
import android.graphics.Bitmap;
import android.graphics.Color;
import android.graphics.drawable.Animatable;
import android.media.MediaScannerConnection;
import android.net.Uri;
import android.os.Build;
import android.os.Bundle;
import android.os.Environment;
import android.os.Handler;
import android.support.annotation.RequiresApi;
import android.support.v7.widget.LinearLayoutManager;
import android.support.v7.widget.RecyclerView;
import android.text.Html;
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
import in.calibrage.akshaya.models.FarmerOtpResponceModel;
import in.calibrage.akshaya.models.GetQuickpayDetails;
import in.calibrage.akshaya.models.GetquickpayDetailsModel;
import in.calibrage.akshaya.models.MSGmodel;
import in.calibrage.akshaya.models.PostQuickpaymodel;
import in.calibrage.akshaya.models.QuickPayModel;
import in.calibrage.akshaya.models.QuickPayResponce;
import in.calibrage.akshaya.service.APIConstantURL;
import in.calibrage.akshaya.service.ApiService;
import in.calibrage.akshaya.service.ServiceFactory;
import in.calibrage.akshaya.views.Adapter.QuickPayDataAdapter;
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
    private Quickpaydetailsadapter adapter;
    TextView ok, getTerms, Click_here ;
    TextView  totalclosingBalanceTxt,totalffbcost, totalTransactionFee, totalQuickpayFee,totalpay;
    String Farmer_code,statename;
    private Subscription mSubscription;
    Button dialogButton;
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
    List<Double> ffbflatchargelist = new ArrayList<>();


    List<GetquickpayDetailsModel> quickpaydetailslist = new ArrayList<>();
    List<Double> netweight_list = new ArrayList<>();
    List<String> post_ids = new ArrayList<>();
    List<String> post_codes = new ArrayList<>();
    private static final String IMAGE_DIRECTORY = "/signdemo";
    String result;
    String statecode, districtName;
    Integer districtId;
    String whs_Code, ccstateName, ccstateCode, ccdistrictName;
    int ccdistrictId;
    String collectiondate;
    Double collectionweight;
    Integer Cluster_id;
    double total_weight = 0.0;
    private FarmerOtpResponceModel catagoriesList;
    DecimalFormat df = new DecimalFormat("####0.00");
    DecimalFormat dff = new DecimalFormat("####0.000");
    RecyclerView detailsrecyclerview;

    boolean QuickpayinProgress = false;
    boolean QuickpaySuccess = true;
    double totalflatcharge = 0;
    double totalFFBcost = 0;
    double totaltransactionfee = 0;
    double totalquickfee = 0;
    double totaldue = 0;
    double totalamounttopay = 0;
    double sumoftotalamounttopay = 0;
    double dueamount = 0;


    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        final int langID = SharedPrefsData.getInstance(this).getIntFromSharedPrefs("lang");
        if (langID == 2)
            updateResources(this, "te");
        else if (langID == 3)
            updateResources(this, "kan");
        else
            updateResources(this, "en-US");
        setContentView(R.layout.quickpaysummaryactivity);

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



            ccstateName = extras.getString("ccstatename");
            ccstateCode = extras.getString("ccstatecode");
            ccdistrictName = extras.getString("ccdistrictname");
            ccdistrictId = extras.getInt("ccdistrictid");

//            Log.e("ccstatename==",ccstateName);
//            Log.e("ccstatecode==",ccstateCode);
//            Log.e("ccdistrictName==",ccdistrictName);
//            Log.e("ccdistrictId==",ccdistrictId + "");

            Log.e("whs_Code==",whs_Code);
        }

        for (int i = 0; i < ids_list.size(); i++) {

            String id = ids_list.get(i);
            String date = dates_list.get(i);
            double weight = netweight_list.get(i);
            post_codes.add(id + "(" + weight +" MT"+ ")" );


            total_weight = total_weight + weight;
            Log.e("total_weight===",total_weight+"");

            Log.e("post_ids==", String.valueOf(post_codes));
        }


        backImg = (ImageView) findViewById(R.id.back);
        signatureView = (SignatureView) findViewById(R.id.signature_view);
        Click_here = (TextView) findViewById(R.id.clear);
        save = (Button) findViewById(R.id.save);
        totalffbcost = (TextView) findViewById(R.id.totalffbcost);
        totalclosingBalanceTxt = (TextView) findViewById(R.id.totaldues);
        totalQuickpayFee = (TextView) findViewById(R.id.totalquickpayfee);
        totalTransactionFee = (TextView) findViewById(R.id.totaltransactioncost);
        totalpay = (TextView) findViewById(R.id.totalpay);
        Button confirmBtn = (Button) findViewById(R.id.buttonConfirm);
        checkbox = (CheckBox) findViewById(R.id.checkBox);
        home_btn = (ImageView) findViewById(R.id.home_btn);
        submit = (Button) findViewById(R.id.buttonConfirm);
        detailsrecyclerview = findViewById(R.id.details_recyclerview);
        mdilogue = (SpotsDialog) new SpotsDialog.Builder()
                .setContext(this)
                .setTheme(R.style.Custom)
                .build();
        SharedPreferences pref = getSharedPreferences("FARMER", MODE_PRIVATE);
        Farmer_code = pref.getString("farmerid", "").trim();       // Saving string data of your editext

    }

    private void setViews() {
        statecode = SharedPrefsData.getInstance(this).getStringFromSharedPrefs("statecode");
        districtId = SharedPrefsData.getInstance(this).getIntFromSharedPrefs("districtId");
        districtName = SharedPrefsData.getInstance(this).getStringFromSharedPrefs("districtName");
        Log.e("statecode===",statecode);
        Log.e("districtId===",districtId.toString() +"");
        Log.e("districtName===",districtName.toString() +"");
        catagoriesList = SharedPrefsData.getCatagories(this);
        if (null != catagoriesList.getResult().getFarmerDetails().get(0).getClusterId() && 0 != catagoriesList.getResult().getFarmerDetails().get(0).getClusterId())
            Cluster_id =  catagoriesList.getResult().getFarmerDetails().get(0).getClusterId();
        Log.e("Cluster_id===",Cluster_id+"");
        statename =catagoriesList.getResult().getFarmerDetails().get(0).getStateName();
        Log.e("stateName===",statename);
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
                    signature_popup();
                }
            }
        });

        detailsrecyclerview.setHasFixedSize(true);
        detailsrecyclerview.setLayoutManager(new LinearLayoutManager(this));

        if(isOnline()) {

//            for (int i=0; i<dates_list.size(); i++){
//
//                Log.d("CollectionDates", dates_list.get(i) + "");
//                collectiondate = dates_list.get(i);
//                collectionweight = netweight_list.get(i);
//                GetQuckPaySummary(collectiondate,collectionweight);
//
//            }
//
            // Got all responces
    QuickpayinProgress =true;
            GetQuckPaySummary(dates_list,netweight_list,0,dates_list.size());
        }
        else {
            showDialog(Quickpay_SummaryActivity.this, getResources().getString(R.string.Internet));
        }
    }

    public void showwDialog(Activity activity, Spanned msg) {
        final Dialog dialog = new Dialog(activity, R.style.DialogSlideAnim);
        dialog.requestWindowFeature(Window.FEATURE_NO_TITLE);
        dialog.setCancelable(false);
        dialog.setContentView(R.layout.dialog);
        final ImageView img = dialog.findViewById(R.id.img_cross);

        TextView text = (TextView) dialog.findViewById(R.id.text_dialog);
        text.setText(msg);
        Button dialogButton = (Button) dialog.findViewById(R.id.btn_dialog);
        dialogButton.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                dialog.dismiss();
                finish();
            }
        });
        dialog.show();
        new Handler().postDelayed(new Runnable() {
            @Override
            public void run() {
                ((Animatable) img.getDrawable()).start();
            }
        }, 500);
    }


    private void signature_popup() {
        final Dialog dialog = new Dialog(this);

        dialog.setContentView(R.layout.signature_view);
        //   dialog.setCancelable(false);
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
         dialogButton = (Button) dialog.findViewById(R.id.dialogButtonOK);
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

    private void GetQuckPaySummary(List<String> Cdate, List<Double> Cweightx, final int curentIndex, int totalIndex) {
        mdilogue.show();

        // cdate[index],


        if (curentIndex < dates_list.size()){


            JsonObject object = GetReuestobject(Cdate.get(curentIndex), Cweightx.get(curentIndex));
            quickpayProcess(curentIndex, object);
        }else{

            QuickpaySuccess = true;
            QuickpayinProgress = false;
            Log.d(TAG, "All API's Completed");
            Log.d(TAG, "DetailsListSize" + quickpaydetailslist.size() + "");
            adapter = new Quickpaydetailsadapter(Quickpay_SummaryActivity.this, quickpaydetailslist, ids_list,dates_list);
            detailsrecyclerview.setAdapter(adapter);
              totalFFBcost =0;

            for(int i =0; i < quickpaydetailslist.size(); i ++)
            {
                totalFFBcost = totalFFBcost+ quickpaydetailslist.get(i).getListResult().get(0).getFfbCost();
                totaltransactionfee =  quickpaydetailslist.get(i).getListResult().get(0).getConvenienceCharge();
                totalquickfee = totalquickfee+ quickpaydetailslist.get(i).getListResult().get(0).getQuickPay();
                totaldue = quickpaydetailslist.get(i).getListResult().get(0).getClosingBalance();
                totalflatcharge = totalflatcharge+ quickpaydetailslist.get(i).getListResult().get(0).getFfbFlatCharge();
                totalamounttopay = totalamounttopay + quickpaydetailslist.get(i).getListResult().get(0).getTotal();
                ffbflatchargelist.add(quickpaydetailslist.get(i).getListResult().get(0).getFfbFlatCharge());

            }

            Log.d(TAG, "totalFFBcostis:"+totalFFBcost);
            Log.d(TAG, "totaltransactionfeeis:"+totaltransactionfee);
            Log.d(TAG, "totalquickfeeis:"+totalquickfee);
            Log.d(TAG, "totaldueis:"+totaldue);
            Log.d(TAG, "totalflatchargeis:"+totalflatcharge);
            Log.d(TAG, "totalamounttopayis:"+totalamounttopay);
            Log.d(TAG, "ffbflatchargelistis:"+ffbflatchargelist);


            if (totalFFBcost > 0.0){
                totalffbcost.setText(df.format(totalFFBcost));;
            }else{
                totalffbcost.setText(" 0.00");
            }

            if (totaltransactionfee > 0.0){
                totalTransactionFee.setText("-" + df.format(totaltransactionfee));;
            }else{
                totalTransactionFee.setText(" 0.00");
            }

            if (totalquickfee > 0.0){
                totalQuickpayFee.setText("-" + df.format(totalquickfee));;
            }else{
                totalQuickpayFee.setText(" 0.00");
            }

            if (totaldue > 0.0){
                totalclosingBalanceTxt.setText("-" +df.format(totaldue));
                //dueamount = Double.parseDouble(totalclosingBalanceTxt.getText().toString());
            }else{
                totalclosingBalanceTxt.setText(" 0.00");
                //dueamount = Double.parseDouble(totalclosingBalanceTxt.getText().toString());
            }

//            double due = 0.0;
//            try{
//                due =  Double.parseDouble(totalclosingBalanceTxt.getText().toString());
//
//            }catch (Exception ex){
//
//                Log.e(TAG, "due amount expection is" + ex);
//            }

            double totalDueamount = 0.0;
            if(totaldue > 0.0)
            {
                totalDueamount  = totaldue;

            }else{
                totalDueamount = 0.0;
            }

            //We are not adding negative due amount ##CIS
            sumoftotalamounttopay = (totalFFBcost - totaltransactionfee - totalquickfee) - totalDueamount;
           // sumoftotalamounttopay = totalFFBcost - totaltransactionfee - totalquickfee - (-165);
            Log.d("sumtotalamounttopay", sumoftotalamounttopay + "");

                totalpay.setText(df.format(sumoftotalamounttopay));
        }

    }
// Get Quickpay Details By FarmerCode
    private void quickpayProcess(final int curentIndex, JsonObject object) {
        //        GetQuickpayDetails requestModel = new GetQuickpayDetails();
//
//        requestModel.setFarmerCode(Farmer_code);
//        requestModel.setQuantity(Cweight);
//        requestModel.setIsSpecialPay(false);
//        requestModel.setStateCode(statecode);
//        requestModel.setDistrictId(districtId);
//        requestModel.setDocDate(Cdate);
//
//
//        object = new Gson().toJsonTree(requestModel).getAsJsonObject();

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

                            // Check Quick pay Deatils Valid or not

                            quickpaydetailslist.add(getquickpayDetailsModel);
                            Log.d("getquickpayDetailsModel", quickpaydetailslist.size() + "");

//                            double sum = 0;
//                            for(Double d : ffbflatchargelist) {
//                                sum += d;
//                            }
//
//                            Log.d("statecode========", statecode);
//
//                            Log.d(TAG, "Sum is: " +sum);
//

//                            ffbflatchargelist = new ArrayList<>();
//
//                            for (int i=0; i<dates_list.size())
//
//                            ffbflatchargelist.add(getquickpayDetailsModel.getListResult().get(0).getFfbFlatCharge());
//
//                            Log.e("ffbflatchargelist====", ffbflatchargelist + "");

                            Log.e("nodada====", "nodata===custom2");
//                            text_quntity.setText(" "+dff.format(getquickpayDetailsModel.getListResult().get(0).getQuantity()));
//
//                            if (getquickpayDetailsModel.getListResult().get(0).getFfbFlatCharge() == null) {
//                                text_flat_charge.setText(" 0.00");
//
//                            } else
//                            ffbCostTxt.setText(" "+df.format(getquickpayDetailsModel.getListResult().get(0).getFfbCost()));
//
//                            if (getquickpayDetailsModel.getListResult().get(0).getConvenienceCharge() == null) {
//                                convenienceChargeTxt.setText(" 0.00");
//
//                            } else {
//
//                                convenienceChargeTxt.setText("-" + df.format(getquickpayDetailsModel.getListResult().get(0).getConvenienceCharge()));
//                            }
//
//                            //   totalAmount.setText(df.format(getquickpayDetailsModel.getListResult().get(0).getTotal()));
//
//
//                            if(getquickpayDetailsModel.getListResult().get(0).getClosingBalance() > 0.0){
//                                closingBalanceTxt.setText(" "+df.format(getquickpayDetailsModel.getListResult().get(0).getClosingBalance()));
//                            }else{
//                                closingBalanceTxt.setText(" 0.00");
//                            }
//                            if (getquickpayDetailsModel.getListResult().get(0).getTotal() > 0.0 ) {
//                                totalAmount.setText(" "+df.format(getquickpayDetailsModel.getListResult().get(0).getTotal()));
//                                // totalAmount.setText("0");
//
//                            } else {
//                                totalAmount.setText(" 0.00");
//                                // totalAmount.setText(String.valueOf(getquickpayDetailsModel.getListResult().get(0).getTotal()));
//                            }
//                            text_quickpay_fee.setText("-" + df.format(getquickpayDetailsModel.getListResult().get(0).getQuickPay()));
                            if (curentIndex < dates_list.size()){

                                GetQuckPaySummary(dates_list,netweight_list,curentIndex+1,dates_list.size());
                            }else{

                                QuickpaySuccess = true;
                                QuickpayinProgress = false;
                                Log.d(TAG, "All API's Completed");
                                Log.d(TAG, "DetailsListSize" + quickpaydetailslist.size() + "");
                                adapter = new Quickpaydetailsadapter(Quickpay_SummaryActivity.this, quickpaydetailslist,ids_list, dates_list);
                                detailsrecyclerview.setAdapter(adapter);
                            }

                        } else {
                            Log.d(TAG, "QuickPay  Deatis #### Error or No Data" );

                            QuickpaySuccess = false;
                            QuickpayinProgress = false;
                            Spanned test = Html.fromHtml(getString(R.string.ffbratenorthere));
                            showwDialog(Quickpay_SummaryActivity.this,test);
                        }

                    }


                });
    }

    private JsonObject GetReuestobject(String Cdate, Double Cweight) {


        GetQuickpayDetails requestModel = new GetQuickpayDetails();

        requestModel.setFarmerCode(Farmer_code);
            requestModel.setQuantity(Cweight);
            requestModel.setIsSpecialPay(false);
            requestModel.setDocDate(Cdate);

        if (ccstateCode != null && !ccstateCode.isEmpty() && !ccstateCode.equals("null")) {
            requestModel.setStateCode(ccstateCode);

            if (!ccstateCode.startsWith("AP") ) {

                requestModel.setDistrictId(0);

            }else{
                requestModel.setDistrictId(ccdistrictId);
            }

        }else{

            requestModel.setStateCode(statecode);

            if (!statecode.startsWith("AP") ) {

                requestModel.setDistrictId(0);

            }else{
                requestModel.setDistrictId(districtId);
            }

        }




//        if (ccstateCode != null && !ccstateCode.isEmpty() && !ccstateCode.equals("null")) {
//
//            if (!ccstateCode.startsWith("AP") ) {
//
//                requestModel.setDistrictId(0);
//
//            }else{
//                requestModel.setDistrictId(ccdistrictId);
//            }
//
//        }else{
//
//            if (!statecode.startsWith("AP") ) {
//
//                requestModel.setDistrictId(0);
//
//            }else{
//                requestModel.setDistrictId(districtId);
//            }
//
//        }

//        if (!statecode.startsWith("AP") ) {
//            requestModel.setDistrictId(0);
//        }else{
//            requestModel.setDistrictId(districtId);
//        }


        return new Gson().toJsonTree(requestModel).getAsJsonObject();
    }



    private void submitReq() {

        Double d1 = sumoftotalamounttopay;

        if (d1 > 0.0) {
            mdilogue.show();
            JsonObject object = quickReuestobject();

            Log.e("object==",object+"");
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
                                    ((HttpException) e).response(                                                                                                                                                                                                                                                                                                                                                                                                                                                                                                                                                                                                                                                                                                                                                                                                                                                                                                                                                                            ).errorBody().string();
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
                                mdilogue.dismiss();
                                dialogButton.setEnabled(false);
                                result = quickPayResponce.getResult();

                              showSuccesspdf(result);


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

//    private void showSuccesspdf(final String result) {
//        mdilogue.show();
//        final Dialog dialog = new Dialog(this);
//        dialog.requestWindowFeature(Window.FEATURE_NO_TITLE);
//        dialog.setCancelable(false);
//        dialog.setContentView(R.layout.pdf_dialog);
//        final WebView webView = dialog.findViewById(R.id.webView);
//        Button btn_dialog = dialog.findViewById(R.id.btn_dialog);
//        String doc = "https://docs.google.com/gview?embedded=true&url=" + result;
//
//      //  String doc=" http://docs.google.com/gview?embedded=true&url="+result;
//        webView.getSettings().setJavaScriptEnabled(true);
//        webView.getSettings().setPluginState(WebSettings.PluginState.ON);
//        webView.getSettings().setAllowFileAccess(true);
//        webView.loadUrl(doc);
//
//        webView.setWebViewClient(new WebViewClient() {
//            @Override
//            public void onPageStarted(WebView view, String url, Bitmap favicon) {
//                super.onPageStarted(view, url, favicon);
//
//                webView.loadUrl("javascript:(function() { " +
//                        "document.querySelector('[role=\"toolbar\"]').remove();})()");
//                mdilogue.show();
//            }
//            @Override
//            public void onPageFinished(final WebView view, String url) {
//                super.onPageFinished(view, url);
//                mdilogue.dismiss();
//                if (view.getContentHeight() == 0) {
//                    view.reload();
//                view.loadUrl("https://drive.google.com/viewerng/viewer?embedded=true&url=" + result);
//                }
////
//
//                webView.loadUrl("javascript:(function() { " +
//                        "document.querySelector('[role=\"toolbar\"]').remove();})()");
//
//
//            }
//        });
//
////       webView.getSettings().setPluginState(WebSettings.PluginState.ON);
////        webView.getSettings().setJavaScriptEnabled(true);
////       webView.getSettings().setLoadWithOverviewMode(true);
////       webView.getSettings().setUseWideViewPort(true);
////        webView.getSettings().setBuiltInZoomControls(true);
////        webView.getSettings().setSupportZoom(true);
////
////        //---you need this to prevent the webview from
////        // launching another browser when a url
////        // redirection occurs---
////        webView.setWebViewClient(new Quickpay_SummaryActivity.Callback());
////        webView.setWebViewClient(new WebViewClient() {
////            @Override
////            public void onPageStarted(WebView view, String url, Bitmap favicon) {
////                super.onPageStarted(view, url, favicon);
////
////                webView.loadUrl("javascript:(function() { " +
////                        "document.querySelector('[role=\"toolbar\"]').remove();})()");
////                mdilogue.show();
////            }
////            @Override
////            public void onPageFinished(WebView view, String url) {
////                super.onPageFinished(view, url);
////                webView.loadUrl("javascript:(function() { " +
////                        "document.querySelector('[role=\"toolbar\"]').remove();})()");
////                mdilogue.dismiss();
////            }
////        });
////        webView.loadUrl("http://docs.google.com/gview?embedded=true&url=" + result);
//        // mdilogue.cancel();
//        btn_dialog.setOnClickListener(new View.OnClickListener() {
//            @RequiresApi(api = Build.VERSION_CODES.M)
//            @Override
//            public void onClick(View v) {
//                dialog.dismiss();
//                List<MSGmodel> displayList = new ArrayList<>();
//
//                //   displayList.add(new MSGmodel(getString(R.string.loan_amount), Amount));
//                showSuccessDialog(displayList, getResources().getString(R.string.qucick_success));
////                Intent intent = new Intent(Quickpay_SummaryActivity.this, HomeActivity.class);
////                intent.setFlags(Intent.FLAG_ACTIVITY_NEW_TASK | Intent.FLAG_ACTIVITY_CLEAR_TOP);
////                startActivity(intent);
////                finish();
//            }
//        });
//        dialog.show();
//
//    }
    private void showSuccesspdf(final String result) {
        mdilogue.show();
        final Dialog dialog = new Dialog(this);
        dialog.requestWindowFeature(Window.FEATURE_NO_TITLE);
        dialog.setCancelable(false);
        dialog.setContentView(R.layout.pdf_dialog);
        final WebView webView = dialog.findViewById(R.id.webView);
        Button btn_dialog = dialog.findViewById(R.id.btn_dialog);

        String doc = "https://docs.google.com/gview?embedded=true&url=" + result;
        webView.getSettings().setJavaScriptEnabled(true);
        webView.getSettings().setAllowFileAccess(true);
        webView.loadUrl(doc);

        webView.setWebViewClient(new WebViewClient() {
            @Override
            public void onPageFinished(WebView view, String url) {
                super.onPageFinished(view, url);
                mdilogue.dismiss();
            }
        });

        btn_dialog.setOnClickListener(new View.OnClickListener() {
            @RequiresApi(api = Build.VERSION_CODES.M)
            @Override
            public void onClick(View v) {
                dialog.dismiss();
                List<MSGmodel> displayList = new ArrayList<>();

                //   displayList.add(new MSGmodel(getString(R.string.loan_amount), Amount));
                showSuccessDialog(displayList, getResources().getString(R.string.qucick_success));
                // Your code for handling the dialog dismissal.
            }
        });

        dialog.show();
    }

    private JsonObject quickReuestobject() {

//        double sum = 0;
//        for(Double d : ffbflatchargelist) {
//            sum += d;
//        }
//
//        Log.d("statecode========", statecode);
//
//        Log.d(TAG, "Sum is: " +sum);

        Double avgffbcost = totalflatcharge/dates_list.size();
        Log.d(TAG, "Avg FFB Cost is: " + avgffbcost);

        PostQuickpaymodel requestModel = new PostQuickpaymodel();

        requestModel.setFarmerName(SharedPrefsData.getusername(this));
        requestModel.setFarmerCode(Farmer_code);
        requestModel.setIsFarmerRequest(true);
        requestModel.setCreatedByUserId(null);
        requestModel.setReqCreatedDate(currentDate);
        requestModel.setCreatedDate(currentDate);
        requestModel.setUpdatedByUserId(null);
        requestModel.setUpdatedDate(currentDate);
        requestModel.setWhsCode(whs_Code);
        requestModel.setClusterId(Cluster_id);
        requestModel.setFileLocation("");
        requestModel.setSpecialPay(false);

        if (ccstateCode != null && !ccstateCode.isEmpty() && !ccstateCode.equals("null")) {
            requestModel.setStateCode(ccstateCode);

            if (!ccstateCode.startsWith("AP") ) {

                requestModel.setDistrictId(0);
                requestModel.setDistrictName("null");

            }else{
                requestModel.setDistrictId(ccdistrictId);
                requestModel.setDistrictName(ccdistrictName);
            }

        }else{

            requestModel.setStateCode(statecode);

            if (!statecode.startsWith("AP") ) {

                requestModel.setDistrictId(0);
                requestModel.setDistrictName("null");

            }else{
                requestModel.setDistrictId(districtId);
                requestModel.setDistrictName(districtName);
            }

        }

        if (ccstateName != null && !ccstateName.isEmpty() && !ccstateName.equals("null")) {
            requestModel.setStateName(ccstateName);
        }else{
            requestModel.setStateName(statename);
        }


        requestModel.setFfbCost(avgffbcost);

        if(totaldue > 0.0){
            requestModel.setClosingBalance(totaldue);
        }else {

            requestModel.setClosingBalance(0.0);
        }

//        if(null != totalclosingBalanceTxt.getText() & !TextUtils.isEmpty(totalclosingBalanceTxt.getText()))
//            requestModel.setClosingBalance(Double.parseDouble(totalclosingBalanceTxt.getText().toString()));
//        else {
//            requestModel.setClosingBalance(0.0);
//        }
        //TODO make dynamic

        for (int i = 0; i < ids_list.size(); i++) {

            String id = ids_list.get(i);
            String date = dates_list.get(i);
            double weight = netweight_list.get(i);
            post_ids.add(id + "|" + weight + "|" + date + "|"+ ffbflatchargelist.get(i) + "");

        }

        String val = arrayTOstring(post_ids);
        Log.d(TAG, "------ analysis ------ >> get values in String(): " + val);
        String val_codes = arrayTOstring(post_codes);
        Log.d(TAG, "------ analysis ------ >> get values in String(): " + val_codes);
        requestModel.setCollectionIds(val);

        requestModel.setCollectionCodes(val_codes);
        //  requestModel.setCost(Double.parseDouble(ffbCostTxt.getText().toString()));
        requestModel.setNetWeight(total_weight);
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


