package in.calibrage.akshaya.views.actvity;

import android.app.AlertDialog;
import android.app.DatePickerDialog;
import android.app.ProgressDialog;
import android.content.Intent;
import android.content.SharedPreferences;
import android.graphics.drawable.Animatable;
import android.os.Handler;
import android.support.v7.app.AppCompatActivity;
import android.os.Bundle;
import android.support.v7.widget.RecyclerView;
import android.util.Log;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.AdapterView;
import android.widget.ArrayAdapter;
import android.widget.Button;
import android.widget.CheckBox;
import android.widget.CompoundButton;
import android.widget.DatePicker;
import android.widget.EditText;
import android.widget.ImageView;
import android.widget.Spinner;
import android.widget.TextView;
import android.widget.Toast;

import com.google.gson.Gson;
import com.google.gson.JsonObject;

import java.io.IOException;
import java.text.ParseException;
import java.text.SimpleDateFormat;
import java.util.ArrayList;
import java.util.Calendar;
import java.util.Date;
import java.util.List;

import dmax.dialog.SpotsDialog;
import in.calibrage.akshaya.R;
import in.calibrage.akshaya.common.BaseActivity;
import in.calibrage.akshaya.common.MultiSelectionSpinner;
import in.calibrage.akshaya.models.AddLabourRequestHeader;
import in.calibrage.akshaya.models.LabourDuration;
import in.calibrage.akshaya.models.Labourservicetype;
import in.calibrage.akshaya.models.LobourResponse;
import in.calibrage.akshaya.service.APIConstantURL;
import in.calibrage.akshaya.service.ApiService;
import in.calibrage.akshaya.service.ServiceFactory;
import retrofit2.adapter.rxjava.HttpException;
import rx.Subscriber;
import rx.Subscription;
import rx.android.schedulers.AndroidSchedulers;
import rx.schedulers.Schedulers;

public class LabourActivity extends BaseActivity implements MultiSelectionSpinner.OnMultipleItemsSelectedListener {
    Calendar myCalendar;
    EditText edittext;
    int day, year, month;
    String Farmer_code;
    Integer ids_list,durationId;
    List<String> list = new ArrayList<String>();

    List<String> service_name = new ArrayList<String>();
    List<Integer> labour_uID = new ArrayList<Integer>();
    List<String> freq_id = new ArrayList<String>();

