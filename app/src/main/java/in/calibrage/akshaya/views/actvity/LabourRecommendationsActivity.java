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
import android.view.Window;
import android.view.WindowManager;
import android.widget.ImageView;
import android.widget.LinearLayout;
import android.widget.Toast;

import com.android.volley.AuthFailureError;
import com.android.volley.DefaultRetryPolicy;
import com.android.volley.NetworkError;
import com.android.volley.NoConnectionError;
import com.android.volley.ParseError;
import com.android.volley.Request;
import com.android.volley.RequestQueue;
import com.android.volley.Response;
import com.android.volley.RetryPolicy;
import com.android.volley.ServerError;
import com.android.volley.TimeoutError;
import com.android.volley.VolleyError;
import com.android.volley.toolbox.JsonObjectRequest;
import com.android.volley.toolbox.StringRequest;
import com.android.volley.toolbox.Volley;

import org.json.JSONArray;
import org.json.JSONException;
import org.json.JSONObject;

import java.io.IOException;
import java.util.ArrayList;
import java.util.HashMap;
import java.util.List;
import java.util.Map;

import dmax.dialog.SpotsDialog;
import in.calibrage.akshaya.R;
import in.calibrage.akshaya.common.BaseActivity;
import in.calibrage.akshaya.localData.SharedPrefsData;
import in.calibrage.akshaya.models.LabourRecommendationsModel;
import in.calibrage.akshaya.service.APIConstantURL;
import in.calibrage.akshaya.service.ApiService;
import in.calibrage.akshaya.service.ServiceFactory;
import in.calibrage.akshaya.views.Adapter.KnowledgeZoneBaseAdapter;
import in.calibrage.akshaya.views.Adapter.LabourRecommendationAdapter;
import in.calibrage.akshaya.views.Adapter.PaymentAdapter;
import retrofit2.adapter.rxjava.HttpException;
import rx.Subscriber;
import rx.Subscription;
import rx.android.schedulers.AndroidSchedulers;
import rx.schedulers.Schedulers;

import static in.calibrage.akshaya.common.CommonUtil.updateResources;

public class LabourRecommendationsActivity  extends BaseActivity {
    private static final String TAG = LabourRecommendationsActivity.class.getSimpleName();

    private RecyclerView recyclerView;
    private LabourRecommendationAdapter adapter;
    private Subscription mSubscription;
    String Farmer_code;
    LinearLayout noRecords;
    ImageView backImg,home_btn;
    private SpotsDialog mdilogue ;
    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        final int langID = SharedPrefsData.getInstance(this).getIntFromSharedPrefs("lang");
        if (langID == 2)
            updateResources(this, "te");
        else
            updateResources(this, "en-US");
        setContentView(R.layout.activity_labour_recommendations);
        init();
        setViews();
    }

    private void init() {
        SharedPreferences pref = getSharedPreferences("FARMER", MODE_PRIVATE);
        Farmer_code = pref.getString("farmerid", "");
        noRecords = (LinearLayout) findViewById(R.id.text);
        backImg = (ImageView) findViewById(R.id.back);
         home_btn = (ImageView) findViewById(R.id.home_btn);
        mdilogue = (SpotsDialog) new SpotsDialog.Builder()
                .setContext(this)
                .setTheme(R.style.Custom)
                .build();
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

                Intent intent = new Intent(LabourRecommendationsActivity.this, HomeActivity.class);
                intent.setFlags(Intent.FLAG_ACTIVITY_NEW_TASK | Intent.FLAG_ACTIVITY_CLEAR_TOP);
                startActivity(intent);
                finish();
            }
        });


        // listSuperHeroes = new ArrayList<>();

        recyclerView = (RecyclerView) findViewById(R.id.recycler_view);
        recyclerView.setHasFixedSize(true);
        recyclerView.setLayoutManager(new LinearLayoutManager(this));
        recyclerView.setAdapter(adapter);
        if (isOnline())
            GetLabourRequestDetails();
        else {
            showDialog(LabourRecommendationsActivity.this, getResources().getString(R.string.Internet));

        }


    }

    private void GetLabourRequestDetails() {
        mdilogue.show();
        ApiService service = ServiceFactory.createRetrofitService(this, ApiService.class);
        mSubscription = service.getrecommdetails(APIConstantURL.GetActivePlotsByFarmerCode +Farmer_code)
                .subscribeOn(Schedulers.newThread())
                .observeOn(AndroidSchedulers.mainThread())
                .subscribe(new Subscriber<LabourRecommendationsModel>() {
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
                        showDialog(LabourRecommendationsActivity.this, getString(R.string.server_error));
                    }

                    @Override
                    public void onNext(LabourRecommendationsModel labourRecommendationsModel) {
                        mdilogue.dismiss();
                        Log.d(TAG, "onNext:lobour " + labourRecommendationsModel);

                        if(labourRecommendationsModel.getListResult() != null)
                        {
                            noRecords.setVisibility(View.GONE);
                            adapter = new LabourRecommendationAdapter(labourRecommendationsModel.getListResult(),LabourRecommendationsActivity.this);
                            recyclerView.setAdapter(adapter);


                        }
                        else{
                            noRecords.setVisibility(View.VISIBLE);

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
