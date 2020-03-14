package in.calibrage.akshaya.views.fragments;

import android.arch.lifecycle.Observer;
import android.arch.lifecycle.ViewModelProviders;
import android.content.BroadcastReceiver;
import android.content.Context;
import android.content.Intent;
import android.content.IntentFilter;
import android.content.SharedPreferences;
import android.net.Uri;
import android.os.Bundle;
import android.support.annotation.Nullable;
import android.support.v4.app.Fragment;
import android.support.v7.widget.LinearLayoutManager;
import android.support.v7.widget.RecyclerView;
import android.util.Log;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.LinearLayout;
import android.widget.TextView;

import com.google.gson.Gson;
import com.google.gson.JsonObject;

import java.io.IOException;
import java.text.DecimalFormat;

import dmax.dialog.SpotsDialog;
import in.calibrage.akshaya.R;
import in.calibrage.akshaya.common.BaseFragment;
import in.calibrage.akshaya.models.GetTranspotationCharges;
import in.calibrage.akshaya.models.PageViewModel;
import in.calibrage.akshaya.models.PaymentRequestModel;
import in.calibrage.akshaya.models.PaymentResponseModel;
import in.calibrage.akshaya.service.ApiService;
import in.calibrage.akshaya.service.ServiceFactory;
import in.calibrage.akshaya.views.Adapter.PaymentAdapter;
import in.calibrage.akshaya.views.Adapter.TransportationAdapter;
import retrofit2.adapter.rxjava.HttpException;
import rx.Subscriber;
import rx.Subscription;
import rx.android.schedulers.AndroidSchedulers;
import rx.schedulers.Schedulers;

import static android.content.Context.MODE_PRIVATE;


public class TransFragment extends BaseFragment {
    public static String TAG = TransFragment.class.getSimpleName();
    String to_date, from_date;
    private Subscription mSubscription;
    private SpotsDialog mdilogue;
    private RecyclerView trans_recycle;
    TextView noRecords;
    TransportationAdapter pay_adapter;
    String Farmer_code;
    LinearLayout linear1;
    @Override
    public View onCreateView(LayoutInflater inflater, ViewGroup container,
                             Bundle savedInstanceState) {

        View rootView = inflater.inflate(R.layout.fragment_trans, container, false);

        SharedPreferences pref = getActivity().getSharedPreferences("FARMER", MODE_PRIVATE);
        Farmer_code = pref.getString("farmerid", "");
        mdilogue = (SpotsDialog) new SpotsDialog.Builder()
                .setContext(getContext())
                .setTheme(R.style.Custom)
                .build();

        trans_recycle = (RecyclerView) rootView.findViewById(R.id.trans_recycler_view);
        noRecords = (TextView) rootView.findViewById(R.id.text);
        trans_recycle.setHasFixedSize(true);
        trans_recycle.setLayoutManager(new LinearLayoutManager(getContext()));


        pay_adapter = new TransportationAdapter(getContext());
        trans_recycle.setAdapter(pay_adapter);
//
        return rootView;
    }
    private BroadcastReceiver mNotificationReceiver = new BroadcastReceiver() {
        @Override
        public void onReceive(Context context, Intent intent) {

            to_date = intent.getStringExtra("todate");
            from_date = intent.getStringExtra("fromdate");
            trans_recycle.setVisibility(View.GONE);

            if(to_date.equalsIgnoreCase("clear"))
            {
                pay_adapter.clearAllDataa();

            }else {

                if (isOnline(getContext()))
                    GetTranspotationChargesByFarmerCode();
                else {
                    showDialog(getActivity(), getResources().getString(R.string.Internet));
                }
            }
            Log.e("roja=====",to_date+"=====" + from_date);
        }
    };

    private void GetTranspotationChargesByFarmerCode() {
        pay_adapter.clearAllDataa();
        mdilogue.show();
        JsonObject object = paymenObject();
        ApiService service = ServiceFactory.createRetrofitService(getContext(), ApiService.class);
        mSubscription = service.posttrans(object)
                .subscribeOn(Schedulers.newThread())
                .observeOn(AndroidSchedulers.mainThread())
                .subscribe(new Subscriber<GetTranspotationCharges>() {
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
                        Log.e("error==",e.getLocalizedMessage());
                        mdilogue.dismiss();
                        pay_adapter.clearAllDataa();
                      showDialog(getActivity(), getString(R.string.server_error));
                    }

                    @Override
                    public void onNext(GetTranspotationCharges getTranspotationCharges) {


                        Log.d(TAG, "onNext:payment " + getTranspotationCharges);

                        try {
                            if (getTranspotationCharges.getListResult() != null) {
                                trans_recycle.setVisibility(View.VISIBLE);
                                noRecords.setVisibility(View.GONE);

                                pay_adapter.updateData(getTranspotationCharges.getListResult());


                                if(getTranspotationCharges.getAffectedRecords()==0){
                                    noRecords.setVisibility(View.VISIBLE);

                                    trans_recycle.setVisibility(View.GONE);
                                }

                            } else {
                                noRecords.setVisibility(View.VISIBLE);
                                //
                                trans_recycle.setVisibility(View.GONE);
                            }
                        } catch (Exception e) {
                            Log.e("Exception.==",e.getLocalizedMessage());
                            e.printStackTrace();
                        }
                    }


                });
    }

    private JsonObject paymenObject() {
        Log.e("roja=====176",to_date+"=====" + from_date);
        PaymentRequestModel requestModel = new PaymentRequestModel();
        //TODO need to save in shared pref
        /*
         * remove fist 2 letters from former code and add v
         * */


        requestModel.setVendorCode(Farmer_code);
        requestModel.setToDate(to_date);
        requestModel.setFromDate(from_date);

        return new Gson().toJsonTree(requestModel).getAsJsonObject();
    }

    @Override
    public void onResume() {
        super.onResume();
        getContext().registerReceiver(mNotificationReceiver, new IntentFilter("KEY"));
    }

    @Override
    public void onPause() {
        super.onPause();
        getContext().unregisterReceiver(mNotificationReceiver);
    }
}
