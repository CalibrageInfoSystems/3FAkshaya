package in.calibrage.akshaya.views.fragments;


import android.app.ProgressDialog;
import android.content.Context;
import android.content.Intent;
import android.os.Bundle;
import android.support.v4.app.Fragment;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.GridView;

import java.util.List;

import in.calibrage.akshaya.R;
import in.calibrage.akshaya.common.BaseFragment;
import in.calibrage.akshaya.models.LerningsModel;
import in.calibrage.akshaya.views.Adapter.KnowledgeZoneBaseAdapter;
import in.calibrage.akshaya.views.actvity.CollectionsActivity;
import in.calibrage.akshaya.views.actvity.PaymentActivity;
import in.calibrage.akshaya.views.actvity.RecommendationActivity;
import rx.Subscription;

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
    private GridView gridView;
    private KnowledgeZoneBaseAdapter knowledgeZoneBaseAdapter;

    public HomeFragment() {
        // Required empty public constructor
    }


    @Override
    public View onCreateView(LayoutInflater inflater, ViewGroup container,
                             Bundle savedInstanceState) {
        // Inflate the layout for this fragment
        View v = inflater.inflate(R.layout.fragment_home, container, false);
        init();
        dialog = new ProgressDialog(getActivity());
        gridView = (GridView) v.findViewById(R.id.gridview);
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
       // getSpinnerPermission();
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
//        mSubscription = service.getlernings(APIConstantURL.LookUpCategory)
//                .subscribeOn(Schedulers.newThread())
//                .observeOn(AndroidSchedulers.mainThread())
//                .subscribe(new Subscriber<LerningsModel>() {
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
//                    public void onNext(LerningsModel getLookUpModel) {
//                        if (dialog.isShowing()) {
//                            dialog.dismiss();
//                        }
//                        Log.d(TAG, "onNext: " + getLookUpModel);
//                        KnowledgeZoneBaseAdapter knowledgeZoneBaseAdapter = new KnowledgeZoneBaseAdapter(getActivity(), getLookUpModel);
//                        gridView.setAdapter(knowledgeZoneBaseAdapter);
//
//                    }
//                });
//
//
//    }


}




