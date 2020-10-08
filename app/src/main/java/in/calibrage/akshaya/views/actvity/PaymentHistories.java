package in.calibrage.akshaya.views.actvity;

import android.content.Context;
import android.content.Intent;
import android.support.annotation.Nullable;
import android.support.annotation.StringRes;
import android.support.design.widget.TabLayout;
import android.support.v4.app.Fragment;
import android.support.v4.app.FragmentManager;
import android.support.v4.app.FragmentPagerAdapter;
import android.support.v4.app.FragmentTransaction;
import android.support.v4.view.ViewPager;
import android.support.v7.app.AppCompatActivity;
import android.os.Bundle;
import android.view.View;
import android.widget.AdapterView;
import android.widget.ArrayAdapter;
import android.widget.ImageView;
import android.widget.Spinner;

import in.calibrage.akshaya.R;
import in.calibrage.akshaya.localData.SharedPrefsData;
import in.calibrage.akshaya.views.fragments.PaymentFragment;
import in.calibrage.akshaya.views.fragments.ProfileFragment;
import in.calibrage.akshaya.views.fragments.TransFragment;

import static in.calibrage.akshaya.common.CommonUtil.updateResources;

public class PaymentHistories extends AppCompatActivity implements AdapterView.OnItemSelectedListener {
    String[] selection = {"Last 30 Days", "Current Financial Year", "Custom Time Period"};
    Spinner spin;
    private ImageView backImg, home_btn;
    Fragment fragment = null;
    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        final int langID = SharedPrefsData.getInstance(this).getIntFromSharedPrefs("lang");
        if (langID == 2)
            updateResources(this, "te");
        else if (langID == 3)
            updateResources(this, "kan");
        else
            updateResources(this, "en-US");
        setContentView(R.layout.activity_payment_histories);


        TabsPagerAdapter tabsPagerAdapter = new TabsPagerAdapter(this, getSupportFragmentManager());

        ViewPager viewPager = findViewById(R.id.view_pager);
        viewPager.setAdapter(tabsPagerAdapter);

        TabLayout tabs = findViewById(R.id.tabs);
        tabs.setupWithViewPager(viewPager);
    }
    //        init();
//        setviews();
//    }
    private void init() {
        backImg = (ImageView) findViewById(R.id.back);
        home_btn = (ImageView) findViewById(R.id.home_btn);
        TabsPagerAdapter tabsPagerAdapter = new TabsPagerAdapter(this, getSupportFragmentManager());
        spin = (Spinner) findViewById(R.id.spinner);
        ViewPager viewPager = findViewById(R.id.view_pager);
        viewPager.setAdapter(tabsPagerAdapter);

        TabLayout tabs = findViewById(R.id.tabs);
        tabs.setupWithViewPager(viewPager);

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
                Intent intent = new Intent(PaymentHistories.this, HomeActivity.class);
                intent.setFlags(Intent.FLAG_ACTIVITY_NEW_TASK | Intent.FLAG_ACTIVITY_CLEAR_TOP);
                startActivity(intent);
                finish();
            }
        });
        spin.setOnItemSelectedListener(this);
        ArrayAdapter aa = new ArrayAdapter(this, R.layout.spinner_item, selection);
        aa.setDropDownViewResource(R.layout.simple_spinner_dropdown_item);

        spin.setAdapter(aa);
    }

    @Override
    public void onItemSelected(AdapterView<?> adapterView, View view, int i, long l) {


    }

    @Override
    public void onNothingSelected(AdapterView<?> adapterView) {

    }



    private class TabsPagerAdapter   extends FragmentPagerAdapter {

        @StringRes
        private final int[] TAB_TITLES =
                new int[] { R.string.payments, R.string.trans };
        private final Context mContext;

        public TabsPagerAdapter(Context context, FragmentManager fm) {
            super(fm);
            mContext = context;
        }
        @Override
        public Fragment getItem(int index) {

            switch (index) {
                case 0:
                    PaymentFragment fragmentDemo = new PaymentFragment();
//			// Top Rated fragment activity
//			return new TopRatedFragment();
//			Bundle args = new Bundle();
//			args.putInt("someInt", someInt);
//			args.putString("someTitle", someTitle);
//			fragmentDemo.setArguments(args);
                    return fragmentDemo;
                case 1:
                    // Games fragment activity
                    return new TransFragment();

            }

            return null;
        }


        @Nullable
        @Override
        public CharSequence getPageTitle(int position) {
            return mContext.getResources().getString(TAB_TITLES[position]);
        }

        @Override
        public int getCount() {
            // Show 3 total pages.
            return 2;
        }
    }
}
