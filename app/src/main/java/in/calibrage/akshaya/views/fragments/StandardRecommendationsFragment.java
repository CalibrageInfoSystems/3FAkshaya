package in.calibrage.akshaya.views.fragments;


import android.annotation.SuppressLint;
import android.app.ProgressDialog;
import android.content.SharedPreferences;
import android.os.Bundle;
import android.support.annotation.NonNull;
import android.support.v4.app.Fragment;
import android.support.v7.widget.LinearLayoutManager;
import android.support.v7.widget.RecyclerView;
import android.util.Log;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.AdapterView;
import android.widget.ArrayAdapter;
import android.widget.Spinner;
import android.widget.TextView;
import android.widget.Toast;



import com.android.volley.AuthFailureError;
import com.android.volley.DefaultRetryPolicy;
import com.android.volley.NetworkError;
import com.android.volley.NoConnectionError;
import com.android.volley.ParseError;
import com.android.volley.Request;
import com.android.volley.RequestQueue;
import com.android.volley.Response;
import com.android.volley.RetryPolicy;
import com.android.volley.ServerError;
import com.android.volley.TimeoutError;
import com.android.volley.VolleyError;
import com.android.volley.toolbox.StringRequest;
import com.android.volley.toolbox.Volley;


import org.json.JSONArray;
import org.json.JSONException;
import org.json.JSONObject;

import java.util.ArrayList;


import in.calibrage.akshaya.R;
import in.calibrage.akshaya.service.APIConstantURL;

import static android.content.Context.MODE_PRIVATE;


public class StandardRecommendationsFragment extends Fragment implements AdapterView.OnItemSelectedListener {
    public static String TAG = "StandardRecommendationsFragment";
    ArrayList<String> listdata;
    private RecyclerView recyclerView;
    private RecyclerView.LayoutManager layoutManager;

    private RecyclerView.Adapter adapter;
    Spinner spin;
    public static String text_year;
    boolean isLoading = false;
    private ProgressDialog dialog;
    TextView text;
    String Farmer_code;

    public StandardRecommendationsFragment() {
        // Required empty public constructor
    }

    @Override
    public View onCreateView(LayoutInflater inflater, ViewGroup container, Bundle savedInstanceState) {
        View view = inflater.inflate(R.layout.fragment_standard_recommendations, container, false);
        dialog = new ProgressDialog(getContext());
        text = (TextView) view.findViewById(R.id.noData);


        SharedPreferences pref = getActivity().getSharedPreferences("FARMER", MODE_PRIVATE);
        Farmer_code = pref.getString("farmerid", "").trim();       // Saving string data of your editext

        // Spinner element
        spin = (Spinner) view.findViewById(R.id.spinner);
        spin.setOnItemSelectedListener(this);
        recyclerView = (RecyclerView) view.findViewById(R.id.recycler_view);
//        recyclerView.setHasFixedSize(true);
        layoutManager = new LinearLayoutManager(getContext());
        recyclerView.setLayoutManager(layoutManager);

        GetRecommendation();


        return view;
    }

    private void GetRecommendation() {

    }

    @Override
    public void onItemSelected(AdapterView<?> adapterView, View view, int i, long l) {

    }

    @Override
    public void onNothingSelected(AdapterView<?> adapterView) {

    }
}


