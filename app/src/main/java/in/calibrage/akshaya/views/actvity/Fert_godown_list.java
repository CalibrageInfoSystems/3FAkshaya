package in.calibrage.akshaya.views.actvity;

import android.content.Context;
import android.content.Intent;
import android.support.annotation.NonNull;
import android.support.design.widget.BottomSheetBehavior;
import android.support.v7.app.AppCompatActivity;
import android.os.Bundle;
import android.support.v7.widget.LinearLayoutManager;
import android.support.v7.widget.RecyclerView;
import android.support.v7.widget.Toolbar;
import android.util.Log;
import android.view.View;
import android.widget.ArrayAdapter;
import android.widget.Button;
import android.widget.EditText;
import android.widget.ImageView;
import android.widget.Spinner;
import android.widget.TextView;
import android.widget.Toast;

import java.lang.reflect.Field;
import java.util.ArrayList;
import java.util.List;

import dmax.dialog.SpotsDialog;
import in.calibrage.akshaya.R;
import in.calibrage.akshaya.common.BaseActivity;
import in.calibrage.akshaya.common.CommonUtil;
import in.calibrage.akshaya.common.Constants;
import in.calibrage.akshaya.localData.SharedPrefsData;
import in.calibrage.akshaya.models.ActiveGodownsModel;
import in.calibrage.akshaya.models.PaymentsType;
import in.calibrage.akshaya.models.product;
import in.calibrage.akshaya.service.APIConstantURL;
import in.calibrage.akshaya.service.ApiService;
import in.calibrage.akshaya.service.ServiceFactory;
import in.calibrage.akshaya.views.Adapter.GodownListAdapter;
import in.calibrage.akshaya.views.Adapter.producut_Adapter;
import lib.kingja.switchbutton.SwitchMultiButton;
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
    private Button btn_submit,button;
    private RecyclerView lst_godown_list;
    private LinearLayoutManager linearLayoutManager;
    private EditText editText;
    private TextView txt_select_godown, txt_Payment_mode,text_amount,Final_amount,gst_amount;
    private BottomSheetBehavior behavior;

    private Toolbar toolbar;
    private Subscription mSubscription;
    private SpotsDialog mdilogue;
    private GodownListAdapter adapter;

    ArrayList<Integer> selected_quntity_list=new ArrayList<Integer>();
    ArrayList<Integer> selected_ids_lists=new ArrayList<Integer>();
    ArrayList<String> product_names=new ArrayList<String>();
    ArrayList<Integer> product_gst=new ArrayList<Integer>();
    ArrayList<Integer> amount_final=new ArrayList<Integer>();
    ArrayList<Integer> gstvalues=new ArrayList<Integer>();
    String Amount;

