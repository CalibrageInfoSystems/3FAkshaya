package in.calibrage.akshaya.views.actvity;

import android.app.DatePickerDialog;
import android.app.ProgressDialog;
import android.content.Intent;
import android.content.SharedPreferences;
//import android.support.design.widget.TextInputLayout;
//import android.support.v7.app.AppCompatActivity;
import android.os.Bundle;
//import android.support.v7.widget.LinearLayoutManager;
//import android.support.v7.widget.RecyclerView;
import android.text.InputType;
import android.text.TextUtils;
import android.util.Log;
import android.view.View;
import android.view.Window;
import android.view.WindowManager;
import android.widget.AdapterView;
import android.widget.ArrayAdapter;
import android.widget.Button;
import android.widget.DatePicker;
import android.widget.EditText;
import android.widget.ImageView;
import android.widget.LinearLayout;
import android.widget.RelativeLayout;
import android.widget.Spinner;
import android.widget.TextView;
import android.widget.Toast;

import androidx.recyclerview.widget.LinearLayoutManager;
import androidx.recyclerview.widget.RecyclerView;

import com.google.android.material.textfield.TextInputLayout;
import com.google.gson.Gson;
import com.google.gson.JsonObject;

import java.io.IOException;
import java.text.DecimalFormat;
import java.text.ParseException;
import java.text.SimpleDateFormat;
import java.util.Calendar;
import java.util.Date;
import java.util.Locale;

import dmax.dialog.SpotsDialog;
import in.calibrage.akshaya.R;
import in.calibrage.akshaya.common.BaseActivity;
import in.calibrage.akshaya.common.CommonUtil;
import in.calibrage.akshaya.models.PaymentRequestModel;
import in.calibrage.akshaya.models.PaymentResponseModel;
import in.calibrage.akshaya.service.ApiService;
import in.calibrage.akshaya.service.ServiceFactory;
import in.calibrage.akshaya.views.Adapter.PaymentAdapter;
import retrofit2.adapter.rxjava.HttpException;
import rx.Subscriber;
import rx.Subscription;
import rx.android.schedulers.AndroidSchedulers;
import rx.schedulers.Schedulers;

public class PaymentHistoryActivity extends BaseActivity implements AdapterView.OnItemSelectedListener {

    private static final String TAG = PaymentHistoryActivity.class.getSimpleName();
    private EditText fromText, toText;
    private String fromString, toString;
    private DatePickerDialog picker;
    private LinearLayout totalLinear,custom_linear;
    private PaymentAdapter pay_adapter;
    private Subscription mSubscription;
    private Button submit;
    private String currentDate,last_30day;
    private String reformattedStrFrom, reformattedStrTo;
    private TextView noRecords, Total_records, ffb, gr, totalAdjusted, totalBalance;
    private Calendar calendar;
    private TextInputLayout from_txt, to_txt;
    private RecyclerView Payment_recycle;
    private SpotsDialog mdilogue;

