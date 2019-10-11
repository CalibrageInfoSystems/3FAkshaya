package in.calibrage.akshaya.views.actvity;

import android.Manifest;
import android.app.ProgressDialog;
import android.content.Context;
import android.content.DialogInterface;
import android.content.Intent;
import android.content.SharedPreferences;
import android.content.pm.PackageManager;
import android.os.Handler;
import android.provider.Settings;
import android.support.annotation.NonNull;
import android.support.v4.app.ActivityCompat;
import android.support.v4.content.ContextCompat;
import android.support.v7.app.AppCompatActivity;
import android.os.Bundle;
import android.telephony.TelephonyManager;
import android.text.TextUtils;
import android.util.Log;
import android.view.View;
import android.view.Window;
import android.view.WindowManager;
import android.widget.Button;
import android.widget.EditText;
import android.widget.LinearLayout;
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
import com.google.gson.Gson;
import com.google.gson.JsonObject;


import org.json.JSONException;
import org.json.JSONObject;

import java.io.IOException;
import java.text.SimpleDateFormat;
import java.util.Date;
import java.util.Locale;

import dmax.dialog.SpotsDialog;
import in.calibrage.akshaya.MainActivity;
import in.calibrage.akshaya.R;
import in.calibrage.akshaya.common.BaseActivity;
import in.calibrage.akshaya.localData.SharedPrefsData;
import in.calibrage.akshaya.models.FarmerResponceModel;
import in.calibrage.akshaya.models.Reqinstall;
import in.calibrage.akshaya.models.Resinstall;
import in.calibrage.akshaya.models.labour_req_response;
import in.calibrage.akshaya.service.APIConstantURL;
import in.calibrage.akshaya.service.ApiService;
import in.calibrage.akshaya.service.ServiceFactory;
import in.calibrage.akshaya.views.Adapter.MyLabour_ReqAdapter;

import me.dm7.barcodescanner.zxing.ZXingScannerView;
import retrofit2.adapter.rxjava.HttpException;
import rx.Subscriber;
import rx.Subscription;
import rx.android.schedulers.AndroidSchedulers;
import rx.schedulers.Schedulers;

public class LoginActivity extends BaseActivity {
    private static final String TAG = LoginActivity.class.getSimpleName();
    private static final int REQUEST_READ_PHONE_STATE = 1;
    private Button loginBtn, Qr_scan;
    public static EditText farmerId;
    private String Farmer_code, Device_id, currentDate;
    private Subscription mSubscription;
    private SpotsDialog mdilogue;
    TelephonyManager tel;
    ZXingScannerView scannerView;
    private int MY_PERMISSIONS_REQUEST_CAMERA = 1;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
       /* requestWindowFeature(Window.FEATURE_NO_TITLE);
        getWindow().setFlags(WindowManager.LayoutParams.FLAG_FULLSCREEN,
                WindowManager.LayoutParams.FLAG_FULLSCREEN);*/
        setContentView(R.layout.activity_login);


