package in.calibrage.akshaya.views.fragments;

import android.content.Context;
import android.net.Uri;
import android.os.Bundle;
import android.support.v4.app.Fragment;
import android.support.v4.view.ViewPager;
import android.text.TextUtils;
import android.util.Log;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.ImageView;
import android.widget.LinearLayout;
import android.widget.TextView;

import com.squareup.picasso.Picasso;

import in.calibrage.akshaya.R;
import in.calibrage.akshaya.common.CircleTransform;
import in.calibrage.akshaya.localData.SharedPrefsData;
import in.calibrage.akshaya.models.FarmerOtpResponceModel;
import in.calibrage.akshaya.views.actvity.HomeActivity;

/**
 * A simple {@link Fragment} subclass.
 * Activities that contain this fragment must implement the
 * {@link profile_fragment.OnFragmentInteractionListener} interface
 * to handle interaction events.
 * Use the {@link profile_fragment#newInstance} factory method to
 * create an instance of this fragment.
 */
public class profile_fragment extends Fragment {
    public static final String TAG= profile_fragment.class.getSimpleName();
    // TODO: Rename parameter arguments, choose names that match
    // the fragment initialization parameters, e.g. ARG_ITEM_NUMBER
    private static final String ARG_PARAM1 = "param1";
    private static final String ARG_PARAM2 = "param2";

    // TODO: Rename and change types of parameters
    private String mParam1;
    private String mParam2;

    ImageView img_profile;

    private FarmerOtpResponceModel catagoriesList;
    private OnFragmentInteractionListener mListener;

    private TextView farmer_name, father_name, res_address, land_mark, village, mandal, dist, state, pin, mobile, alt_mobile, email;
    private LinearLayout lyt_fathername, lyt_address, lyt_landmark, lyt_village, lyt_mandal, lyt_dist, lyt_state, lyt_pin, lyt_mobile, lyt_alt_mobile, lyt_email;

    public profile_fragment() {
        // Required empty public constructor
    }


    public static profile_fragment newInstance(String param1, String param2) {
        profile_fragment fragment = new profile_fragment();
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
    }

    @Override
    public View onCreateView(LayoutInflater inflater, ViewGroup container,
                             Bundle savedInstanceState) {
        // Inflate the layout for this fragment


        View view = inflater.inflate(R.layout.profile_fragment,
                container, false);
        ViewPager viewPager = (ViewPager) view.findViewById(R.id.viewpager);
        init(view);

        setviews();

        return view;
    }

