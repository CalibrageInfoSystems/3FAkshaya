package in.calibrage.akshaya.views.actvity;

import android.app.Dialog;
import android.app.ProgressDialog;
import android.content.Intent;
import android.content.SharedPreferences;
import android.os.Build;
import android.os.Handler;
import android.support.annotation.RequiresApi;
import android.support.v7.app.AppCompatActivity;
import android.os.Bundle;
import android.text.TextUtils;
import android.util.Log;
import android.view.View;
import android.widget.Button;
import android.widget.CheckBox;
import android.widget.EditText;
import android.widget.ImageView;
import android.widget.TextView;
import android.widget.Toast;

import com.android.volley.AuthFailureError;
import com.android.volley.Request;
import com.android.volley.RequestQueue;
import com.android.volley.Response;
import com.android.volley.VolleyError;
import com.android.volley.toolbox.JsonObjectRequest;
import com.android.volley.toolbox.Volley;
import com.google.gson.Gson;
import com.google.gson.JsonObject;

import org.json.JSONException;
import org.json.JSONObject;

import java.io.IOException;
import java.text.SimpleDateFormat;
import java.util.ArrayList;
import java.util.Date;
import java.util.HashMap;
import java.util.List;
import java.util.Locale;
import java.util.Map;

import dmax.dialog.SpotsDialog;
import in.calibrage.akshaya.R;
import in.calibrage.akshaya.common.BaseActivity;
import in.calibrage.akshaya.models.AddLabourRequestHeader;
import in.calibrage.akshaya.models.Labourservicetype;
import in.calibrage.akshaya.models.LoanRequest;
import in.calibrage.akshaya.models.LoanResponse;
import in.calibrage.akshaya.models.LobourResponse;
import in.calibrage.akshaya.models.MSGmodel;
import in.calibrage.akshaya.service.APIConstantURL;
import in.calibrage.akshaya.service.ApiService;
import in.calibrage.akshaya.service.ServiceFactory;
import retrofit2.adapter.rxjava.HttpException;
import rx.Subscriber;
import rx.Subscription;
import rx.android.schedulers.AndroidSchedulers;
import rx.schedulers.Schedulers;

public class LoanActivity extends BaseActivity {
    CheckBox checkbox;
    private ProgressDialog dialog;
    public static String TAG = "LoanActivity";
    String currentDate;
    TextView ok, getTerms,head_text;
    TextView terms;
    String Farmer_code;
    ImageView backImg,home_btn;
    Button loan_Btn;
    private Subscription mSubscription;
    private SpotsDialog mdilogue;

    EditText amount,reason;
    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_loan);
        init();
        setViews();
    }
    private void init() {

        checkbox = (CheckBox) findViewById(R.id.checkBox);

        backImg = (ImageView) findViewById(R.id.back);
        terms = (TextView) findViewById(R.id.terms);
        loan_Btn = (Button) findViewById(R.id.req_loan);
        SharedPreferences pref = getSharedPreferences("FARMER", MODE_PRIVATE);
        Farmer_code=pref.getString("farmerid", "");       // Saving string data of your editext
         home_btn=(ImageView)findViewById(R.id.home_btn);
        amount= (EditText) findViewById(R.id.loan_amount);
        reason= (EditText) findViewById(R.id.reason);
        mdilogue = (SpotsDialog) new SpotsDialog.Builder()
                .setContext(this)
                .setTheme(R.style.Custom)
                .build();
    }

        private void setViews() {
        backImg.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View view) {
                Intent intent = new Intent(getApplicationContext(), HomeActivity.class);
                startActivity(intent);
            }
        });


            loan_Btn.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View view) {
                if (amount.getText() != null & amount.getText().toString().trim() != "" & !TextUtils.isEmpty(amount.getText())) {
                    if (checkbox.isChecked()) {

                        GetLoanDetails();
                    }
                    else {
                        showDialog(LoanActivity.this,getResources().getString(R.string.terms_agree));

                    }
                }else {
                    showDialog(LoanActivity.this,"Please Enter Loan Amount" );
                }


            }
        });

        home_btn.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View view) {
                Intent intent =new Intent(getApplicationContext(),HomeActivity.class);
                startActivity(intent);
            }
        });
        currentDate = new SimpleDateFormat("yyyy-MM-dd", Locale.getDefault()).format(new Date());
        Log.i("LOG_RESPONSE date ", currentDate);
        terms.setOnClickListener(new View.OnClickListener() {

            @Override
            public void onClick(View v) {
                // TODO Auto-generated method stub
                Log.e("Roja.....","1==================");
                showCustomDialog();

            }

        });
    }

    private void GetLoanDetails() {
        mdilogue.show();
        JsonObject object = LoanReuestobject();
        ApiService service = ServiceFactory.createRetrofitService(this, ApiService.class);
        mSubscription = service.postLoan(object)
                .subscribeOn(Schedulers.newThread())
                .observeOn(AndroidSchedulers.mainThread())
                .subscribe(new Subscriber<LoanResponse>() {
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

                    @RequiresApi(api = Build.VERSION_CODES.M)
                    @Override
                    public void onNext(LoanResponse loanResponse) {


                        if (loanResponse.getIsSuccess()) {

                            String Amount = amount.getText().toString();
                            String Reason = reason.getText().toString();
                            List<MSGmodel> displayList = new ArrayList<>();

                            displayList.add(new MSGmodel(getString(R.string.loan_amount), Amount));
                            displayList.add(new MSGmodel(getResources().getString(R.string.reason_loan), Reason));

                            showSuccessDialog(displayList);


                        } else {
                        showDialog(LoanActivity.this, loanResponse.getEndUserMessage());
                        }

                    }


                });
    }

    private JsonObject LoanReuestobject() {
        LoanRequest requestModel = new LoanRequest();
        requestModel.setFarmerCode(Farmer_code);
        requestModel.setPlotCode("APAB0001000001");
        requestModel.setIsFarmerRequest(true);
        requestModel.setComments(reason.getText().toString());
        requestModel.setCreatedByUserId(null);
        requestModel.setCreatedDate(currentDate);
        requestModel.setUpdatedByUserId(null);
        requestModel.setUpdatedDate(currentDate);
        requestModel.setRequestCreatedDate(currentDate);
        requestModel.setCropMaintainceDate(currentDate);
        requestModel.setStatusTypeId(15);
        requestModel.setRequestTypeId(28);
        requestModel.setTotalCost(Double.parseDouble(amount.getText().toString()));
        return new Gson().toJsonTree(requestModel).getAsJsonObject();
    }


    private void showCustomDialog() {
        final Dialog dialog = new Dialog(this);
        // Include dialog.xml file
        dialog.setContentView(R.layout.loan_dialog);
        // Set dialog title
        dialog.setTitle("Custom Dialog");

        // set values for custom dialog components - text, image and button
        /*TextView text = (TextView) dialog.findViewById(R.id.textDialog);
        text.setText("Custom dialog Android example.");
        ImageView image = (ImageView) dialog.findViewById(R.id.imageDialog);*/

        ok=(TextView)dialog.findViewById(R.id.ok);

        getTerms=(TextView)dialog.findViewById(R.id.txtclose) ;
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






}

