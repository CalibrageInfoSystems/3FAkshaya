package in.calibrage.akshaya.views.actvity;

import android.app.ProgressDialog;
import android.content.Intent;
import android.content.SharedPreferences;
import android.os.Handler;
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
import java.io.Serializable;
import java.util.ArrayList;
import java.util.List;

import dmax.dialog.SpotsDialog;
import in.calibrage.akshaya.R;
import in.calibrage.akshaya.common.BaseActivity;
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

public class QuickPayActivity extends BaseActivity implements QuickPayDataAdapter.quick_paylistener {

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
    TextView noRecords;
    private SpotsDialog mdilogue;
    ImageView backImg, home_btn;
    private List<QuickPayModel.ListResult> collection_list = new ArrayList<>();
    List<String> ids_list = new ArrayList<>();
    List<String> dates_list = new ArrayList<>();
    List<Double> weight_list = new ArrayList<>();
    int SPLASH_DISPLAY_DURATION = 500;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_quick_pay);

        init();
        setViews();

    }
    private void init() {

        backImg = (ImageView) findViewById(R.id.back);
        home_btn = (ImageView) findViewById(R.id.home_btn);
        recyclerView = (RecyclerView) findViewById(R.id.quick_recycler_view);

        mdilogue = (SpotsDialog) new SpotsDialog.Builder()
                .setContext(this)
                .setTheme(R.style.Custom)
                .build();

        noRecords = (TextView) findViewById(R.id.no_text);
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

                new Handler().postDelayed(new Runnable() {
                    @Override
                    public void run() {

                        Intent intent = new Intent(getApplicationContext(), Quickpay_SummaryActivity.class);


                        intent.putExtra("collection_ids", (Serializable) ids_list);

                        intent.putExtra("collection_dates", (Serializable) dates_list);
                        intent.putExtra("collection_weight", (Serializable) weight_list);
                        startActivity(intent);

                        Log.e("ids_list===", String.valueOf(ids_list));
                        Log.e("dates_list===", String.valueOf(dates_list));
                        Log.e("weight_list===", String.valueOf(weight_list));

                        overridePendingTransition(R.anim.enter_from_right, R.anim.exit_to_left);


                    }
                }, SPLASH_DISPLAY_DURATION);

                //  i.addFlags(Intent.FLAG_ACTIVITY_CLEAR_TOP);

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
        if (isOnline())
            getQuckPay();
        else {
            showDialog(QuickPayActivity.this,getResources().getString(R.string.Internet));

        }



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
                        //   showDialog(QuickPayActivity.this, getString(R.string.server_error));
                    }

                    @Override
                    public void onNext(QuickPayModel quickPayModel) {
                        mdilogue.dismiss();


                        if (quickPayModel.getListResult() != null) {
                            noRecords.setVisibility(View.GONE);
                            recyclerView.setVisibility(View.VISIBLE);
                            adapter = new QuickPayDataAdapter(QuickPayActivity.this, quickPayModel.getListResult(), QuickPayActivity.this);
                            recyclerView.setAdapter(adapter);
                            for (QuickPayModel.ListResult item : quickPayModel.getListResult()
                            ) {
                                ids_list.add(item.getUColnid());
                                dates_list.add(item.getDocDate());
                                weight_list.add(item.getQuantity());
                            }

                        } else {
                            noRecords.setVisibility(View.VISIBLE);
                            // no_data.setText("No " + name + " Found");
                            recyclerView.setVisibility(View.GONE);
                            nextButton.setVisibility(View.GONE);
                        }


                        Log.d("", "onNext: " + quickPayModel);


                    }


                });
    }
    @Override
    public void onBackPressed() {
        super.onBackPressed();
        this.finish();
    }
    @Override
    public void setOnClickAckListener(QuickPayModel.ListResult item) {

        if (ids_list.size() > 0) {

            if (ids_list.contains(item.getUColnid())) {
                ids_list.set(ids_list.indexOf(item.getUColnid()), item.getUColnid());

            }
        }
    }
}