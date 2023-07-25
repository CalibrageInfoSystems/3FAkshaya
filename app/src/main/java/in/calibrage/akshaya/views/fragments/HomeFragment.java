package in.calibrage.akshaya.views.fragments;


import android.app.ProgressDialog;
import android.content.BroadcastReceiver;
import android.content.Context;
import android.content.Intent;
import android.content.SharedPreferences;
import android.graphics.Color;
import android.os.Bundle;
import android.support.v4.app.Fragment;
import android.support.v7.widget.DefaultItemAnimator;
import android.support.v7.widget.DividerItemDecoration;
import android.support.v7.widget.GridLayoutManager;
import android.support.v7.widget.LinearLayoutManager;
import android.support.v7.widget.RecyclerView;
import android.util.Log;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.ImageView;
import android.widget.TextView;
import android.widget.Toast;

import com.smarteist.autoimageslider.IndicatorAnimations;
import com.smarteist.autoimageslider.SliderAnimations;
import com.smarteist.autoimageslider.SliderView;

import java.io.IOException;
import java.util.HashMap;
import java.util.List;

import dmax.dialog.SpotsDialog;
import in.calibrage.akshaya.R;
import in.calibrage.akshaya.common.BaseFragment;
import in.calibrage.akshaya.localData.SharedPrefsData;
import in.calibrage.akshaya.models.BannerresponseModel;
import in.calibrage.akshaya.models.FarmerOtpResponceModel;
import in.calibrage.akshaya.models.GetActiveEncyclopediaCategoryDetails;
import in.calibrage.akshaya.models.GetServicesByStateCode;
import in.calibrage.akshaya.models.IsActiveFarmer;
import in.calibrage.akshaya.models.LerningsModel;
import in.calibrage.akshaya.service.APIConstantURL;
import in.calibrage.akshaya.service.ApiService;
import in.calibrage.akshaya.service.ServiceFactory;
import in.calibrage.akshaya.views.Adapter.KnowledgeZoneBaseAdapter;
import in.calibrage.akshaya.views.Adapter.SlideAdapter;
import in.calibrage.akshaya.views.actvity.CollectionsActivity;
import in.calibrage.akshaya.views.actvity.FertilizerActivity;
import in.calibrage.akshaya.views.actvity.Godown_list;
import in.calibrage.akshaya.views.actvity.LabourRecommendationsActivity;
import in.calibrage.akshaya.views.actvity.LoanActivity;
import in.calibrage.akshaya.views.actvity.PaymentActivity;
import in.calibrage.akshaya.views.actvity.PoleActivity;
import in.calibrage.akshaya.views.actvity.QuickPayActivity;
import in.calibrage.akshaya.views.actvity.RecommendationActivity;
import in.calibrage.akshaya.views.actvity.RequestVisitActivity;
import retrofit2.adapter.rxjava.HttpException;
import rx.Subscriber;
import rx.Subscription;
import rx.android.schedulers.AndroidSchedulers;
import rx.schedulers.Schedulers;

