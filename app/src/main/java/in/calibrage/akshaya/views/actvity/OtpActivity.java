package in.calibrage.akshaya.views.actvity;

import android.app.ProgressDialog;
import android.content.Intent;
import android.content.SharedPreferences;
import android.graphics.Typeface;
import android.support.v7.app.AppCompatActivity;
import android.os.Bundle;
import android.util.Log;
import android.view.View;
import android.view.Window;
import android.view.WindowManager;
import android.widget.Button;
import android.widget.EditText;
import android.widget.ImageView;
import android.widget.TextView;
import android.widget.Toast;

import com.android.volley.DefaultRetryPolicy;
import com.android.volley.Request;
import com.android.volley.RequestQueue;
import com.android.volley.Response;
import com.android.volley.RetryPolicy;
import com.android.volley.VolleyError;
import com.android.volley.toolbox.StringRequest;
import com.android.volley.toolbox.Volley;

import org.json.JSONArray;
import org.json.JSONException;
import org.json.JSONObject;

import in.calibrage.akshaya.R;
import in.calibrage.akshaya.common.PinEntryEditText;
import in.calibrage.akshaya.service.APIConstantURL;

public class OtpActivity extends AppCompatActivity {

    public static String farmerId ;
    public static  String TAG="OtpActivity";
    public PinEntryEditText pinEntry;
    public  SharedPreferences.Editor editor ;
    private ProgressDialog dialog;
    String  first_name,middle_name,last_name,State_code;
    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        requestWindowFeature(Window.FEATURE_NO_TITLE);
        getWindow().setFlags(WindowManager.LayoutParams.FLAG_FULLSCREEN,
                WindowManager.LayoutParams.FLAG_FULLSCREEN);
        setContentView(R.layout.activity_otp);
        dialog = new ProgressDialog(this);

        TextView otpDesc=(TextView)findViewById(R.id.otp_desc);
      //  otpDesc.setTypeface(faceRegular);

        Button submitBtn=(Button)findViewById(R.id.btn_otp_login);
      //  submitBtn.setTypeface(faceBold);
        submitBtn.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View view) {
                GetOtp();
             /*   Intent intent =new Intent(getApplicationContext(),SideMenuActivity.class);
                startActivity(intent);
*/
            }
        });

        ImageView backImg=(ImageView)findViewById(R.id.back);
        backImg.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View view) {
                finish();
            }
        });

        Intent in = getIntent();
    //    farmerId= in.getExtras().getString("Farmer id");
        Log.d("Otp","Farmer id======"+ farmerId);
/*
if(farmerId==null){

}*/
        pinEntry = findViewById(R.id.txt_pin_entry);
        pinEntry.requestFocus();
        getWindow().setSoftInputMode(WindowManager.LayoutParams.SOFT_INPUT_STATE_VISIBLE);
        if (pinEntry != null) {

                }

        }


    private void GetOtp() {
        String otpText=pinEntry.getText().toString();
        if (otpText.matches("")) {

            return;
        }
        dialog.setMessage("Loading, please wait....");
        dialog.show();
        dialog.setCanceledOnTouchOutside(false);


        Log.d(TAG,"RESPONSE======"+ otpText);
        String url = APIConstantURL.LOCAL_URL +"Farmer/"+"APWGBDAB00010001"+"/"+otpText;
        //   String url ="http://183.82.111.111/3FFarmerAPI/api/Farmer/"+farmerId+"/"+otpText;
        Log.d("Otp","url======"+ url);

        RequestQueue requestQueue = Volley.newRequestQueue(getApplicationContext());
        StringRequest stringRequest = new StringRequest(Request.Method.GET, url, new Response.Listener<String>() {
            @Override
            public void onResponse(String response) {
                Log.d(TAG,"RESPONSE======"+ response);
                if (dialog.isShowing()) {
                    dialog.dismiss();
                }
                try {
                    JSONObject jsonObject = new JSONObject(response);
                    Log.d(TAG,"RESPONSE======"+ jsonObject);
                    String success=jsonObject.getString("isSuccess");
                    Log.d(TAG,"success======"+ success);
                    if (success.equals("true")){

                        Intent intent =new Intent(getApplicationContext(), HomeActivity.class);

                        startActivity(intent);
                        //  Toasty.success(getApplicationContext(), "OTP Valided Successfully", Toast.LENGTH_SHORT).show();
                        //  Toast.makeText(getApplicationContext(),success,Toast.LENGTH_SHORT).show();
                    }else{
                        //Toasty.error(getApplicationContext(), "OTP Invalid", Toast.LENGTH_LONG).show();

                    }
                    JSONArray alsoKnownAsArray = jsonObject.getJSONArray("listResult");
                    Log.e("alsoKnownAsArray===", String.valueOf(alsoKnownAsArray));




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
        requestQueue.add(stringRequest);
    }

}
