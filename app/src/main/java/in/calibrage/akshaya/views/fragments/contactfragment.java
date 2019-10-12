package in.calibrage.akshaya.views.fragments;

import android.content.Context;
import android.content.Intent;
import android.content.SharedPreferences;
import android.net.Uri;
import android.os.Bundle;
import android.support.v4.app.Fragment;
import android.text.Html;
import android.util.Log;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.TextView;
import android.widget.Toast;

import dmax.dialog.SpotsDialog;
import in.calibrage.akshaya.R;
import in.calibrage.akshaya.common.BaseFragment;
import in.calibrage.akshaya.localData.SharedPrefsData;
import in.calibrage.akshaya.models.Resbasicinfo;
import in.calibrage.akshaya.models.resGet3FInfo;
import in.calibrage.akshaya.service.APIConstantURL;
import in.calibrage.akshaya.service.ApiService;
import in.calibrage.akshaya.service.ServiceFactory;
import in.calibrage.akshaya.views.actvity.HomeActivity;
import rx.Subscriber;
import rx.Subscription;
import rx.android.schedulers.AndroidSchedulers;
import rx.schedulers.Schedulers;

import static android.content.Context.MODE_PRIVATE;


public class contactfragment extends BaseFragment {
    // TODO: Rename parameter arguments, choose names that match
    // the fragment initialization parameters, e.g. ARG_ITEM_NUMBER
    private static final String ARG_PARAM1 = "param1";
    private static final String ARG_PARAM2 = "param2";
    public static String TAG = contactfragment.class.getSimpleName();

    private String mParam1;
    private String mParam2;
    private  TextView officer_name,officer_mobile,manager_mobile,head_name,care_number,whatsapp_number;
    private Subscription mSubscription;
    private SpotsDialog mdilogue;

    private OnFragmentInteractionListener mListener;

    public contactfragment() {
        // Required empty public constructor
    }


    public static contactfragment newInstance(String param1, String param2) {
        contactfragment fragment = new contactfragment();
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
        mdilogue = (SpotsDialog) new SpotsDialog.Builder()
                .setContext(getContext())
                .setTheme(R.style.Custom)
                .build();
    }

    @Override
    public View onCreateView(LayoutInflater inflater, ViewGroup container,
                             Bundle savedInstanceState) {
        View view = inflater.inflate(R.layout.fragment_contact,
                container, false);
        officer_name=(TextView)view.findViewById(R.id.officer_name);
        officer_mobile=(TextView)view.findViewById(R.id.officer_mobile);
        manager_mobile=(TextView)view.findViewById(R.id.manager_name);
        head_name=(TextView)view.findViewById(R.id.head_name);
        care_number=(TextView)view.findViewById(R.id.care_number);
        whatsapp_number=(TextView)view.findViewById(R.id.whatsapp);
        care_number.setText("1234567890");
        whatsapp_number.setText("9876543210");
        if (isOnline(getContext()))
            Get3FInfo();
        else {
            showDialog(getActivity(), getResources().getString(R.string.Internet));

        }

        return view;




    }

    private void Get3FInfo() {
        String statecode = SharedPrefsData.getInstance(getContext()).getStringFromSharedPrefs("statecode");
        SharedPreferences pref = getActivity().getSharedPreferences("FARMER", MODE_PRIVATE);
     String   Farmer_code = pref.getString("farmerid", "");
        mdilogue.show();
        ApiService service = ServiceFactory.createRetrofitService(getContext(), ApiService.class);
        mSubscription = service.get3finfo(APIConstantURL.Get3FInfo  + Farmer_code+"/"+statecode)
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
                    public void onNext(resGet3FInfo resGet3FInfo) {


                        mdilogue.cancel();
                        officer_name.setText(resGet3FInfo.getResult().getImportantContacts().getClusterOfficerName());
                        officer_mobile.setText(resGet3FInfo.getResult().getImportantContacts().getClusterOfficerContactNumber());
                        manager_mobile.setText(resGet3FInfo.getResult().getImportantContacts().getClusterOfficerManagerName());
                        head_name.setText(resGet3FInfo.getResult().getImportantContacts().getStateHeadName());

                        Log.d(TAG, "---- analysis ---->GetContactInfo -->> :" + resGet3FInfo.getResult().getImportantContacts());
//                        care_number.setText("1234567890");
//                        whatsapp_number.setText("9876543210");
                        care_number.setOnClickListener(new View.OnClickListener() {
                            public void onClick(View v) {
                                Uri u = Uri.parse("tel:" + "1234567890");


                                Intent i = new Intent(Intent.ACTION_DIAL, u);

                                try
                                {

                                    startActivity(i);
                                }
                                catch (SecurityException s)
                                {

                                    Toast.makeText(getContext(), "SecurityException", Toast.LENGTH_LONG)
                                            .show();
                                }
                            }
                        });

                        whatsapp_number.setOnClickListener(new View.OnClickListener() {
                            @Override
                            public void onClick(View view) {
                                String url = "https://api.whatsapp.com/send?phone="+"+91 9876543210";;
                                Intent i = new Intent(Intent.ACTION_VIEW);
                                i.setData(Uri.parse(url));
                                startActivity(i);
                            }
                        });
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
//
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