    private ImageView backImg, home_btn;
    String Farmer_code;
    String[] selection = {"Last 30 Days", "Current Financial Year", "Select Time Period"};
    String financiyalYearFrom = "";
    String financiyalYearTo = "";
    Spinner spin;
    DecimalFormat  df;
    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_payment_history);
        init();
        setviews();

    }

    private void init() {
        noRecords = (TextView) findViewById(R.id.text);
        ffb = (TextView) findViewById(R.id.ffb_total);
        spin = (Spinner) findViewById(R.id.spinner);
        totalBalance = (TextView) findViewById(R.id.totalBalance);
        Total_records = (TextView) findViewById(R.id.total_records);
        mdilogue = (SpotsDialog) new SpotsDialog.Builder()
                .setContext(this)
                .setTheme(R.style.Custom)
                .build();
        totalLinear = (LinearLayout) findViewById(R.id.linear1);
        custom_linear =(LinearLayout) findViewById(R.id.linear2);

        backImg = (ImageView) findViewById(R.id.back);

        home_btn = (ImageView) findViewById(R.id.home_btn);
        from_txt = (TextInputLayout) findViewById(R.id.txt_from_date);
        to_txt = (TextInputLayout) findViewById(R.id.txt_to_date);
        submit = (Button) findViewById(R.id.btn__sub);

        Payment_recycle = (RecyclerView) findViewById(R.id.payment_recycler_view);
        fromText = (EditText) findViewById(R.id.from_date);
        fromText.setInputType(InputType.TYPE_NULL);
        toText = (EditText) findViewById(R.id.to_date);
        final TextInputLayout txt_to_date = findViewById(R.id.txt_to_date);
        final TextInputLayout txt_from_date = findViewById(R.id.txt_from_date);


        fromText.setHint(CommonUtil.getMultiColourString(getString(R.string.from_date)));
        toText.setHint(CommonUtil.getMultiColourString(getString(R.string.to_date)));
        //  txt_to_date.setHint(CommonUtil.getMultiColourString(getString(R.string.to_date)));

        SharedPreferences pref = getSharedPreferences("FARMER", MODE_PRIVATE);
        Farmer_code = pref.getString("farmerid", "").trim();
    }

    private void setviews() {
        backImg.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View view) {
                finish();
            }
        });

        home_btn.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View view) {
                Intent intent = new Intent(PaymentHistoryActivity.this, HomeActivity.class);
                intent.setFlags(Intent.FLAG_ACTIVITY_NEW_TASK | Intent.FLAG_ACTIVITY_CLEAR_TOP);
                startActivity(intent);
                finish();
            }
        });

        spin.setOnItemSelectedListener(this);
        ArrayAdapter aa = new ArrayAdapter(this, R.layout.spinner_item, selection);
        aa.setDropDownViewResource(R.layout.simple_spinner_dropdown_item);

        spin.setAdapter(aa);

        Payment_recycle.setHasFixedSize(true);
        LinearLayoutManager layoutManager = new LinearLayoutManager(this);
        Payment_recycle.setLayoutManager(layoutManager);


        fromText.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View view) {
                calendar = Calendar.getInstance();
                int day = calendar.get(Calendar.DAY_OF_MONTH);
                int month = calendar.get(Calendar.MONTH);
                int year = calendar.get(Calendar.YEAR);
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


        toText.setInputType(InputType.TYPE_NULL);
        toText.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View view) {
                Calendar calendar1 = Calendar.getInstance();
                int day = calendar1.get(Calendar.DAY_OF_MONTH);
                int month = calendar1.get(Calendar.MONTH);
                int year = calendar1.get(Calendar.YEAR);
                // date picker dialog
                DatePickerDialog picker1 = new DatePickerDialog(PaymentHistoryActivity.this,
                        new DatePickerDialog.OnDateSetListener() {
                            @Override
                            public void onDateSet(DatePicker view, int year, int monthOfYear, int dayOfMonth) {
                                toText.setText(dayOfMonth + "/" + (monthOfYear + 1) + "/" + year);
                                String selected_date = (dayOfMonth + "/" + (monthOfYear + 1) + "/" + year);
                                int month = (monthOfYear + 1);
                                Log.e("selected_date===", selected_date);
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

                if (fromString.equalsIgnoreCase("") || toString.equalsIgnoreCase("")) {
                    showDialog(PaymentHistoryActivity.this, getResources().getString(R.string.enter_Date));
                    pay_adapter.clearAllDataa();
                    totalLinear.setVisibility(View.GONE);
                    Payment_recycle.setVisibility(View.GONE);
                } else {
                    SimpleDateFormat formatter = new SimpleDateFormat("dd/MM/yyyy");
                    Date date1 = null;
                    try {
                        date1 = formatter.parse(fromString);

                        Date date2 = formatter.parse(toString);
                        if (date2.compareTo(date1) < 0) {

                            showDialog(PaymentHistoryActivity.this, getResources().getString(R.string.datevalidation));
                            pay_adapter.clearAllDataa();
                            totalLinear.setVisibility(View.GONE); //
                            Payment_recycle.setVisibility(View.GONE);
                            //Toast.makeText(getApplicationContext(), "Please Enter From Date is less than To Date", Toast.LENGTH_LONG).show();
                        } else {

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
                            if (isOnline())
                                getPaymentDetails(fromString, toString);
                            else {
                                showDialog(PaymentHistoryActivity.this, getResources().getString(R.string.Internet));
                                //Toast.makeText(LoginActivity.this, "Please Check Internet Connection ", Toast.LENGTH_SHORT).show();
                            }

                        }
                    } catch (ParseException e) {
                        e.printStackTrace();
                    }


                }


            }
        });
        pay_adapter = new PaymentAdapter(PaymentHistoryActivity.this);
        Payment_recycle.setAdapter(pay_adapter);
    }


    public static long compareTo(Date date1, Date date2) {
        return date1.getTime() - date2.getTime();

    }
