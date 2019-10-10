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
import android.widget.ImageView;
import android.widget.LinearLayout;

import java.io.IOException;

import dmax.dialog.SpotsDialog;
import in.calibrage.akshaya.R;
import in.calibrage.akshaya.models.LabourRecommendationsModel;
import in.calibrage.akshaya.models.res_plotdetails;
import in.calibrage.akshaya.service.APIConstantURL;
import in.calibrage.akshaya.service.ApiService;
import in.calibrage.akshaya.service.ServiceFactory;
import in.calibrage.akshaya.views.Adapter.LabourRecommendationAdapter;
import in.calibrage.akshaya.views.Adapter.PlotDetailsAdapter;
import in.calibrage.akshaya.views.actvity.LabourRecommendationsActivity;
import retrofit2.adapter.rxjava.HttpException;
import rx.Subscriber;
import rx.Subscription;
import rx.android.schedulers.AndroidSchedulers;
import rx.schedulers.Schedulers;

/**
 * A simple {@link Fragment} subclass.
 * Activities that contain this fragment must implement the
 * {@link plot_details.OnFragmentInteractionListener} interface
 * to handle interaction events.
 * Use the {@link plot_details#newInstance} factory method to
 * create an instance of this fragment.
 */
public class plot_details extends Fragment {
    // TODO: Rename parameter arguments, choose names that match
    public static String TAG = plot_details.class.getSimpleName();
    private static final String ARG_PARAM1 = "param1";
    private static final String ARG_PARAM2 = "param2";
    private RecyclerView recyclerView;
    // TODO: Rename and change types of parameters
    private String mParam1;
    private String mParam2;
    View view;
    String Farmer_code;
    LinearLayout noRecords;
    private Subscription mSubscription;
    private SpotsDialog mdilogue ;

    private OnFragmentInteractionListener mListener;

    public plot_details() {
        // Required empty public constructor
    }


    public static plot_details newInstance(String param1, String param2) {
        plot_details fragment = new plot_details();
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
        // Inflate the layout for this fragment

         view = inflater.inflate(R.layout.fragment_plot_details,
                container, false);
        mdilogue = (SpotsDialog) new SpotsDialog.Builder()
                .setContext(getContext())
                .setTheme(R.style.Custom)
                .build();
        recyclerView = (RecyclerView)view.findViewById(R.id.recycler_view);
        noRecords = (LinearLayout) view.findViewById(R.id.text);
        recyclerView.setHasFixedSize(true);
        recyclerView.setLayoutManager(new LinearLayoutManager(getContext()));
        // recyclerView.setAdapter(adapter);

        GetPlotDetailsByFarmerCode();

        return view;
    }



    private void GetPlotDetailsByFarmerCode() {

            mdilogue.show();
            ApiService service = ServiceFactory.createRetrofitService(getContext(), ApiService.class);
            mSubscription = service.getplotinfo(APIConstantURL.GetPlotDetailsByFarmerCode +"APWGBDAB00010001")
                    .subscribeOn(Schedulers.newThread())
                    .observeOn(AndroidSchedulers.mainThread())
                    .subscribe(new Subscriber<res_plotdetails>() {
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
                        }

                        @Override
                        public void onNext(res_plotdetails res_plotdetails) {




                            if(res_plotdetails.getListResult() != null)
                            {
                                noRecords.setVisibility(View.GONE);
                                PlotDetailsAdapter adapter = new PlotDetailsAdapter(res_plotdetails.getListResult(),getContext() );
                                recyclerView.setAdapter(adapter);


                            }
                            else{
                                noRecords.setVisibility(View.VISIBLE);

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
