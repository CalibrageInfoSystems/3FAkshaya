package in.calibrage.akshaya.views.actvity;

import android.app.DatePickerDialog;
import android.app.ProgressDialog;
import android.content.Intent;
import android.content.SharedPreferences;
import android.graphics.Color;
import android.support.design.widget.TextInputLayout;
import android.support.v7.app.AppCompatActivity;
import android.os.Bundle;
import android.support.v7.widget.LinearLayoutManager;
import android.support.v7.widget.RecyclerView;
import android.text.Html;
import android.text.InputType;
import android.text.Spannable;
import android.text.SpannableString;
import android.text.style.ForegroundColorSpan;
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

import com.google.gson.Gson;
import com.google.gson.JsonObject;

import java.io.IOException;
import java.text.ParseException;
import java.text.SimpleDateFormat;
import java.util.ArrayList;
import java.util.Calendar;
import java.util.Date;
import java.util.Locale;

import dmax.dialog.SpotsDialog;
import in.calibrage.akshaya.R;
import in.calibrage.akshaya.models.CollectionResponceModel;
import in.calibrage.akshaya.models.collectionRequestModel;

import in.calibrage.akshaya.service.ApiService;
import in.calibrage.akshaya.service.ServiceFactory;
import in.calibrage.akshaya.views.Adapter.Collection_Adapter;
import retrofit2.adapter.rxjava.HttpException;
import rx.Subscriber;
import rx.Subscription;
import rx.android.schedulers.AndroidSchedulers;
import rx.schedulers.Schedulers;

public class CollectionsActivity extends AppCompatActivity implements AdapterView.OnItemSelectedListener {
    DatePickerDialog picker;
    public static  String TAG="CollectionsActivity";
    EditText fromText,toText;
    String[] selection = { "Last 30 Days", "Current Financial Year", "Custom Time Period"};

