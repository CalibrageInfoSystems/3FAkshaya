package in.calibrage.akshaya.views.actvity;

import android.app.ProgressDialog;
import android.content.Intent;
import android.content.SharedPreferences;
import android.support.v7.app.AppCompatActivity;
import android.os.Bundle;
import android.support.v7.widget.LinearLayoutManager;
import android.support.v7.widget.RecyclerView;
import android.util.Log;
import android.view.View;
import android.widget.ImageView;
import android.widget.TextView;

import java.io.IOException;
import java.util.ArrayList;

import in.calibrage.akshaya.R;
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

public class RecommendationActivity extends AppCompatActivity {
    private RecyclerView recom_recyclerView;
    private RecommendationAdapter rec_adapter;
    private ProgressDialog dialog;
    private Subscription mSubscription;
    TextView no_plots;
    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_recommendation);
        dialog = new ProgressDialog(this);
        ImageView backImg=(ImageView)findViewById(R.id.back);
        backImg.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View view) {
                Intent intent =new Intent(getApplicationContext(),HomeActivity.class);
                startActivity(intent);
                //  finish();
            }
        });
        no_plots=(TextView)findViewById(R.id.text);
        ImageView home_btn=(ImageView)findViewById(R.id.home_btn);
        home_btn.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View view) {
                Intent intent =new Intent(getApplicationContext(),HomeActivity.class);
                startActivity(intent);
            }
        });
        recom_recyclerView = (RecyclerView) findViewById(R.id.recycler_view);
        recom_recyclerView.setHasFixedSize(true);
        recom_recyclerView.setLayoutManager(new LinearLayoutManager(this));
      //  recom_recyclerView.setAdapter(rec_adapter);

        Getplots();
    }

    private void Getplots() {
        dialog.setMessage("Loading, please wait....");
        dialog.show();
        dialog.setCanceledOnTouchOutside(false);

        ApiService service = ServiceFactory.createRetrofitService(this, ApiService.class);
        mSubscription = service.getplots(APIConstantURL.Recommede_plots)
                .subscribeOn(Schedulers.newThread())
                .observeOn(AndroidSchedulers.mainThread())
                .subscribe(new Subscriber<RecomPlotcodes>() {
                    @Override
                    public void onCompleted() {
                        if (dialog.isShowing()) {
                            dialog.dismiss();
                        }
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
                    }

                    @Override
                    public void onNext(RecomPlotcodes recomPlotcodes) {
                        Log.d("", "onNext: " + recomPlotcodes);
                        if (dialog.isShowing()) {
                            dialog.dismiss();
                        }
                        if(recomPlotcodes.getIsSuccess())
                        {

                            if (recomPlotcodes.getListResult().isEmpty()) {
                                no_plots.setVisibility(View.VISIBLE);

                            } else {
                                no_plots.setVisibility(View.GONE);
                            }
                        }
                        RecommendationAdapter rec_adapter= new RecommendationAdapter(RecommendationActivity.this, recomPlotcodes.getListResult());
                        recom_recyclerView.setAdapter(rec_adapter);
                    }





                });

    }


}

