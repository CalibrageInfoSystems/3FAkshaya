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
import android.widget.Button;
import android.widget.ImageView;
import android.widget.LinearLayout;
import android.widget.TextView;

import java.io.IOException;
import java.util.List;

import dmax.dialog.SpotsDialog;
import in.calibrage.akshaya.R;
import in.calibrage.akshaya.common.Constants;
import in.calibrage.akshaya.localData.SharedPrefsData;
import in.calibrage.akshaya.models.QuickPayModel;
import in.calibrage.akshaya.models.RecomPlotcodes;
import in.calibrage.akshaya.service.APIConstantURL;
import in.calibrage.akshaya.service.ApiService;
import in.calibrage.akshaya.service.ServiceFactory;
import in.calibrage.akshaya.views.Adapter.QuickPayDataAdapter;
import in.calibrage.akshaya.views.Adapter.RecommendationAdapter;
import retrofit2.adapter.rxjava.HttpException;
import rx.Subscriber;
import rx.Subscription;
import rx.android.schedulers.AndroidSchedulers;
import rx.schedulers.Schedulers;

public class QuickPayActivity extends AppCompatActivity {

    private RecyclerView recyclerView;
    private RecyclerView mRecyclerView;
    private RecyclerView.Adapter mAdapter;
    private RecyclerView.LayoutManager mLayoutManager;
    private QuickPayDataAdapter adapter;
    private int checkedcount = 0;
    private Subscription mSubscription;
    private List<QuickPayModel> studentList;
    public static String TAG = "QuickPayActivity";
    private Button btnSelection;
    String datetimevaluereq, Farmer_code;
    TextView no_unpaid_collections;
    Button nextButton;
    LinearLayout noRecords;
    private SpotsDialog mdilogue;
    ImageView backImg, home_btn;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_quick_pay);

        init();
        setViews();

      //Farmer_code = SharedPrefsData.getInstance(this).getStringFromSharedPrefs(Constants.USER_ID);     // Saving string data of your e
    }

    private void init() {

        backImg = (ImageView) findViewById(R.id.back);
        home_btn = (ImageView) findViewById(R.id.home_btn);
        recyclerView = (RecyclerView) findViewById(R.id.quick_recycler_view);

        mdilogue = (SpotsDialog) new SpotsDialog.Builder()
                .setContext(this)
                .setTheme(R.style.Custom)
                .build();

        noRecords = (LinearLayout) findViewById(R.id.text);
        nextButton = (Button) findViewById(R.id.nextButton);
        SharedPreferences pref = getSharedPreferences("FARMER", MODE_PRIVATE);
        Farmer_code = pref.getString("farmerid", "");
    }


    private void setViews() {
        backImg.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View view) {
                Intent intent = new Intent(getApplicationContext(), HomeActivity.class);
                startActivity(intent);
            }
        });
        nextButton.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View view) {
                Intent intent = new Intent(getApplicationContext(), Quickpay_SummaryActivity.class);
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

        recyclerView.setHasFixedSize(true);
        recyclerView.setLayoutManager(new LinearLayoutManager(this));
        recyclerView.setAdapter(adapter);
        getQuckPay();

    }

    private void getQuckPay() {
        mdilogue.show();
        ApiService service = ServiceFactory.createRetrofitService(this, ApiService.class);
        mSubscription = service.getquick(APIConstantURL.GetUnPayedCollectionsByFarmerCode + Farmer_code)
                .subscribeOn(Schedulers.newThread())
                .observeOn(AndroidSchedulers.mainThread())
                .subscribe(new Subscriber<QuickPayModel>() {
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
                    public void onNext(QuickPayModel quickPayModel) {


                        Log.d("", "onNext: " + quickPayModel);
                        mdilogue.dismiss();
                        if (quickPayModel.getIsSuccess()) {

                            if (quickPayModel.getListResult().isEmpty()) {
                                noRecords.setVisibility(View.VISIBLE);

                            } else {
                                noRecords.setVisibility(View.GONE);
                            }
                        }
                        adapter = new QuickPayDataAdapter(QuickPayActivity.this, quickPayModel.getListResult());
                        recyclerView.setAdapter(adapter);
                    }


                });
    }
    @Override
    public void onBackPressed() {
        super.onBackPressed();
        this.finish();
    }
}