import static android.content.Context.MODE_PRIVATE;
import static in.calibrage.akshaya.common.CommonUtil.updateResources;

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
    private RecyclerView leaning_recycle,service_list;
    private KnowledgeZoneBaseAdapter knowledgeZoneBaseAdapter;
    private  ServiceAdapter serviceadpter;
    private TextView txt_banner;
    SliderView sliderView;
    private SpotsDialog mdilogue;
    String Farmer_code;
    View v;
    TextView noRecords;
    boolean isactive ;
    ImageView defalt_iimage;
    public HomeFragment() {
        // Required empty public constructor
    }


    @Override
    public void onCreate(Bundle savedInstanceState) {
        final int langID = SharedPrefsData.getInstance(getContext()).getIntFromSharedPrefs("lang");
        if (langID == 2)
            updateResources(getContext(), "te");
        else if (langID == 3)
            updateResources(getContext(), "kan");
        else
            updateResources(getContext(), "en-US");
        super.onCreate(savedInstanceState);



    }
    @Override
    public View onCreateView(LayoutInflater inflater, ViewGroup container,
                             Bundle savedInstanceState) {
        // Inflate the layout for this fragment

        v = inflater.inflate(R.layout.fragment_home, container, false);



//            catagoriesList = SharedPrefsData.getCatagories(getContext());
//
//            Log.e("catagoriesList===",catagoriesList+"");


        init();

        dialog = new ProgressDialog(getActivity());
        leaning_recycle = (RecyclerView) v.findViewById(R.id.learning_list);
        service_list =(RecyclerView) v.findViewById(R.id.service_list);
        // img_banner =  v.findViewById(R.id.img_banner);
        noRecords = (TextView) v.findViewById(R.id.no_data);
        txt_banner = v.findViewById(R.id.txt_banner);
        txt_banner.setSelected(true);
        defalt_iimage = v.findViewById(R.id.defalt_iimage);
        RecyclerView.LayoutManager mLayoutManager = new GridLayoutManager(getContext(), 3);
        leaning_recycle.setLayoutManager(mLayoutManager);
        leaning_recycle.setItemAnimator(new DefaultItemAnimator());
        // adding inbuilt divider line service_list =(RecyclerView) v.findViewById(R.id.service_list)
        leaning_recycle.addItemDecoration(new DividerItemDecoration(getContext(), LinearLayoutManager.VERTICAL));
        leaning_recycle.addItemDecoration(new MyDividerItemDecoration(getContext(), LinearLayoutManager.HORIZONTAL, 30));
        RecyclerView.LayoutManager mLayoutManager1 = new GridLayoutManager(getContext(), 3);
        service_list.setLayoutManager(mLayoutManager1);
        service_list.setItemAnimator(new DefaultItemAnimator());
        // adding inbuilt divider line service_list =(RecyclerView) v.findViewById(R.id.service_list)
        service_list.addItemDecoration(new DividerItemDecoration(getContext(), LinearLayoutManager.VERTICAL));
        service_list.addItemDecoration(new MyDividerItemDecoration(getContext(), LinearLayoutManager.HORIZONTAL, 30));




        v.findViewById(R.id.collections_button).setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                Intent intent = new Intent(getContext(), CollectionsActivity.class);
                startActivity(intent);
            }
        });


        v.findViewById(R.id.recommendations_button).setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                Intent intent = new Intent(getContext(), RecommendationActivity.class);
                startActivity(intent);
            }
        });
        v.findViewById(R.id.payments_button).setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                Intent intent = new Intent(getContext(), PaymentActivity.class);
                startActivity(intent);
            }
        });

//        v.findViewById(R.id.labour_button).setOnClickListener(new View.OnClickListener() {
//            @Override
//            public void onClick(View v) {
//
//                if (isactive = true) {
//                    Log.e("isactive====2",isactive+"");
//                    Intent intent = new Intent(getContext(), LabourRecommendationsActivity.class);
//                    startActivity(intent);
//                } else {
//                    Log.e("isactive====3",isactive+"");
//                    showDialog(getActivity(), getResources().getString(R.string.inactive));
//
//                }
//            }
//        });
//
//        v.findViewById(R.id.pole_button).setOnClickListener(new View.OnClickListener() {
//            @Override
//            public void onClick(View v) {
//                if (isactive = true){
//                    Intent intent = new Intent(getContext(), Godown_list.class);
//                    intent.putExtra("godown", "pole");
//                    startActivity(intent);
//                } else {
//
//                    showDialog(getActivity(), getResources().getString(R.string.inactive));
//
//
//                }
//            }
//        });
//
//
//            v.findViewById(R.id.fertilizer_button).setOnClickListener(new View.OnClickListener() {
//                @Override
//                public void onClick(View v) {
//                    if (isactive = true){
//                        Intent intent = new Intent(getContext(), Godown_list.class);
//                        intent.putExtra("godown", "fert");
//                        startActivity(intent);
//                    } else {
//
//                        showDialog(getActivity(), getResources().getString(R.string.inactive));
//
//                    }
//                }
//            });
//
//        v.findViewById(R.id.quickPay_button).setOnClickListener(new View.OnClickListener() {
//            @Override
//            public void onClick(View v) {
//                if (isactive = true){
//                    Intent intent = new Intent(getContext(), QuickPayActivity.class);
//                    startActivity(intent);
//                } else {
//
//                    showDialog(getActivity(), getResources().getString(R.string.inactive));
//
//                }
//            }
//        });
//
//        v.findViewById(R.id.visit_button).setOnClickListener(new View.OnClickListener() {
//            @Override
//            public void onClick(View v) {
//                if (isactive = true){
//                    Intent intent = new Intent(getContext(), RequestVisitActivity.class);
//                    startActivity(intent);
//                } else {
//
//                    showDialog(getActivity(), getResources().getString(R.string.inactive));
//
//
//                }
//            }
//        });
//        v.findViewById(R.id.loan_button).setOnClickListener(new View.OnClickListener() {
//            @Override
//            public void onClick(View v) {
//                if (isactive = true){
//                    Intent intent = new Intent(getContext(), LoanActivity.class);
//                    startActivity(intent);
//                } else {
//
//                    showDialog(getActivity(), getResources().getString(R.string.inactive));
//
//
//                }
//            }
//
//        });



        if (isOnline(getContext())) {
            GetBannerByStateCode();
            GetActiveEncyclopediaCategoryDetails();
            GetServicesByStateCode();
        }
        else {
            showDialog(getActivity(), getResources().getString(R.string.Internet));

        }

        //Picasso.with(getContext()).load(catagoriesList.getResult().getBannerDetails().get(0).getImageURL()).into(img_banner);
        // txt_banner.setText(catagoriesList.getResult().getBannerDetails().get(0).getDescription() + "                    " + catagoriesList.getResult().getBannerDetails().get(0).getDescription() + "                    " + catagoriesList.getResult().getBannerDetails().get(0).getDescription());
        sliderView = v.findViewById(R.id.imageSliderr);
