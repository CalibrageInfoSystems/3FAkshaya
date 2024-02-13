package in.calibrage.akshaya.views.fragments;

import android.content.Context;
import android.content.SharedPreferences;
import android.net.Uri;
import android.os.Build;
import android.os.Bundle;
import android.text.Html;
import android.util.Log;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.webkit.WebSettings;
import android.webkit.WebView;
import android.widget.EditText;
import android.widget.TextView;

import dmax.dialog.SpotsDialog;
import in.calibrage.akshaya.R;
import in.calibrage.akshaya.common.BaseFragment;
import in.calibrage.akshaya.localData.SharedPrefsData;
import in.calibrage.akshaya.models.Resbasicinfo;
import in.calibrage.akshaya.service.APIConstantURL;
import in.calibrage.akshaya.service.ApiService;
import in.calibrage.akshaya.service.ServiceFactory;
import in.calibrage.akshaya.views.actvity.LabourActivity;
import rx.Subscriber;
import rx.Subscription;
import rx.android.schedulers.AndroidSchedulers;
import rx.schedulers.Schedulers;

import static android.content.Context.MODE_PRIVATE;


public class BasicinfoFragment extends BaseFragment {
    public static String TAG = BasicinfoFragment.class.getSimpleName();
    private static final String ARG_PARAM1 = "param1";
    private static final String ARG_PARAM2 = "param2";
    TextView txt;
    WebView webView;
    TextView no_data;
    private Subscription mSubscription;
    private SpotsDialog mdilogue;
    // TODO: Rename and change types of parameters
    private String mParam1;
    private String mParam2;

    private OnFragmentInteractionListener mListener;

    public BasicinfoFragment() {

    }

    public static BasicinfoFragment newInstance(String param1, String param2) {
        BasicinfoFragment fragment = new BasicinfoFragment();
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
        View view = inflater.inflate(R.layout.fragment_basicinfo,
                container, false);
        webView = (WebView) view.findViewById(R.id.webView1);
        no_data =(TextView) view.findViewById(R.id.no_data);
        WebSettings webSetting = webView.getSettings();
        webSetting.setBuiltInZoomControls(true);
        webSetting.setJavaScriptEnabled(true);

        webView.setWebViewClient(new WebViewClient());
        if (isOnline(getContext()))
            GetContactInfo();
        else {
            showDialog(getActivity(), getResources().getString(R.string.Internet));

        }

        return view;
    }
// Get Basic Information
    private void GetContactInfo() {
        mdilogue.show();
        String statecode = SharedPrefsData.getInstance(getContext()).getStringFromSharedPrefs("statecode");
        SharedPreferences pref = getActivity().getSharedPreferences("FARMER", MODE_PRIVATE);
        String Farmer_code = pref.getString("farmerid", "").trim();

        ApiService service = ServiceFactory.createRetrofitService(getContext(), ApiService.class);
        mSubscription = service.getbasicinfo(APIConstantURL.GetContactInfo + Farmer_code +"/"+ statecode)
                .subscribeOn(Schedulers.newThread())
                .observeOn(AndroidSchedulers.mainThread()).subscribe(new Subscriber<Resbasicinfo>() {
                    @Override
                    public void onCompleted() {
                        mdilogue.dismiss();
                    }

                    @Override
                    public void onError(Throwable e) {
                        mdilogue.dismiss();
                        Log.d(TAG, "---- analysis ---->GetActiveGodows -->> error -->> :" + e.getLocalizedMessage());
                    //   showDialog(getActivity(), getString(R.string.nobasic));
                    }

                    @Override
                    public void onNext(Resbasicinfo resbasicinfo) {

                        mdilogue.dismiss();
                        if(resbasicinfo.getListResult()!= null) {
                            no_data.setVisibility(View.GONE);
                            webView.setVisibility(View.VISIBLE);
                            final String mimeType = "text/html";
                            final String encoding = "UTF-8";

                            String discription = resbasicinfo.getListResult().get(0).getDescription();


                            webView.loadDataWithBaseURL("", discription, mimeType, encoding, "");
                        }else{
                            no_data.setVisibility(View.VISIBLE);
                            webView.setVisibility(View.GONE);
                           // showDialog(getActivity(), getString(R.string.nobasic));
                        }
//}
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

    private class WebViewClient extends android.webkit.WebViewClient {
        @Override
        public boolean shouldOverrideUrlLoading(WebView view, String url) {
            return super.shouldOverrideUrlLoading(view, url);
        }
    }
}
