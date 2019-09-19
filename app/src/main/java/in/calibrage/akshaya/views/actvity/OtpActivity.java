package in.calibrage.akshaya.views.actvity;

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
import android.view.Window;
import android.view.WindowManager;
import android.widget.Button;
import android.widget.ImageView;
import android.widget.LinearLayout;
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

import dmax.dialog.SpotsDialog;
import in.calibrage.akshaya.R;
import in.calibrage.akshaya.common.BaseActivity;
import in.calibrage.akshaya.common.Constants;
import in.calibrage.akshaya.common.PinEntryEditText;
import in.calibrage.akshaya.localData.SharedPrefsData;
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

public class OtpActivity extends BaseActivity {
    public static final String TAG = OtpActivity.class.getSimpleName();
    private Subscription mSubscription;


    private Button sub_Btn;

    private String Pin_text;
    public  String Farmer_code;

    private PinEntryEditText pinEntry;
    public SharedPreferences.Editor editor;
    private ProgressDialog dialog;
    String first_name, middle_name, last_name, State_code;
    private ImageView backImg;
    private SpotsDialog mdilogue;

    @RequiresApi(api = Build.VERSION_CODES.M)
    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);

        requestWindowFeature(Window.FEATURE_NO_TITLE);
        getWindow().setFlags(WindowManager.LayoutParams.FLAG_FULLSCREEN,
                WindowManager.LayoutParams.FLAG_FULLSCREEN);
        setContentView(R.layout.activity_otp);

        init();
        setview();
    }

    @RequiresApi(api = Build.VERSION_CODES.M)
    private void init() {
        // TextView otpDesc = (TextView) findViewById(R.id.otp_desc);

        sub_Btn = (Button) findViewById(R.id.btn_otp_login);

        backImg = (ImageView) findViewById(R.id.back);
        pinEntry = findViewById(R.id.txt_pin_entry);
        pinEntry.requestFocus();
        mdilogue = (SpotsDialog) new SpotsDialog.Builder()
                .setContext(this)
                .setTheme(R.style.Custom)
                .build();
        SharedPreferences pref = getSharedPreferences("FARMER", MODE_PRIVATE);
        Farmer_code = pref.getString("farmerid", "");
    }
    //  submitBtn.setTypeface(faceBold);

    private void setview() {
        sub_Btn.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View view) {


                if (pinEntry.getText() != null & pinEntry.getText().toString().trim() != "" & !TextUtils.isEmpty(pinEntry.getText())) {
                    GetOtp();
                } else {
                    showDialog(OtpActivity.this,getResources().getString(R.string.ente_pin));
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


    }


    private void GetOtp() {
        mdilogue.show();
        ApiService service = ServiceFactory.createRetrofitService(this, ApiService.class);
        mSubscription = service.getFormerdetails(APIConstantURL.Farmer_otp +Farmer_code+"/" + pinEntry.getText().toString())
                .subscribeOn(Schedulers.newThread())
                .observeOn(AndroidSchedulers.mainThread()).subscribe(new Subscriber<FarmerOtpResponceModel>() {
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
                    public void onNext(final FarmerOtpResponceModel farmerOtpResponceModel) {
                        mdilogue.dismiss();
                        if (farmerOtpResponceModel.getIsSuccess()) {


                            new Handler().postDelayed(new Runnable() {
                                @Override
                                public void run() {
                                    /* Create an Intent that will start the Menu-Activity. */
                                    SharedPrefsData.putBool(OtpActivity.this, Constants.IS_LOGIN, true);
                                    SharedPrefsData.saveCatagories(OtpActivity.this, farmerOtpResponceModel);
  SharedPrefsData.getInstance(OtpActivity.this).updateStringValue(OtpActivity.this,Constants.USER_ID,farmerOtpResponceModel.getResult().getFarmerDetails().get(0).getCode());
  Log.e("Formarcode==",farmerOtpResponceModel.getResult().getFarmerDetails().get(0).getCode());
                                    SharedPrefsData.getInstance(OtpActivity.this).updateStringValue(OtpActivity.this, "statecode", farmerOtpResponceModel.getResult().getFarmerDetails().get(0).getStateCode());

                                    Intent intent = new Intent(getApplicationContext(), HomeActivity.class);
                                    startActivity(intent);
                                    finish();

                                }
                            }, 000);

                        }
                        else {
                            showDialog(OtpActivity.this,farmerOtpResponceModel.getEndUserMessage());
                        }
                    }


                });


    }

}
