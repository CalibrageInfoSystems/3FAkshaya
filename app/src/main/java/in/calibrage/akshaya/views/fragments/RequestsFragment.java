package in.calibrage.akshaya.views.fragments;


import android.content.Intent;
import android.os.Bundle;
import android.support.v4.app.Fragment;
import android.support.v7.widget.LinearLayoutManager;
import android.support.v7.widget.RecyclerView;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;

import java.util.ArrayList;
import java.util.List;

import in.calibrage.akshaya.R;
import in.calibrage.akshaya.common.BaseFragment;
import in.calibrage.akshaya.models.Request_settings;
import in.calibrage.akshaya.views.Adapter.MyReqListAdapter;
import in.calibrage.akshaya.views.actvity.MyQuickPayRequests;
import in.calibrage.akshaya.views.actvity.MyReqDeatilsActivity;
import in.calibrage.akshaya.views.actvity.MyReqFertilizerActivity;
import in.calibrage.akshaya.views.actvity.MyReqLoanActivity;
import in.calibrage.akshaya.views.actvity.MyReqVisitActivity;
import in.calibrage.akshaya.views.actvity.MyReqpoleActivity;
import in.calibrage.akshaya.views.actvity.RequestListctivity;

/**
 * A simple {@link Fragment} subclass.
 */
public class RequestsFragment extends Fragment implements MyReqListAdapter.RequestAdapterListener {
    public static String TAG = RequestsFragment.class.getSimpleName();
    private List<Request_settings> request_List = new ArrayList<>();

    public RequestsFragment() {
        // Required empty public constructor
    }

    @Override
    public View onCreateView(LayoutInflater inflater, ViewGroup container,
                             Bundle savedInstanceState) {
        View view = inflater.inflate(R.layout.fragment_requests,
                container, false);


        RecyclerView recyclerView = (RecyclerView) view.findViewById(R.id.recyclerView);
        MyReqListAdapter adapter = new MyReqListAdapter(getActivity(), request_List, RequestsFragment.this);
        recyclerView.setHasFixedSize(true);
        recyclerView.setLayoutManager(new LinearLayoutManager(getContext()));
        recyclerView.setAdapter(adapter);
        fetchContacts();
        return view;


    }

    private void fetchContacts() {

        int[] covers = new int[]{
                R.drawable.ic_labour,
                R.drawable.ic_pole,
                R.drawable.ic_ferti,
                R.drawable.ic_collection,
                R.drawable.ic_visits,
                R.drawable.ic_bank,

        };
        Request_settings a = new Request_settings("Labour Request", covers[0]);
        request_List.add(a);
        a = new Request_settings("Pole Request", covers[1]);
        request_List.add(a);
        a = new Request_settings("Fertilizer Request", covers[2]);
        request_List.add(a);
        a = new Request_settings("QuickPay Request", covers[3]);
        request_List.add(a);
        a = new Request_settings("Visit Request", covers[4]);
        request_List.add(a);
        a = new Request_settings("Loan Request", covers[5]);
        request_List.add(a);


    }

    @Override
    public void onContactSelected(Request_settings request) {
        if (request.getName().contains("Labour Request")) {
            Intent intent = new Intent(getContext(), RequestListctivity.class);
            intent.putExtra("key", "labour");
            startActivity(intent);
        }
        if (request.getName().contains("Pole Request")) {
            Intent intent = new Intent(getContext(), RequestListctivity.class);
            intent.putExtra("key", "pole");
            startActivity(intent);
        }
        if (request.getName().contains("Fertilizer Request")) {
            Intent intent = new Intent(getContext(), RequestListctivity.class);
            intent.putExtra("key", "Fertilizer");
            startActivity(intent);
        }
        if (request.getName().contains("QuickPay Request")) {
            Intent intent = new Intent(getContext(), RequestListctivity.class);
            intent.putExtra("key", "QuickPay");
            startActivity(intent);
        }
        if (request.getName().contains("Visit Request")) {
            Intent intent = new Intent(getContext(), RequestListctivity.class);
            intent.putExtra("key", "Visit");
            startActivity(intent);
        }
        if (request.getName().contains("Loan Request")) {
            Intent intent = new Intent(getContext(), RequestListctivity.class);
            intent.putExtra("key", "Loan");
            startActivity(intent);
        }

    }
}


