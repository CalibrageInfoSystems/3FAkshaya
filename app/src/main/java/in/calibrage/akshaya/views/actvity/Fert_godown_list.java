package in.calibrage.akshaya.views.actvity;

import android.content.Context;
import android.content.Intent;
import android.content.SharedPreferences;
import android.os.Build;
import android.os.Bundle;
import android.os.Handler;
import android.support.annotation.RequiresApi;
import android.support.design.widget.BottomSheetBehavior;
import android.support.v7.widget.LinearLayoutManager;
import android.support.v7.widget.RecyclerView;
import android.support.v7.widget.Toolbar;
import android.util.Log;
import android.view.View;
import android.widget.Button;
import android.widget.EditText;
import android.widget.ImageView;
import android.widget.Spinner;
import android.widget.TextView;

import com.google.gson.Gson;
import com.google.gson.JsonObject;

import java.io.IOException;
import java.text.DecimalFormat;
import java.text.SimpleDateFormat;
import java.util.ArrayList;
import java.util.Calendar;
import java.util.List;

import dmax.dialog.SpotsDialog;
import in.calibrage.akshaya.R;
import in.calibrage.akshaya.common.BaseActivity;
import in.calibrage.akshaya.common.CommonUtil;
import in.calibrage.akshaya.common.Constants;
import in.calibrage.akshaya.localData.SharedPrefsData;
import in.calibrage.akshaya.models.ActiveGodownsModel;
import in.calibrage.akshaya.models.FertRequest;
import in.calibrage.akshaya.models.FertResponse;
import in.calibrage.akshaya.models.MSGmodel;
import in.calibrage.akshaya.models.PaymentsType;
import in.calibrage.akshaya.models.SubsidyResponse;
import in.calibrage.akshaya.models.product;
import in.calibrage.akshaya.service.APIConstantURL;
import in.calibrage.akshaya.service.ApiService;
import in.calibrage.akshaya.service.ServiceFactory;
import in.calibrage.akshaya.views.Adapter.GodownListAdapter;
import in.calibrage.akshaya.views.Adapter.producut_Adapter;
import lib.kingja.switchbutton.SwitchMultiButton;
import retrofit2.adapter.rxjava.HttpException;
import rx.Subscriber;
import rx.Subscription;
import rx.android.schedulers.AndroidSchedulers;
import rx.schedulers.Schedulers;

import static in.calibrage.akshaya.common.CommonUtil.arrayToString;

public class Fert_godown_list extends BaseActivity implements GodownListAdapter.OnItemClickListener {
    public static final String TAG = Fert_godown_list.class.getSimpleName();
    /*
     *
     * this is shows godows and payment process also
     * */

    private Context ctx;
    private Button btn_submit, button;
    private RecyclerView lst_godown_list;
    private LinearLayoutManager linearLayoutManager;
    private EditText editText;
    private TextView txt_select_godown, txt_Payment_mode, text_amount, Final_amount, gst_amount, subsidy_amount,paybleamount;
    private BottomSheetBehavior behavior;

    private Toolbar toolbar;
    private Subscription mSubscription;
    private SpotsDialog mdilogue;
    private GodownListAdapter adapter;
      Integer RequestType;
    ArrayList<Integer> selected_quntity_list = new ArrayList<Integer>();
    ArrayList<Integer> selected_ids_lists = new ArrayList<Integer>();
    ArrayList<String> product_names = new ArrayList<String>();
    ArrayList<Integer> product_gst = new ArrayList<Integer>();
    ArrayList<Integer> amount_final = new ArrayList<Integer>();
    ArrayList<Integer> gstvalues = new ArrayList<Integer>();
    ArrayList<String> selects_product_size = new ArrayList<String>();
    String Amount;
    String Farmer_code, formattedDate, IsSuccess;
    ImageView home_btn;
    Integer GodownId;
    private Spinner paymentspin;
    List<String> listdata = new ArrayList<>();

