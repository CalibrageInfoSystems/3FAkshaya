package in.calibrage.akshaya.views.actvity;

import android.content.Context;
import android.content.Intent;
import android.content.SharedPreferences;
import android.support.v7.app.AppCompatActivity;
import android.os.Bundle;
import android.support.v7.widget.LinearLayoutManager;
import android.support.v7.widget.RecyclerView;
import android.support.v7.widget.Toolbar;
import android.util.Log;
import android.view.View;
import android.widget.TextView;
import android.widget.Toast;

import com.google.gson.Gson;
import com.google.gson.JsonObject;

import java.io.IOException;
import java.util.List;

import dmax.dialog.SpotsDialog;
import in.calibrage.akshaya.R;
import in.calibrage.akshaya.common.BaseActivity;
import in.calibrage.akshaya.localData.SharedPrefsData;
import in.calibrage.akshaya.models.LabourRequestModel;
import in.calibrage.akshaya.models.ReqPole;
import in.calibrage.akshaya.models.ResLoan;
import in.calibrage.akshaya.models.ResPole;
import in.calibrage.akshaya.models.Resfert;
import in.calibrage.akshaya.models.Resquickpay;
import in.calibrage.akshaya.models.labour_req_response;
import in.calibrage.akshaya.service.ApiService;
import in.calibrage.akshaya.service.ServiceFactory;
import in.calibrage.akshaya.views.Adapter.GetLoanAdapter;
import in.calibrage.akshaya.views.Adapter.GetPoleAdapter;
import in.calibrage.akshaya.views.Adapter.GetfertAdapter;
import in.calibrage.akshaya.views.Adapter.MyLabour_ReqAdapter;
import in.calibrage.akshaya.views.Adapter.MyQuickPayDataAdapter;
import retrofit2.adapter.rxjava.HttpException;
import rx.Subscriber;
import rx.Subscription;
import rx.android.schedulers.AndroidSchedulers;
import rx.schedulers.Schedulers;

public class RequestListctivity extends BaseActivity implements GetPoleAdapter.GetPoleAdapterListener1, GetfertAdapter.GetPoleAdapterListener{

    public static final String TAG = RequestListctivity.class.getSimpleName();

