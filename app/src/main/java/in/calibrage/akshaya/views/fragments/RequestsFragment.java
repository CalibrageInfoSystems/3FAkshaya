package in.calibrage.akshaya.views.fragments;


import android.os.Bundle;
import android.support.v4.app.Fragment;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;

import in.calibrage.akshaya.R;
import in.calibrage.akshaya.common.BaseFragment;

/**
 * A simple {@link Fragment} subclass.
 */
public class RequestsFragment extends BaseFragment {


    public RequestsFragment() {
        // Required empty public constructor
    }


    @Override
    public View onCreateView(LayoutInflater inflater, ViewGroup container,
                             Bundle savedInstanceState) {
        // Inflate the layout for this fragment
        return inflater.inflate(R.layout.fragment_requests, container, false);
    }

}
