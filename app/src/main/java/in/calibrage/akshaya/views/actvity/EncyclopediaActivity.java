package in.calibrage.akshaya.views.actvity;

import android.content.Intent;
import android.os.Bundle;
import android.support.design.widget.TabLayout;
import android.support.v4.view.ViewPager;
import android.support.v7.app.AppCompatActivity;
import android.support.v7.widget.Toolbar;
import android.util.Log;
import android.view.View;
import android.widget.ImageView;
import android.widget.TextView;

import java.io.IOException;
import java.util.ArrayList;
import java.util.List;

import dmax.dialog.SpotsDialog;
import in.calibrage.akshaya.R;
import in.calibrage.akshaya.common.BaseActivity;
import in.calibrage.akshaya.localData.SharedPrefsData;
import in.calibrage.akshaya.models.GetEncyclopediaDetails;
import in.calibrage.akshaya.service.APIConstantURL;
import in.calibrage.akshaya.service.ApiService;
import in.calibrage.akshaya.service.ServiceFactory;
import in.calibrage.akshaya.views.Adapter.ViewPagerAdapter;
import retrofit2.adapter.rxjava.HttpException;
import rx.Subscriber;
import rx.Subscription;
import rx.android.schedulers.AndroidSchedulers;
import rx.schedulers.Schedulers;

public class EncyclopediaActivity extends BaseActivity {
    private static final String TAG = EncyclopediaActivity.class.getSimpleName();
    private int postTypeId;
    private String titleName;
    private Toolbar toolbar;
    private TabLayout tabLayout;
    private ViewPager viewPager;
    private TextView label;
    private ImageView backImg;
    private SpotsDialog mdilogue;
    private Subscription mSubscription;
    private String[] tabnames;
   private ViewPagerAdapter adapter;
    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
     /*   requestWindowFeature(Window.FEATURE_NO_TITLE);
        getWindow().setFlags(WindowManager.LayoutParams.FLAG_FULLSCREEN,
                WindowManager.LayoutParams.FLAG_FULLSCREEN);*/
        setContentView(R.layout.activity_encyclopedia);
        init();
        setViews();
    }

    private void init() {


        viewPager = findViewById(R.id.viewpager);
        tabLayout = findViewById(R.id.tablayout);
        toolbar = findViewById(R.id.toolbar);
        setSupportActionBar(toolbar);
        getSupportActionBar().setHomeAsUpIndicator(R.drawable.ic_left);
        getSupportActionBar().setDisplayShowHomeEnabled(true);
        getSupportActionBar().setDisplayHomeAsUpEnabled(true);
        getSupportActionBar().setDisplayShowTitleEnabled(false);

    }

    private void setViews() {
        if (getIntent() != null) {
            postTypeId = getIntent().getIntExtra("postTypeId", 0);
            titleName = getIntent().getStringExtra("name");
            tabnames = getIntent().getStringArrayExtra("tabslist");
            SharedPrefsData.getInstance(this).updateIntValue(this,"count",tabnames.length);
            SharedPrefsData.getInstance(this).updateIntValue(this,"postTypeId",postTypeId);
            adapter = new ViewPagerAdapter(getSupportFragmentManager(),tabnames);
        }



        viewPager.setAdapter(adapter);
        tabLayout.setupWithViewPager(viewPager);
        toolbar.setTitle(titleName);
        toolbar.setNavigationOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                finish();
            }
        });
        mdilogue = (SpotsDialog) new SpotsDialog.Builder()
                .setContext(this)
                .setTheme(R.style.Custom)
                .build();
    }





}

