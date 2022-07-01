package in.calibrage.akshaya.views.fragments;


import android.content.Intent;
import android.os.Bundle;
import android.support.v4.app.Fragment;
import android.support.v7.widget.LinearLayoutManager;
import android.support.v7.widget.RecyclerView;
import android.util.Log;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.TextView;

import java.io.IOException;
import java.util.ArrayList;
import java.util.List;

import dmax.dialog.SpotsDialog;
import in.calibrage.akshaya.R;
import in.calibrage.akshaya.common.BaseFragment;
import in.calibrage.akshaya.localData.SharedPrefsData;
import in.calibrage.akshaya.models.GetServicesByStateCode;
import in.calibrage.akshaya.models.Request_settings;
import in.calibrage.akshaya.service.APIConstantURL;
import in.calibrage.akshaya.service.ApiService;
import in.calibrage.akshaya.service.ServiceFactory;
import in.calibrage.akshaya.views.Adapter.MyReqListAdapter;
import in.calibrage.akshaya.views.actvity.RequestListctivity;
import retrofit2.adapter.rxjava.HttpException;
import rx.Subscriber;
import rx.Subscription;
import rx.android.schedulers.AndroidSchedulers;
import rx.schedulers.Schedulers;

import static in.calibrage.akshaya.common.CommonUtil.updateResources;

/**
 * A simple {@link Fragment} subclass.
 */
public class RequestsFragment extends BaseFragment implements MyReqListAdapter.RequestAdapterListener {
    public static String TAG = RequestsFragment.class.getSimpleName();
    private List<Request_settings> request_List = new ArrayList<>();
    private Subscription mSubscription;
    private SpotsDialog mdilogue;
    TextView noRecords;
    RecyclerView recyclerView;
    MyReqListAdapter myReqListAdapter;
    public RequestsFragment() {
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
        View view = inflater.inflate(R.layout.fragment_requests,
                container, false);

        mdilogue = (SpotsDialog) new SpotsDialog.Builder()
                .setContext( getContext())
                .setTheme(R.style.Custom)
                .build();
        noRecords = (TextView) view.findViewById(R.id.no_data);
         recyclerView = (RecyclerView) view.findViewById(R.id.recyclerView);

        recyclerView.setHasFixedSize(true);
        recyclerView.setLayoutManager(new LinearLayoutManager(getContext()));
    //    recyclerView.setAdapter(adapter);
        fetchContacts();
        return view;


    }
    //Get All Services  base on login Farmer State Code
    private void fetchContacts() {

        request_List.clear();
        String statecode = SharedPrefsData.getInstance(getContext()).getStringFromSharedPrefs("statecode");
        Log.e("state===",statecode);
        ApiService service = ServiceFactory.createRetrofitService(getContext(), ApiService.class);
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
                            recyclerView.setVisibility(View.VISIBLE);
                            Log.d(TAG, "---- analysis ---->Getservices-->> Responce size-->> :" + getServicesByStateCode.getListResult().size());
                            myReqListAdapter =    new MyReqListAdapter(getActivity(), getServicesByStateCode.getListResult(), RequestsFragment.this);
                            recyclerView.setAdapter(myReqListAdapter);
                        }else{
                            noRecords.setVisibility(View.VISIBLE);
                            recyclerView.setVisibility(View.GONE);
                        }
                    }





                });}

        int[] covers = new int[]{
                R.drawable.labour,
                R.drawable.fertilizers,
                R.drawable.equipment,
                R.drawable.quick_pay,
                R.drawable.visit,
                R.drawable.loan,

        };
//       Request_settings a = new Request_settings( getResources().getString(R.string.lab_req), covers[0]);
//        request_List.add(a);
//        a = new Request_settings( getResources().getString(R.string.fert_req), covers[1]);
//        request_List.add(a);
//        a = new Request_settings( getResources().getString(R.string.pole_req), covers[2]);
//        request_List.add(a);
//
//        a = new Request_settings(getResources().getString(R.string.quick_req), covers[3]);
//        request_List.add(a);
//        a = new Request_settings(getResources().getString(R.string.visit_req), covers[4]);
//        request_List.add(a);
//        a = new Request_settings(getResources().getString(R.string.Loan_req), covers[5]);
//        request_List.add(a);






    @Override
    public void onContactSelected(GetServicesByStateCode.ListResult request) {
        if (request.getServiceTypeId()==11) {
            Intent intent = new Intent(getContext(), RequestListctivity.class);
            intent.putExtra("key", getResources().getString(R.string.lab_req));
            startActivity(intent);
        }
        if (request.getServiceTypeId()==10) {
            Intent intent = new Intent(getContext(), RequestListctivity.class);
            intent.putExtra("key", getResources().getString(R.string.pole_req));
            startActivity(intent);
        }
        if (request.getServiceTypeId()==12) {
            Intent intent = new Intent(getContext(), RequestListctivity.class);
            intent.putExtra("key", getResources().getString(R.string.fert_req));
            startActivity(intent);
        }
        if (request.getServiceTypeId()==13) {
            Intent intent = new Intent(getContext(), RequestListctivity.class);
            intent.putExtra("key", getResources().getString(R.string.quick_req));
            startActivity(intent);
        }
        if (request.getServiceTypeId()==14) {
            Intent intent = new Intent(getContext(), RequestListctivity.class);
            intent.putExtra("key", getResources().getString(R.string.visit_req));
            startActivity(intent);
        }
        if (request.getServiceTypeId()==28){
            Intent intent = new Intent(getContext(), RequestListctivity.class);
            intent.putExtra("key", getResources().getString(R.string.Loan_req));
            startActivity(intent);
        }
        if (request.getServiceTypeId()==107){
            Intent intent = new Intent(getContext(), RequestListctivity.class);
            intent.putExtra("key", getResources().getString(R.string.labproduct_req));
            startActivity(intent);
        }
    }
}


