package in.calibrage.akshaya.views.actvity;

import android.content.Intent;
import android.support.v7.app.AppCompatActivity;
import android.os.Bundle;
import android.support.v7.widget.LinearLayoutManager;
import android.support.v7.widget.RecyclerView;
import android.support.v7.widget.Toolbar;
import android.util.Log;
import android.view.View;
import android.widget.ImageView;
import android.widget.TextView;

import com.google.gson.Gson;
import com.google.gson.JsonObject;

import java.io.IOException;
import java.util.ArrayList;
import java.util.List;

import dmax.dialog.SpotsDialog;
import in.calibrage.akshaya.R;
import in.calibrage.akshaya.models.CollectionResponceModel;
import in.calibrage.akshaya.models.LabourRequestModel;
import in.calibrage.akshaya.models.MyReqModel;
import in.calibrage.akshaya.models.collectionRequestModel;
import in.calibrage.akshaya.models.labour_req_response;
import in.calibrage.akshaya.service.ApiService;
import in.calibrage.akshaya.service.ServiceFactory;
import in.calibrage.akshaya.views.Adapter.MyLabour_ReqAdapter;
import retrofit2.adapter.rxjava.HttpException;
import rx.Subscriber;
import rx.Subscription;
import rx.android.schedulers.AndroidSchedulers;
import rx.schedulers.Schedulers;

public class MyReqDeatilsActivity extends AppCompatActivity {
    public static final String TAG = MyReqDeatilsActivity.class.getSimpleName();
    private Toolbar toolbar;

    RecyclerView req_recyclerView;
    MyLabour_ReqAdapter adapter;
    String datetimevaluereq, date_req, datetimevalueSub;
    TextView text;
    String Farmer_code;
    ImageView home_btn;
    private SpotsDialog mdilogue;
    private Subscription mSubscription;
    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_my_req_deatils);
        init();
        setviews();
        settoolbar();
    }

    private void init() {
        mdilogue = (SpotsDialog) new SpotsDialog.Builder()
                .setContext(this)
                .setTheme(R.style.Custom)
                .build();
        text=(TextView)findViewById(R.id.text);

        req_recyclerView = (RecyclerView) findViewById(R.id.recycler_view);
        home_btn=(ImageView)findViewById(R.id.home_btn);
    }
    private void setviews() {


        home_btn.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View view) {
                Log.d(TAG,"---------- Finish ----------------");
                Intent intent = new Intent(MyReqDeatilsActivity.this, HomeActivity.class);
                intent.setFlags(Intent.FLAG_ACTIVITY_NEW_TASK | Intent.FLAG_ACTIVITY_CLEAR_TOP);
                startActivity(intent);
                finish();
            }
        });
        req_recyclerView.setHasFixedSize(true);
        req_recyclerView.setLayoutManager(new LinearLayoutManager(this));

        GetLabourRequestDetails();
    }

    private void GetLabourRequestDetails() {

            mdilogue.show();
            JsonObject object = labourrequest_object();
            ApiService service = ServiceFactory.createRetrofitService(this, ApiService.class);
            mSubscription = service.postLabour_request(object)
                    .subscribeOn(Schedulers.newThread())
                    .observeOn(AndroidSchedulers.mainThread())
                    .subscribe(new Subscriber<labour_req_response>() {
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
                            mdilogue.cancel();
                        }

                        @Override
                        public void onNext(labour_req_response labour_req_response) {
                            req_recyclerView.setAdapter(adapter);
                        }


                    });
        }

    private JsonObject labourrequest_object() {
        LabourRequestModel requestModel = new LabourRequestModel();
        requestModel.setFarmerCode(Farmer_code);
        requestModel.setToDate(null);
        requestModel.setFromDate(null);

        return new Gson().toJsonTree(requestModel).getAsJsonObject();
    }




    private void settoolbar() {
        toolbar = (Toolbar) findViewById(R.id.toolbar);
        toolbar.setTitle("Product Request");
        setSupportActionBar(toolbar);
        getSupportActionBar().setHomeAsUpIndicator(R.drawable.ic_left);
        getSupportActionBar().setDisplayShowHomeEnabled(true);
        getSupportActionBar().setDisplayHomeAsUpEnabled(true);
        getSupportActionBar().setDisplayShowTitleEnabled(true);
        toolbar.setNavigationOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                finish();
            }
        });
    }

}
