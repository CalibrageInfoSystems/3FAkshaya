package in.calibrage.akshaya.views.actvity;

import android.app.ProgressDialog;
import android.content.Intent;
import android.content.SharedPreferences;
//import android.support.v7.app.AppCompatActivity;
import android.os.Bundle;
//import android.support.v7.widget.LinearLayoutManager;
//import android.support.v7.widget.RecyclerView;
import android.util.Log;
import android.view.View;
import android.view.Window;
import android.view.WindowManager;
import android.widget.ImageView;
import android.widget.TextView;

import java.io.IOException;
import java.util.ArrayList;

import dmax.dialog.SpotsDialog;
import in.calibrage.akshaya.R;
import in.calibrage.akshaya.common.BaseActivity;
import in.calibrage.akshaya.localData.SharedPrefsData;
import in.calibrage.akshaya.models.RecomPlotcodes;
import in.calibrage.akshaya.service.APIConstantURL;
import in.calibrage.akshaya.service.ApiService;
import in.calibrage.akshaya.service.ServiceFactory;
import in.calibrage.akshaya.views.Adapter.RecommendationAdapter;
import retrofit2.adapter.rxjava.HttpException;
import rx.Subscriber;
import rx.Subscription;
import rx.android.schedulers.AndroidSchedulers;
import rx.schedulers.Schedulers;

import static in.calibrage.akshaya.common.CommonUtil.updateResources;

import androidx.recyclerview.widget.LinearLayoutManager;
import androidx.recyclerview.widget.RecyclerView;

public class RecommendationActivity extends BaseActivity {
    private RecyclerView recom_recyclerView;
    private RecommendationAdapter rec_adapter;
    private ProgressDialog dialog;

    String Farmer_code;
    private Subscription mSubscription;
    TextView no_plots;
    ImageView backImg,home_btn;
    private SpotsDialog mdilogue ;
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
        setContentView(R.layout.activity_recommendation);
        init();
        setViews();
    }
    private void init() {
        no_plots=(TextView)findViewById(R.id.text);
        home_btn=(ImageView)findViewById(R.id.home_btn);

        backImg=(ImageView)findViewById(R.id.back);
        mdilogue = (SpotsDialog) new SpotsDialog.Builder()
                .setContext(this)
                .setTheme(R.style.Custom)
                .build();
        recom_recyclerView = (RecyclerView) findViewById(R.id.recycler_view);
        SharedPreferences pref = getSharedPreferences("FARMER", MODE_PRIVATE);
        Farmer_code = pref.getString("farmerid", "").trim();
    }
    private void setViews() {
        backImg.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View view) {
                Intent intent =new Intent(getApplicationContext(),HomeActivity.class);
                startActivity(intent);
                //  finish();
            }
        });
        home_btn.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View view) {
               /* Intent intent =new Intent(getApplicationContext(),HomeActivity.class);
                startActivity(intent);*/
                Intent intent = new Intent(RecommendationActivity.this, HomeActivity.class);
                intent.setFlags(Intent.FLAG_ACTIVITY_NEW_TASK | Intent.FLAG_ACTIVITY_CLEAR_TOP);
                startActivity(intent);
                finish();
            }
        });
        recom_recyclerView.setHasFixedSize(true);
        recom_recyclerView.setLayoutManager(new LinearLayoutManager(this));

        if (isOnline())
            Getplots();
        else {
            showDialog(RecommendationActivity.this, getResources().getString(R.string.Internet));
        }


    }





    //Get Farmer Active Plots
    private void Getplots() {

        mdilogue.show();
        ApiService service = ServiceFactory.createRetrofitService(this, ApiService.class);
        mSubscription = service.getplots(APIConstantURL.Recommede_plots+Farmer_code)
                .subscribeOn(Schedulers.newThread())
                .observeOn(AndroidSchedulers.mainThread())
                .subscribe(new Subscriber<RecomPlotcodes>() {
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
                        showDialog(RecommendationActivity.this, getString(R.string.server_error));
                    }

                    @Override
                    public void onNext(RecomPlotcodes recomPlotcodes) {
                        Log.d("", "onNext: " + recomPlotcodes);
                        mdilogue.dismiss();


                        if (recomPlotcodes.getListResult() != null) {
                            no_plots.setVisibility(View.GONE);
                            RecommendationAdapter rec_adapter= new RecommendationAdapter(RecommendationActivity.this, recomPlotcodes.getListResult());
                            recom_recyclerView.setAdapter(rec_adapter);

                        } else {
                            no_plots.setVisibility(View.VISIBLE);

                        }

                    }


                });

    }
    @Override
    public void onBackPressed() {
        super.onBackPressed();
        this.finish();
    }

}

