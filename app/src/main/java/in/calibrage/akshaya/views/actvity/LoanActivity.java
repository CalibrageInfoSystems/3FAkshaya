package in.calibrage.akshaya.views.actvity;

import android.app.Dialog;
import android.app.ProgressDialog;
import android.content.Intent;
import android.content.SharedPreferences;
import android.support.v7.app.AppCompatActivity;
import android.os.Bundle;
import android.util.Log;
import android.view.View;
import android.widget.Button;
import android.widget.CheckBox;
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

import org.json.JSONException;
import org.json.JSONObject;

import java.text.SimpleDateFormat;
import java.util.Date;
import java.util.HashMap;
import java.util.Locale;
import java.util.Map;

import in.calibrage.akshaya.R;

public class LoanActivity extends AppCompatActivity {
    CheckBox checkbox;
    private ProgressDialog dialog;
    public static String TAG = "LoanActivity";
    String currentDate;
    TextView ok, getTerms,head_text;
    TextView terms;
    String Farmer_code;
    ImageView backImg,home_btn;
    Button loan_Btn;
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
                if (checkbox.isChecked()) {
                    GetLoanDetails();
                } else {
                    Toast.makeText(getApplicationContext(), R.string.terms_agree, Toast.LENGTH_SHORT).show();
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