//        final SlideAdapter adapter = new SlideAdapter(getContext());
//        adapter.setCount(3);
//
//        sliderView.setSliderAdapter(adapter);
        sliderView.setIndicatorAnimation(IndicatorAnimations.SCALE_DOWN); //set indicator animation by using SliderLayout.IndicatorAnimations. :WORM or THIN_WORM or COLOR or DROP or FILL or NONE or SCALE or SCALE_DOWN or SLIDE and SWAP!!
        sliderView.setSliderTransformAnimation(SliderAnimations.POPTRANSFORMATION);
        sliderView.setAutoCycleDirection(SliderView.FOCUS_FORWARD);
        sliderView.setIndicatorSelectedColor(Color.WHITE);
        sliderView.setIndicatorUnselectedColor(Color.GRAY);
        sliderView.startAutoCycle();
        return v;
    }
//Get All Services  base on login Farmer State Code
    private void GetServicesByStateCode() {
        String statecode = SharedPrefsData.getInstance(getContext()).getStringFromSharedPrefs("statecode");
        Log.e("state===",statecode);
        ApiService service = ServiceFactory.createRetrofitService(mContext, ApiService.class);
        mSubscription = service.getservices(APIConstantURL.GetServicesByStateCode+statecode)
                .subscribeOn(Schedulers.newThread())
                .observeOn(AndroidSchedulers.mainThread())
                .subscribe(new Subscriber<GetServicesByStateCode>() {
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
                        showDialog(getActivity(), getString(R.string.server_error));
                    }

                    @Override
                    public void onNext(GetServicesByStateCode getServicesByStateCode) {
                        mdilogue.cancel();
                        if (getServicesByStateCode.getListResult() != null && getServicesByStateCode.getListResult().size()!= 0 ) {

                            noRecords.setVisibility(View.GONE);
                            service_list.setVisibility(View.VISIBLE);
                            Log.d(TAG, "---- analysis ---->Getservices-->> Responce size-->> :" + getServicesByStateCode.getListResult().size());
                            serviceadpter = new ServiceAdapter(mContext, getServicesByStateCode.getListResult());
                            service_list.setAdapter(serviceadpter);
                        }else{
                            noRecords.setVisibility(View.VISIBLE);
                            service_list.setVisibility(View.GONE);
                        }
                    }





                });}



//Is Active Farmer
    private void IsActiveFarmer() {
        SharedPreferences pref = getActivity().getSharedPreferences("FARMER", MODE_PRIVATE);
        Farmer_code = pref.getString("farmerid", "").trim();
        ApiService service = ServiceFactory.createRetrofitService(mContext, ApiService.class);
        mSubscription = service.getActiveFarmer(APIConstantURL.IsActiveFarmer +Farmer_code)
                .subscribeOn(Schedulers.newThread())
                .observeOn(AndroidSchedulers.mainThread())
                .subscribe(new Subscriber<IsActiveFarmer>() {
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
                    public void onNext(final IsActiveFarmer isActiveFarmer) {


                        if (isActiveFarmer.getIsSuccess()) {

                            isactive = true;
                        }
                        else {
                            isactive = false;
                        }







                    }


                });
    }



    private void GetActiveEncyclopediaCategoryDetails() {
        ApiService service = ServiceFactory.createRetrofitService(mContext, ApiService.class);
        mSubscription = service.getCategoryDetails(APIConstantURL.GetActiveEncyclopediaCategoryDetails)
                .subscribeOn(Schedulers.newThread())
                .observeOn(AndroidSchedulers.mainThread())
                .subscribe(new Subscriber<GetActiveEncyclopediaCategoryDetails>() {
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
                        showDialog(getActivity(), getString(R.string.server_error));
                    }

                    @Override
                    public void onNext(GetActiveEncyclopediaCategoryDetails getActiveEncyclopediaCategoryDetails) {
                        mdilogue.cancel();
                        if (getActiveEncyclopediaCategoryDetails.getListResult() != null && getActiveEncyclopediaCategoryDetails.getListResult().size()!= 0 ) {

                            Log.d(TAG, "---- analysis ---->GetActiveGodows-->> Responce size-->> :" + getActiveEncyclopediaCategoryDetails.getListResult().size());
                            knowledgeZoneBaseAdapter = new KnowledgeZoneBaseAdapter(mContext, getActiveEncyclopediaCategoryDetails.getListResult());
                            leaning_recycle.setAdapter(knowledgeZoneBaseAdapter);
                        }

                    }

                });}

    public static String getTAG() {
        return TAG;
    }

    public static void setTAG(String TAG) {
        HomeFragment.TAG = TAG;
    }