    private void setviews() {
        catagoriesList = SharedPrefsData.getCatagories(getContext());

        String name = catagoriesList.getResult().getFarmerDetails().get(0).getFirstName() + " " + catagoriesList.getResult().getFarmerDetails().get(0).getMiddleName() + " " + catagoriesList.getResult().getFarmerDetails().get(0).getLastName();
        farmer_name.setText(": " + name.replace("null", ""));

        if (null != catagoriesList.getResult().getFarmerDetails().get(0).getVillageName())
            village.setText(": " + catagoriesList.getResult().getFarmerDetails().get(0).getVillageName());
        else
            lyt_village.setVisibility(View.GONE); // village.setText(": N/A");


        if (null != catagoriesList.getResult().getFarmerDetails().get(0).getAddress())
            res_address.setText(": " + catagoriesList.getResult().getFarmerDetails().get(0).getAddressLine1() + " - " + catagoriesList.getResult().getFarmerDetails().get(0).getAddressLine2());
        else
            lyt_address.setVisibility(View.GONE);// res_address.setText(": N/A");

        if (null != catagoriesList.getResult().getFarmerDetails().get(0).getMandalName())
            mandal.setText(": " + catagoriesList.getResult().getFarmerDetails().get(0).getMandalName());
        else
            lyt_mandal.setVisibility(View.GONE);//mandal.setText(": N/A");

        if (null != catagoriesList.getResult().getFarmerDetails().get(0).getLandmark())
            land_mark.setText(": " + catagoriesList.getResult().getFarmerDetails().get(0).getLandmark());
        else
            lyt_landmark.setVisibility(View.GONE); // land_mark.setText(": N/A");

        if (null != catagoriesList.getResult().getFarmerDetails().get(0).getMobileNumber())
            mobile.setText(catagoriesList.getResult().getFarmerDetails().get(0).getMobileNumber().toString());
        else
            lyt_mobile.setVisibility(View.GONE);  // mobile.setText(": N/A");


        if (null != catagoriesList.getResult().getFarmerDetails().get(0).getDistrictName())
            dist.setText(": " + catagoriesList.getResult().getFarmerDetails().get(0).getDistrictName());
        else
            lyt_dist.setVisibility(View.GONE);// dist.setText(": N/A");

        if (null != catagoriesList.getResult().getFarmerDetails().get(0).getStateName())
            state.setText(": " + catagoriesList.getResult().getFarmerDetails().get(0).getStateName());
        else
           lyt_state.setVisibility(View.GONE);// state.setText(": N/A");

        if (!TextUtils.isEmpty(catagoriesList.getResult().getFarmerDetails().get(0).getFarmerPictureLocation()))
            Picasso.with(getContext()).load(catagoriesList.getResult().getFarmerDetails().get(0).getFarmerPictureLocation()).error(R.drawable.ic_user).transform(new CircleTransform()).into(img_profile);


        Log.d(TAG,"--- >> Bind ----->> PIN :"+catagoriesList.getResult().getFarmerDetails().get(0).getPinCode());
        if (null != catagoriesList.getResult().getFarmerDetails().get(0).getPinCode())
            pin.setText(": "+catagoriesList.getResult().getFarmerDetails().get(0).getPinCode());
        else
           lyt_pin.setVisibility(View.GONE);//; pin.setText(": N/A");


        if (null != catagoriesList.getResult().getFarmerDetails().get(0).getGuardianName())
            father_name.setText(": " + catagoriesList.getResult().getFarmerDetails().get(0).getGuardianName());
        else
           lyt_fathername.setVisibility(View.GONE); // father_name.setText(": N/A");

        if (null != catagoriesList.getResult().getFarmerDetails().get(0).getContactNumber())
            alt_mobile.setText(": " + catagoriesList.getResult().getFarmerDetails().get(0).getContactNumber());
        else
           lyt_alt_mobile.setVisibility(View.GONE); // alt_mobile.setText(": N/A");

        if (null != catagoriesList.getResult().getFarmerDetails().get(0).getEmail())
            email.setText(": " + catagoriesList.getResult().getFarmerDetails().get(0).getEmail());
        else
          lyt_email.setVisibility(View.GONE) ;//;  email.setText(": N/A");


    }

    private void init(View view) {
        img_profile = (ImageView) view.findViewById(R.id.img_profile);
        farmer_name = (TextView) view.findViewById(R.id.farmerName);
        father_name = (TextView) view.findViewById(R.id.fatherName);
        res_address = (TextView) view.findViewById(R.id.address);
        land_mark = (TextView) view.findViewById(R.id.landmark);
        village = (TextView) view.findViewById(R.id.village);
        mandal = (TextView) view.findViewById(R.id.mandal);
        dist = (TextView) view.findViewById(R.id.district);
        state = (TextView) view.findViewById(R.id.state);
        pin = (TextView) view.findViewById(R.id.pincode);
        mobile = (TextView) view.findViewById(R.id.mobilenumber);
        alt_mobile = (TextView) view.findViewById(R.id.alternatemobilenumber);
        email = (TextView) view.findViewById(R.id.emailid);


        lyt_fathername = view.findViewById(R.id.lyt_fathername);
        lyt_address = view.findViewById(R.id.lyt_address);
        lyt_landmark = view.findViewById(R.id.lyt_landmark);
        lyt_village = view.findViewById(R.id.lyt_village);
        lyt_mandal = view.findViewById(R.id.lyt_mandal);
        lyt_dist = view.findViewById(R.id.lyt_dist);
        lyt_state = view.findViewById(R.id.lyt_state);
        lyt_pin = view.findViewById(R.id.lyt_pin);
        lyt_mobile = view.findViewById(R.id.lyt_mobile);
        lyt_alt_mobile = view.findViewById(R.id.lyt_alt_mobile);
        lyt_email = view.findViewById(R.id.lyt_email);
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
//        if (context instanceof OnFragmentInteractionListener) {
//            mListener = (OnFragmentInteractionListener) context;
//        } else {
//            throw new RuntimeException(context.toString()
//                    + " must implement OnFragmentInteractionListener");
//        }
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
