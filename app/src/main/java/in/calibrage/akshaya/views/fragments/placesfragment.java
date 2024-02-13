package in.calibrage.akshaya.views.fragments;

import android.content.Context;
import android.content.SharedPreferences;
import android.net.Uri;
import android.opengl.Visibility;
import android.os.Bundle;
//import android.support.v4.app.Fragment;
//import android.support.v7.widget.LinearLayoutManager;
//import android.support.v7.widget.RecyclerView;
import android.util.Log;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.LinearLayout;
import android.widget.RelativeLayout;
import android.widget.TextView;
import android.widget.Toast;


import java.util.ArrayList;
import java.util.List;

import com.google.android.gms.maps.GoogleMap;
import com.google.android.gms.maps.MapFragment;
import com.google.android.gms.maps.OnMapReadyCallback;
import com.google.android.gms.maps.SupportMapFragment;


import dmax.dialog.SpotsDialog;
import in.calibrage.akshaya.R;
import in.calibrage.akshaya.common.BaseFragment;
import in.calibrage.akshaya.localData.SharedPrefsData;
import in.calibrage.akshaya.models.resGet3FInfo;
import in.calibrage.akshaya.service.APIConstantURL;
import in.calibrage.akshaya.service.ApiService;
import in.calibrage.akshaya.service.ServiceFactory;
import in.calibrage.akshaya.views.Adapter.Godown_adapter;
import in.calibrage.akshaya.views.Adapter.Mills_adapter;
import in.calibrage.akshaya.views.Adapter.Nursery_adapter;
import in.calibrage.akshaya.views.Adapter.PlotDetailsAdapter;
import lib.kingja.switchbutton.SwitchMultiButton;
import rx.Subscriber;
import rx.Subscription;
import rx.android.schedulers.AndroidSchedulers;
import rx.schedulers.Schedulers;

import static android.content.Context.MODE_PRIVATE;
import static android.view.View.VISIBLE;
import static in.calibrage.akshaya.common.CommonUtil.updateResources;

import androidx.recyclerview.widget.LinearLayoutManager;
import androidx.recyclerview.widget.RecyclerView;


public class placesfragment extends BaseFragment implements OnMapReadyCallback {
    // TODO: Rename parameter arguments, choose names that match
    // the fragment initialization parameters, e.g. ARG_ITEM_NUMBER
    private static final String ARG_PARAM1 = "param1";
    private static final String ARG_PARAM2 = "param2";
    public static String TAG = placesfragment.class.getSimpleName();
    // TODO: Rename and change types of parameters
    private String mParam1;
    private String mParam2;
    private SpotsDialog mdilogue;
    private OnFragmentInteractionListener mListener;
    private RecyclerView fert_recyclerView, collection_recycleview, mill_recycleview,nurseries_recycleview;
    RelativeLayout fert_text, collection_text, mill_text;
    SwitchMultiButton sw_paymentMode,sw_paymentMode2;
    private Subscription mSubscription;
    TextView no_data;
    private GoogleMap googleMap;
    String statecode;
    int position;
    public placesfragment() {
        // Required empty public constructor
    }