    SwitchMultiButton sw_paymentMode;
    private ActiveGodownsModel.ListResult selectedGodown;
    private ArrayList<product> product_List = new ArrayList<>();
    private String final_amount, only_amount;
    int mealTotal = 0;
    String Gst_sum, Amount_, include_gst_amount;
    Integer Paymode, Statusid;
    private producut_Adapter mAdapter;
    RecyclerView recycler_view_products;
    PaymentsType paymentsTypes;
    double payble_amount;
    double Subsidy_amount,subsidy_amountt;
    int Gst_total;
    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_fert_godown_list);
        init();
        setviews();
        settoolbar();

    }


    private void init() {
        ctx = this;
        recycler_view_products = (RecyclerView) findViewById(R.id.products_recy);
        RecyclerView.LayoutManager mLayoutManager = new LinearLayoutManager(getApplicationContext());
        recycler_view_products.setLayoutManager(mLayoutManager);
        btn_submit = findViewById(R.id.btn_submit);
        txt_select_godown = findViewById(R.id.txt_select_godown);
        txt_Payment_mode = findViewById(R.id.txt_Payment_mode);
        lst_godown_list = findViewById(R.id.lst_godown_list);
        subsidy_amount = findViewById(R.id.subcdamount);
        paybleamount = findViewById(R.id.paybleamount);
        sw_paymentMode = findViewById(R.id.sw_paymentMode);
        linearLayoutManager = new LinearLayoutManager(ctx);
        lst_godown_list.setLayoutManager(linearLayoutManager);
        mdilogue = (SpotsDialog) new SpotsDialog.Builder()
                .setContext(this)
                .setTheme(R.style.Custom)
                .build();
//        txt_select_godown.setText(CommonUtil.getMultiColourString(getString(R.string.select_godown)));
//        txt_Payment_mode.setText(CommonUtil.getMultiColourString(getString(R.string.payment_mode)));
        text_amount = (TextView) findViewById(R.id.amount);
        Final_amount = (TextView) findViewById(R.id.final_amount_gst);
        gst_amount = (TextView) findViewById(R.id.gst_amount);
        home_btn = (ImageView) findViewById(R.id.home_btn);
        home_btn.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View view) {
                Log.d(TAG,"---------- Finish ----------------");
                Intent intent = new Intent(Fert_godown_list.this, HomeActivity.class);
                intent.setFlags(Intent.FLAG_ACTIVITY_NEW_TASK | Intent.FLAG_ACTIVITY_CLEAR_TOP);
                startActivity(intent);
                finish();
            }
        });
      /* ImageView backImg = (ImageView) findViewById(R.id.back);
       backImg.setOnClickListener(new View.OnClickListener() {
           @Override
           public void onClick(View view) {
               finish();
           }
       });*/
       /*ImageView homeImg = (ImageView) findViewById(R.id.home_btn);
       homeImg.setOnClickListener(new View.OnClickListener() {
           @Override
           public void onClick(View view) {
               Intent intent = new Intent(Fert_godown_list.this, HomeActivity.class);
               intent.setFlags(Intent.FLAG_ACTIVITY_NEW_TASK | Intent.FLAG_ACTIVITY_CLEAR_TOP);
               startActivity(intent);
               finish();
           }
       });*/
        SharedPreferences pref = getSharedPreferences("FARMER", MODE_PRIVATE);
        Farmer_code = pref.getString("farmerid", "");
        getPaymentMods();
        getFertilizerSubsidies();
        getActiveGodowns();


        sw_paymentMode.setOnSwitchListener(new SwitchMultiButton.OnSwitchListener() {
            @Override
            public void onSwitch(int position, String tabText) {
                Paymode = paymentsTypes.getListResult().get(position).getTypeCdId();
                String name = paymentsTypes.getListResult().get(position).getDesc();
                if (name.contains("Cash")) {
                    Statusid = 16;
                }
                if (name.contains("Against FFB")) {
                    Statusid = 15;
                }

                Log.d(TAG, "------------ analysis -------- >> Mahesh :" + Paymode);
                Log.d(TAG, "------------ analysis -------- >> Mahesh :" + name);

            }
        });

    }

    private void getPaymentMods() {
        mdilogue.show();
        ApiService service = ServiceFactory.createRetrofitService(this, ApiService.class);
        mSubscription = service.getpaymentModes(APIConstantURL.GetPaymentsTypeByFarmerCode + SharedPrefsData.getInstance(this).getStringFromSharedPrefs(Constants.USER_ID))
                .subscribeOn(Schedulers.newThread())
                .observeOn(AndroidSchedulers.mainThread()).subscribe(new Subscriber<PaymentsType>() {
                    @Override
                    public void onCompleted() {
                        mdilogue.cancel();
                    }

                    @Override
                    public void onError(Throwable e) {
                        mdilogue.cancel();
                    }

                    @Override
                    public void onNext(PaymentsType paymentsType) {
                        paymentsTypes = paymentsType;
                        mdilogue.cancel();
                        for (PaymentsType.ListResult string : paymentsType.getListResult()
                        ) {
                            listdata.add(string.getDesc());

                        }
                        
                        char ch = '"';
                        String finalstring = ch + arrayToString(listdata) + ch;
                        finalstring = finalstring.replace(",", ch + "," + ch);

                        String[] stockArr = new String[listdata.size()];
                        stockArr = listdata.toArray(stockArr);
                        // String[] a = listdata.toArray(new String[0]);
                        // Log.d("Commonutil ", "--- analysis ----->> List to string -->>" + a);
                        sw_paymentMode.setText(stockArr);
                        sw_paymentMode.setSelectedTab(0);
                        Log.d("Commonutil ", "--- analysis ----->> List to string -->>" + finalstring);

                    }
                });
    }

    private void setviews() {
        Calendar c = Calendar.getInstance();
        System.out.println("Current time => " + c.getTime());

        SimpleDateFormat df = new SimpleDateFormat("yyyy-MM-dd HH:mm:ss");
        formattedDate = df.format(c.getTime());
        for (int i = 0; i < SharedPrefsData.getCartData(this).size(); i++) {
            int gst =SharedPrefsData.getCartData(this).get(i).getGst();

            Double amount_product = SharedPrefsData.getCartData(this).get(i).getAmount();
            int quantity = SharedPrefsData.getCartData(this).get(i).getQuandity();
            String quan = String.valueOf(quantity);
            //  mealTotal = amount_product * quantity;
            String product_amount = String.valueOf(mealTotal);

            int percentage = quantity * gst;

            Log.e("percentage_value===", String.valueOf(percentage));
            //  int k = (int)(product_amount*(percentage/100.0f));
            int k = (int) (percentage * amount_product) / 100;

            gstvalues.add(k);

            Log.e("percentage_value===", String.valueOf(gstvalues));
            Gst_total = CommonUtil.sum(gstvalues);
            // include_gst_amount = Gst_sum + Amount_;
            Log.e("gst_Sum===", String.valueOf(Gst_total));

            gst_amount.setText(Gst_total+"");
            //  Final_amount.setText("" + String.valueOf(include_gst_amount));

        }
        Bundle extras = getIntent().getExtras();
        if (extras != null) {
            include_gst_amount = extras.getString("Total_amount");
            //  text_amount.setText("" + only_amount);
            // gst_amount.setText("" + String.valueOf(Gst_sum));

        }
        Final_amount.setText(include_gst_amount+"");
        DecimalFormat dff = new DecimalFormat("####0.00");
        DecimalFormat form = new DecimalFormat("0.00");

       // Final_amount.setText(""+form.format( include_gst_amount));

//        double products_amount =Double.parseDouble(include_gst_amount)- Double.parseDouble(String.valueOf(Gst_total));
//        Log.e("products_amount===", String.valueOf(products_amount));
//        text_amount.setText("" + products_amount);

        double products_amount = Double.parseDouble(include_gst_amount) - Double.parseDouble(String.valueOf(Gst_total));
        Log.e("products_amount===", String.valueOf(products_amount));
        text_amount.setText("" +dff.format(products_amount));


        btn_submit.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View view) {
                onSubmit();


            }
        });
        mAdapter = new producut_Adapter(this, SharedPrefsData.getCartData(this));
        recycler_view_products.setAdapter(mAdapter);
