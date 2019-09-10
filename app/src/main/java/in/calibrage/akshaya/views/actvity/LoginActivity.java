package in.calibrage.akshaya.views.actvity;

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
import android.widget.EditText;
import android.widget.Toast;

import com.android.volley.AuthFailureError;
import com.android.volley.DefaultRetryPolicy;
import com.android.volley.NetworkError;
import com.android.volley.NoConnectionError;
import com.android.volley.ParseError;
import com.android.volley.Request;
import com.android.volley.RequestQueue;
import com.android.volley.Response;
import com.android.volley.RetryPolicy;
import com.android.volley.ServerError;
import com.android.volley.TimeoutError;
import com.android.volley.VolleyError;
import com.android.volley.toolbox.StringRequest;
import com.android.volley.toolbox.Volley;

import org.json.JSONException;
import org.json.JSONObject;

import java.io.IOException;

import in.calibrage.akshaya.R;
import in.calibrage.akshaya.common.BaseActivity;
import in.calibrage.akshaya.models.FarmerResponceModel;
import in.calibrage.akshaya.service.APIConstantURL;
import in.calibrage.akshaya.service.ApiService;
import in.calibrage.akshaya.service.ServiceFactory;
import retrofit2.adapter.rxjava.HttpException;
import rx.Subscriber;
import rx.Subscription;
import rx.android.schedulers.AndroidSchedulers;
import rx.schedulers.Schedulers;

public class LoginActivity extends BaseActivity {
    private static final String TAG = LoginActivity.class.getSimpleName();
    private Button loginBtn, Qr_scan;
    private EditText farmerId;
    private String Farmer_code;
    private Subscription mSubscription;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        requestWindowFeature(Window.FEATURE_NO_TITLE);
        getWindow().setFlags(WindowManager.LayoutParams.FLAG_FULLSCREEN,
                WindowManager.LayoutParams.FLAG_FULLSCREEN);
        setContentView(R.layout.activity_login);
        init();

        setview();
    }

    private void init() {
        loginBtn = (Button) findViewById(R.id.btn_login);
        Qr_scan = (Button) findViewById(R.id.btn_qrscan);

        farmerId = findViewById(R.id.farmer_id_edittxt);
        //
        farmerId.setText("APWGBDAB00010001");
    }

    private void setview() {

        loginBtn.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View view) {

                if (farmerId.getText() != null & farmerId.getText().toString().trim() != "" & !TextUtils.isEmpty(farmerId.getText())) {
                    if (isOnline())
                        GetLogin();
                    else {
                        Toast.makeText(LoginActivity.this, "Please Check Internet Connection ", Toast.LENGTH_SHORT).show();
                    }
                } else {
                    farmerId.setError("Please Enter Farmer Id");
                }
            }
        });
        Qr_scan.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View view) {
               /* Intent intent = new Intent(getApplicationContext(), ScannedBarcodeActivity.class);
                st(intent);
                if (validations()) {
                    GetLogin();
                }else
                {
                    farmerId.setError("Please Enter Farmer Id");
                }*/
            }
        });


    }


    private void GetLogin() {


        ApiService service = ServiceFactory.createRetrofitService(this, ApiService.class);
        mSubscription = service.getFormerOTP(APIConstantURL.Farmer_ID_CHECK + farmerId.getText().toString())
                .subscribeOn(Schedulers.newThread())
                .observeOn(AndroidSchedulers.mainThread()).subscribe(new Subscriber<FarmerResponceModel>() {
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
                    public void onNext(FarmerResponceModel farmerResponceModel) {


                        Log.d(TAG, "onNext: " + farmerResponceModel);
                        if (farmerResponceModel.getIsSuccess()) {
                            new Handler().postDelayed(new Runnable() {
                                @Override
                                public void run() {
                                    /* Create an Intent that will start the Menu-Activity. */
                                    Intent intent = new Intent(getApplicationContext(), OtpActivity.class);
                                    startActivity(intent);


                                }
                            }, 300);

                        } else {
                            Toast.makeText(LoginActivity.this, farmerResponceModel.getEndUserMessage(), Toast.LENGTH_SHORT).show();
                        }
                        // Log.d(TAG,)
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