    public static placesfragment newInstance(String param1, String param2) {
        placesfragment fragment = new placesfragment();
        Bundle args = new Bundle();
        args.putString(ARG_PARAM1, param1);
        args.putString(ARG_PARAM2, param2);
        fragment.setArguments(args);
        return fragment;
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
        if (getArguments() != null) {
            mParam1 = getArguments().getString(ARG_PARAM1);
            mParam2 = getArguments().getString(ARG_PARAM2);
        }
    }
    @Override
    public View onCreateView(LayoutInflater inflater, ViewGroup container,
                             Bundle savedInstanceState) {
        View view = inflater.inflate(R.layout.fragment_places,
                container, false);
        mdilogue = (SpotsDialog) new SpotsDialog.Builder()
                .setContext(getContext())
                .setTheme(R.style.Custom)
                .build();

        sw_paymentMode = (SwitchMultiButton) view.findViewById(R.id.sw_paymentMode);
        sw_paymentMode2 =(SwitchMultiButton) view.findViewById(R.id.sw_paymentMode2);
        fert_recyclerView = (RecyclerView) view.findViewById(R.id.recyclerView_fert);

        fert_recyclerView.setHasFixedSize(true);
        RecyclerView.LayoutManager fert = new LinearLayoutManager(getContext());
        fert_recyclerView.setLayoutManager(fert);


        collection_recycleview = (RecyclerView) view.findViewById(R.id.recyclerView_collection);

        collection_recycleview.setHasFixedSize(true);
        RecyclerView.LayoutManager collection = new LinearLayoutManager(getContext());
        collection_recycleview.setLayoutManager(collection);
        no_data = (TextView) view.findViewById(R.id.no_godown);

        mill_recycleview = (RecyclerView) view.findViewById(R.id.recyclerView_mill);

        mill_recycleview.setHasFixedSize(true);
        RecyclerView.LayoutManager mill = new LinearLayoutManager(getContext());
        mill_recycleview.setLayoutManager(mill);


        nurseries_recycleview= (RecyclerView) view.findViewById(R.id.recyclerView_nurcery);
        nurseries_recycleview.setHasFixedSize(true);
        RecyclerView.LayoutManager nur = new LinearLayoutManager(getContext());
        nurseries_recycleview.setLayoutManager(nur);
         statecode = SharedPrefsData.getInstance(getContext()).getStringFromSharedPrefs("statecode");
        if (isOnline(getContext())) {

            if (statecode.equalsIgnoreCase("AP")) {
                sw_paymentMode.setVisibility(VISIBLE);
                sw_paymentMode2.setVisibility(View.GONE);
                Get3FInfoo(statecode);
            } else {
                sw_paymentMode.setVisibility(View.GONE);
                sw_paymentMode2.setVisibility(VISIBLE);
                Get3FInfoo2();
            }
        }
        else {
            showDialog(getActivity(), getResources().getString(R.string.Internet));

        }





        return view;
    }
// Get Important contacts & Important places
    private void Get3FInfoo(String code) {
       //  statecode = SharedPrefsData.getInstance(getContext()).getStringFromSharedPrefs("statecode");
        SharedPreferences pref = getActivity().getSharedPreferences("FARMER", MODE_PRIVATE);
        String Farmer_code = pref.getString("farmerid", "").trim();
        mdilogue.show();
        ApiService service = ServiceFactory.createRetrofitService(getContext(), ApiService.class);
        mSubscription = service.get3finfo(APIConstantURL.Get3FInfo + Farmer_code + "/" + code)
                .subscribeOn(Schedulers.newThread())
                .observeOn(AndroidSchedulers.mainThread()).subscribe(new Subscriber<resGet3FInfo>() {
                    @Override
                    public void onCompleted() {
                        mdilogue.cancel();
                    }

                    @Override
                    public void onError(Throwable e) {
                        mdilogue.cancel();
                        Log.d(TAG, "---- analysis ---->GetContactInfo -->> error -->> :" + e.getLocalizedMessage());
                        showDialog(getActivity(), getString(R.string.server_error));
                    }

                    @Override
                    public void onNext(final resGet3FInfo resGet3FInfo) {

                        sw_paymentMode.setOnSwitchListener(new SwitchMultiButton.OnSwitchListener() {
                            @Override
                            public void onSwitch(int position, String tabText) {

                                if (position == 0  )  {

                                    if (resGet3FInfo.getResult().getImportantPlaces().getGodowns() != null && resGet3FInfo.getResult().getImportantPlaces().getGodowns().size()!= 0) {
                                        //     no_data.setVisibility(View.GONE);
                                        fert_recyclerView.setVisibility(VISIBLE);
                                        Godown_adapter adapter = new Godown_adapter(resGet3FInfo.getResult().getImportantPlaces().getGodowns(), getContext());
                                        fert_recyclerView.setAdapter(adapter);
                                        Log.d(TAG, "---- analysis ---->GetContactInfo -->> have -->> :" );

                                    }

//                        if ( fert_recyclerView.getVisibility() == View.VISIBLE){
//                            no_data.setVisibility(View.GONE);}
//                        else {
//                            no_data.setVisibility(VISIBLE);
                                    else {
                                        no_data.setText(getResources().getString(R.string.no_godowns));
                                        no_data.setVisibility(View.VISIBLE);
                                        fert_recyclerView.setVisibility(View.GONE);

                                        Log.d(TAG, "---- analysis ---->GetContactInfo -->> nodata -->> :" );
                                    }
                                    collection_recycleview.setVisibility(View.GONE);
                                    mill_recycleview.setVisibility(View.GONE);
                                    nurseries_recycleview.setVisibility(View.GONE);

                                } else if (position == 1) {
                                    fert_recyclerView.setVisibility(View.GONE);

                                    mill_recycleview.setVisibility(View.GONE);
                                    no_data.setVisibility(VISIBLE);
                                    nurseries_recycleview.setVisibility(View.GONE);

                                    if (resGet3FInfo.getResult().getImportantPlaces().getCollectionCenters() != null && resGet3FInfo.getResult().getImportantPlaces().getCollectionCenters().size()!= 0) {
                                        no_data.setVisibility(View.GONE);
                                        collection_recycleview.setVisibility(View.VISIBLE);
                                        collectioncenters_adapter adapter = new collectioncenters_adapter(resGet3FInfo.getResult().getImportantPlaces().getCollectionCenters(), getContext());
                                        collection_recycleview.setAdapter(adapter);


                                    } else {
                                        no_data.setText(getResources().getString(R.string.no_collectioncenter_found));
                                        no_data.setVisibility(VISIBLE);

                                        collection_recycleview.setVisibility(View.GONE);

                                    }
                                } else if (position == 2) {
                                    fert_recyclerView.setVisibility(View.GONE);
                                    collection_recycleview.setVisibility(View.GONE);

                                    nurseries_recycleview.setVisibility(View.GONE);
                                    if (resGet3FInfo.getResult().getImportantPlaces().getMills() != null && resGet3FInfo.getResult().getImportantPlaces().getMills().size()!= 0) {
                                        no_data.setVisibility(View.GONE);
                                        mill_recycleview.setVisibility(VISIBLE);

                                        Mills_adapter adapter = new Mills_adapter(resGet3FInfo.getResult().getImportantPlaces().getMills(), getContext());
                                        mill_recycleview.setAdapter(adapter);


                                    } else {
                                        no_data.setText(getResources().getString(R.string.no_mills));

                                        no_data.setVisibility(VISIBLE);
                                        mill_recycleview.setVisibility(View.GONE);


                                    }
                                }
                                else if (position == 3) {
                                    fert_recyclerView.setVisibility(View.GONE);
                                    collection_recycleview.setVisibility(View.GONE);
                                    mill_recycleview.setVisibility(View.GONE);
                                    Log.e("Nurseries======",resGet3FInfo.getResult().getImportantPlaces().getNurseries()+"");
                                    if (resGet3FInfo.getResult().getImportantPlaces().getNurseries() != null && resGet3FInfo.getResult().getImportantPlaces().getNurseries().size()!= 0) {
                                        Log.e("Nurseries======236",resGet3FInfo.getResult().getImportantPlaces().getNurseries()+"");
                                        no_data.setVisibility(View.GONE);
                                        nurseries_recycleview.setVisibility(VISIBLE);

                                        Nursery_adapter nus_adapter = new Nursery_adapter(resGet3FInfo.getResult().getImportantPlaces().getNurseries(), getContext());
                                        nurseries_recycleview.setAdapter(nus_adapter);


                                    } else {
                                        no_data.setText(getResources().getString(R.string.no_nus));
                                        no_data.setVisibility(VISIBLE);
                                        nurseries_recycleview.setVisibility(View.GONE);


                                    }
                                }

                            }


                        });







                        sw_paymentMode.setSelectedTab(0);



                    }
                });
    }
// Get Important contacts & Important places
    private void Get3FInfoo2() {
        statecode = SharedPrefsData.getInstance(getContext()).getStringFromSharedPrefs("statecode");
        SharedPreferences pref = getActivity().getSharedPreferences("FARMER", MODE_PRIVATE);
        String Farmer_code = pref.getString("farmerid", "").trim();
        mdilogue.show();
        ApiService service = ServiceFactory.createRetrofitService(getContext(), ApiService.class);
        mSubscription = service.get3finfo(APIConstantURL.Get3FInfo + Farmer_code + "/" + statecode)
                .subscribeOn(Schedulers.newThread())
                .observeOn(AndroidSchedulers.mainThread()).subscribe(new Subscriber<resGet3FInfo>() {
                    @Override
                    public void onCompleted() {
                        mdilogue.cancel();
                    }

                    @Override
                    public void onError(Throwable e) {
                        mdilogue.cancel();
                        Log.d(TAG, "---- analysis ---->GetContactInfo -->> error -->> :" + e.getLocalizedMessage());
                        showDialog(getActivity(), getString(R.string.server_error));
                    }

                    @Override
                    public void onNext(final resGet3FInfo resGet3FInfo) {

                        sw_paymentMode2.setOnSwitchListener(new SwitchMultiButton.OnSwitchListener() {
                            @Override
                            public void onSwitch(int position, String tabText) {

                                if (position == 0  )  {

                                    if (resGet3FInfo.getResult().getImportantPlaces().getGodowns() != null && resGet3FInfo.getResult().getImportantPlaces().getGodowns().size()!= 0) {
                                        //     no_data.setVisibility(View.GONE);
                                        fert_recyclerView.setVisibility(VISIBLE);
                                        Godown_adapter adapter = new Godown_adapter(resGet3FInfo.getResult().getImportantPlaces().getGodowns(), getContext());
                                        fert_recyclerView.setAdapter(adapter);
                                        Log.d(TAG, "---- analysis ---->GetContactInfo -->> have -->> :" );

                                    }

//                        if ( fert_recyclerView.getVisibility() == View.VISIBLE){
//                            no_data.setVisibility(View.GONE);}
//                        else {
//                            no_data.setVisibility(VISIBLE);
                                    else {
                                        no_data.setText(getResources().getString(R.string.no_godowns));
                                        no_data.setVisibility(View.VISIBLE);
                                        fert_recyclerView.setVisibility(View.GONE);

                                        Log.d(TAG, "---- analysis ---->GetContactInfo -->> nodata -->> :" );
                                    }
                                    collection_recycleview.setVisibility(View.GONE);
                                    mill_recycleview.setVisibility(View.GONE);
                                    nurseries_recycleview.setVisibility(View.GONE);

                                } else if (position == 1) {
                                    fert_recyclerView.setVisibility(View.GONE);

                                    mill_recycleview.setVisibility(View.GONE);
                                    no_data.setVisibility(VISIBLE);
                                    nurseries_recycleview.setVisibility(View.GONE);

                                    if (resGet3FInfo.getResult().getImportantPlaces().getMills() != null && resGet3FInfo.getResult().getImportantPlaces().getMills().size()!= 0) {
                                        no_data.setVisibility(View.GONE);
                                        mill_recycleview.setVisibility(VISIBLE);

                                        Mills_adapter adapter = new Mills_adapter(resGet3FInfo.getResult().getImportantPlaces().getMills(), getContext());
                                        mill_recycleview.setAdapter(adapter);


                                    } else {
                                        no_data.setText(getResources().getString(R.string.no_mills));

                                        no_data.setVisibility(VISIBLE);
                                        mill_recycleview.setVisibility(View.GONE);}
                                }
                                else if (position == 2) {
                                    fert_recyclerView.setVisibility(View.GONE);
                                    collection_recycleview.setVisibility(View.GONE);
                                    mill_recycleview.setVisibility(View.GONE);
                                    Log.e("Nurseries======",resGet3FInfo.getResult().getImportantPlaces().getNurseries()+"");
                                    if (resGet3FInfo.getResult().getImportantPlaces().getNurseries() != null && resGet3FInfo.getResult().getImportantPlaces().getNurseries().size()!= 0) {
                                        Log.e("Nurseries======236",resGet3FInfo.getResult().getImportantPlaces().getNurseries()+"");
                                        no_data.setVisibility(View.GONE);
                                        nurseries_recycleview.setVisibility(VISIBLE);

                                        Nursery_adapter nus_adapter = new Nursery_adapter(resGet3FInfo.getResult().getImportantPlaces().getNurseries(), getContext());
                                        nurseries_recycleview.setAdapter(nus_adapter);


                                    } else {
                                        no_data.setText(getResources().getString(R.string.no_nus));
                                        no_data.setVisibility(VISIBLE);
                                        nurseries_recycleview.setVisibility(View.GONE);


                                    }
                                }

                            }


                        });


                        sw_paymentMode2.setSelectedTab(0);



                    }
                });
    }

    // TODO: Rename method, update argument and hook method into UI event
    public void onButtonPressed(Uri uri) {
        if (mListener != null) {
            mListener.onFragmentInteraction(uri);
        }
    }

    @Override
    public void onAttach(Context context) {
        super.onAttach(context);
//        if (context instanceof OnFragmentInteractionListener) {
//            mListener = (OnFragmentInteractionListener) context;
//        } else {
//            throw new RuntimeException(context.toString()
//                    + " must implement OnFragmentInteractionListener");
//        }
    }

    @Override
    public void onDetach() {
        super.onDetach();
        mListener = null;
    }

    @Override
    public void onMapReady(GoogleMap googleMap) {

    }

    /**
     * This interface must be implemented by activities that contain this
     * fragment to allow an interaction in this fragment to be communicated
     * to the activity and potentially other fragments contained in that
     * activity.
     * <p>
     * See the Android Training lesson <a href=
     * "http://developer.android.com/training/basics/fragments/communicating.html"
     * >Communicating with Other Fragments</a> for more information.
     */
    public interface OnFragmentInteractionListener {
        // TODO: Update argument type and name
        void onFragmentInteraction(Uri uri);
    }
}
