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
import in.calibrage.akshaya.localData.SharedPrefsData;
import in.calibrage.akshaya.models.Request_settings;
import in.calibrage.akshaya.views.Adapter.MyReqListAdapter;
import in.calibrage.akshaya.views.actvity.RequestListctivity;

import static in.calibrage.akshaya.common.CommonUtil.updateResources;

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

        final int langID = SharedPrefsData.getInstance(getContext()).getIntFromSharedPrefs("lang");
        if (langID == 2)
            updateResources(getContext(), "te");
        else
            updateResources(getContext(), "en-US");
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
                R.drawable.labour,
                R.drawable.fertilizers,
                R.drawable.equipment,
                R.drawable.quick_pay,
                R.drawable.visit,
                R.drawable.loan,

        };
        Request_settings a = new Request_settings( getResources().getString(R.string.lab_req), covers[0]);
        request_List.add(a);

        a = new Request_settings( getResources().getString(R.string.pole_req), covers[2]);
        request_List.add(a);
        a = new Request_settings( getResources().getString(R.string.fert_req), covers[1]);
        request_List.add(a);
        a = new Request_settings(getResources().getString(R.string.quick_req), covers[3]);
        request_List.add(a);
        a = new Request_settings(getResources().getString(R.string.visit_req), covers[4]);
        request_List.add(a);
        a = new Request_settings(getResources().getString(R.string.Loan_req), covers[5]);
        request_List.add(a);


    }

    @Override
    public void onContactSelected(Request_settings request) {
        if (request.getName().contains( getResources().getString(R.string.lab_req))) {
            Intent intent = new Intent(getContext(), RequestListctivity.class);
            intent.putExtra("key", getResources().getString(R.string.lab_req));
            startActivity(intent);
        }
        if (request.getName().contains( getResources().getString(R.string.pole_req))) {
            Intent intent = new Intent(getContext(), RequestListctivity.class);
            intent.putExtra("key", getResources().getString(R.string.pole_req));
            startActivity(intent);
        }
        if (request.getName().contains( getResources().getString(R.string.fert_req))) {
            Intent intent = new Intent(getContext(), RequestListctivity.class);
            intent.putExtra("key", getResources().getString(R.string.fert_req));
            startActivity(intent);
        }
        if (request.getName().contains(getResources().getString(R.string.quick_req))) {
            Intent intent = new Intent(getContext(), RequestListctivity.class);
            intent.putExtra("key", getResources().getString(R.string.quick_req));
            startActivity(intent);
        }
        if (request.getName().contains(getResources().getString(R.string.visit_req))) {
            Intent intent = new Intent(getContext(), RequestListctivity.class);
            intent.putExtra("key", getResources().getString(R.string.visit_req));
            startActivity(intent);
        }
        if (request.getName().contains(getResources().getString(R.string.Loan_req))) {
            Intent intent = new Intent(getContext(), RequestListctivity.class);
            intent.putExtra("key", getResources().getString(R.string.Loan_req));
            startActivity(intent);
        }

    }
}


