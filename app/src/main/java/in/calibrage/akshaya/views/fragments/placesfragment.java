package in.calibrage.akshaya.views.fragments;

import android.content.Context;
import android.net.Uri;
import android.os.Bundle;
import android.support.v4.app.Fragment;
import android.support.v7.widget.LinearLayoutManager;
import android.support.v7.widget.RecyclerView;
import android.util.Log;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.LinearLayout;
import android.widget.RelativeLayout;
import android.widget.TextView;

import dmax.dialog.SpotsDialog;
import in.calibrage.akshaya.R;
import in.calibrage.akshaya.localData.SharedPrefsData;
import in.calibrage.akshaya.models.resGet3FInfo;
import in.calibrage.akshaya.service.APIConstantURL;
import in.calibrage.akshaya.service.ApiService;
import in.calibrage.akshaya.service.ServiceFactory;
import in.calibrage.akshaya.views.Adapter.Godown_adapter;
import in.calibrage.akshaya.views.Adapter.PlotDetailsAdapter;
import rx.Subscriber;
import rx.Subscription;
import rx.android.schedulers.AndroidSchedulers;
import rx.schedulers.Schedulers;


public class placesfragment extends Fragment {
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
    private RecyclerView fert_recyclerView,collection_recycleview,mill_recycleview;
    RelativeLayout fert_text,collection_text,mill_text;

    private Subscription mSubscription;
    public placesfragment() {
        // Required empty public constructor
    }

    /**
     * Use this factory method to create a new instance of
     * this fragment using the provided parameters.
     *
     * @param param1 Parameter 1.
     * @param param2 Parameter 2.
     * @return A new instance of fragment placesfragment.
     */
    // TODO: Rename and change types and number of parameters
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
        fert_recyclerView = (RecyclerView)view.findViewById(R.id.recyclerView_fert);
        fert_text = (RelativeLayout) view.findViewById(R.id.fert_text);
        fert_recyclerView.setHasFixedSize(true);
        fert_recyclerView.setLayoutManager(new LinearLayoutManager(getContext()));
        // recyclerView.setAdapter(adapter);



        collection_recycleview = (RecyclerView)view.findViewById(R.id.recyclerView_collection);
        collection_text = (RelativeLayout) view.findViewById(R.id.collection_text);
        collection_recycleview.setHasFixedSize(true);
        collection_recycleview.setLayoutManager(new LinearLayoutManager(getContext()));


        mill_recycleview = (RecyclerView)view.findViewById(R.id.recyclerView_mill);
        mill_text = (RelativeLayout) view.findViewById(R.id.collection_text);
        mill_recycleview.setHasFixedSize(true);
        mill_recycleview.setLayoutManager(new LinearLayoutManager(getContext()));
        Get3FInfoo();
        return view;
    }

    private void Get3FInfoo() {
        String statecode = SharedPrefsData.getInstance(getContext()).getStringFromSharedPrefs("statecode");
        mdilogue.show();
        ApiService service = ServiceFactory.createRetrofitService(getContext(), ApiService.class);
        mSubscription = service.get3finfo(APIConstantURL.Get3FInfo + "APWGBDAB00010001" + "/" + statecode)
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

                    }

                    @Override
                    public void onNext(resGet3FInfo resGet3FInfo) {
                        if(resGet3FInfo.getResult().getImportantPlaces().getGodowns() != null)
                        {
                            fert_text.setVisibility(View.GONE);
                            Godown_adapter adapter = new Godown_adapter(resGet3FInfo.getResult().getImportantPlaces().getGodowns(),getContext());
                            fert_recyclerView.setAdapter(adapter);


                        }
                        else{
                            fert_text.setVisibility(View.VISIBLE);

                        }

                        if(resGet3FInfo.getResult().getImportantPlaces().getCollectionCenters() != null)
                        {
                            fert_text.setVisibility(View.GONE);
                            Godown_adapter adapter = new Godown_adapter(resGet3FInfo.getResult().getImportantPlaces().getGodowns(),getContext());
                            fert_recyclerView.setAdapter(adapter);


                        }
                        else{
                            fert_text.setVisibility(View.VISIBLE);

                        }
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