    private Context ctx;
    private LinearLayoutManager lytManager;
    private RecyclerView rcv_requests;
    private SpotsDialog mdilogue;
    private Subscription mSubscription;
    String name;
    String  Farmer_code;
    TextView no_data;
    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_request_listctivity);
        /*
         * check comming from Request
         *
         * */
         name = getIntent().getStringExtra("key");

        setuptoolbar();
        init();

        if (name.equalsIgnoreCase(getResources().getString(R.string.lab_req))) {
            GetLabourRequestDetails();

        } else if (name.equalsIgnoreCase(getResources().getString(R.string.pole_req))) {
            getPole();
        } else if (name.equalsIgnoreCase(getResources().getString(R.string.fert_req))) {
            getfertilizer();

        } else if (name.equalsIgnoreCase(getResources().getString(R.string.quick_req))) {
            getquickpay();

        } else if (name.equalsIgnoreCase(getResources().getString(R.string.visit_req))) {
            getvisit();
        } else if (name.equalsIgnoreCase( getResources().getString(R.string.Loan_req))) {
            getLoan();

        }


    }



    private void GetLabourRequestDetails() {

        mdilogue.show();
        JsonObject object = getPoleobject();
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
                        MyLabour_ReqAdapter adapter = new MyLabour_ReqAdapter(labour_req_response.getListResult(), ctx);
                        rcv_requests.setAdapter(adapter);

                    }


                });
    }


    private void init() {
        ctx = this;
        lytManager = new LinearLayoutManager(this);
        rcv_requests = findViewById(R.id.rcv_requests);
        no_data=findViewById(R.id.no_data);
        rcv_requests.setLayoutManager(lytManager);
        mdilogue = (SpotsDialog) new SpotsDialog.Builder()
                .setContext(this)
                .setTheme(R.style.Custom)
                .build();
        SharedPreferences pref = getSharedPreferences("FARMER", MODE_PRIVATE);
        Farmer_code = pref.getString("farmerid", "");
       // no_data.setText("No" + name);
    }

    private void setuptoolbar() {
        Toolbar toolbar = (Toolbar) findViewById(R.id.toolbar);
        TextView title =(TextView) findViewById(R.id.txt_name);
        title.setText(name);
        setSupportActionBar(toolbar);
        getSupportActionBar().setHomeAsUpIndicator(R.drawable.ic_left);
        getSupportActionBar().setDisplayShowHomeEnabled(true);
        getSupportActionBar().setDisplayHomeAsUpEnabled(true);
        getSupportActionBar().setDisplayShowTitleEnabled(false);
        toolbar.setNavigationOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                finish();
            }
        });
    }

    private void getPole() {
        mdilogue.show();
        JsonObject object = getPoleobject();
        ApiService service = ServiceFactory.createRetrofitService(this, ApiService.class);
        mSubscription = service.GetPoleRequestDetails(object)
                .subscribeOn(Schedulers.newThread())
                .observeOn(AndroidSchedulers.mainThread())
                .subscribe(new Subscriber<ResPole>() {
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
                        no_data.setVisibility(View.VISIBLE);
                    }

                    @Override
                    public void onNext(ResPole poleResponce) {
                        if(poleResponce.getListResult() != null)
                        {
                            no_data.setVisibility(View.GONE);
                            GetPoleAdapter adapter = new GetPoleAdapter(poleResponce.getListResult(), ctx, RequestListctivity.this);
                            rcv_requests.setAdapter(adapter);

                        }
                        else{

                            no_data.setText(poleResponce.getEndUserMessage());

                        }

                    }


                });


    }
    private void getfertilizer() {
        mdilogue.show();
        JsonObject object = getPoleobject();
        ApiService service = ServiceFactory.createRetrofitService(this, ApiService.class);
        mSubscription = service.GetfertRequestDetails(object)
                .subscribeOn(Schedulers.newThread())
                .observeOn(AndroidSchedulers.mainThread())
                .subscribe(new Subscriber<Resfert>() {
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
                        no_data.setVisibility(View.VISIBLE);
                    }

                    @Override
                    public void onNext(Resfert fertResponce) {


                        if(fertResponce.getListResult() != null)
                        {
                            no_data.setVisibility(View.GONE);
                            GetfertAdapter adapter = new GetfertAdapter(fertResponce.getListResult(), ctx, RequestListctivity.this);
                            rcv_requests.setAdapter(adapter);
                        }
                        else{
                            no_data.setVisibility(View.VISIBLE);

                        }

                    }


                });

    }

    private void getquickpay() {
        mdilogue.show();
        JsonObject object = getheaderobject();
        ApiService service = ServiceFactory.createRetrofitService(this, ApiService.class);
        mSubscription = service.GetRequestheaderDetails(object)
                .subscribeOn(Schedulers.newThread())
                .observeOn(AndroidSchedulers.mainThread())
                .subscribe(new Subscriber<Resquickpay>() {
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
                        no_data.setVisibility(View.VISIBLE);
                    }

                    @Override
                    public void onNext(Resquickpay resquickpay) {


                        if(resquickpay.getListResult() != null)
                        {
                            no_data.setVisibility(View.GONE);
                            MyQuickPayDataAdapter adapter = new MyQuickPayDataAdapter(resquickpay.getListResult(), ctx);
                            rcv_requests.setAdapter(adapter);
                        }
                        else{
                            no_data.setVisibility(View.VISIBLE);

                        }

                    }




                });

    }

    private JsonObject getheadervisitobject() {
        ReqPole requestModel = new ReqPole();
        requestModel.setFarmerCode(Farmer_code);
        requestModel.setToDate(null);
        requestModel.setFromDate(null);
        requestModel.setRequestTypeId(14);
        return new Gson().toJsonTree(requestModel).getAsJsonObject();
    }

    private void getLoan() {
        mdilogue.show();
        JsonObject object = getLoanheaderobject();
        ApiService service = ServiceFactory.createRetrofitService(this, ApiService.class);
        mSubscription = service.GetRequestheaderLoanDetails(object)
                .subscribeOn(Schedulers.newThread())
                .observeOn(AndroidSchedulers.mainThread())
                .subscribe(new Subscriber<ResLoan>() {
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
                    public void onNext(ResLoan resLoan) {
                        if(resLoan.getListResult() != null)
                        {
                            no_data.setVisibility(View.GONE);
                            GetLoanAdapter adapter = new GetLoanAdapter(resLoan.getListResult(), ctx);
                            rcv_requests.setAdapter(adapter);
                        }
                        else{
                            no_data.setVisibility(View.VISIBLE);

                        }


                    }




                });
    }
    private void getvisit() {
        {
            mdilogue.show();
            JsonObject object = getheadervisitobject();
            ApiService service = ServiceFactory.createRetrofitService(this, ApiService.class);
            mSubscription = service.GetRequestheaderLoanDetails(object)
                    .subscribeOn(Schedulers.newThread())
                    .observeOn(AndroidSchedulers.mainThread())
                    .subscribe(new Subscriber<ResLoan>() {
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
                        public void onNext(ResLoan resLoan) {

                            if(resLoan.getListResult() != null)
                            {
                                no_data.setVisibility(View.GONE);
                                GetLoanAdapter adapter = new GetLoanAdapter(resLoan.getListResult(), ctx);
                                rcv_requests.setAdapter(adapter);
                            }
                            else{
                                no_data.setVisibility(View.VISIBLE);

                            }

//                            GetLoanAdapter adapter = new GetLoanAdapter(resLoan.getListResult(), ctx);
//                            rcv_requests.setAdapter(adapter);
                        }




                    });
        }
    }


    private JsonObject getLoanheaderobject() {
        ReqPole requestModel = new ReqPole();
        requestModel.setFarmerCode(Farmer_code);
        requestModel.setToDate(null);
        requestModel.setFromDate(null);
        requestModel.setRequestTypeId(28);
        return new Gson().toJsonTree(requestModel).getAsJsonObject();
    }


    private JsonObject getheaderobject() {
        ReqPole requestModel = new ReqPole();
        requestModel.setFarmerCode(Farmer_code);
        requestModel.setToDate(null);
        requestModel.setFromDate(null);
        requestModel.setRequestTypeId(13);
        return new Gson().toJsonTree(requestModel).getAsJsonObject();
    }

    private JsonObject getPoleobject() {
        ReqPole requestModel = new ReqPole();
        requestModel.setFarmerCode(Farmer_code);
        requestModel.setToDate(null);
        requestModel.setFromDate(null);
        return new Gson().toJsonTree(requestModel).getAsJsonObject();
    }

    @Override
    public void onContactSelected(String products) {

        Intent intent = new Intent(RequestListctivity.this, product_list.class);

//                    // Sending Student Id, Name, Number and Class to next UpdateActivity.
        intent.putExtra("Name",  products);

        startActivity(intent);
    }


    @Override
    public void onContactSelected1(String products) {
        Intent intent = new Intent(RequestListctivity.this, product_list.class);

//                    // Sending Student Id, Name, Number and Class to next UpdateActivity.
        intent.putExtra("Name",  products);

        startActivity(intent);
    }
}
