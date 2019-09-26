package in.calibrage.akshaya.views.fragments;


import android.app.ProgressDialog;
import android.content.Context;
import android.content.Intent;
import android.graphics.Color;
import android.os.Bundle;
import android.support.v4.app.Fragment;
import android.support.v7.widget.DefaultItemAnimator;
import android.support.v7.widget.GridLayoutManager;
import android.support.v7.widget.RecyclerView;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.ImageView;
import android.widget.TextView;

import com.smarteist.autoimageslider.IndicatorAnimations;
import com.smarteist.autoimageslider.SliderAnimations;
import com.smarteist.autoimageslider.SliderView;

import java.util.HashMap;
import java.util.List;

import in.calibrage.akshaya.R;
import in.calibrage.akshaya.common.BaseFragment;
import in.calibrage.akshaya.localData.SharedPrefsData;
import in.calibrage.akshaya.models.FarmerOtpResponceModel;
import in.calibrage.akshaya.views.Adapter.KnowledgeZoneBaseAdapter;
import in.calibrage.akshaya.views.Adapter.SlideAdapter;
import in.calibrage.akshaya.views.actvity.CollectionsActivity;
import in.calibrage.akshaya.views.actvity.FertilizerActivity;
import in.calibrage.akshaya.views.actvity.LabourRecommendationsActivity;
import in.calibrage.akshaya.views.actvity.LoanActivity;
import in.calibrage.akshaya.views.actvity.PaymentActivity;
import in.calibrage.akshaya.views.actvity.PoleActivity;
import in.calibrage.akshaya.views.actvity.QuickPayActivity;
import in.calibrage.akshaya.views.actvity.RecommendationActivity;
import in.calibrage.akshaya.views.actvity.RequestVisitActivity;
import rx.Subscription;

/**
 * A simple {@link Fragment} subclass.
 */
public class HomeFragment extends BaseFragment {
    public static String TAG = "HomeFragment";
    private ImageView img_banner;
    private ProgressDialog dialog;
    private Context mContext;
    private Subscription mSubscription;
    private List<in.calibrage.akshaya.models.LerningsModel> getCategoryList;
    private Object LerningsModel;
    private RecyclerView leaning_recycle;
    private KnowledgeZoneBaseAdapter knowledgeZoneBaseAdapter;
    private FarmerOtpResponceModel catagoriesList;
    private TextView txt_banner;


    HashMap<String, String> HashMapForURL;

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
        // img_banner =  v.findViewById(R.id.img_banner);

        txt_banner = v.findViewById(R.id.txt_banner);
        txt_banner.setSelected(true);

        RecyclerView.LayoutManager mLayoutManager = new GridLayoutManager(getContext(), 3);
        leaning_recycle.setLayoutManager(mLayoutManager);
        leaning_recycle.setItemAnimator(new DefaultItemAnimator());
        knowledgeZoneBaseAdapter = new KnowledgeZoneBaseAdapter(mContext, catagoriesList);
        leaning_recycle.setAdapter(knowledgeZoneBaseAdapter);

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

//
        v.findViewById(R.id.labour_button).setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                Intent intent = new Intent(getContext(), LabourRecommendationsActivity.class);
                startActivity(intent);
            }
        });
        v.findViewById(R.id.pole_button).setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                Intent intent = new Intent(getContext(), PoleActivity.class);
                startActivity(intent);
            }
        });
        v.findViewById(R.id.fertilizer_button).setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                Intent intent = new Intent(getContext(), FertilizerActivity.class);
                startActivity(intent);
            }
        });

        v.findViewById(R.id.quickPay_button).setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                Intent intent = new Intent(getContext(), QuickPayActivity.class);
                startActivity(intent);
            }
        });
        v.findViewById(R.id.visit_button).setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                Intent intent = new Intent(getContext(), RequestVisitActivity.class);
                startActivity(intent);
            }
        });
        v.findViewById(R.id.loan_button).setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                Intent intent = new Intent(getContext(), LoanActivity.class);
                startActivity(intent);
            }
        });
        //getSpinnerPermission();
        //Picasso.with(getContext()).load(catagoriesList.getResult().getBannerDetails().get(0).getImageURL()).into(img_banner);
       // txt_banner.setText(catagoriesList.getResult().getBannerDetails().get(0).getDescription() + "                    " + catagoriesList.getResult().getBannerDetails().get(0).getDescription() + "                    " + catagoriesList.getResult().getBannerDetails().get(0).getDescription());
        SliderView sliderView=  v.findViewById(R.id.imageSlider);
        final SlideAdapter adapter = new SlideAdapter(getContext());
        adapter.setCount(3);

        sliderView.setSliderAdapter(adapter);
        sliderView.setIndicatorAnimation(IndicatorAnimations.SLIDE); //set indicator animation by using SliderLayout.IndicatorAnimations. :WORM or THIN_WORM or COLOR or DROP or FILL or NONE or SCALE or SCALE_DOWN or SLIDE and SWAP!!
        sliderView.setSliderTransformAnimation(SliderAnimations.CUBEINROTATIONTRANSFORMATION);
        sliderView.setAutoCycleDirection(SliderView.AUTO_CYCLE_DIRECTION_BACK_AND_FORTH);
        sliderView.setIndicatorSelectedColor(Color.WHITE);
        sliderView.setIndicatorUnselectedColor(Color.GRAY);
        sliderView.startAutoCycle();
        return v;
    }

    private void init() {
        mContext = getContext();


    }


}




