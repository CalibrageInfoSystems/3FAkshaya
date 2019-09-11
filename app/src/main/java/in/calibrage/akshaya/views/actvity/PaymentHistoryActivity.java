package in.calibrage.akshaya.views.actvity;

import android.app.DatePickerDialog;
import android.app.ProgressDialog;
import android.content.Intent;
import android.support.design.widget.TextInputLayout;
import android.support.v7.app.AppCompatActivity;
import android.os.Bundle;
import android.support.v7.widget.LinearLayoutManager;
import android.support.v7.widget.RecyclerView;
import android.text.InputType;
import android.util.Log;
import android.view.View;
import android.view.Window;
import android.view.WindowManager;
import android.widget.Button;
import android.widget.DatePicker;
import android.widget.EditText;
import android.widget.ImageView;
import android.widget.RelativeLayout;
import android.widget.TextView;
import android.widget.Toast;

import java.text.ParseException;
import java.text.SimpleDateFormat;
import java.util.Calendar;
import java.util.Date;

import in.calibrage.akshaya.R;
import in.calibrage.akshaya.views.Adapter.PaymentAdapter;

public class PaymentHistoryActivity extends AppCompatActivity {
    EditText fromText,toText;
    String fromString,toString;
    DatePickerDialog picker;
    RelativeLayout totalLinear;
    private PaymentAdapter pay_adapter;

    Button submit;
    String  datetimevaluereq;
    String reformattedStrFrom,reformattedStrTo;
    TextView text,Total_records,ffb,gr,totalAdjusted,totalBalance;
    private Calendar calendar;
    String finalbalance;
    private ProgressDialog dialog;
    ImageView _infoView;
    TextInputLayout from_txt,to_txt;
    RecyclerView Payment_recycle;
    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        requestWindowFeature(Window.FEATURE_NO_TITLE);
        getWindow().setFlags(WindowManager.LayoutParams.FLAG_FULLSCREEN,
                WindowManager.LayoutParams.FLAG_FULLSCREEN);
        setContentView(R.layout.activity_payment_history);