//        behavior.setBottomSheetCallback(new BottomSheetBehavior.BottomSheetCallback() {
//            @Override
//            public void onStateChanged(@NonNull View bottomSheet, int newState) {
//                // React to state change
//            }
//
//            @Override
//            public void onSlide(@NonNull View bottomSheet, float slideOffset) {
//                // React to dragging events
//            }
//        });

        paybleamount.setText(payble_amount+"");
    }

    private void FertilizerRequest() {


        mdilogue.show();
        JsonObject object = fertReuestobject();
        ApiService service = ServiceFactory.createRetrofitService(this, ApiService.class);
        mSubscription = service.postfert(object)
                .subscribeOn(Schedulers.newThread())
                .observeOn(AndroidSchedulers.mainThread())
                .subscribe(new Subscriber<FertResponse>() {
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

                    @RequiresApi(api = Build.VERSION_CODES.M)
                    @Override
                    public void onNext(FertResponse fertResponse) {

                        mdilogue.cancel();

                        if (fertResponse.getIsSuccess()) {


                           // Toast.makeText(getApplicationContext(), "sucess", Toast.LENGTH_SHORT).show();

                            new Handler().postDelayed(new Runnable() {
                                @RequiresApi(api = Build.VERSION_CODES.M)
                                @Override
                                public void run() {

                                    List<MSGmodel> displayList = new ArrayList<>();

//                                    displayList.add(new MSGmodel(getString(R.string.select_labour_type), "Rojs"));
//                                    displayList.add(new MSGmodel(getResources().getString(R.string.labour_duration), "fsdmfdl"));

//
//                                    Log.d(TAG, "------ analysis ------ >> get selected_name in String(): " + selected_name);

                                    showSuccessDialog(displayList, getString(R.string.success_fertilizer));
                                }
                            }, 300);
                        } else {
                            showDialog(Fert_godown_list.this, fertResponse.getEndUserMessage());
                        }


                    }


                });


    }


    private JsonObject fertReuestobject() {
        FertRequest requestModel = new FertRequest();

        requestModel.setId(0);
        requestModel.setRequestTypeId(12);
        requestModel.setFarmerCode(Farmer_code);
        requestModel.setPlotCode(null);
        requestModel.setRequestCreatedDate(formattedDate);

        requestModel.setIsFarmerRequest(true);
        requestModel.setCreatedByUserId(null);
        requestModel.setCreatedDate(formattedDate);
        requestModel.setUpdatedByUserId(null);
        requestModel.setUpdatedDate(formattedDate);
        requestModel.setGodownId(GodownId);
        requestModel.setPaymentModeType(Paymode);
        requestModel.setFileName(null);
        requestModel.setFileExtension(null);
        requestModel.setFileLocation(null);

        requestModel.setTotalCost(Double.parseDouble(SharedPrefsData.getInstance(ctx).getStringFromSharedPrefs("amount")));
        requestModel.setSubcidyAmount(Subsidy_amount);
        requestModel.setPaybleAmount(payble_amount);
        requestModel.setComments(null);
        requestModel.setCropMaintainceDate(formattedDate);
        requestModel.setIssueTypeId(null);

        List<FertRequest.RequestProductDetail> req_products = new ArrayList<>();

        for (int i = 0; i < SharedPrefsData.getCartData(this).size(); i++) {


            FertRequest.RequestProductDetail products = new FertRequest.RequestProductDetail();
            products.setBagCost( Double.parseDouble(SharedPrefsData.getCartData(this).get(i).getWithGSTamount()));
            products.setGstPersentage(SharedPrefsData.getCartData(this).get(i).getGst().doubleValue());
            products.setProductId(SharedPrefsData.getCartData(this).get(i).getProductID());
            products.setQuantity(SharedPrefsData.getCartData(this).get(i).getQuandity());
            products.setSize(String.valueOf(SharedPrefsData.getCartData(this).get(i).getSize()));

            req_products.add(products);
        }


        requestModel.setRequestProductDetails(req_products);


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

                    }

                    @Override
                    public void onNext(ActiveGodownsModel activeGodownsModel) {
                        mdilogue.cancel();
                        Log.d(TAG, "---- analysis ---->GetActiveGodows-->> Responce size-->> :" + activeGodownsModel.getListResult().size());
                        adapter = new GodownListAdapter(activeGodownsModel.getListResult(), ctx, Fert_godown_list.this);
                        lst_godown_list.setAdapter(adapter);


                    }
                });

    }

    private void getFertilizerSubsidies() {
        mdilogue.show();
        ApiService service = ServiceFactory.createRetrofitService(this, ApiService.class);
        mSubscription = service.getsubsidy(APIConstantURL.Getfarmersubsidy + SharedPrefsData.getInstance(this).getStringFromSharedPrefs(Constants.USER_ID))
                .subscribeOn(Schedulers.newThread())
                .observeOn(AndroidSchedulers.mainThread()).subscribe(new Subscriber<SubsidyResponse>() {
                    @Override
                    public void onCompleted() {
                        mdilogue.cancel();
                    }

                    @Override
                    public void onError(Throwable e) {
                        mdilogue.cancel();
                    }

                    @Override
                    public void onNext(SubsidyResponse subsidyResponse) {
                        mdilogue.cancel();
                        if (subsidyResponse.getIsSuccess()) {
                            subsidy_amount.setText(subsidyResponse.getResult().getRemainingAmount().toString());
                             subsidy_amountt = subsidyResponse.getResult().getRemainingAmount();


                            if (subsidy_amountt > 0) {
                                if ( Double.parseDouble(include_gst_amount) < subsidy_amountt) {
                                    Double remaining_subsidy_amountt = subsidy_amountt -  Double.parseDouble(include_gst_amount);
                                    /*
                                     * nothing to pay
                                     * */
                                    payble_amount =0.0;

                                    Subsidy_amount=Double.parseDouble(include_gst_amount);
                                } else if (subsidy_amountt < Double.parseDouble(include_gst_amount)) {
                                    Double remaining_Amoubt = Double.parseDouble(include_gst_amount) - subsidy_amountt;
                                    /*
                                     * payble amount
                                     * */
                                    payble_amount =remaining_Amoubt;
                                    Subsidy_amount=Double.parseDouble(include_gst_amount);
                                } else if (Double.parseDouble(include_gst_amount) == subsidy_amountt) {
                                    Double remaining_Amoubt = Double.parseDouble(include_gst_amount) - subsidy_amountt;
                                    /*
                                     * nothing to pay
                                     * */
                                    payble_amount =0.0;
                                }
                            }

                        }
                    }

                });
    }

    public void onSubmit() {
        /*
         * validate Fealds
         * */
        if (selectedGodown != null) {
            FertilizerRequest();
        } else {
            showDialog(this, getString(R.string.godown_valid));
        }
    }

    @Override
    public void onItemClick(ActiveGodownsModel.ListResult item) {

        selectedGodown = item;
        GodownId = selectedGodown.getId();
        // Log.e("selectedGodown===",selectedGodown.getId().toString());
    }
}
