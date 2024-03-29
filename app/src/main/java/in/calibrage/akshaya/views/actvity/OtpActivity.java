package in.calibrage.akshaya.views.actvity;

import android.content.Intent;
import android.content.SharedPreferences;
import android.os.Build;
import android.os.Handler;
import android.provider.Settings;
//import android.support.annotation.RequiresApi;
import android.os.Bundle;
import android.text.TextUtils;
import android.util.Log;
import android.view.View;
import android.view.Window;
import android.view.WindowManager;
import android.widget.Button;
import android.widget.ImageView;
import android.widget.TextView;
import android.widget.Toast;

import androidx.annotation.RequiresApi;

import com.google.gson.Gson;
import com.google.gson.JsonObject;

import java.io.IOException;
import java.text.SimpleDateFormat;
import java.util.Date;
import java.util.Locale;

import dmax.dialog.SpotsDialog;
import in.calibrage.akshaya.R;
import in.calibrage.akshaya.common.BaseActivity;
import in.calibrage.akshaya.common.Constants;
import in.calibrage.akshaya.common.PinEntryEditText;
import in.calibrage.akshaya.localData.SharedPrefsData;
import in.calibrage.akshaya.models.FarmerOtpResponceModel;
import in.calibrage.akshaya.models.FarmerResponceModel;
import in.calibrage.akshaya.models.Reqinstall;
import in.calibrage.akshaya.models.Resinstall;
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
    private String currentDate, Farmer_code;
    private PinEntryEditText pinEntry;
    private ImageView backImg;
    TextView otp_desc;
    String Reg_mobilenumber;
    private SpotsDialog mdilogue;
    String F_number, S_number;
    TextView resend;



    @RequiresApi(api = Build.VERSION_CODES.M)
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
        requestWindowFeature(Window.FEATURE_NO_TITLE);
        getWindow().setFlags(WindowManager.LayoutParams.FLAG_FULLSCREEN,
                WindowManager.LayoutParams.FLAG_FULLSCREEN);
        setContentView(R.layout.activity_otp);

        init();
        setview();
    }

    private void updateResources(OtpActivity otpActivity, String s) {
    }

    @RequiresApi(api = Build.VERSION_CODES.M)
    private void init() {

        currentDate = new SimpleDateFormat("yyyy-MM-dd", Locale.getDefault()).format(new Date());
        String Device_id = Settings.Secure.getString(this.getContentResolver(),
                Settings.Secure.ANDROID_ID);
        Log.e("deviece==id", Device_id);
        sub_Btn = (Button) findViewById(R.id.btn_otp_login);
        backImg = (ImageView) findViewById(R.id.back);
        otp_desc = (TextView) findViewById(R.id.otp_desc);
        pinEntry = findViewById(R.id.txt_pin_entry);
        resend =findViewById(R.id.resend_otp);
        pinEntry.requestFocus();
        mdilogue = (SpotsDialog) new SpotsDialog.Builder()
                .setContext(this)
                .setTheme(R.style.Custom)
                .build();
        SharedPreferences pref = getSharedPreferences("FARMER", MODE_PRIVATE);
        Farmer_code = pref.getString("farmerid", "").trim();


    }
