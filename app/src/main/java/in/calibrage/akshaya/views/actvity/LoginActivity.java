package in.calibrage.akshaya.views.actvity;

import android.content.Intent;
import android.content.SharedPreferences;
import android.support.v7.app.AppCompatActivity;
import android.os.Bundle;
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

import in.calibrage.akshaya.R;
import in.calibrage.akshaya.service.APIConstantURL;

public class LoginActivity extends AppCompatActivity {
    Button loginBtn,Qr_scan;
   EditText farmerId;
   String Farmer_code;
    public static  String TAG="LoginActivity";
    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        requestWindowFeature(Window.FEATURE_NO_TITLE);
        getWindow().setFlags(WindowManager.LayoutParams.FLAG_FULLSCREEN,
                WindowManager.LayoutParams.FLAG_FULLSCREEN);
        setContentView(R.layout.activity_login);

        loginBtn=(Button)findViewById(R.id.btn_login);
        Qr_scan=(Button)findViewById(R.id.btn_qrscan);


        farmerId = findViewById(R.id.farmer_id_edittxt);
        loginBtn.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View view) {
               Intent intent =new Intent(getApplicationContext(),OtpActivity.class);
               startActivity(intent);

                GetLogin();
            }
        });

        Qr_scan.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View view) {
                Intent intent =new Intent(getApplicationContext(),ScannedBarcodeActivity.class);
                startActivity(intent);

                GetLogin();
            }
        });
    }

    private void GetLogin() {

        Farmer_code=farmerId.getText().toString();
        if (Farmer_code.matches("")) {

            return;
        }



        String url = APIConstantURL.LOCAL_URL +"Farmer/"+Farmer_code;
        Log.e("Login==url",url);

        SharedPreferences pref = getApplicationContext().getSharedPreferences("FARMER", MODE_PRIVATE);
        SharedPreferences.Editor editor = pref.edit();
        editor.putString("farmerid", Farmer_code);  // Saving string data of your editext
        editor.commit();
        RequestQueue requestQueue = Volley.newRequestQueue(getApplicationContext());
        StringRequest stringRequest = new StringRequest(Request.Method.GET, url, new Response.Listener<String>() {
            @Override
            public void onResponse(String response) {

                try {
                    JSONObject jsonObject = new JSONObject(response);
                    Log.d(TAG,"RESPONSE======"+ jsonObject);
                    String success=jsonObject.getString("isSuccess");
                    Log.d(TAG,"success======"+ success);
                    if (success.equals("true")){
                        Intent intent =new Intent(getApplicationContext(),OtpActivity.class);
                        intent.putExtra ( "Farmer id", farmerId.getText().toString() );
                        startActivity(intent);

                    }else{



                    }


                } catch (JSONException e) {
                    e.printStackTrace();
                }
            }
        }, new Response.ErrorListener() {
            @Override
            public void onErrorResponse(VolleyError error) {
                error.printStackTrace();
                if (error instanceof NetworkError) {
                    Log.i("one:" + TAG, error.toString());

                } else if (error instanceof ServerError) {
                    Log.i("two:" + TAG, error.toString());

                } else if (error instanceof AuthFailureError) {
                    Log.i("three:" + TAG, error.toString());

                } else if (error instanceof ParseError) {
                    Log.i("four::" + TAG, error.toString());

                } else if (error instanceof NoConnectionError) {
                    Log.i("five::" + TAG, error.toString());

                } else if (error instanceof TimeoutError) {
                    Log.i("six::" + TAG, error.toString());

                } else {
                    System.out.println("Checking error in else");
                }
            }
        });
        int socketTimeout = 30000;
        RetryPolicy policy = new DefaultRetryPolicy(socketTimeout, DefaultRetryPolicy.DEFAULT_MAX_RETRIES, DefaultRetryPolicy.DEFAULT_BACKOFF_MULT);
        stringRequest.setRetryPolicy(policy);
        requestQueue.add(stringRequest);
    }


}
