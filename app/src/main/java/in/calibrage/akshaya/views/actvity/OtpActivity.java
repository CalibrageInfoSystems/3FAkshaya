package in.calibrage.akshaya.views.actvity;

import android.app.ProgressDialog;
import android.content.Intent;
import android.content.SharedPreferences;
import android.os.Handler;
import android.support.v7.app.AppCompatActivity;
import android.os.Bundle;
import android.text.TextUtils;
import android.util.Log;
import android.view.View;
import android.view.Window;
import android.view.WindowManager;
import android.widget.Button;
import android.widget.ImageView;
import android.widget.TextView;

import com.android.volley.DefaultRetryPolicy;
import com.android.volley.Request;
import com.android.volley.RequestQueue;
import com.android.volley.Response;
import com.android.volley.RetryPolicy;
import com.android.volley.VolleyError;
import com.android.volley.toolbox.StringRequest;
import com.android.volley.toolbox.Volley;

import org.json.JSONException;
import org.json.JSONObject;

import java.io.IOException;

import in.calibrage.akshaya.R;
import in.calibrage.akshaya.common.PinEntryEditText;
import in.calibrage.akshaya.models.FarmerOtpResponceModel;
import in.calibrage.akshaya.models.FarmerResponceModel;
import in.calibrage.akshaya.service.APIConstantURL;
import in.calibrage.akshaya.service.ApiService;
import in.calibrage.akshaya.service.ServiceFactory;
import retrofit2.adapter.rxjava.HttpException;
import rx.Subscriber;
import rx.Subscription;
import rx.android.schedulers.AndroidSchedulers;
import rx.schedulers.Schedulers;

public class OtpActivity extends AppCompatActivity {
    public static final String TAG= OtpActivity.class.getSimpleName();
    private Subscription mSubscription;


    private Button sub_Btn;

    private String Pin_text;
    public static String farmerId;

    private PinEntryEditText pinEntry;
    public SharedPreferences.Editor editor;
    private ProgressDialog dialog;
    String first_name, middle_name, last_name, State_code;
    private  ImageView backImg;
    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        requestWindowFeature(Window.FEATURE_NO_TITLE);
        getWindow().setFlags(WindowManager.LayoutParams.FLAG_FULLSCREEN,
                WindowManager.LayoutParams.FLAG_FULLSCREEN);
        setContentView(R.layout.activity_otp);
        dialog = new ProgressDialog(this);
        init();
        setview();
    }

    private void init() {
        // TextView otpDesc = (TextView) findViewById(R.id.otp_desc);

        sub_Btn = (Button) findViewById(R.id.btn_otp_login);

         backImg = (ImageView) findViewById(R.id.back);
        pinEntry = findViewById(R.id.txt_pin_entry);
    }
        //  submitBtn.setTypeface(faceBold);

    private void setview() {
        sub_Btn.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View view) {


                if (pinEntry.getText() != null & pinEntry.getText().toString().trim() != "" & !TextUtils.isEmpty(pinEntry.getText())) {
                    GetOtp();
                }
                else
                {
                    pinEntry.setError("Please Enter Pin");
                }
            }
        });
        backImg.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View view) {
                finish();
            }
        });
        Pin_text =pinEntry.getText().toString();
    }



//        Intent in = getIntent();
//        //    farmerId= in.getExtras().getString("Farmer id");
//        Log.d("Otp", "Farmer id======" + farmerId);
///*
//if(farmerId==null){
//
//}*/
//        pinEntry = findViewById(R.id.txt_pin_entry);
//        pinEntry.requestFocus();
//        getWindow().setSoftInputMode(WindowManager.LayoutParams.SOFT_INPUT_STATE_VISIBLE);
//        if (pinEntry != null) {
//
//        }




    private void GetOtp() {
       // getFormerdetails

        ApiService service = ServiceFactory.createRetrofitService(this, ApiService.class);
        mSubscription = service.getFormerdetails(APIConstantURL.Farmer_otp+Pin_text)
                .subscribeOn(Schedulers.newThread())
                .observeOn(AndroidSchedulers.mainThread()).subscribe(new Subscriber<FarmerOtpResponceModel>() {
                    @Override
                    public void onCompleted() {

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
                    }

                    @Override
                    public void onNext(FarmerOtpResponceModel farmerOtpResponceModel) {
                        if (farmerOtpResponceModel.getIsSuccess()) {
                            new Handler().postDelayed(new Runnable() {
                                @Override
                                public void run() {
                                    /* Create an Intent that will start the Menu-Activity. */
                                    Intent intent = new Intent(getApplicationContext(), HomeActivity.class);
                                    startActivity(intent);


                                }
                            }, 300);

                        }
                    }


                });

        /*String otpText = pinEntry.getText().toString();

        dialog.setMessage("Loading, please wait....");
        dialog.show();
        dialog.setCanceledOnTouchOutside(false);

        String url = APIConstantURL.LOCAL_URL + "Farmer/" + "APWGBDAB00010001" + "/" + otpText;

        Log.d("Otp", "url======" + url);

        RequestQueue requestQueue = Volley.newRequestQueue(getApplicationContext());
        StringRequest stringRequest = new StringRequest(Request.Method.GET, url, new Response.Listener<String>() {
            @Override
            public void onResponse(String response) {
                Log.d(TAG, "RESPONSE======" + response);
                dialog.cancel();
                try {
                    JSONObject jsonObject = new JSONObject(response);
                    Log.d(TAG, "RESPONSE======" + jsonObject);
                    String success = jsonObject.getString("isSuccess");
                    Log.d(TAG, "success======" + success);
                    if (success.equals("true")) {

                        Intent intent = new Intent(getApplicationContext(), HomeActivity.class);

                        startActivity(intent);
                        //  Toasty.success(getApplicationContext(), "OTP Valided Successfully", Toast.LENGTH_SHORT).show();
                        //  Toast.makeText(getApplicationContext(),success,Toast.LENGTH_SHORT).show();
                    } else {
                        //Toasty.error(getApplicationContext(), "OTP Invalid", Toast.LENGTH_LONG).show();

                    }
//                    JSONArray alsoKnownAsArray = jsonObject.getJSONArray("listResult");
//                    Log.e("alsoKnownAsArray===", String.valueOf(alsoKnownAsArray));


                    // Toasty.success(getApplicationContext(), userDetails.getAddress(), Toast.LENGTH_LONG).show();

//                    jsonString = gson.toJson(student);
//                    System.out.println(jsonString);


                } catch (JSONException e) {
                    e.printStackTrace();
                }
            }
        }, new Response.ErrorListener() {
            @Override
            public void onErrorResponse(VolleyError error) {
                error.printStackTrace();
            }
        });
        int socketTimeout = 30000;
        RetryPolicy policy = new DefaultRetryPolicy(socketTimeout, DefaultRetryPolicy.DEFAULT_MAX_RETRIES, DefaultRetryPolicy.DEFAULT_BACKOFF_MULT);
        stringRequest.setRetryPolicy(policy);
        requestQueue.add(stringRequest);*/
    }

}