        dialog = new ProgressDialog(this);
        text=(TextView)findViewById(R.id.text);
        ffb=(TextView)findViewById(R.id.ffb_total);
      /*  gr=(TextView)findViewById(R.id.GR_total);
        amountTotal=(TextView)findViewById(R.id.amount_total);
        totalAdjusted=(TextView)findViewById(R.id.totalAdjusted);*/
        totalBalance=(TextView)findViewById(R.id.totalBalance);
        Total_records=(TextView)findViewById(R.id.total_records);
        ImageView backImg=(ImageView)findViewById(R.id.back);
        backImg.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View view) {
                Intent intent =new Intent(getApplicationContext(),PaymentActivity.class);
                startActivity(intent);
            }
        });

        ImageView home_btn=(ImageView)findViewById(R.id.home_btn);
        home_btn.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View view) {
                Intent intent =new Intent(getApplicationContext(),HomeActivity.class);
                startActivity(intent);
            }
        });
        from_txt = (TextInputLayout)findViewById(R.id.txt_from_date);
        to_txt = (TextInputLayout)findViewById(R.id.txt_to_date);
        submit=(Button)findViewById(R.id.btn__sub);
        Payment_recycle = (RecyclerView) findViewById(R.id.recycler_view);
        Payment_recycle.setHasFixedSize(true);
        LinearLayoutManager  layoutManager = new LinearLayoutManager(this);
        Payment_recycle.setLayoutManager(layoutManager);

        fromText=(EditText) findViewById(R.id.from_date);
        fromText.setInputType(InputType.TYPE_NULL);
        fromText.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View view) {
                calendar = Calendar.getInstance();
                int day = calendar.get(Calendar.DAY_OF_MONTH);
                int month = calendar.get(Calendar.MONTH);
                int year = calendar.get(Calendar.YEAR);
                // date picker dialog
                //   picker.getDatePicker().setMaxDate(System.currentTimeMillis());
                picker = new DatePickerDialog(PaymentHistoryActivity.this,
                        new DatePickerDialog.OnDateSetListener() {
                            @Override
                            public void onDateSet(DatePicker view, int year, int monthOfYear, int dayOfMonth) {
                                fromText.setText(dayOfMonth + "/" + (monthOfYear + 1) + "/" + year);


                            }
                        }, year, month, day);
                picker.show();
                picker.getDatePicker().setMaxDate(System.currentTimeMillis());
            }
        });

        toText=(EditText) findViewById(R.id.to_date);
        toText.setInputType(InputType.TYPE_NULL);
        toText.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View view) {
                Calendar  calendar1 = Calendar.getInstance();
                int day = calendar1.get(Calendar.DAY_OF_MONTH);
                int month = calendar1.get(Calendar.MONTH);
                int year = calendar1.get(Calendar.YEAR);
                // date picker dialog
                DatePickerDialog   picker1 = new DatePickerDialog(PaymentHistoryActivity.this,
                        new DatePickerDialog.OnDateSetListener() {
                            @Override
                            public void onDateSet(DatePicker view, int year, int monthOfYear, int dayOfMonth) {


                                toText.setText(dayOfMonth + "/" + (monthOfYear + 1) + "/" + year);

                                String selected_date=(dayOfMonth + "/" + (monthOfYear + 1) + "/" + year);
                                int month = (monthOfYear + 1);

                                Log.e("selected_date===",selected_date);
                            }
                        }, year, month, day);

                picker1.show();
                picker1.getDatePicker().setMaxDate(System.currentTimeMillis());
            }
        });


        submit.setOnClickListener(new View.OnClickListener() {

            @Override
            public void onClick(View view) {

                fromString = fromText.getText().toString().trim();
                toString = toText.getText().toString().trim();
                Log.d("fromString==", fromString);
                Log.d("toString==", toString);

                if(fromString.equalsIgnoreCase("")||toString.equalsIgnoreCase(""))
                {
                    Toast.makeText(PaymentHistoryActivity.this, "Please Enter From Date and To Date", Toast.LENGTH_SHORT).show();

                }
                else {
                    SimpleDateFormat formatter = new SimpleDateFormat("dd/MM/yyyy");
                    // String str1 = "9/10/2015";
                    Date date1 = null;
                    try {
                        date1 = formatter.parse(fromString);

                        Date date2 = formatter.parse(toString);
                        if (date2.compareTo(date1)<0)
                        {
                            Toast.makeText(getApplicationContext(),"Please Enter From Date is less than To Date",Toast.LENGTH_LONG).show();
                        }else{

                            SimpleDateFormat sdf = new SimpleDateFormat("dd/MM/yyyy");
                            Date d1 = null;
                            Date d2 = null;
                            try {
                                d1 = sdf.parse(fromString);

                                d2 = sdf.parse(toString);


                                System.out.println("1. " + sdf.format(d1).toUpperCase());
                                System.out.println("2. " + sdf.format(d2).toUpperCase());

                                if (compareTo(d1, d2) < 0) {

                                    System.out.println("proceed");
                                } else if (compareTo(d1, d2) > 0) {
                                    System.out.println("invalid");
                                } else {
                                    System.out.println("equal");
                                }
                            } catch (ParseException e) {
                                e.printStackTrace();
                            }

                            long diff = d2.getTime() - d1.getTime();

                            Log.e("diff===", String.valueOf(diff));

                            float dayCount = (float) diff / (24 * 60 * 60 * 1000);

                            Log.e("dayCount===", String.valueOf(dayCount));

                            getPaymentDetails(fromString,toString);
                        }
                    } catch (ParseException e) {
                        e.printStackTrace();
                    }






                }



            }
        });

    }

    public static long compareTo( Date date1, Date date2 )
    {

        return date1.getTime() - date2.getTime();

    }

    private void getPaymentDetails(String fromString, String toString) {

    }
}
