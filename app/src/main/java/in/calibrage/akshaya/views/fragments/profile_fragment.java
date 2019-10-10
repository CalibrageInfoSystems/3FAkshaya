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

private TextView farmer_name,father_name,res_address,land_mark,village,mandal,dist,state,pin,mobile,alt_mobile,email;

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
        img_profile = (ImageView)view.findViewById(R.id.img_profile);
        farmer_name=(TextView) view.findViewById(R.id.farmerName);
        father_name=(TextView)  view.findViewById(R.id.fatherName);
        res_address=(TextView) view.findViewById(R.id.address);
        land_mark=(TextView) view.findViewById(R.id.landmark);
        village=(TextView) view.findViewById(R.id.village);
        mandal=(TextView) view.findViewById(R.id.mandal);
        dist=(TextView) view.findViewById(R.id.district);
        state=(TextView) view.findViewById(R.id.state);
        pin=(TextView) view.findViewById(R.id.pincode);
        mobile=(TextView) view.findViewById(R.id.mobilenumber);

        alt_mobile=(TextView) view.findViewById(R.id.alternatemobilenumber);
        email=(TextView) view.findViewById(R.id.emailid);

        catagoriesList = SharedPrefsData.getCatagories(getContext());

        String name = catagoriesList.getResult().getFarmerDetails().get(0).getFirstName() + " " + catagoriesList.getResult().getFarmerDetails().get(0).getMiddleName() + " " + catagoriesList.getResult().getFarmerDetails().get(0).getLastName();
        farmer_name.setText(name.replace("null", ""));
        village.setText(catagoriesList.getResult().getFarmerDetails().get(0).getVillageName());
        //Log.e("village_naME",catagoriesList.getResult().getFarmerDetails().get(0).getVillageName());
        mandal.setText(catagoriesList.getResult().getFarmerDetails().get(0).getMandalName());
        dist.setText(catagoriesList.getResult().getFarmerDetails().get(0).getDistrictName());
        if (catagoriesList.getResult().getFarmerDetails().get(0).getMobileNumber() == null)
            mobile.setVisibility(View.GONE);
        else
            mobile.setText(catagoriesList.getResult().getFarmerDetails().get(0).getMobileNumber().toString());
        res_address.setText(catagoriesList.getResult().getFarmerDetails().get(0).getAddressLine1() + " - " + catagoriesList.getResult().getFarmerDetails().get(0).getAddressLine2());
        if (!TextUtils.isEmpty(catagoriesList.getResult().getFarmerDetails().get(0).getAddressLine1()))
            Picasso.with(getContext()).load(catagoriesList.getResult().getFarmerDetails().get(0).getFarmerPictureLocation()).error(R.drawable.ic_user).transform(new CircleTransform()).into(img_profile);

        father_name.setText(catagoriesList.getResult().getFarmerDetails().get(0).getGuardianName());
        land_mark.setText(catagoriesList.getResult().getFarmerDetails().get(0).getLandmark());
        state.setText(catagoriesList.getResult().getFarmerDetails().get(0).getStateName());
        if (catagoriesList.getResult().getFarmerDetails().get(0).getPinCode() == null)
            pin.setVisibility(View.GONE);
        else
        pin.setText(catagoriesList.getResult().getFarmerDetails().get(0).getPinCode());

        return view;
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