    Spinner spin;
    Collection_Adapter collection_Adapter;
    private ArrayList<CollectionResponceModel.CollectioDatum> collection_list = new ArrayList<>();
    //  Button subBtn;
    private RecyclerView collecton_data;
    String currentDate;
    private ProgressDialog dialog;
    private RecyclerView.LayoutManager layoutManager;
    String farmerCode,Farmer_code;
    LinearLayout noRecords;
    String last_30day;
    TextView collectionsWeight,collectionsCount,paidCollectionsWeight,unPaidCollectionsWeight;
    RelativeLayout relativeLayoutCount;
    private Subscription mSubscription;
    String financiyalYearFrom="";
    String financiyalYearTo="";
    String fromString,toString;
    String reformattedStrFrom,reformattedStrTo;
    Button collection_Submit;
    TextView Seleteddatefrom,selectedsateto;
    private SpotsDialog mdilogue ;
    LinearLayout date_linear;
    ImageView backImg,home_btn;
    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        requestWindowFeature(Window.FEATURE_NO_TITLE);
        getWindow().setFlags(WindowManager.LayoutParams.FLAG_FULLSCREEN,
                WindowManager.LayoutParams.FLAG_FULLSCREEN);
        setContentView(R.layout.activity_collections);
        init();
        setViews();
    }

    private void init() {
        noRecords = (LinearLayout) findViewById(R.id.text);

        collectionsWeight = (TextView) findViewById(R.id.collectionsWeight);
        collectionsCount = (TextView) findViewById(R.id.collectionsCount);
        paidCollectionsWeight = (TextView) findViewById(R.id.paidCollectionsWeight);
        unPaidCollectionsWeight = (TextView) findViewById(R.id.unPaidCollectionsWeight);

        date_linear = (LinearLayout) findViewById(R.id.selecte_label);
        backImg = (ImageView) findViewById(R.id.back);
        home_btn = (ImageView) findViewById(R.id.home_btn);
        mdilogue = (SpotsDialog) new SpotsDialog.Builder()
                .setContext(this)
                .setTheme(R.style.Custom)
                .build();


        collection_Submit = (Button) findViewById(R.id.buttonSubmit);
        fromText = (EditText) findViewById(R.id.from_date);


        relativeLayoutCount = (RelativeLayout) findViewById(R.id.top_linear);
        String from_date = "<font color='#000000'>From Date </font>" + "<font color='#FF0000'>*</font>" + "<font color='#000000'></font>";
        //   fromText.setText(Html.fromHtml(from_date));
        toText = (EditText) findViewById(R.id.to_date);
        String to_date = "<font color='#000000'>To Date </font>" + "<font color='#FF0000'>*</font>" + "<font color='#000000'></font>";
        //  toText.setText(Html.fromHtml(to_date));
        collecton_data = (RecyclerView) findViewById(R.id.collection_recycler_view);
        spin = (Spinner) findViewById(R.id.spinner);
        SharedPreferences pref = getSharedPreferences("FARMER", MODE_PRIVATE);
        Farmer_code = pref.getString("farmerid", "");       // Saving string data of your editext

      //  timePeroidLinear=(RelativeLayout) findViewById(R.id.new_relative);
    }
    private void setViews() {
        backImg.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View view) {

                finish();

            }
        });
        home_btn.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View view) {
                Intent intent =new Intent(getApplicationContext(),HomeActivity.class);
                startActivity(intent);
            }
        });

        fromText.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View view) {
                final Calendar cldr = Calendar.getInstance();
                int day = cldr.get(Calendar.DAY_OF_MONTH);
                int month = cldr.get(Calendar.MONTH);
                int year = cldr.get(Calendar.YEAR);
                // date picker dialog
                picker = new DatePickerDialog(CollectionsActivity.this,
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
                final Calendar cldr = Calendar.getInstance();
                int day = cldr.get(Calendar.DAY_OF_MONTH);
                int month = cldr.get(Calendar.MONTH);
                int year = cldr.get(Calendar.YEAR);
                // date picker dialog
                picker = new DatePickerDialog(CollectionsActivity.this,
                        new DatePickerDialog.OnDateSetListener() {
                            @Override
                            public void onDateSet(DatePicker view, int year, int monthOfYear, int dayOfMonth) {
                                toText.setText(dayOfMonth + "/" + (monthOfYear + 1) + "/" + year);
                            }
                        }, year, month, day);
                picker.show();
                picker.getDatePicker().setMaxDate(System.currentTimeMillis());
            }
        });
        collecton_data.setHasFixedSize(true);
        layoutManager = new LinearLayoutManager(this);
        collecton_data.setLayoutManager(layoutManager);
        spin.setOnItemSelectedListener(this);

        ArrayAdapter aa = new ArrayAdapter(this,R.layout.spinner_item,selection);
        aa.setDropDownViewResource(R.layout.simple_spinner_dropdown_item);

        spin.setAdapter(aa);

        currentDate = new SimpleDateFormat("yyyy-MM-dd", Locale.getDefault()).format(new Date());
        Log.i("LOG_RESPONSE date ", currentDate);

        Calendar calendar = Calendar.getInstance();
        calendar.add(Calendar.MONTH, -1);
        Date date = calendar.getTime();
        SimpleDateFormat format = new SimpleDateFormat("yyyy-MM-dd");
        last_30day = format.format(date);
        Log.i("last==30thdate ", last_30day);


        int CurrentYear = Calendar.getInstance().get(Calendar.YEAR);
        int CurrentMonth = (Calendar.getInstance().get(Calendar.MONTH)+1);

        if(CurrentMonth<4)
        {
            financiyalYearFrom=(CurrentYear-1)+"-04-01";
            financiyalYearTo=(CurrentYear)+"-03-31";
            Log.i(" from_year ", financiyalYearFrom);
            Log.i("tp_year ", financiyalYearTo);
        }
        else
        {
            financiyalYearFrom=(CurrentYear)+"-04-01";
            financiyalYearTo=(CurrentYear+1)+"-03-31";;
            Log.i(" financiyalYearFrom2 ", financiyalYearFrom);
            Log.i("financiyalYearTo2 ", financiyalYearTo);
        }

    }





