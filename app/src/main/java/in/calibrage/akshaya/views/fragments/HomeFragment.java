package in.calibrage.akshaya.views.fragments;


import android.app.ProgressDialog;
import android.content.Context;
import android.content.Intent;
import android.os.Bundle;
import android.support.v4.app.Fragment;
import android.support.v7.widget.DefaultItemAnimator;
import android.support.v7.widget.GridLayoutManager;
import android.support.v7.widget.RecyclerView;
import android.util.Log;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.GridView;

import java.io.IOException;
import java.util.List;

import in.calibrage.akshaya.R;
import in.calibrage.akshaya.common.BaseFragment;
import in.calibrage.akshaya.localData.SharedPrefsData;
import in.calibrage.akshaya.models.FarmerOtpResponceModel;
import in.calibrage.akshaya.models.LerningsModel;
import in.calibrage.akshaya.service.APIConstantURL;
import in.calibrage.akshaya.service.ApiService;
import in.calibrage.akshaya.service.ServiceFactory;
import in.calibrage.akshaya.views.Adapter.KnowledgeZoneBaseAdapter;
import in.calibrage.akshaya.views.actvity.CollectionsActivity;
import in.calibrage.akshaya.views.actvity.PaymentActivity;
import in.calibrage.akshaya.views.actvity.RecommendationActivity;
import retrofit2.adapter.rxjava.HttpException;
import rx.Subscriber;
import rx.Subscription;
import rx.android.schedulers.AndroidSchedulers;
import rx.schedulers.Schedulers;

/**
 * A simple {@link Fragment} subclass.
 */
public class HomeFragment extends BaseFragment {
    public static String TAG = "HomeFragment";
    private ProgressDialog dialog;
    private Context mContext;
    private Subscription mSubscription;
    private List<in.calibrage.akshaya.models.LerningsModel> getCategoryList;
    private Object LerningsModel;
 private RecyclerView leaning_recycle;
    private KnowledgeZoneBaseAdapter knowledgeZoneBaseAdapter;

   private FarmerOtpResponceModel catagoriesList;
    public HomeFragment() {
        // Required empty public constructor
    }



    @Override
    public View onCreateView(LayoutInflater inflater, ViewGroup container,
                             Bundle savedInstanceState) {
        // Inflate the layout for this fragment
        View v = inflater.inflate(R.layout.fragment_home, container, false);
        catagoriesList = SharedPrefsData.getCatagories(getContext());
        init();
        dialog = new ProgressDialog(getActivity());
        leaning_recycle = (RecyclerView) v.findViewById(R.id.learning_list);
        RecyclerView.LayoutManager mLayoutManager = new GridLayoutManager(getContext(), 3);
        leaning_recycle.setLayoutManager(mLayoutManager);
        leaning_recycle.setItemAnimator(new DefaultItemAnimator());
        knowledgeZoneBaseAdapter = new KnowledgeZoneBaseAdapter(mContext,catagoriesList);
        leaning_recycle.setAdapter(knowledgeZoneBaseAdapter);

        v.findViewById(R.id.collections_button).setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                Intent intent =new Intent(getContext(), CollectionsActivity.class);
                startActivity(intent);
            }
        });


        v.findViewById(R.id.recommendations_button).setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                Intent intent =new Intent(getContext(), RecommendationActivity.class);
                startActivity(intent);
            }
        });
        v.findViewById(R.id.payments_button).setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                Intent intent =new Intent(getContext(), PaymentActivity.class);
                startActivity(intent);
            }
        });
       //getSpinnerPermission();
        return v;
    }

    private void init() {
        mContext = getContext();
    }

//    private void getSpinnerPermission() {
//
//
//        dialog.setMessage("Loading, please wait....");
//        dialog.show();
//        dialog.setCanceledOnTouchOutside(false);
//
//        ApiService service = ServiceFactory.createRetrofitService(mContext, ApiService.class);
//        mSubscription = service.getCropmaintaindetails(APIConstantURL.crop_maintance)
//                .subscribeOn(Schedulers.newThread())
//                .observeOn(AndroidSchedulers.mainThread())
//                .subscribe(new Subscriber<CropResponseModel>() {
//                    @Override
//                    public void onCompleted() {
//                        if (dialog.isShowing()) {
//                            dialog.dismiss();
//                        }
//                    }
//
//                    @Override
//                    public void onError(Throwable e) {
//                        if (e instanceof HttpException) {
//                            ((HttpException) e).code();
//                            ((HttpException) e).message();
//                            ((HttpException) e).response().errorBody();
//                            try {
//                                ((HttpException) e).response().errorBody().string();
//                            } catch (IOException e1) {
//                                e1.printStackTrace();
//                            }
//                            e.printStackTrace();
//                        }
//                    }
//
//                    @Override
//                    public void onNext(FarmerOtpResponceModel farmerOtpResponceModel) {
//
//                        if (dialog.isShowing()) {
//                            dialog.dismiss();
//                        }
//                        Log.d(TAG, "onNext: " + farmerOtpResponceModel);
//                        KnowledgeZoneBaseAdapter knowledgeZoneBaseAdapter = new KnowledgeZoneBaseAdapter(getContext(), farmerOtpResponceModel.getResult().getCategoriesDetails());
//                        gridView.setAdapter(knowledgeZoneBaseAdapter);
//
//                    }
//                });
//
//
//    }


}