    List<Integer> period_id = new ArrayList<Integer>();
    Spinner frequencySpinner, labourSpinner;
    private ProgressDialog dialog;
    public static String TAG = "LabourActivity";
    ArrayList<String> listdata = new ArrayList<String>();
    MultiSelectionSpinner multiSelectionSpinner;
    //  LabourTermsNCondtionsAdapter Tadapter;
    TextView terms;
    private Subscription mSubscription;
    TextView ok, getTerms, head_text;
    TextView Age, id_plot, area, landMark;
    RecyclerView terms_recycle;
    Button button_submit;
    private SpotsDialog mdilogue;
    CheckBox checkbox;
    String frequencyId, serviceTypeId, Seleted_date, farmated_date, isSuccess, register;
    String plot_id, plot_Age, location, farmerCode, plotMandal, plotState, plotDistrict, landmarkCode, reformattedDate, commentString;
    EditText commentsTxt;
    ImageView backImg, home_btn;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_labour);


        Bundle extras = getIntent().getExtras();
        if (extras != null) {
            plot_id = extras.getString("plotid");
            plot_Age = extras.getString("plotAge");
            location = extras.getString("plotVillage");
            farmerCode = extras.getString("farmerCode");
            landmarkCode = extras.getString("landMark");
            plotMandal = extras.getString("plotMandal");
            plotState = extras.getString("plotState");
            plotDistrict = extras.getString("plotDistrict");
        }
        intview();
        setViews();
        SharedPreferences pref = getSharedPreferences("FARMER", MODE_PRIVATE);
        Farmer_code = pref.getString("farmerid", "");       // Saving string data of your editext

        // GetCropMaintenanceHistoryDetailsByCode();


    }

    private void intview() {
        Age = findViewById(R.id.age_plot);
        id_plot = findViewById(R.id.plot);
        area = findViewById(R.id.palmArea);
        landMark = findViewById(R.id.landmark);
        commentsTxt = findViewById(R.id.commentTxt);
        home_btn = (ImageView) findViewById(R.id.home_btn);
        backImg = (ImageView) findViewById(R.id.back);
        multiSelectionSpinner = (MultiSelectionSpinner) findViewById(R.id.spinner_labour_type);
        terms = (TextView) findViewById(R.id.terms);
        labourSpinner = (Spinner) findViewById(R.id.labour_duration);
        edittext = (EditText) findViewById(R.id.date_display);
        checkbox = (CheckBox) findViewById(R.id.checkBox);
        button_submit = findViewById(R.id.buttonSubmit);
        mdilogue = (SpotsDialog) new SpotsDialog.Builder()
                .setContext(this)
                .setTheme(R.style.Custom)
                .build();
    }

    private void setViews() {

        backImg.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View view) {
                Intent intent = new Intent(getApplicationContext(), LabourRecommendationsActivity.class);
                startActivity(intent);
            }
        });

        home_btn.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View view) {
                Intent intent = new Intent(getApplicationContext(), HomeActivity.class);
                startActivity(intent);
            }
        });


        Age.setText(plot_Age);
        area.setText(location);
        id_plot.setText(plot_id);
        landMark.setText(landmarkCode);
        myCalendar = Calendar.getInstance();


        SimpleDateFormat dateFormat = new SimpleDateFormat("dd/MM/yyyy");
        edittext.setText(dateFormat.format(new Date()));

        SimpleDateFormat input = new SimpleDateFormat("dd/MM/yyyy");
        SimpleDateFormat output = new SimpleDateFormat("yyyy/MM/dd");
        try {
            Date oneWayTripDate = input.parse(dateFormat.format(new Date()));

            reformattedDate = output.format(oneWayTripDate);
            //  GetAll_tokens_closed();
            Log.e("===============", "======current===========" + reformattedDate);
        } catch (ParseException e) {
            e.printStackTrace();
        }

        edittext.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {

                final Calendar c = Calendar.getInstance();
                year = c.get(Calendar.YEAR);
                month = c.get(Calendar.MONTH);
                day = c.get(Calendar.DAY_OF_MONTH);


                DatePickerDialog datePickerDialog = new DatePickerDialog(LabourActivity.this,
                        new DatePickerDialog.OnDateSetListener() {

                            @Override
                            public void onDateSet(DatePicker view, int year,
                                                  int monthOfYear, int dayOfMonth) {
                                int month = monthOfYear + 1;
                                String formattedMonth = "" + month;
                                String formattedDayOfMonth = "" + dayOfMonth;

                                if (month < 10) {

                                    formattedMonth = "0" + month;
                                }
                                if (dayOfMonth < 10) {

                                    formattedDayOfMonth = "0" + dayOfMonth;
                                }
                                String dateVal = formattedDayOfMonth + "/" + formattedMonth + "/" + year;


                                edittext.setText(dateVal);


                                SimpleDateFormat input = new SimpleDateFormat("dd/MM/yyyy");
                                SimpleDateFormat output = new SimpleDateFormat("yyyy/MM/dd");
                                try {
                                    Date oneWayTripDate = input.parse(dateVal);

                                    reformattedDate = output.format(oneWayTripDate);
                                    //  GetAll_tokens_closed();
                                    Log.e("===============", "======sending_date===========" + reformattedDate);
                                } catch (ParseException e) {
                                    e.printStackTrace();
                                }

                            }
                        }, year, month, day);
                datePickerDialog.show();
                datePickerDialog.getDatePicker().setMinDate(System.currentTimeMillis());
            }

        });


        checkbox.setOnCheckedChangeListener(new CompoundButton.OnCheckedChangeListener() {
            @Override
            public void onCheckedChanged(CompoundButton buttonView, boolean isChecked) {
                if (isChecked) {
//                    if (validationspop()) {
//                        myDialog = new Dialog(LabourActivity.this);
//
//                        ShowPopup();
//                    }
                }

            }
        });

        button_submit.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View view) {

                AddLabourRequestHeader();

//
//                if (validations()) {
//
//
//
//                    AddLabourRequestHeader();
//                    //
//                }

            }


        });
        terms.setOnClickListener(new View.OnClickListener() {

            @Override
            public void onClick(View v) {
                // TODO Auto-generated method stub
//                if (validationspop()) {
//                    myDialog = new Dialog(LabourActivity.this);
//
//                    ShowPopup();
//                }
            }
        });
        Getlabour_duration();
        labourSpinner.setOnItemSelectedListener(new AdapterView.OnItemSelectedListener() {
            @Override
            public void onItemSelected(AdapterView<?> adapterView, View view, int i, long l) {
                String seleced_period = labourSpinner.getItemAtPosition(labourSpinner.getSelectedItemPosition()).toString();

                Log.e("seleced_period===", seleced_period);
              durationId=period_id.get(labourSpinner.getSelectedItemPosition());
                Log.e("duration======", String.valueOf(durationId));
//
            }

            @Override
            public void onNothingSelected(AdapterView<?> adapterView) {
                // DO Nothing here
            }
        });

        GetSpinnerLabourType();


        multiSelectionSpinner.setListener(this);
    }

    private void AddLabourRequestHeader() {
        mdilogue.show();
        JsonObject object = LabourReuestobject();
        ApiService service = ServiceFactory.createRetrofitService(this, ApiService.class);
        mSubscription = service.postLabour(object)
                .subscribeOn(Schedulers.newThread())
                .observeOn(AndroidSchedulers.mainThread())
                .subscribe(new Subscriber<LobourResponse>() {
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
                    public void onNext(LobourResponse lobourResponse) {
                        if (lobourResponse.getIsSuccess()) {
                            new Handler().postDelayed(new Runnable() {
                                @Override
                                public void run() {
                                    /* Create an Intent that will start the Menu-Activity. */
                                    Toast.makeText(LabourActivity.this, "Success==", Toast.LENGTH_LONG).show();


                                    showSuccessDialog("mallem mahesh");
                                }
                            }, 300);
                        } else {
                            showDialog(LabourActivity.this, lobourResponse.getEndUserMessage());
                        }

                    }


                });
    }


    private void GetSpinnerLabourType() {
        ApiService service = ServiceFactory.createRetrofitService(this, ApiService.class);
        mSubscription = service.getLabourService(APIConstantURL.GetLabourServicetype)
                .subscribeOn(Schedulers.newThread())
                .observeOn(AndroidSchedulers.mainThread()).subscribe(new Subscriber<Labourservicetype>() {
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
                    public void onNext(Labourservicetype labourservicetype) {

                        if (labourservicetype.getListResult() != null) {

                            for (Labourservicetype.ListResult data : labourservicetype.getListResult()
                            ) {
                                service_name.add(data.getDesc());
                                labour_uID.add(data.getTypeCdId());
                            }
                            Log.d(TAG, "RESPONSE======" + service_name);

                            multiSelectionSpinner.setItems(service_name);

//

//                            ArrayAdapter aa = new ArrayAdapter(LabourActivity.this, R.layout.spinner_item, listdata);
//                            aa.setDropDownViewResource(R.layout.simple_spinner_dropdown_item);
//                            labourSpinner.setAdapter(aa);
                        } else {
                            Log.e("nodada====", "nodata===custom2");

                        }

                    }

                });
    }


    private void Getlabour_duration() {

        ApiService service = ServiceFactory.createRetrofitService(this, ApiService.class);
        mSubscription = service.getLabourduration(APIConstantURL.GetLabourDuration)
                .subscribeOn(Schedulers.newThread())
                .observeOn(AndroidSchedulers.mainThread()).subscribe(new Subscriber<LabourDuration>() {
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
                    public void onNext(LabourDuration labourDuration) {


                        if (labourDuration.getListResult() != null) {
                            listdata.add("Select");
                            for (LabourDuration.ListResult data : labourDuration.getListResult()
                            ) {
                                listdata.add(data.getDesc());
                                period_id.add(data.getTypeCdId());
                            }
                            Log.d(TAG, "RESPONSE======" + listdata);

//

                            ArrayAdapter aa = new ArrayAdapter(LabourActivity.this, R.layout.spinner_item, listdata);
                            aa.setDropDownViewResource(R.layout.simple_spinner_dropdown_item);
                            labourSpinner.setAdapter(aa);
                        } else {
                            Log.e("nodada====", "nodata===custom2");

                        }

                    }

                });
    }


    @Override
    public void selectedIndices(List<Integer> indices) {

        for (Integer values : indices) {
            Log.d(TAG, "---- analysis ---- > get selected labour ID :"+labour_uID.get(values));
            ids_list =labour_uID.get(values);

            Log.d(TAG, "---- analysis ---- > get selected labour ID :"+ids_list);
        }


    }
    @Override
    public void selectedStrings(List<String> strings) {

    }


    private JsonObject LabourReuestobject() {

        AddLabourRequestHeader requestModel = new AddLabourRequestHeader();
        requestModel.setFarmerCode(Farmer_code);
        requestModel.setPlotCode(plot_id);
        requestModel.setIsFarmerRequest(true);
        requestModel.setComments(commentsTxt.getText().toString());
        requestModel.setPreferredDate(reformattedDate);
        requestModel.setCreatedByUserId(null);
        requestModel.setDurationId(2);
        requestModel.setPlotVillage(location);
        requestModel.setPlotMandal(plotMandal);

        requestModel.setPlotDistrict(plotDistrict);

        requestModel.setServiceTypes("19,21");

        requestModel.setCreatedDate(reformattedDate);
        requestModel.setUpdatedByUserId(null);
        requestModel.setUpdatedDate(reformattedDate);
        requestModel.setAmount(1.1);

        return new Gson().toJsonTree(requestModel).getAsJsonObject();

    }
}