//        SharedPreferences pref = getSharedPreferences("FARMER", MODE_PRIVATE);
//        Farmer_code=pref.getString("farmerid", "");       // Saving string data of your editext



    @Override
    public void onItemSelected(AdapterView<?> adapterView, View view, int i, long l) {

        fromText.getText().clear();
        toText.getText().clear();
        if (spin.getSelectedItem().toString().equals("Last 30 Days")) {

            collecton_data.setVisibility(View.VISIBLE); //
            get30days();
        } else {
            collecton_data.setVisibility(View.GONE);

        }

        if (spin.getSelectedItem().toString().equals("Current Financial Year")) {

            collecton_data.setVisibility(View.VISIBLE); //
            getfinacialyear();
        } else {
            collecton_data.setVisibility(View.GONE);

        }
        {
            //  String Month = MonthArray[index];
            if (spin.getSelectedItem().toString().equals("Custom Time Period")) {
                //   adapter.notifyDataSetChanged();
                collecton_data.setVisibility(View.GONE); //
                noRecords.setVisibility(View.GONE);

                collection_Submit.setOnClickListener(new View.OnClickListener() {
                    @Override
                    public void onClick(View view) {
                        fromString = fromText.getText().toString().trim();
                        toString = toText.getText().toString().trim();
                        SimpleDateFormat fromUser = new SimpleDateFormat("dd/MM/yyyy");
                        SimpleDateFormat myFormat = new SimpleDateFormat("yyyy-MM-dd");

                        try {

                            reformattedStrFrom = myFormat.format(fromUser.parse(fromString));
                            reformattedStrTo = myFormat.format(fromUser.parse(toString));
                            Log.d("collection", "RESPONSE reformattedStr======" + reformattedStrFrom);
                        } catch (ParseException e) {
                            e.printStackTrace();
                        }

                        if(fromString.equalsIgnoreCase("")||toString.equalsIgnoreCase(""))
                        {
                            Toast.makeText(CollectionsActivity.this, "Please Enter From Date and To Date", Toast.LENGTH_SHORT).show();
                            date_linear.setVisibility(View.VISIBLE); //
                            collecton_data.setVisibility(View.GONE);
                        }
                        else
                        {


                            SimpleDateFormat formatter = new SimpleDateFormat("dd/MM/yyyy");
                            Date date1 = null;
                            try {
                                date1 = formatter.parse(fromString);
                                Date date2 = formatter.parse(toString);
                                if (date2.compareTo(date1)<0)
                                {
                                    Toast.makeText(getApplicationContext(),"Please Enter From Date is less than To Date",Toast.LENGTH_LONG).show();
                                }else{
                                    collecton_data.invalidate();
                                    //       recyclerView.setVisibility(View.VISIBLE);
                                    getCustomCollections(fromString,toString);


                                }
                            } catch (ParseException e) {
                                e.printStackTrace();
                            }


                        }



                    }

                });

            }
            else{
                collecton_data.setVisibility(View.GONE);

            }
        }
        if(spin.getSelectedItem().toString().equals("Custom Time Period")){
            // Toast.makeText(getApplicationContext(),"hiddd" , Toast.LENGTH_LONG).show();
            date_linear.setVisibility(View.VISIBLE); //
            relativeLayoutCount.setVisibility(View.GONE);
            //  subBtn.setVisibility(View.VISIBLE);

//do something
        }else {
            date_linear.setVisibility(View.GONE);
            relativeLayoutCount.setVisibility(View.GONE);
            //   subBtn.setVisibility(View.VISIBLE);
        }

    }

    private void getCustomCollections(String fromString, String toString) {
        collection_list.clear();
        mdilogue.show();
        JsonObject object = collectionObject3();
        ApiService service = ServiceFactory.createRetrofitService(this, ApiService.class);
        mSubscription = service.postcollection(object)
                .subscribeOn(Schedulers.newThread())
                .observeOn(AndroidSchedulers.mainThread())
                .subscribe(new Subscriber<CollectionResponceModel>() {
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
                    public void onNext(CollectionResponceModel collectionResponcemodel) {
                        mdilogue.cancel();
                        Log.d(TAG, "onNext:collection " + collectionResponcemodel);

                        if(collectionResponcemodel.getResult()!= null)

                        {
                            Log.e("nodada====","nodata===custom");
                            collecton_data.setVisibility(View.VISIBLE);
                            noRecords.setVisibility(View.GONE);
                            collection_Adapter = new Collection_Adapter(CollectionsActivity.this, collectionResponcemodel.getResult().getCollectioData());
                            collecton_data.setAdapter(collection_Adapter);
                            relativeLayoutCount.setVisibility(View.VISIBLE);
                            // collectionsWeight,collectionsCount,paidCollectionsWeight,unPaidCollectionsWeight
                            unPaidCollectionsWeight.setText( String.valueOf(collectionResponcemodel.getResult().getCollectionCount().get(0).getUnPaidCollectionsWeight())+""+"0 Kgs");
                            collectionsWeight.setText( String.valueOf(collectionResponcemodel.getResult().getCollectionCount().get(0).getCollectionsWeight())+""+"0 Kgs");
                            collectionsCount.setText( String.valueOf(collectionResponcemodel.getResult().getCollectionCount().get(0).getCollectionsCount()));
                            paidCollectionsWeight.setText( String.valueOf(collectionResponcemodel.getResult().getCollectionCount().get(0).getPaidCollectionsWeight())+""+"0 Kgs");
                        }
                        else{
                            Log.e("nodada====","nodata===custom2");
                            noRecords.setVisibility(View.VISIBLE);
                            relativeLayoutCount.setVisibility(View.GONE);
                        }





                    }
                });
    }

    private JsonObject collectionObject3() {
        collectionRequestModel requestModel = new collectionRequestModel();
        requestModel.setFarmerCode(Farmer_code);
        requestModel.setToDate(reformattedStrTo);
        requestModel.setFromDate(reformattedStrFrom);

        return new Gson().toJsonTree(requestModel).getAsJsonObject();
    }

    private void getfinacialyear() {
        collection_list.clear();
        mdilogue.show();
        JsonObject object = collectionObject2();
        ApiService service = ServiceFactory.createRetrofitService(this, ApiService.class);
        mSubscription = service.postcollection(object)
                .subscribeOn(Schedulers.newThread())
                .observeOn(AndroidSchedulers.mainThread())
                .subscribe(new Subscriber<CollectionResponceModel>() {
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
                    }

                    @Override
                    public void onNext(CollectionResponceModel collectionResponcemodel) {
                        mdilogue.dismiss();
                        Log.d(TAG, "onNext:collection " + collectionResponcemodel);

                        if(collectionResponcemodel.getResult()!= null)
                        {

                            Log.e("nodada====","nodata===1year");
                            collecton_data.setVisibility(View.VISIBLE);
                            noRecords.setVisibility(View.GONE);
                            collection_Adapter = new Collection_Adapter(CollectionsActivity.this, collectionResponcemodel.getResult().getCollectioData());
                            collecton_data.setAdapter(collection_Adapter);
                            relativeLayoutCount.setVisibility(View.VISIBLE);

                            // collectionsWeight,collectionsCount,paidCollectionsWeight,unPaidCollectionsWeight

                            unPaidCollectionsWeight.setText( String.valueOf(collectionResponcemodel.getResult().getCollectionCount().get(0).getUnPaidCollectionsWeight())+""+"0 Kgs");
                            collectionsWeight.setText( String.valueOf(collectionResponcemodel.getResult().getCollectionCount().get(0).getCollectionsWeight())+""+"0 Kgs");
                            collectionsCount.setText( String.valueOf(collectionResponcemodel.getResult().getCollectionCount().get(0).getCollectionsCount()));
                            paidCollectionsWeight.setText( String.valueOf(collectionResponcemodel.getResult().getCollectionCount().get(0).getPaidCollectionsWeight())+""+"0 Kgs");



                        }  else{
                            Log.e("nodada====","nodata===1year2");
                            noRecords.setVisibility(View.VISIBLE);
                            relativeLayoutCount.setVisibility(View.GONE);
                        }


                    }
                });


    }

    private JsonObject collectionObject2(){
        collectionRequestModel requestModel = new collectionRequestModel();
        requestModel.setFarmerCode(Farmer_code);
        requestModel.setToDate(financiyalYearTo);
        requestModel.setFromDate(financiyalYearFrom);

        return new Gson().toJsonTree(requestModel).getAsJsonObject();
    }

    private void get30days() {
        collection_list.clear();
        mdilogue.show();
        JsonObject object = collectionObject();
        ApiService service = ServiceFactory.createRetrofitService(this, ApiService.class);
        mSubscription = service.postcollection(object)
                .subscribeOn(Schedulers.newThread())
                .observeOn(AndroidSchedulers.mainThread())
                .subscribe(new Subscriber<CollectionResponceModel>() {
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
                    public void onNext(CollectionResponceModel collectionResponcemodel) {
                        mdilogue.dismiss();
                        Log.d(TAG, "onNext:collection " + collectionResponcemodel);

                        if(collectionResponcemodel.getResult()!= null)
                        {


                            noRecords.setVisibility(View.GONE);
                            collection_Adapter = new Collection_Adapter(CollectionsActivity.this, collectionResponcemodel.getResult().getCollectioData());
                            collecton_data.setAdapter(collection_Adapter);
                            relativeLayoutCount.setVisibility(View.VISIBLE);

                            unPaidCollectionsWeight.setText( String.valueOf(collectionResponcemodel.getResult().getCollectionCount().get(0).getUnPaidCollectionsWeight())+""+"0 Kgs");
                            collectionsWeight.setText( String.valueOf(collectionResponcemodel.getResult().getCollectionCount().get(0).getCollectionsWeight())+""+"0 Kgs");
                            collectionsCount.setText( String.valueOf(collectionResponcemodel.getResult().getCollectionCount().get(0).getCollectionsCount()));
                            paidCollectionsWeight.setText( String.valueOf(collectionResponcemodel.getResult().getCollectionCount().get(0).getPaidCollectionsWeight())+""+"0 Kgs");
                        }
                        else{
                            noRecords.setVisibility(View.VISIBLE);
                            relativeLayoutCount.setVisibility(View.GONE);

                        }

                    }
                });


    }


    private JsonObject collectionObject() {
        collectionRequestModel requestModel = new collectionRequestModel();
        requestModel.setFarmerCode(Farmer_code);
        requestModel.setToDate(currentDate);
        requestModel.setFromDate(last_30day);

        return new Gson().toJsonTree(requestModel).getAsJsonObject();
    }
    @Override
    public void onNothingSelected(AdapterView<?> adapterView) {

    }

    @Override
    public void onBackPressed() {
        super.onBackPressed();
       // this.finish();
    }
}