ImageView home_btn;

    private Spinner paymentspin;
    List<String> listdata = new ArrayList<>();

    SwitchMultiButton sw_paymentMode;
    private ActiveGodownsModel.ListResult selectedGodown;
    private List<product> product_List = new ArrayList<>();
    private String final_amount,only_amount;
    int mealTotal = 0;
    int Gst_sum, Amount_,include_gst_amount;

    private producut_Adapter mAdapter;
    RecyclerView recycler_view_products;

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
        recycler_view_products=(RecyclerView)findViewById(R.id.products_recy) ;
        RecyclerView.LayoutManager mLayoutManager = new LinearLayoutManager(getApplicationContext());
        recycler_view_products.setLayoutManager(mLayoutManager);
        btn_submit = findViewById(R.id.btn_submit);
        txt_select_godown = findViewById(R.id.txt_select_godown);
        txt_Payment_mode = findViewById(R.id.txt_Payment_mode);
        lst_godown_list = findViewById(R.id.lst_godown_list);
        sw_paymentMode = findViewById(R.id.sw_paymentMode);
        linearLayoutManager = new LinearLayoutManager(ctx);
        lst_godown_list.setLayoutManager(linearLayoutManager);
        mdilogue = (SpotsDialog) new SpotsDialog.Builder()
                .setContext(this)
                .setTheme(R.style.Custom)
                .build();
        txt_select_godown.setText(CommonUtil.getMultiColourString(getString(R.string.select_godown)));
        txt_Payment_mode.setText(CommonUtil.getMultiColourString(getString(R.string.payment_mode)));
        text_amount=(TextView)findViewById(R.id.amount) ;
        Final_amount=(TextView)findViewById(R.id.final_amount_gst) ;
        gst_amount=(TextView)findViewById(R.id.gst_amount) ;
        home_btn = (ImageView) findViewById(R.id.home_btn);


        getActiveGodowns();
        getPaymentMods();
        //sw_paymentMode.setText("one", "two", "three", "four");
    }

    private void setviews() {

//        home_btn.setOnClickListener(new View.OnClickListener() {
//            @Override
//            public void onClick(View view) {
//               /* Intent intent =new Intent(getApplicationContext(),HomeActivity.class);
//                startActivity(intent);*/
//                Intent intent = new Intent(Fert_godown_list.this, HomeActivity.class);
//                intent.setFlags(Intent.FLAG_ACTIVITY_NEW_TASK | Intent.FLAG_ACTIVITY_CLEAR_TOP);
//                startActivity(intent);
//                finish();
//            }
//        });
        Bundle extras = getIntent().getExtras();
        if (extras != null) {
            selected_ids_lists = (ArrayList<Integer>) getIntent().getSerializableExtra("Ids");
            selected_quntity_list = (ArrayList<Integer>) getIntent().getSerializableExtra("quantity");
            product_names=(ArrayList<String>) getIntent().getSerializableExtra("item_names");
            amount_final=(ArrayList<Integer>) getIntent().getSerializableExtra("item_amount");
            product_gst=(ArrayList<Integer>) getIntent().getSerializableExtra("gst_per");
            final_amount = getIntent().getExtras().getString("amount");
            String[] parts = final_amount.split(" "); // escape .
            String part1 = parts[0];
            only_amount = parts[1];
            Log.e("final_amount===", "=1===  " + part1 + " ===rs ===" + only_amount);
            text_amount.setText(":"+only_amount);
            Amount_ = Integer.parseInt(only_amount);

            try {
                for (int i = 0; i < product_names.size(); i++) {
                    String name = product_names.get(i);
                    int gst = product_gst.get(i);
                    // String gst = String.valueOf(gstt);
                    int amount_product = amount_final.get(i);
                    int quantity = selected_quntity_list.get(i);
                    String quan = String.valueOf(quantity);
                    mealTotal = amount_product * quantity;
                    String product_amount = String.valueOf(mealTotal);
                    product a = new product(name, quan, product_amount, gst);

                    product_List.add(a);

                    int percentage = quantity * gst;

                    Log.e("percentage_value===", String.valueOf(percentage));
                    //  int k = (int)(product_amount*(percentage/100.0f));
                    int k = (int) (percentage * amount_product) / 100;

                    gstvalues.add(k);

                    Log.e("percentage_value===", String.valueOf(gstvalues));
                    Gst_sum = CommonUtil.sum(gstvalues);
                    include_gst_amount = Gst_sum + Amount_;
                    Log.e("gst_Sum===", String.valueOf(Gst_sum));

                    gst_amount.setText(":"+String.valueOf(Gst_sum));
                    Final_amount.setText(":"+String.valueOf(include_gst_amount));

                }
                mAdapter = new producut_Adapter(this, product_List);
                recycler_view_products.setAdapter(mAdapter);


            }catch (Exception e){e.printStackTrace();}
        }

        btn_submit.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View view) {
                onSubmit();
            }
        });
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
        mdilogue.show();
        ApiService service = ServiceFactory.createRetrofitService(this, ApiService.class);
        mSubscription = service.getActiveGodowns(APIConstantURL.GetActiveGodowns)
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
                        mdilogue.cancel();
                        for (PaymentsType.ListResult string : paymentsType.getListResult()
                        ) {
                            listdata.add(string.getDesc());

                        }


                        char ch = '"';
                        String finalstring = ch + arrayToString(listdata) + ch;
                        finalstring = finalstring.replace(",", ch + "," + ch);
                        Log.d("Commonutil ", "--- analysis ----->> List to string -->>" + finalstring);
                        //  sw_paymentMode.setText(finalstring);
                        sw_paymentMode.setText("Cash", "Against FFB");
                        Log.d("Commonutil ", "--- analysis ----->> List to string -->>" + finalstring);
                    }
                });
    }

    public void onSubmit() {
        /*
         * validate Fealds
         * */
        if (selectedGodown != null) {
            Intent intent = new Intent(Fert_godown_list.this, HomeActivity.class);
            intent.setFlags(Intent.FLAG_ACTIVITY_NEW_TASK | Intent.FLAG_ACTIVITY_CLEAR_TOP);
            startActivity(intent);
            finish();
        } else {
            showDialog(this, "please Select Godown");
        }
    }

    @Override
    public void onItemClick(ActiveGodownsModel.ListResult item) {
        selectedGodown = item;
    }
}
