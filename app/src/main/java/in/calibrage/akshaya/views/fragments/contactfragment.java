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
import static in.calibrage.akshaya.common.CommonUtil.updateResources;


public class contactfragment extends BaseFragment {

    private static final String ARG_PARAM1 = "param1";
    private static final String ARG_PARAM2 = "param2";
    public static String TAG = contactfragment.class.getSimpleName();

    private String mParam1;
    private String mParam2;
    private TextView officer_name, officer_mobile, manager_mobile, head_name, care_number, whatsapp_number,manager_name;
    private Subscription mSubscription;
    private SpotsDialog mdilogue;

    private OnFragmentInteractionListener mListener;

    public contactfragment() {

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
        officer_name = (TextView) view.findViewById(R.id.officer_name);
        officer_mobile = (TextView) view.findViewById(R.id.officer_mobile);
        manager_name = (TextView) view.findViewById(R.id.manager_name);
        head_name = (TextView) view.findViewById(R.id.head_name);
        care_number = (TextView) view.findViewById(R.id.care_number);
        whatsapp_number = (TextView) view.findViewById(R.id.whatsapp);
        manager_mobile = (TextView) view.findViewById(R.id.manager_mobile);
        care_number.setText("040 23324733");
        whatsapp_number.setText("9515103107");
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


                        mdilogue.cancel();
                        officer_name.setText(resGet3FInfo.getResult().getImportantContacts().getClusterOfficerName());
                        if (!resGet3FInfo.getResult().getImportantContacts().getClusterOfficerContactNumber().isEmpty()) {
                            officer_mobile.setText(resGet3FInfo.getResult().getImportantContacts().getClusterOfficerContactNumber());
                            officer_mobile.setOnClickListener(new View.OnClickListener() {
                                public void onClick(View v) {
                                    Uri u = Uri.parse("tel:" + resGet3FInfo.getResult().getImportantContacts().getClusterOfficerContactNumber());


                                    Intent i = new Intent(Intent.ACTION_DIAL, u);

                                    try {

                                        startActivity(i);
                                    } catch (SecurityException s) {

                                        Toast.makeText(getContext(), "SecurityException", Toast.LENGTH_LONG)
                                                .show();
                                    }
                                }
                            });
                        }

                        else
                        officer_mobile.setText("N/A");
                        if ( !resGet3FInfo.getResult().getImportantContacts().getClusterOfficerManagerName().isEmpty())
                        manager_name.setText(resGet3FInfo.getResult().getImportantContacts().getClusterOfficerManagerName());
                        else
                            manager_name.setText("N/A");
                        if(!resGet3FInfo.getResult().getImportantContacts().getStateHeadName().isEmpty())
                        head_name.setText(resGet3FInfo.getResult().getImportantContacts().getStateHeadName());
                        else
                            head_name.setText("N/A");

                        if(!resGet3FInfo.getResult().getImportantContacts().getClusterOfficerManagerContactNumber().isEmpty()){
                        manager_mobile.setText(resGet3FInfo.getResult().getImportantContacts().getClusterOfficerManagerContactNumber());
                            manager_mobile.setOnClickListener(new View.OnClickListener() {
                                public void onClick(View v) {
                                    Uri u = Uri.parse("tel:" + resGet3FInfo.getResult().getImportantContacts().getClusterOfficerManagerContactNumber());


                                    Intent i = new Intent(Intent.ACTION_DIAL, u);

                                    try {

                                        startActivity(i);
                                    } catch (SecurityException s) {

                                        Toast.makeText(getContext(), "SecurityException", Toast.LENGTH_LONG)
                                                .show();
                                    }
                                }
                            });
                        }
                      else
                            manager_mobile.setText("N/A");
                        try {
                            Log.d(TAG, "---- analysis ---->GetContactInfo -->> :" + resGet3FInfo.getResult().getImportantContacts());
//
                            care_number.setOnClickListener(new View.OnClickListener() {
                                public void onClick(View v) {
                                    Uri u = Uri.parse("tel:" + "040 23324733");


                                    Intent i = new Intent(Intent.ACTION_DIAL, u);

                                    try {

                                        startActivity(i);
                                    } catch (SecurityException s) {

                                        Toast.makeText(getContext(), "SecurityException", Toast.LENGTH_LONG)
                                                .show();
                                    }
                                }
                            });



                            whatsapp_number.setOnClickListener(new View.OnClickListener() {
                                @Override
                                public void onClick(View view) {
                                    String url = "https://api.whatsapp.com/send?phone=" + "+91 9515103107";
                                    ;
                                    Intent i = new Intent(Intent.ACTION_VIEW);
                                    i.setData(Uri.parse(url));
                                    startActivity(i);
                                }
                            });
                        } catch (Exception e) {
                            e.printStackTrace();
                            Log.d(TAG, "---- analysis ---->GetContactInfo -->> :" + e.getLocalizedMessage());
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
//
    }

    @Override
    public void onDetach() {
        super.onDetach();
        mListener = null;
    }


    public interface OnFragmentInteractionListener {
        // TODO: Update argument type and name
        void onFragmentInteraction(Uri uri);
    }
}