        init();
        setview();


    }

    private void init() {

        loginBtn = (Button) findViewById(R.id.btn_login);
        Qr_scan = (Button) findViewById(R.id.btn_qrscan1);

        farmerId = findViewById(R.id.farmer_id_edittxt);
        //



        
        //farmerId.setText("APWGBDAB00010001");

        tel = (TelephonyManager) getSystemService(Context.TELEPHONY_SERVICE);


        tel = (TelephonyManager) getSystemService(Context.TELEPHONY_SERVICE);


        //  Device_id = tel.getDeviceId().toString();

        if (ActivityCompat.checkSelfPermission(this, Manifest.permission.READ_PHONE_STATE) != PackageManager.PERMISSION_GRANTED) {
            // TODO: Consider calling
            //    ActivityCompat#requestPermissions
            // here to request the missing permissions, and then overriding
            //   public void onRequestPermissionsResult(int requestCode, String[] permissions,
            //                                          int[] grantResults)
            // to handle the case where the user grants the permission. See the documentation
            // for ActivityCompat#requestPermissions for more details.
            return;
        }
        Log.e("deviece==id", tel.getDeviceId().toString());
        Device_id = tel.getDeviceId().toString();

        currentDate = new SimpleDateFormat("yyyy-MM-dd", Locale.getDefault()).format(new Date());
        //  imei = (TextView) findViewById(R.id.textView2);

        mdilogue = (SpotsDialog) new SpotsDialog.Builder()
                .setContext(this)
                .setTheme(R.style.Custom)
                .build();
        validationPopShow();


    }


    private void setview() {

        loginBtn.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View view) {

                if (farmerId.getText() != null & farmerId.getText().toString().trim() != "" & !TextUtils.isEmpty(farmerId.getText())) {
                    Farmer_code = farmerId.getText().toString();
                    Log.e("former==id", Farmer_code);

                    SharedPreferences pref = getApplicationContext().getSharedPreferences("FARMER", MODE_PRIVATE);
                    SharedPreferences.Editor editor = pref.edit();
                    editor.putString("farmerid", Farmer_code);  // Saving string data of your editext
                    editor.commit();
//

                    if (isOnline())
                        GetLogin();

                    else {
                        showDialog(LoginActivity.this, getResources().getString(R.string.Internet));
                        //Toast.makeText(LoginActivity.this, "Please Check Internet Connection ", Toast.LENGTH_SHORT).show();
                    }
                } else {
                    // validationPopShow();
                    // farmerId.setError("Please Enter Farmer Id");
                    showDialog(LoginActivity.this, getResources().getString(R.string.farmar_id));
                    //showDialog(LoginActivity.this,"Please Enter Farmer Id");
                }
            }
        });

        Qr_scan.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View view) {


               /* Intent intent = new Intent(getApplicationContext(), ScannedBarcodeActivity.class);
                st(intent);
                if (validations()) {
                    GetLogin(); Invalid Farmer Id
                }else
                {
                    farmerId.setError("Please Enter Farmer Id");
                }*/

                if (ContextCompat.checkSelfPermission(LoginActivity.this,
                        Manifest.permission.CAMERA) == PackageManager.PERMISSION_GRANTED) {

                    startActivity(new Intent(LoginActivity.this, QRScannerActivity.class));

                } else {
                    ActivityCompat.requestPermissions((LoginActivity) LoginActivity.this,
                            new String[]{Manifest.permission.CAMERA}, MY_PERMISSIONS_REQUEST_CAMERA);
                }


            }

        });
    }

    private void GetLogin() {
        if (null != mdilogue)
            mdilogue.show();
        else {

            mdilogue = (SpotsDialog) new SpotsDialog.Builder()
                    .setContext(this)
                    .setTheme(R.style.Custom)
                    .build();
            mdilogue.show();
        }
        ApiService service = ServiceFactory.createRetrofitService(this, ApiService.class);
        mSubscription = service.getFormerOTP(APIConstantURL.Farmer_ID_CHECK + farmerId.getText().toString())
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
                        mdilogue.cancel();
                    }

                    @Override
                    public void onNext(FarmerResponceModel farmerResponceModel) {
                        mdilogue.cancel();
                        Log.d(TAG, "onNext: " + farmerResponceModel);
                        if (farmerResponceModel.getIsSuccess()) {
                            new Handler().postDelayed(new Runnable() {
                                @Override
                                public void run() {
                                    /* Create an Intent that will start the Menu-Activity. */

                                    startActivity(new Intent(getApplicationContext(), OtpActivity.class));
                                    finish();
                                }
                            }, 300);

                        } else {
                            showDialog(LoginActivity.this, getResources().getString(R.string.Invalid));
                        }
                    }
                });


    }


    @Override
    public void onRequestPermissionsResult(int requestCode, @NonNull String[] permissions, @NonNull int[] grantResults) {
        super.onRequestPermissionsResult(requestCode, permissions, grantResults);
        if (requestCode == 1) {
            startActivity(new Intent(LoginActivity.this, QRScannerActivity.class));
        }
    }
}



