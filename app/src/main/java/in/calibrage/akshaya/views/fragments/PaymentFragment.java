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
import android.widget.Toast;

import com.google.gson.Gson;
import com.google.gson.JsonObject;

import java.io.IOException;
import java.text.DecimalFormat;

import dmax.dialog.SpotsDialog;
import in.calibrage.akshaya.R;
import in.calibrage.akshaya.common.BaseFragment;
import in.calibrage.akshaya.models.PageViewModel;
import in.calibrage.akshaya.models.PaymentRequestModel;
import in.calibrage.akshaya.models.PaymentResponseModel;
import in.calibrage.akshaya.service.ApiService;
import in.calibrage.akshaya.service.ServiceFactory;
import in.calibrage.akshaya.views.Adapter.PaymentAdapter;
import in.calibrage.akshaya.views.actvity.PaymentHistoryActivity;
import retrofit2.adapter.rxjava.HttpException;
import rx.Subscriber;
import rx.Subscription;
import rx.android.schedulers.AndroidSchedulers;
import rx.schedulers.Schedulers;

import static android.content.Context.MODE_PRIVATE;

public class PaymentFragment  extends BaseFragment {
    public static String TAG = PaymentFragment.class.getSimpleName();
    String to_date, from_date;
    private Subscription mSubscription;
    private SpotsDialog mdilogue;
    private RecyclerView Payment_recycle;
    TextView noRecords,ffb,totalBalance;
    PaymentAdapter pay_adapter;
    String Farmer_code;
    @Override
    public View onCreateView(LayoutInflater inflater, ViewGroup container,
                             Bundle savedInstanceState) {

        View rootView = inflater.inflate(R.layout.fragment_payment, container, false);

        if (getArguments() != null) {


//UNPACK OUR DATA FROM OUR BUNDLE
            to_date = this.getArguments().getString("todate").toString();
            from_date = this.getArguments().getString("fromdate");
            Log.e("NAME===", to_date + "====" + from_date + "");
        }
        SharedPreferences pref = getActivity().getSharedPreferences("FARMER", MODE_PRIVATE);
         Farmer_code = pref.getString("farmerid", "");
        mdilogue = (SpotsDialog) new SpotsDialog.Builder()
                .setContext(getContext())
                .setTheme(R.style.Custom)
                .build();

        Payment_recycle = (RecyclerView) rootView.findViewById(R.id.payment_recycler_view);
        noRecords = (TextView) rootView.findViewById(R.id.text);
        ffb = (TextView) rootView.findViewById(R.id.ffb_total);
        totalBalance = (TextView) rootView.findViewById(R.id.totalBalance);

        Payment_recycle.setHasFixedSize(true);
        Payment_recycle.setLayoutManager(new LinearLayoutManager(getContext()));
        // recyclerView.setAdapter(adapter);
        if (isOnline(getContext()))
            getPaymentDetails();
        else {
            showDialog(getActivity(), getResources().getString(R.string.Internet));

        }
        pay_adapter = new PaymentAdapter(getContext());
        Payment_recycle.setAdapter(pay_adapter);
//
//        nameFragTxt.setText("NAME : "+name);
//        yearFragTxt.setText("YEAR : "+String.valueOf(year));
        return rootView;
    }
    private BroadcastReceiver mServiceReceiver = new BroadcastReceiver(){
        @Override
        public void onReceive(Context context, Intent intent)
        {
            //Extract your data - better to use constants...
            String IncomingSms=intent.getStringExtra("incomingSms");//
            String phoneNumber=intent.getStringExtra("incomingPhoneNumber");
            Toast.makeText(context, "mallem Mahesh", Toast.LENGTH_SHORT).show();

        }
    };
    private void getPaymentDetails() {
        JsonObject object = paymenObject();
        ApiService service = ServiceFactory.createRetrofitService(getContext(), ApiService.class);
        mSubscription = service.postpayment(object)
                .subscribeOn(Schedulers.newThread())
                .observeOn(AndroidSchedulers.mainThread())
                .subscribe(new Subscriber<PaymentResponseModel>() {
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
                        pay_adapter.clearAllDataa();
                        showDialog(getActivity(), getString(R.string.server_error));
                    }

                    @Override
                    public void onNext(PaymentResponseModel paymentResponseModel) {
                        mdilogue.dismiss();

                        Log.d(TAG, "onNext:payment " + paymentResponseModel);

                        try {
                            if (paymentResponseModel.getResult().getPaymentResponce() != null) {
                                Payment_recycle.setVisibility(View.VISIBLE);
                                noRecords.setVisibility(View.GONE);

                                pay_adapter.updateData(paymentResponseModel.getResult().getPaymentResponce());
    Log.d("TotalQuanitity===", paymentResponseModel.getResult().getTotalQuanitity()+"");
                                double TotalQuanitity = paymentResponseModel.getResult().getTotalQuanitity();

                                DecimalFormat df = new DecimalFormat("#,###,##0.000");
                                System.out.println(df.format(TotalQuanitity));
    //

                                ffb.setText((df.format(TotalQuanitity)));
                                if (paymentResponseModel.getResult().getTotalBalance() == null) {
                                    totalBalance.setText("0.00");

                                } else {
                                    totalBalance.setText(String.valueOf(paymentResponseModel.getResult().getTotalBalance()));
                                }

                            } else {
                                noRecords.setVisibility(View.VISIBLE);
    //
                                Payment_recycle.setVisibility(View.GONE);
                            }
                        } catch (Exception e) {
                            Log.e("Exception.==",e.getLocalizedMessage());
                            e.printStackTrace();
                        }
                    }


                });
    }

    private JsonObject paymenObject() {
        PaymentRequestModel requestModel = new PaymentRequestModel();
        //TODO need to save in shared pref
        /*
         * remove fist 2 letters from former code and add v
         * */

        String text;
        text = Farmer_code.substring(1);
        text = text.substring(1);


        String finalstring = "V" + text;

        Log.i("VendorCode", finalstring);
        requestModel.setVendorCode(finalstring);
        requestModel.setToDate(to_date);
        requestModel.setFromDate(from_date);

        return new Gson().toJsonTree(requestModel).getAsJsonObject();
    }

    @Override
    public void onPause() {
        super.onPause();
        try {
            if(mServiceReceiver != null){
                getContext().unregisterReceiver(mServiceReceiver);
            }
        } catch (Exception e) {
            e.printStackTrace();
        }
    }

    @Override
    public void onResume() {
        super.onResume();
        IntentFilter filter = new IntentFilter();
        filter.addAction("android.intent.action.SmsReceiver");
        getContext().registerReceiver(mServiceReceiver , filter);
    }
}