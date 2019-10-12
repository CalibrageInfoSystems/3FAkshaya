package in.calibrage.akshaya.views.actvity;


import android.content.Intent;
import android.os.Bundle;
import android.support.v7.widget.DefaultItemAnimator;
import android.support.v7.widget.LinearLayoutManager;
import android.support.v7.widget.RecyclerView;
import android.util.Log;
import android.view.View;
import android.widget.ImageView;

import java.io.IOException;
import java.util.ArrayList;
import java.util.List;

import dmax.dialog.SpotsDialog;
import in.calibrage.akshaya.R;
import in.calibrage.akshaya.common.BaseActivity;
import in.calibrage.akshaya.models.Product_new;
import in.calibrage.akshaya.models.Resproduct;
import in.calibrage.akshaya.service.APIConstantURL;
import in.calibrage.akshaya.service.ApiService;
import in.calibrage.akshaya.service.ServiceFactory;
import in.calibrage.akshaya.views.Adapter.Req_producut_Adapter;
import in.calibrage.akshaya.views.Adapter.producut_Adapter;
import retrofit2.adapter.rxjava.HttpException;
import rx.Subscriber;
import rx.Subscription;
import rx.android.schedulers.AndroidSchedulers;
import rx.schedulers.Schedulers;


public class product_list extends BaseActivity {
    String id_holder;
    private SpotsDialog mdilogue;
    RecyclerView recycler_view_products;
    private producut_Adapter mAdapter;
    //LinearLayout noRecords;
    private Subscription mSubscription;
    private List<Product_new> product_List = new ArrayList<>();

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_product_list);
        mdilogue = (SpotsDialog) new SpotsDialog.Builder()
                .setContext(this)
                .setTheme(R.style.Custom)
                .build();
        ImageView backImg = (ImageView) findViewById(R.id.back);
        backImg.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View view) {
                finish();
            }
        });
        ImageView home_btn = (ImageView) findViewById(R.id.home_btn);
        home_btn.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View view) {
                Intent intent = new Intent(getApplicationContext(), HomeActivity.class);
                intent.setFlags(Intent.FLAG_ACTIVITY_NEW_TASK | Intent.FLAG_ACTIVITY_CLEAR_TOP);
                startActivity(intent);
                finish();
            }
        });
        id_holder = getIntent().getStringExtra("Name");
        Log.e("id_holder===", id_holder);
        recycler_view_products = (RecyclerView) findViewById(R.id.products_recy);
        RecyclerView.LayoutManager mLayoutManager = new LinearLayoutManager(getApplicationContext());
        recycler_view_products.setLayoutManager(mLayoutManager);
        recycler_view_products.setItemAnimator(new DefaultItemAnimator());
        if (isOnline())
            GetProductDetailsByRequestCode();
        else {
            showDialog(product_list.this, getResources().getString(R.string.Internet));
        }
    }
    private void GetProductDetailsByRequestCode() {
        mdilogue.show();
        ApiService service = ServiceFactory.createRetrofitService(this, ApiService.class);
        mSubscription = service.getLoan(APIConstantURL.GetProductDetailsByRequestCode + id_holder)
                .subscribeOn(Schedulers.newThread())
                .observeOn(AndroidSchedulers.mainThread())
                .subscribe(new Subscriber<Resproduct>() {
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
                        showDialog(product_list.this, getString(R.string.server_error));
                    }

                    @Override
                    public void onNext(Resproduct resproduct) {
                        if (resproduct.getListResult() != null) {
                            Log.e("data", "have");
                            Req_producut_Adapter adapter = new Req_producut_Adapter(product_list.this, resproduct.getListResult());
                            recycler_view_products.setAdapter(adapter);
                        } else {
                            Log.e("data", "No==have");
                        }
                    }


                });
    }


}