//Get Active Banner By StateCode
    private void GetBannerByStateCode() {

        String statecode = SharedPrefsData.getInstance(getContext()).getStringFromSharedPrefs("statecode");
        mdilogue.show();
        ApiService service = ServiceFactory.createRetrofitService(mContext, ApiService.class);
        mSubscription = service.getbannerdetails(APIConstantURL.GetActiveBannerByStateCode + statecode)
                .subscribeOn(Schedulers.newThread())
                .observeOn(AndroidSchedulers.mainThread())
                .subscribe(new Subscriber<BannerresponseModel>() {
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
                        showDialog(getActivity(), getString(R.string.server_error));
                    }

                    @Override
                    public void onNext(BannerresponseModel bannerresponseModel) {

                        if (bannerresponseModel.getListResult() != null && bannerresponseModel.getListResult().size()!=0) {

                            final SlideAdapter adapter = new SlideAdapter(getContext(), bannerresponseModel.getListResult());
                            adapter.setCount(bannerresponseModel.getAffectedRecords());
                            sliderView.setSliderAdapter(adapter);
                            sliderView.setIndicatorAnimation(IndicatorAnimations.SLIDE); //set indicator animation by using SliderLayout.IndicatorAnimations. :WORM or THIN_WORM or COLOR or DROP or FILL or NONE or SCALE or SCALE_DOWN or SLIDE and SWAP!!
                            sliderView.setSliderTransformAnimation(SliderAnimations.CUBEINROTATIONTRANSFORMATION);
                            sliderView.setAutoCycleDirection(SliderView.AUTO_CYCLE_DIRECTION_BACK_AND_FORTH);
                            sliderView.setIndicatorSelectedColor(Color.WHITE);
                            sliderView.setIndicatorUnselectedColor(Color.GRAY);
                            sliderView.startAutoCycle();
                            sliderView.setScrollTimeInSec(8);
                            // txt_banner.setText(bannerresponseModel.getListResult().get(0).getDescription());
                            txt_banner.setText(bannerresponseModel.getListResult().get(0).getDescription() + "                                 " + bannerresponseModel.getListResult().get(0).getDescription() + "                          ");
                        }
                        else{
                            sliderView.setVisibility(View.GONE);
                            txt_banner.setVisibility(View.GONE);
                            defalt_iimage.setVisibility(View.VISIBLE);
                        }
                    }
//                    @Override
//                    public void onNext(LabourRecommendationsModel labourRecommendationsModel) {
//                        mdilogue.dismiss();
//                        Log.d(TAG, "onNext:lobour " + labourRecommendationsModel);
//
//                        if(labourRecommendationsModel.getListResult() != null)
//                        {
//                            noRecords.setVisibility(View.GONE);
//                            adapter = new LabourRecommendationAdapter(labourRecommendationsModel.getListResult(),LabourRecommendationsActivity.this);
//                            recyclerView.setAdapter(adapter);
//                        }
//                        else{
//                            noRecords.setVisibility(View.VISIBLE);
//
//                        }
//                    }
                });

    }

    private void init() {
        mContext = getContext();
        mdilogue = (SpotsDialog) new SpotsDialog.Builder()
                .setContext(mContext)
                .setTheme(R.style.Custom)
                .build();
        if (isOnline(getContext()))
            IsActiveFarmer();
        else {
            showDialog(getActivity(), getResources().getString(R.string.Internet));

        }


    }

    @Override
    public void onResume() {
        super.onResume();
        Log.d(TAG, "----- analysis -- Life cycle :ON Resume()");
//        if (isOnline(getContext()))
//            GetBannerByStateCode();
//        else {
//            //  showDialog(getActivity(), getResources().getString(R.string.Internet));
//
//        }

    }

    @Override
    public void onPause() {
        super.onPause();
        Log.d(TAG, "----- analysis -- Life cycle :onPause()");
    }

    public class MyReceiver extends BroadcastReceiver {
        public MyReceiver() {
        }

        @Override
        public void onReceive(Context context, Intent intent) {

            Toast.makeText(context, "Action: " + intent.getAction(), Toast.LENGTH_SHORT).show();
        }
    }

}




