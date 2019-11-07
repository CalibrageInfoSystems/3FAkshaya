package in.calibrage.akshaya.views.actvity;

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
import android.util.Log;
import android.view.View;
import android.widget.AdapterView;
import android.widget.ArrayAdapter;
import android.widget.Spinner;

import java.text.SimpleDateFormat;
import java.util.Calendar;
import java.util.Date;
import java.util.Locale;

import in.calibrage.akshaya.R;
import in.calibrage.akshaya.common.Constants;
import in.calibrage.akshaya.common.IDataChnaged;
import in.calibrage.akshaya.localData.SharedPrefsData;
import in.calibrage.akshaya.views.fragments.PaymentFragment;
import in.calibrage.akshaya.views.fragments.TransFragment;

public class MainActivity extends AppCompatActivity implements AdapterView.OnItemSelectedListener {

    Toolbar toolbar;
    TabLayout tabLayout;
    ViewPager viewPager;
    PageAdapter pageAdapter;
    TabItem tabChats;
    TabItem tabStatus;
    String financiyalYearFrom ;
    String financiyalYearTo ;
    String selected_item;
    String[] selection = {"Last 30 Days", "Current Financial Year", "Custom Time Period"};
    Spinner spin;
    PaymentFragment myFragment;
    private String currentDate,last_30day;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_main);

//        toolbar = findViewById(R.id.toolbar);
//        toolbar.setTitle(getResources().getString(R.string.app_name));
//        setSupportActionBar(toolbar);
        spin = (Spinner) findViewById(R.id.spinner);
        tabLayout = findViewById(R.id.tablayout);
        tabChats = findViewById(R.id.tabpayments);
        tabStatus = findViewById(R.id.tabtrans);
       // spin.setOnItemSelectedListener(this);
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

        Calendar calendar = Calendar.getInstance();
        calendar.add(Calendar.MONTH, -1);
        Date date = calendar.getTime();
        SimpleDateFormat format = new SimpleDateFormat("yyyy-MM-dd");
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
    }

    @Override
    public void onItemSelected(AdapterView<?> adapterView, View view, int i , long l) {
         selected_item =selection[i];
        SharedPrefsData.getInstance(this).updateStringValue(this,"sitem",selected_item);
        Log.e("selected_item===",selected_item);
        Intent i2 = new Intent("android.intent.action.SmsReceiver").putExtra("incomingSms", "mahesh");
        i2.putExtra("incomingPhoneNumber", "7032214460");
        sendBroadcast(i2);
        return ;
      //   pageAdapter.setSpinner_item(selected_item);

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

           String selectedText= SharedPrefsData.getInstance(getApplicationContext()).getStringFromSharedPrefs("sitem");
            switch (position) {
                case 0:


                    Bundle bundle = new Bundle();

                                  Log.e("selected_item===109",selectedText);


                    if(selectedText.equalsIgnoreCase("Current Financial Year")) {
//
                        bundle.putString("todate", financiyalYearTo);
                        bundle.putString("fromdate", financiyalYearFrom);

                    }
                    if (selectedText.equalsIgnoreCase("Last 30 Days")) {

                    bundle.putString("todate", currentDate);
                    bundle.putString("fromdate", last_30day);

                }
                    else if(selectedText.equalsIgnoreCase("Current Financial Year")) {

                        bundle.putString("todate", financiyalYearTo);
                        bundle.putString("fromdate", financiyalYearFrom);

                    }
                    //PASS OVER THE BUNDLE TO OUR FRAGMENT
                    PaymentFragment myFragment = new PaymentFragment();
                    myFragment.setArguments(bundle);

//


              //  getSupportFragmentManager().beginTransaction().replace(R.id.container,myFragment).commit();
                    return myFragment;

//                    PaymentFragment fragmentDemo = new PaymentFragment();
////
//			Bundle args = new Bundle();
//			args.putInt("someInt", 1);
//			args.putString("someTitle", "Roja");
//			fragmentDemo.setArguments(args);
//                    return fragmentDemo;

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