//Get  payment History
    private void getPaymentDetails(String fromString, String toString) {
        SimpleDateFormat fromUser = new SimpleDateFormat("dd/MM/yyyy");
        SimpleDateFormat myFormat = new SimpleDateFormat("yyyy-MM-dd");
        try {

            reformattedStrFrom = myFormat.format(fromUser.parse(this.fromString));
            reformattedStrTo = myFormat.format(fromUser.parse(this.toString));

        } catch (ParseException e) {
            e.printStackTrace();
        }
        mdilogue.show();
        JsonObject object = paymenObject();
        ApiService service = ServiceFactory.createRetrofitService(this, ApiService.class);
        mSubscription = service.postpayment(object)
                .subscribeOn(Schedulers.newThread())
                .observeOn(AndroidSchedulers.mainThread())
                .subscribe(new Subscriber<PaymentResponseModel>() {
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
                        mdilogue.dismiss();
                        pay_adapter.clearAllDataa();
                        showDialog(PaymentHistoryActivity.this, getString(R.string.server_error));
                    }

                    @Override
                    public void onNext(PaymentResponseModel paymentResponseModel) {
                        mdilogue.dismiss();

                        Log.d(TAG, "onNext:collection " + paymentResponseModel);

                        if (paymentResponseModel.getResult().getPaymentResponce() != null) {
                            Payment_recycle.setVisibility(View.VISIBLE);
                            noRecords.setVisibility(View.GONE);
                           /* pay_adapter = new PaymentAdapter(PaymentHistoryActivity.this, paymentResponseModel.getResult().getPaymentResponce());
                            Payment_recycle.setAdapter(pay_adapter);*/
                            pay_adapter.updateData(paymentResponseModel.getResult().getPaymentResponce());
                            pay_adapter.notifyDataSetChanged();
                            totalLinear.setVisibility(View.VISIBLE);
                            //  unPaidCollectionsWeight.setText( String.valueOf(paymentResponseModel.getResult().getPaymentResponce().get(0).g())+""+"0 Kgs");

                            Total_records.setText(String.valueOf(paymentResponseModel.getAffectedRecords()));

                            double TotalQuanitity = paymentResponseModel.getResult().getTotalQuanitity();

                            DecimalFormat    df = new DecimalFormat("#,###,##0.000");
                            System.out.println(df.format(TotalQuanitity));
//
                          // Log.e("TotalQuanitity======",TotalQuanitity+"==="+df.format(TotalQuanitity +""));
                            ffb.setText((df.format(TotalQuanitity)));
                            if (paymentResponseModel.getResult().getTotalBalance() == null) {
                                totalBalance.setText("0");

                            } else {
                                totalBalance.setText(String.valueOf(paymentResponseModel.getResult().getTotalBalance()));
                            }

                        } else {
                            noRecords.setVisibility(View.VISIBLE);
                            totalLinear.setVisibility(View.GONE);
                            Payment_recycle.setVisibility(View.GONE);
                        }
                    }


                });
    }

    private JsonObject paymenObject() {
        PaymentRequestModel requestModel = new PaymentRequestModel();
        //TODO need to save in shared pref
        /*
         * remove fist 2 letters from former code and add v
         * */

        String text;
        text = Farmer_code.substring(1);
        text = text.substring(1);


        String finalstring = "V" + text;

        Log.i("VendorCode", finalstring);
        requestModel.setVendorCode(finalstring);
        requestModel.setToDate(reformattedStrTo);
        requestModel.setFromDate(reformattedStrFrom);

        return new Gson().toJsonTree(requestModel).getAsJsonObject();
    }

    @Override
    public void onBackPressed() {
        super.onBackPressed();
        this.finish();
    }

    @Override
    public void onItemSelected(AdapterView<?> adapterView, View view, int i, long l) {
        if (spin.getSelectedItem().toString().equals("Last 30 Days")) {

            Payment_recycle.setVisibility(View.VISIBLE);
            custom_linear.setVisibility(View.GONE);//
            if (isOnline())
                get30days();
            else {
                showDialog(PaymentHistoryActivity.this, getResources().getString(R.string.Internet));

            }
            //  get30days();
        } else {
            Payment_recycle.setVisibility(View.GONE);
            custom_linear.setVisibility(View.VISIBLE);

        }

        if (spin.getSelectedItem().toString().equals("Current Financial Year")) {
            custom_linear.setVisibility(View.GONE);
            Payment_recycle.setVisibility(View.VISIBLE); //
            if (isOnline())
                getfinacial_year();
            else {
                showDialog(PaymentHistoryActivity.this, getResources().getString(R.string.Internet));
                //Toast.makeText(LoginActivity.this, "Please Check Internet Connection ", Toast.LENGTH_SHORT).show();
            }

        } else {
            Payment_recycle.setVisibility(View.GONE);
            custom_linear.setVisibility(View.VISIBLE);
        }
        if (spin.getSelectedItem().toString().equals("Select Time Period")) {
            // Toast.makeText(getApplicationContext(),"hiddd" , Toast.LENGTH_LONG).show();
            Payment_recycle.setVisibility(View.GONE);
            custom_linear.setVisibility(View.VISIBLE);
            totalLinear.setVisibility(View.GONE);//
        }
    }
    //Payments  API Requests
    private void getfinacial_year() {
        int CurrentYear = Calendar.getInstance().get(Calendar.YEAR);
        int CurrentMonth = (Calendar.getInstance().get(Calendar.MONTH) + 1);

        if (CurrentMonth < 4) {
            financiyalYearFrom = (CurrentYear - 1) + "-04-01";
            financiyalYearTo = (CurrentYear) + "-03-31";
            Log.i(" from_year ", financiyalYearFrom);
            Log.i("tp_year ", financiyalYearTo);
        } else {
            financiyalYearFrom = (CurrentYear) + "-04-01";
            financiyalYearTo = (CurrentYear + 1) + "-03-31";
            ;
            Log.i(" financiyalYearFrom2 ", financiyalYearFrom);
            Log.i("financiyalYearTo2 ", financiyalYearTo);
        }
        mdilogue.show();
        JsonObject object = paymentyearObject();
        ApiService service = ServiceFactory.createRetrofitService(this, ApiService.class);
        mSubscription = service.postpayment(object)
                .subscribeOn(Schedulers.newThread())
                .observeOn(AndroidSchedulers.mainThread())
                .subscribe(new Subscriber<PaymentResponseModel>() {
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
                        mdilogue.dismiss();
                        pay_adapter.clearAllDataa();
                        showDialog(PaymentHistoryActivity.this, getString(R.string.server_error));
                    }

                    @Override
                    public void onNext(PaymentResponseModel paymentResponseModel) {
                        mdilogue.dismiss();

                        Log.d(TAG, "onNext:collection " + paymentResponseModel);

                        if (paymentResponseModel.getResult().getPaymentResponce() != null) {
                            Payment_recycle.setVisibility(View.VISIBLE); //
                            noRecords.setVisibility(View.GONE);
                           /* pay_adapter = new PaymentAdapter(PaymentHistoryActivity.this, paymentResponseModel.getResult().getPaymentResponce());
                            Payment_recycle.setAdapter(pay_adapter);*/
                            pay_adapter.updateData(paymentResponseModel.getResult().getPaymentResponce());
                            totalLinear.setVisibility(View.VISIBLE);
                            //  unPaidCollectionsWeight.setText( String.valueOf(paymentResponseModel.getResult().getPaymentResponce().get(0).g())+""+"0 Kgs");

                            Total_records.setText(String.valueOf(paymentResponseModel.getAffectedRecords()));

                            double TotalQuanitity = paymentResponseModel.getResult().getTotalQuanitity();

                            DecimalFormat    df = new DecimalFormat("#,###,##0.000");
                            System.out.println(df.format(TotalQuanitity));
//
                            // Log.e("TotalQuanitity======",TotalQuanitity+"==="+df.format(TotalQuanitity +""));
                            ffb.setText((df.format(TotalQuanitity)));
                            if (paymentResponseModel.getResult().getTotalBalance() == null) {
                                totalBalance.setText("0.00");

                            } else {
                                totalBalance.setText(String.valueOf(paymentResponseModel.getResult().getTotalBalance()));
                            }

                        } else {
                            noRecords.setVisibility(View.VISIBLE);
                            totalLinear.setVisibility(View.GONE);
                            Payment_recycle.setVisibility(View.GONE); //
                        }
                    }


                });
    }

    private JsonObject paymentyearObject() {
        PaymentRequestModel requestModel = new PaymentRequestModel();
        //TODO need to save in shared pref
        /*
         * remove fist 2 letters from former code and add v
         * */

        String text;
        text = Farmer_code.substring(1);
        text = text.substring(1);


        String finalstring = "V" + text;

        Log.i("VendorCode", finalstring);
        requestModel.setVendorCode(finalstring);
        requestModel.setToDate(financiyalYearTo);
        requestModel.setFromDate(financiyalYearFrom);

        return new Gson().toJsonTree(requestModel).getAsJsonObject();
    }

    private void get30days() {
        currentDate = new SimpleDateFormat("yyyy-MM-dd", Locale.getDefault()).format(new Date());
        Log.i("LOG_RESPONSE date ", currentDate);

        Calendar calendar = Calendar.getInstance();
        calendar.add(Calendar.MONTH, -1);
        Date date = calendar.getTime();
        SimpleDateFormat format = new SimpleDateFormat("yyyy-MM-dd");
        last_30day = format.format(date);
        Log.i("last==30thdate ", last_30day);

        mdilogue.show();
        JsonObject object = paymen30Object();
        ApiService service = ServiceFactory.createRetrofitService(this, ApiService.class);
        mSubscription = service.postpayment(object)
                .subscribeOn(Schedulers.newThread())
                .observeOn(AndroidSchedulers.mainThread())
                .subscribe(new Subscriber<PaymentResponseModel>() {
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
                        mdilogue.dismiss();
                        pay_adapter.clearAllDataa();
                        showDialog(PaymentHistoryActivity.this, getString(R.string.server_error));
                    }

                    @Override
                    public void onNext(PaymentResponseModel paymentResponseModel) {
                        mdilogue.dismiss();

                        Log.d(TAG, "onNext:collection " + paymentResponseModel);

                        if (paymentResponseModel.getResult().getPaymentResponce() != null) {
                            Payment_recycle.setVisibility(View.VISIBLE); //
                            noRecords.setVisibility(View.GONE);
                           /* pay_adapter = new PaymentAdapter(PaymentHistoryActivity.this, paymentResponseModel.getResult().getPaymentResponce());
                            Payment_recycle.setAdapter(pay_adapter);*/
                            pay_adapter.updateData(paymentResponseModel.getResult().getPaymentResponce());
                            totalLinear.setVisibility(View.VISIBLE);
                            //  unPaidCollectionsWeight.setText( String.valueOf(paymentResponseModel.getResult().getPaymentResponce().get(0).g())+""+"0 Kgs");

                            Total_records.setText(String.valueOf(paymentResponseModel.getAffectedRecords()));

                            double TotalQuanitity = paymentResponseModel.getResult().getTotalQuanitity();

                            DecimalFormat    df = new DecimalFormat("#,###,##0.000");
                            System.out.println(df.format(TotalQuanitity));
//
                            // Log.e("TotalQuanitity======",TotalQuanitity+"==="+df.format(TotalQuanitity +""));
                            ffb.setText((df.format(TotalQuanitity)));
                            if (paymentResponseModel.getResult().getTotalBalance() == null) {
                                totalBalance.setText("0.00");

                            } else {
                                totalBalance.setText(String.valueOf(paymentResponseModel.getResult().getTotalBalance()));
                            }

                        } else {
                            noRecords.setVisibility(View.VISIBLE);
                            totalLinear.setVisibility(View.GONE);
                            Payment_recycle.setVisibility(View.GONE); //
                        }
                    }


                });

    }

    private JsonObject paymen30Object() {
        PaymentRequestModel requestModel = new PaymentRequestModel();
        //TODO need to save in shared pref
        /*
         * remove fist 2 letters from former code and add v
         * */

        String text;
        text = Farmer_code.substring(1);
        text = text.substring(1);


        String finalstring = "V" + text;

        Log.i("VendorCode", finalstring);
        requestModel.setVendorCode(finalstring);
        requestModel.setToDate(currentDate);
        requestModel.setFromDate(last_30day);

        return new Gson().toJsonTree(requestModel).getAsJsonObject();
    }

    @Override
    public void onNothingSelected(AdapterView<?> adapterView) {

    }
}
