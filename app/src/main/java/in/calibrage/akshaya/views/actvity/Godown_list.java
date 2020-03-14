package in.calibrage.akshaya.views.actvity;

import android.content.Context;
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

import dmax.dialog.SpotsDialog;
import in.calibrage.akshaya.R;
import in.calibrage.akshaya.common.BaseActivity;
import in.calibrage.akshaya.localData.SharedPrefsData;
import in.calibrage.akshaya.models.ActiveGodownsModel;
import in.calibrage.akshaya.service.APIConstantURL;
import in.calibrage.akshaya.service.ApiService;
import in.calibrage.akshaya.service.ServiceFactory;
import in.calibrage.akshaya.views.Adapter.GodownListAdapter;
import rx.Subscriber;
import rx.Subscription;
import rx.android.schedulers.AndroidSchedulers;
import rx.schedulers.Schedulers;

import static in.calibrage.akshaya.common.CommonUtil.updateResources;

public class Godown_list extends BaseActivity implements GodownListAdapter.OnItemClickListener {
    public static final String TAG = Godown_list.class.getSimpleName();
    private RecyclerView lst_godown_list;
    private LinearLayoutManager linearLayoutManager;
    private GodownListAdapter adapter;
    private Toolbar toolbar;
    private SpotsDialog mdilogue;
    private Context ctx;
    Integer GodownId;
    ImageView home_btn;
    TextView noRecords;
    String  Godown_name,Godown_code,request_code;
    private ActiveGodownsModel.ListResult selectedGodown;
    private Subscription mSubscription;
    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        final int langID = SharedPrefsData.getInstance(this).getIntFromSharedPrefs("lang");
        if (langID == 2)
            updateResources(this, "te");
        else
            updateResources(this, "en-US");
        setContentView(R.layout.activity_godown_list);
        init();
        setviews();
        settoolbar();
    }

    private void settoolbar() {
        toolbar = (Toolbar) findViewById(R.id.toolbar);
        toolbar.setTitle("Select Godown");
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

    private void setviews() {
        Bundle extras = getIntent().getExtras();
        if (extras != null) {
            request_code = extras.getString("godown");
            Log.e("request_code===",request_code);




        }
        home_btn.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View view) {
                Log.d(TAG, "---------- Finish ----------------");
                Intent intent = new Intent(Godown_list.this, HomeActivity.class);
                intent.setFlags(Intent.FLAG_ACTIVITY_NEW_TASK | Intent.FLAG_ACTIVITY_CLEAR_TOP);
                startActivity(intent);
                finish();
            }
        });
        if (isOnline()) {

            getActiveGodowns();
        } else {
            showDialog(Godown_list.this, getResources().getString(R.string.Internet));

        }
    }

    private void init() {
        ctx = this;
        lst_godown_list = findViewById(R.id.lst_godown_list);
        home_btn = (ImageView) findViewById(R.id.home_btn);
        noRecords =(TextView)findViewById(R.id.text) ;
        linearLayoutManager = new LinearLayoutManager(ctx);
        lst_godown_list.setLayoutManager(linearLayoutManager);
        mdilogue = (SpotsDialog) new SpotsDialog.Builder()
                .setContext(this)
                .setTheme(R.style.Custom)
                .build();

    }




    private void getActiveGodowns() {

        int typeid = SharedPrefsData.getInstance(this).getIntFromSharedPrefs("postTypeId");

        String statecode = SharedPrefsData.getInstance(this).getStringFromSharedPrefs("statecode");
        mdilogue.show();
        ApiService service = ServiceFactory.createRetrofitService(this, ApiService.class);
        mSubscription = service.getActiveGodowns(APIConstantURL.GetActiveGodowns + "/" + statecode)
                .subscribeOn(Schedulers.newThread())
                .observeOn(AndroidSchedulers.mainThread()).subscribe(new Subscriber<ActiveGodownsModel>() {
                    @Override
                    public void onCompleted() {
                        mdilogue.cancel();
                    }

                    @Override
                    public void onError(Throwable e) {
                        mdilogue.cancel();
                        Log.d(TAG, "---- analysis ---->GetActiveGodows -->> error -->> :" + e.getLocalizedMessage());
                        showDialog(Godown_list.this, getString(R.string.server_error));
                    }

                    @Override
                    public void onNext(ActiveGodownsModel activeGodownsModel) {
                        mdilogue.cancel();
                        if (activeGodownsModel.getListResult() != null && activeGodownsModel.getListResult().size()!= 0 ) {
                            lst_godown_list.setVisibility(View.VISIBLE);
                            noRecords.setVisibility(View.GONE);
                            Log.d(TAG, "---- analysis ---->GetActiveGodows-->> Responce size-->> :" + activeGodownsModel.getListResult().size());
                            adapter = new GodownListAdapter(activeGodownsModel.getListResult(), ctx, Godown_list.this);
                            lst_godown_list.setAdapter(adapter);

                        }
                        else {
                            lst_godown_list.setVisibility(View.GONE);
                            noRecords.setVisibility(View.VISIBLE);
                        }
                    }
                });

    }

    @Override
    public void onItemClick(ActiveGodownsModel.ListResult item) {

        selectedGodown = item;
        GodownId = selectedGodown.getId();
        Godown_name = selectedGodown.getName();
        Godown_code =selectedGodown.getCode();
        if (request_code.equalsIgnoreCase("fert")){

            Intent i = new Intent(Godown_list.this, FertilizerActivity.class);
        i.putExtra("code_godown",Godown_code);
        i.putExtra("id_godown",GodownId);
        i.putExtra("name_godown",Godown_name);
        startActivity(i);
        overridePendingTransition(R.anim.enter_from_right, R.anim.exit_to_left);
    }
        if (request_code.equalsIgnoreCase("pole")){

            Intent i = new Intent(Godown_list.this, PoleActivity.class);
            i.putExtra("code_godown",Godown_code);
            i.putExtra("id_godown",GodownId);
            i.putExtra("name_godown",Godown_name);
            startActivity(i);
            overridePendingTransition(R.anim.enter_from_right, R.anim.exit_to_left);
        }
    }

}
