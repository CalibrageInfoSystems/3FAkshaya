package in.calibrage.akshaya.views.actvity;

import android.app.DatePickerDialog;
import android.content.Intent;
import android.os.Build;
import android.os.Bundle;
import android.support.design.widget.TabItem;
import android.support.design.widget.TabLayout;
import android.support.v4.app.Fragment;
import android.support.v4.app.FragmentManager;
import android.support.v4.app.FragmentPagerAdapter;
import android.support.v4.content.ContextCompat;
import android.support.v4.view.ViewPager;
import android.support.v7.app.AppCompatActivity;
import android.support.v7.widget.Toolbar;
import android.text.InputType;
import android.util.Log;
import android.view.View;
import android.widget.AdapterView;
import android.widget.ArrayAdapter;
import android.widget.Button;
import android.widget.DatePicker;
import android.widget.EditText;
import android.widget.ImageView;
import android.widget.LinearLayout;
import android.widget.Spinner;

import java.text.ParseException;
import java.text.SimpleDateFormat;
import java.util.Calendar;
import java.util.Date;
import java.util.Locale;

import in.calibrage.akshaya.R;
import in.calibrage.akshaya.common.BaseActivity;
import in.calibrage.akshaya.common.CommonUtil;
import in.calibrage.akshaya.common.Constants;
import in.calibrage.akshaya.common.IDataChnaged;
import in.calibrage.akshaya.localData.SharedPrefsData;
import in.calibrage.akshaya.views.fragments.PaymentFragment;
import in.calibrage.akshaya.views.fragments.TransFragment;

import static in.calibrage.akshaya.common.CommonUtil.updateResources;
import static in.calibrage.akshaya.views.actvity.PaymentHistoryActivity.compareTo;

public class MainActivity extends BaseActivity implements AdapterView.OnItemSelectedListener {

    Toolbar toolbar;
    TabLayout tabLayout;
    ViewPager viewPager;
    PageAdapter pageAdapter;
    TabItem tabChats;
    TabItem tabStatus;
    String financiyalYearFrom ;
    String financiyalYearTo ;
    String selected_item;
    private Button submit;
    private String fromString, toString;

    private String farmatted_fromdate, farmated_todate;
    String[] selection ;
    Spinner spin;
    PaymentFragment myFragment;
    private String currentDate,last_30day;
    LinearLayout custom_linear;
    private EditText fromText, toText;
    private DatePickerDialog picker;
    private Calendar calendar;
    SimpleDateFormat format = new SimpleDateFormat("yyyy-MM-dd");
    private ImageView backImg, home_btn;
    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        final int langID = SharedPrefsData.getInstance(this).getIntFromSharedPrefs("lang");
        if (langID == 2)
            updateResources(this, "te");
        else
            updateResources(this, "en-US");
        setContentView(R.layout.activity_main);

        String[]  selection2=  {
                getString(R.string.thirtyP_days), getString(R.string.currentfinicialP), getString(R.string.selectedP)};
        selection= selection2;

//        toolbar = findViewById(R.id.toolbar);
//        toolbar.setTitle(getResources().getString(R.string.app_name));
//        setSupportActionBar(toolbar);
        spin = (Spinner) findViewById(R.id.spinner);
        tabLayout = findViewById(R.id.tablayout);
        tabChats = findViewById(R.id.tabpayments);
        tabStatus = findViewById(R.id.tabtrans);
        custom_linear =(LinearLayout) findViewById(R.id.linear2);
        fromText = (EditText) findViewById(R.id.from_date);
        fromText.setInputType(InputType.TYPE_NULL);
        toText = (EditText) findViewById(R.id.to_date);
        submit = (Button) findViewById(R.id.btn__sub);
        backImg = (ImageView) findViewById(R.id.back);