// First time App installation based on IMEI number
    private void AddAppInstallation() {
        JsonObject object = getinstallobject();
        ApiService service = ServiceFactory.createRetrofitService(this, ApiService.class);
        mSubscription = service.post_install(object)
                .subscribeOn(Schedulers.newThread())
                .observeOn(AndroidSchedulers.mainThread())
                .subscribe(new Subscriber<Resinstall>() {
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
                    public void onNext(Resinstall resinstall) {
                        SharedPrefsData.putBool(OtpActivity.this, "installed", true);
                    }


                });
    }

    private JsonObject getinstallobject() {
        String android_id = Settings.Secure.getString(this.getContentResolver(),
                Settings.Secure.ANDROID_ID);
        Reqinstall requestModel = new Reqinstall();
        requestModel.setId(0);
        requestModel.setFarmerCode(Farmer_code);
        requestModel.setFarmerName(SharedPrefsData.getusername(this));
        requestModel.setInstalledOn(currentDate);
        requestModel.setLastLoginDate(currentDate);
        requestModel.setImeiNumber(android_id);
        return new Gson().toJsonTree(requestModel).getAsJsonObject();

    }

    private void setview() {
        Bundle extras = getIntent().getExtras();
        if (extras != null) {
            Reg_mobilenumber = extras.getString("mobile");
            if (Reg_mobilenumber.contains(",")) {
                String[] separated = Reg_mobilenumber.split(",");
                F_number = separated[0].replaceAll("\\d(?=(?:\\D*\\d){4})", "*");
                S_number = separated[1].replaceAll("\\d(?=(?:\\D*\\d){4})", "*");
                otp_desc.setText(getString(R.string.otp_desc) + " " + F_number + "," + S_number);
            }
            else {
                String number = Reg_mobilenumber
                        .replaceAll("\\d(?=(?:\\D*\\d){4})", "*");
                otp_desc.setText(getString(R.string.otp_desc) + " " + number);
            }
        }
        sub_Btn.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View view) {
                if (pinEntry.getText() != null & pinEntry.getText().toString().trim() != "" & !TextUtils.isEmpty(pinEntry.getText())) {
                    if (isOnline())
                        GetOtp();

                    else {
                        showDialog(OtpActivity.this, getResources().getString(R.string.Internet));
                    }
                } else {
                    showDialog(OtpActivity.this, getResources().getString(R.string.ente_pin));
                    //pinEntry.setError("Please Enter Pin");
                }
            }
        });
        resend.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View view) {
                resendotp();
            }
        });

        backImg.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View view) {
                finish();
            }
        });
    }

    private void resendotp() {
        mdilogue.show();
        ApiService service = ServiceFactory.createRetrofitService(this, ApiService.class);
        mSubscription = service.getFormerOTP(APIConstantURL.Farmer_ID_CHECK + Farmer_code+"/"+null)
                .subscribeOn(Schedulers.newThread())
                .observeOn(AndroidSchedulers.mainThread()).subscribe(new Subscriber<FarmerResponceModel>() {
                    @Override
                    public void onCompleted() {
                        mdilogue.cancel();
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
                        showDialog(OtpActivity.this, getString(R.string.server_error));
                    }

                    @Override
                    public void onNext(FarmerResponceModel farmerResponceModel) {
                        mdilogue.cancel();
                        Log.d(TAG, "onNext: " + farmerResponceModel);
                        if (farmerResponceModel.getIsSuccess()) {
                            Toast.makeText(getApplicationContext(), getString(R.string.otpsuccess), Toast.LENGTH_LONG).show();
                          //  showDialog(OtpActivity.this, "Otp sent to Your Register Mobile Number(s)");
//                            if (farmerResponceModel.getResult()!=null) {
//                                mobile_number = farmerResponceModel.getResult();
//OTP మీ రిజిస్టర్ మొబైల్ నంబర్ (ల) కు పంపబడింది
//                                Log.d("mobile_number===", mobile_number);
//                            }
//                            else {
//                                showDialog(LoginActivity.this, "No Register Mobile Number for Send Otp");
//                            }

                        } else {
                            showDialog(OtpActivity.this, getResources().getString(R.string.Invalid));
                        }
                    }
                });

    }

    private void GetOtp() {
        mdilogue.show();
        ApiService service = ServiceFactory.createRetrofitService(this, ApiService.class);
        mSubscription = service.getFormerdetails(APIConstantURL.Farmer_otp + Farmer_code + "/" + pinEntry.getText().toString())
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
                            Log.e("getFormerdetails",   e.getLocalizedMessage());
                        }
                        mdilogue.dismiss();
                        showDialog(OtpActivity.this, getString(R.string.server_error));
                    }

                    @Override
                    public void onNext(final FarmerOtpResponceModel farmerOtpResponceModel) {

                        if (farmerOtpResponceModel.getIsSuccess()) {
                            mdilogue.dismiss();
                            sub_Btn.setEnabled(false);
//                            if (null != farmerOtpResponceModel.getResult().getFarmerDetails() && farmerOtpResponceModel.getResult().getFarmerDetails().size() > 0) {
                                new Handler().postDelayed(new Runnable() {
                                    @Override
                                    public void run() {
                                        /* Create an Intent that will start the Menu-Activity. */
                                        SharedPrefsData.putBool(OtpActivity.this, Constants.IS_LOGIN, true);
                                        SharedPrefsData.saveCatagories(OtpActivity.this, farmerOtpResponceModel);
                                        SharedPrefsData.getInstance(OtpActivity.this).updateStringValue(OtpActivity.this, Constants.USER_ID, farmerOtpResponceModel.getResult().getFarmerDetails().get(0).getCode());
                                        Log.e("Formarclustter==", farmerOtpResponceModel.getResult().getFarmerDetails().get(0).getClusterId()+"");
                                        SharedPrefsData.getInstance(OtpActivity.this).updateStringValue(OtpActivity.this, "statecode", farmerOtpResponceModel.getResult().getFarmerDetails().get(0).getStateCode());
                                        SharedPrefsData.getInstance(OtpActivity.this).updateIntValue(OtpActivity.this, "districtId", farmerOtpResponceModel.getResult().getFarmerDetails().get(0).getDistrictId());
                                        SharedPrefsData.getInstance(OtpActivity.this).updateStringValue(OtpActivity.this, "districtName", farmerOtpResponceModel.getResult().getFarmerDetails().get(0).getDistrictName());


                                        AddAppInstallation() ;
                                        Intent intent = new Intent(getApplicationContext(), HomeActivity.class);
                                        startActivity(intent);
                                        finish();
                                    }
                                }, 000);
//                            } else {
//                                showDialog(OtpActivity.this, farmerOtpResponceModel.getEndUserMessage());
//                            }

                        } else {
                            showDialog(OtpActivity.this, farmerOtpResponceModel.getEndUserMessage());
                        }
                    }


                });


    }

}
