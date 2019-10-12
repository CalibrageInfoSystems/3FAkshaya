package in.calibrage.akshaya.views.actvity;

import android.content.Intent;
import android.content.SharedPreferences;
import android.os.Bundle;
import android.support.v7.widget.LinearLayoutManager;
import android.support.v7.widget.RecyclerView;
import android.util.Log;
import android.view.View;
import android.view.Window;
import android.view.WindowManager;
import android.widget.ImageView;
import android.widget.LinearLayout;

import java.io.IOException;

import dmax.dialog.SpotsDialog;
import in.calibrage.akshaya.R;
import in.calibrage.akshaya.common.BaseActivity;
import in.calibrage.akshaya.models.RecomPlotcodes;
import in.calibrage.akshaya.models.VisitplotDetailsModel;
import in.calibrage.akshaya.service.APIConstantURL;
import in.calibrage.akshaya.service.ApiService;
import in.calibrage.akshaya.service.ServiceFactory;
import in.calibrage.akshaya.views.Adapter.RecommendationAdapter;
import in.calibrage.akshaya.views.Adapter.ReqVisitAdapter;
import retrofit2.adapter.rxjava.HttpException;
import rx.Subscriber;
import rx.Subscription;
import rx.android.schedulers.AndroidSchedulers;
import rx.schedulers.Schedulers;

public class RequestVisitActivity extends BaseActivity {
    private RecyclerView recyclerView;
    private static final String TAG = LabourRecommendationsActivity.class.getSimpleName();

    private ReqVisitAdapter adapter;
    private Subscription mSubscription;
    String Farmer_code;
    LinearLayout noRecords;
    ImageView backImg, home_btn;
    private SpotsDialog mdilogue;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
//        requestWindowFeature(Window.FEATURE_NO_TITLE);
//        getWindow().setFlags(WindowManager.LayoutParams.FLAG_FULLSCREEN,
//                WindowManager.LayoutParams.FLAG_FULLSCREEN);
        setContentView(R.layout.activity_request_visit);

        init();

        setViews();


    }

    private void init() {
        noRecords = (LinearLayout) findViewById(R.id.text);
        backImg = (ImageView) findViewById(R.id.back);
        home_btn = (ImageView) findViewById(R.id.home_btn);
        mdilogue = (SpotsDialog) new SpotsDialog.Builder()
                .setContext(this)
                .setTheme(R.style.Custom)
                .build();
        recyclerView = (RecyclerView) findViewById(R.id.recycler_view);
        SharedPreferences pref = getSharedPreferences("FARMER", MODE_PRIVATE);
        Farmer_code = pref.getString("farmerid", "");       // Saving string data of your editext

    }

    private void setViews() {

        backImg.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View view) {
                Intent intent = new Intent(getApplicationContext(), HomeActivity.class);
                startActivity(intent);
            }
        });
        home_btn.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View view) {
               /* Intent intent =new Intent(getApplicationContext(),HomeActivity.class);
                startActivity(intent);*/
                Intent intent = new Intent(RequestVisitActivity.this, HomeActivity.class);
                intent.setFlags(Intent.FLAG_ACTIVITY_NEW_TASK | Intent.FLAG_ACTIVITY_CLEAR_TOP);
                startActivity(intent);
                finish();
            }
        });

        // listSuperHeroes = new ArrayList<>();


        recyclerView.setHasFixedSize(true);
        recyclerView.setLayoutManager(new LinearLayoutManager(this));
        recyclerView.setAdapter(adapter);
        if (isOnline())
            GetLabourRequestDetails();
        else {
            showDialog(RequestVisitActivity.this,getResources().getString(R.string.Internet));
            //Toast.makeText(LoginActivity.this, "Please Check Internet Connection ", Toast.LENGTH_SHORT).show();
        }


    }

    private void GetLabourRequestDetails() {

        mdilogue.show();
        ApiService service = ServiceFactory.createRetrofitService(this, ApiService.class);
        mSubscription = service.getplots(APIConstantURL.Recommede_plots + Farmer_code)
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
                    }

                    @Override
                    public void onNext(RecomPlotcodes recomPlotcodes) {
                        Log.d("", "onNext: " + recomPlotcodes);
                        mdilogue.dismiss();
                        if (recomPlotcodes.getIsSuccess()) {

                            if (recomPlotcodes.getListResult().isEmpty()) {
                                noRecords.setVisibility(View.VISIBLE);

                            } else {
                                noRecords.setVisibility(View.GONE);
                            }
                        }
                        ReqVisitAdapter rec_adapter = new ReqVisitAdapter(RequestVisitActivity.this, recomPlotcodes.getListResult());
                        recyclerView.setAdapter(rec_adapter);
                    }

                });

    }
    @Override
    public void onBackPressed() {
        super.onBackPressed();
        this.finish();
    }
}