        home_btn = (ImageView) findViewById(R.id.home_btn);
        backImg.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View view) {
                finish();
            }
        });

        home_btn.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View view) {
                Intent intent = new Intent(MainActivity.this, HomeActivity.class);
                intent.setFlags(Intent.FLAG_ACTIVITY_NEW_TASK | Intent.FLAG_ACTIVITY_CLEAR_TOP);
                startActivity(intent);
                finish();
            }
        });
        spin.setOnItemSelectedListener(this);
        ArrayAdapter aa = new ArrayAdapter(this, R.layout.spinner_item, selection);
        aa.setDropDownViewResource(R.layout.simple_spinner_dropdown_item);

        spin.setAdapter(aa);
        viewPager = findViewById(R.id.viewPager);

        pageAdapter = new PageAdapter(getSupportFragmentManager(), tabLayout.getTabCount());
        viewPager.setAdapter(pageAdapter);

        SharedPrefsData.getInstance(this).updateStringValue(this,"sitem","Last 30 Days");
        tabLayout.addOnTabSelectedListener(new TabLayout.OnTabSelectedListener() {
            @Override
            public void onTabSelected(TabLayout.Tab tab) {
                viewPager.setCurrentItem(tab.getPosition());

            }

            @Override
            public void onTabUnselected(TabLayout.Tab tab) {

            }

            @Override
            public void onTabReselected(TabLayout.Tab tab) {

            }
        });
        viewPager.addOnPageChangeListener(new TabLayout.TabLayoutOnPageChangeListener(tabLayout));
        currentDate = new SimpleDateFormat("yyyy-MM-dd", Locale.getDefault()).format(new Date());
        Log.i("LOG_RESPONSE date ", currentDate);

         calendar = Calendar.getInstance();
        calendar.add(Calendar.DAY_OF_MONTH, -30);
        Date date = calendar.getTime();
    //    SimpleDateFormat format = new SimpleDateFormat("yyyy-MM-dd");
        last_30day = format.format(date);
        Log.i("last==30thdate ", last_30day);
        int CurrentYear = Calendar.getInstance().get(Calendar.YEAR);
        int CurrentMonth = (Calendar.getInstance().get(Calendar.MONTH) + 1);

        if (CurrentMonth < 4) {
            financiyalYearFrom = (CurrentYear - 1) + "-04-01";
            financiyalYearTo = (CurrentYear) + "-03-31";

        } else {
            financiyalYearFrom = (CurrentYear) + "-04-01";
            financiyalYearTo = (CurrentYear + 1) + "-03-31";

        }
        fromText.setHint(CommonUtil.getMultiColourString(getString(R.string.from_date)));
        toText.setHint(CommonUtil.getMultiColourString(getString(R.string.to_date)));

        fromText.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View view) {
                calendar = Calendar.getInstance();
                int day = calendar.get(Calendar.DAY_OF_MONTH);
                int month = calendar.get(Calendar.MONTH);
                int year = calendar.get(Calendar.YEAR);
                picker = new DatePickerDialog(MainActivity.this,
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
                DatePickerDialog picker1 = new DatePickerDialog(MainActivity.this,
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
                    showDialog(MainActivity.this, getResources().getString(R.string.enter_Date));
//                    pay_adapter.clearAllDataa();
//                    totalLinear.setVisibility(View.GONE);
//                    Payment_recycle.setVisibility(View.GONE);
                    Intent intent = new Intent("KEY");
                    intent.putExtra("todate", "clear");
                    intent.putExtra("fromdate", fromString);
                    sendBroadcast(intent);
                } else {
                    SimpleDateFormat formatter = new SimpleDateFormat("dd/MM/yyyy");
                    Date date1 = null;
                    try {
                        date1 = formatter.parse(fromString);

                        Date date2 = formatter.parse(toString);
                        if (date2.compareTo(date1) < 0) {

                            showDialog(MainActivity.this, getResources().getString(R.string.datevalidation));
                            Intent intent = new Intent("KEY");
                            intent.putExtra("todate", "clear");
                            intent.putExtra("fromdate", fromString);
                            sendBroadcast(intent);
//                            makeText(getApplicationContext(), "Please Enter From Date is less than To Date", Toast.LENGTH_LONG).show();
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
                            farmatted_fromdate = format.format(date1);
                            farmated_todate= format.format(date2);
                            Log.e("dayCount===", String.valueOf(dayCount));
                            Intent intent = new Intent("KEY");
                            intent.putExtra("todate", farmated_todate);
                            intent.putExtra("fromdate", farmatted_fromdate);
                            sendBroadcast(intent);
                        }
                    } catch (ParseException e) {
                        e.printStackTrace();
                    }


                }


            }
        });
    }

    @Override
    public void onItemSelected(AdapterView<?> adapterView, View view, int i , long l) {
        selected_item = selection[i];
        fromText.getText().clear();
        toText.getText().clear();

        if(i == 0)
        {
            Intent intent = new Intent("KEY");
            intent.putExtra("todate", currentDate);
            intent.putExtra("fromdate", last_30day);
            sendBroadcast(intent);
        }else if(i== 1)
        {

            Intent intent = new Intent("KEY");
            intent.putExtra("todate", financiyalYearTo);
            intent.putExtra("fromdate", financiyalYearFrom);
            sendBroadcast(intent);
        }else if(i == 2)
        {
            Intent intent = new Intent("KEY");
            intent.putExtra("todate", "clear");
            intent.putExtra("fromdate", financiyalYearFrom);
            sendBroadcast(intent);
        }

        if (spin.getSelectedItemPosition()== 2){


            custom_linear.setVisibility(View.VISIBLE);
        }
        else {
            custom_linear.setVisibility(View.GONE);
        }
    }
    @Override
    public void onNothingSelected(AdapterView<?> adapterView) {

    }




    private class PageAdapter extends FragmentPagerAdapter {
        String spinner_item;
        public String getSpinner_item() {
            return spinner_item;
        }

        public void setSpinner_item(String spinner_item) {
            this.spinner_item = spinner_item;
        }


        private int numOfTabs;

        PageAdapter(FragmentManager fm, int numOfTabs) {
            super(fm);
            this.numOfTabs = numOfTabs;
        }

        @Override
        public Fragment getItem(int position) {

         //  String selectedText= SharedPrefsData.getInstance(getApplicationContext()).getStringFromSharedPrefs("sitem");
            switch (position) {
                case 0:

                    return new PaymentFragment();


                case 1:
                    return new TransFragment();

                default:
                    return null;
            }
        }

        @Override
        public int getCount() {
            return numOfTabs;
        }
    }
}
