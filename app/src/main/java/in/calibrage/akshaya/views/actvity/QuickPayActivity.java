package in.calibrage.akshaya.views.actvity;

import android.app.Activity;
import android.app.Dialog;
import android.app.ProgressDialog;
import android.content.Intent;
import android.content.SharedPreferences;
import android.graphics.drawable.Animatable;
import android.os.Build;
import android.os.Handler;
import android.support.annotation.RequiresApi;
import android.support.v7.app.AppCompatActivity;
import android.os.Bundle;
import android.support.v7.widget.LinearLayoutManager;
import android.support.v7.widget.RecyclerView;
import android.util.Log;
import android.view.View;
import android.view.Window;
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
import in.calibrage.akshaya.models.IsQuickPayBlockDate;
import in.calibrage.akshaya.models.QuickPayModel;
import in.calibrage.akshaya.models.RaiseRequest;
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

import static in.calibrage.akshaya.common.CommonUtil.updateResources;

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
    String w_Code;
    int SPLASH_DISPLAY_DURATION = 500;

    @RequiresApi(api = Build.VERSION_CODES.LOLLIPOP)
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
        Farmer_code = pref.getString("farmerid", "").trim();
    }

    @RequiresApi(api = Build.VERSION_CODES.LOLLIPOP)
    private void setViews() {
        if (isOnline()){
            IsQuickPayBlockDate();}
        else {
            showDialog(QuickPayActivity.this, getResources().getString(R.string.Internet));
        }
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
                CanRaiseRequest();


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
//        if (isOnline()) {
//            getQuckPay();
//            nextButton.setVisibility(View.VISIBLE);
//        }
//        else {
//            showDialog(QuickPayActivity.this, getResources().getString(R.string.Internet));
//            nextButton.setVisibility(View.GONE);
////            nextButton.setBackground(this.getDrawable(R.drawable.button_bg_disable));
////            nextButton.setEnabled(false);
//        }


    }
    private void IsQuickPayBlockDate() {
        mdilogue.show();
        String statecode = SharedPrefsData.getInstance(this).getStringFromSharedPrefs("statecode");
        ApiService service = ServiceFactory.createRetrofitService(this, ApiService.class);
        mSubscription = service.getblockdate(APIConstantURL.IsQuickPayBlockDate+statecode)
                .subscribeOn(Schedulers.newThread())
                .observeOn(AndroidSchedulers.mainThread())
                .subscribe(new Subscriber<IsQuickPayBlockDate>() {
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
                    public void onNext(IsQuickPayBlockDate isQuickPayBlockDate) {


                        if (isQuickPayBlockDate.getIsSuccess()){
                            if(isQuickPayBlockDate.getResult().equals(true)) {
                                if (isOnline()) {
                                    getQuckPay();
                                  //  nextButton.setVisibility(View.VISIBLE);
                                } else {
                                    showDialog(QuickPayActivity.this, getResources().getString(R.string.Internet));
                                    //nextButton.setVisibility(View.GONE);
                                }
                            }

                        }
                        else {
                            // edittext.getText().clear();Quick pay has already been requested, another quick pay request cannot be requested until the 1st one is completed.
                            showDialogf(QuickPayActivity.this, isQuickPayBlockDate.getEndUserMessage());

                        }
                    }
                });
    }

    public void showDialogf(Activity activity, String msg) {
        final Dialog dialog = new Dialog(activity, R.style.DialogSlideAnim);
        dialog.requestWindowFeature(Window.FEATURE_NO_TITLE);
        dialog.setCancelable(false);
        dialog.setContentView(R.layout.dialog);
        final ImageView img = dialog.findViewById(R.id.img_cross);

        TextView text = (TextView) dialog.findViewById(R.id.text_dialog);
        text.setText(msg);
        Button dialogButton = (Button) dialog.findViewById(R.id.btn_dialog);
        dialogButton.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                dialog.dismiss();
                Intent intent = new Intent(getApplicationContext(), HomeActivity.class);
                intent.setFlags(Intent.FLAG_ACTIVITY_NEW_TASK | Intent.FLAG_ACTIVITY_CLEAR_TOP);
                startActivity(intent);
                finish();
            }
        });
        dialog.show();
        new Handler().postDelayed(new Runnable() {
            @Override
            public void run() {
                ((Animatable) img.getDrawable()).start();
            }
        }, 500);
    }

    private void CanRaiseRequest() {
        mdilogue.show();
        ApiService service = ServiceFactory.createRetrofitService(this, ApiService.class);
        mSubscription = service.getRaiseRequest(APIConstantURL.CanRaiseRequest + Farmer_code + "/" + null + "/" + 13)
                .subscribeOn(Schedulers.newThread())
                .observeOn(AndroidSchedulers.mainThread())
                .subscribe(new Subscriber<RaiseRequest>() {
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
                    public void onNext(RaiseRequest raiseRequest) {
                        mdilogue.dismiss();
                        if (raiseRequest.getIsSuccess()){
                            Log.e("test=== ",raiseRequest.getIsSuccess()+"");
                            new Handler().postDelayed(new Runnable() {
                                @Override
                                public void run() {

                                    Intent intent = new Intent(getApplicationContext(), Quickpay_SummaryActivity.class);
                                    intent.putExtra("collection_ids", (Serializable) ids_list);

                                    intent.putExtra("collection_dates", (Serializable) dates_list);
                                    intent.putExtra("collection_weight", (Serializable) weight_list);
                                    intent.putExtra("whsCode",w_Code);
                                    startActivity(intent);

                                    Log.e("ids_list===", String.valueOf(ids_list));
                                    Log.e("dates_list===", String.valueOf(dates_list));
                                    Log.e("weight_list===", String.valueOf(weight_list));
                                    overridePendingTransition(R.anim.enter_from_right, R.anim.exit_to_left);


                                }
                            }, SPLASH_DISPLAY_DURATION);

                        }
                        else {
                           // edittext.getText().clear();Quick pay has already been requested, another quick pay request cannot be requested until the 1st one is completed.
                            showDialog(QuickPayActivity.this,  getResources().getString(R.string.quick_reqc));

                        }
                    }
                });
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


                        if (quickPayModel.getListResult().size()!= 0  ) {
                            nextButton.setVisibility(View.VISIBLE);
                            adapter = new QuickPayDataAdapter(QuickPayActivity.this, quickPayModel.getListResult(), QuickPayActivity.this);
                            recyclerView.setAdapter(adapter);
                            w_Code = quickPayModel.getListResult().get(0).getWhsCode();
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