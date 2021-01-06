package in.calibrage.akshaya.views.fragments;

import android.content.Context;
import android.content.Intent;
import android.content.SharedPreferences;
import android.graphics.Bitmap;
import android.graphics.Point;
import android.net.Uri;
import android.os.Bundle;
import android.support.v4.app.Fragment;
import android.support.v4.view.ViewPager;
import android.text.TextUtils;
import android.util.Log;
import android.view.Display;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.view.WindowManager;
import android.widget.ImageView;
import android.widget.LinearLayout;
import android.widget.TextView;
import android.widget.Toast;

import com.google.zxing.BarcodeFormat;
import com.google.zxing.WriterException;
import com.squareup.picasso.Picasso;

import in.calibrage.akshaya.R;
import in.calibrage.akshaya.common.BaseFragment;
import in.calibrage.akshaya.common.CircleTransform;
import in.calibrage.akshaya.localData.SharedPrefsData;
import in.calibrage.akshaya.models.FarmerOtpResponceModel;
import in.calibrage.akshaya.views.actvity.Contents;
import in.calibrage.akshaya.views.actvity.HomeActivity;
import in.calibrage.akshaya.views.actvity.QRCodeEncoder;

import static android.content.Context.MODE_PRIVATE;
import static android.content.Context.WINDOW_SERVICE;
import static in.calibrage.akshaya.common.CommonUtil.updateResources;


public class profile_fragment extends BaseFragment {
    public static final String TAG = profile_fragment.class.getSimpleName();

    private static final String ARG_PARAM1 = "param1";
    private static final String ARG_PARAM2 = "param2";

    // TODO: Rename and change types of parameters
    private String mParam1;
    private String mParam2;
    String Farmer_code;
    ImageView img_profile;

    private FarmerOtpResponceModel catagoriesList;
    private OnFragmentInteractionListener mListener;

    private TextView farmer_name, father_name, res_address, land_mark, village, mandal, dist, state, pin, mobile, alt_mobile, email,farmer_code;
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
        final int langID = SharedPrefsData.getInstance(getContext()).getIntFromSharedPrefs("lang");
        if (langID == 2)
            updateResources(getContext(), "te");
        else if (langID == 3)
            updateResources(getContext(), "kan");
        else
            updateResources(getContext(), "en-US");
        super.onCreate(savedInstanceState);
        if (getArguments() != null) {
            mParam1 = getArguments().getString(ARG_PARAM1);
            mParam2 = getArguments().getString(ARG_PARAM2);
        }
    }

    @Override
    public View onCreateView(LayoutInflater inflater, ViewGroup container,
                             Bundle savedInstanceState) {


        View view = inflater.inflate(R.layout.profile_fragment,
                container, false);
        ViewPager viewPager = (ViewPager) view.findViewById(R.id.viewpager);

        SharedPreferences pref = getActivity().getSharedPreferences("FARMER", MODE_PRIVATE);
         Farmer_code = pref.getString("farmerid", "").trim();
        init(view);

        setviews();

        return view;
    }

    private void setviews() {
        catagoriesList = SharedPrefsData.getCatagories(getContext());
Log.e("Clusterid===",catagoriesList.getResult().getFarmerDetails().get(0).getClusterId()+"===="+ catagoriesList.getResult().getFarmerDetails().get(0).getClusterName());
        farmer_code.setText("" +Farmer_code);
        String name = catagoriesList.getResult().getFarmerDetails().get(0).getFirstName() + " " + catagoriesList.getResult().getFarmerDetails().get(0).getMiddleName() + " " + catagoriesList.getResult().getFarmerDetails().get(0).getLastName();
        farmer_name.setText("" + name.replace("null", ""));

        if (null != catagoriesList.getResult().getFarmerDetails().get(0).getVillageName())
            village.setText("" + catagoriesList.getResult().getFarmerDetails().get(0).getVillageName());
        else
            lyt_village.setVisibility(View.GONE); // village.setText(": N/A");


        if (null != catagoriesList.getResult().getFarmerDetails().get(0).getAddress())
            res_address.setText("" + catagoriesList.getResult().getFarmerDetails().get(0).getAddressLine1() + " - " + catagoriesList.getResult().getFarmerDetails().get(0).getAddressLine2());
        else
            lyt_address.setVisibility(View.GONE);// res_address.setText(": N/A");

        if (null != catagoriesList.getResult().getFarmerDetails().get(0).getMandalName())
            mandal.setText("" + catagoriesList.getResult().getFarmerDetails().get(0).getMandalName());
        else
            lyt_mandal.setVisibility(View.GONE);//mandal.setText(": N/A");

        if (null != catagoriesList.getResult().getFarmerDetails().get(0).getLandmark())
            land_mark.setText("" + catagoriesList.getResult().getFarmerDetails().get(0).getLandmark());
        else
            lyt_landmark.setVisibility(View.GONE); // land_mark.setText(": N/A");
        if (null != catagoriesList.getResult().getFarmerDetails().get(0).getContactNumber()) {
//        if (null != catagoriesList.getResult().getFarmerDetails().get(0).getMobileNumber())
            mobile.setText("" + catagoriesList.getResult().getFarmerDetails().get(0).getContactNumber().toString());
            mobile.setOnClickListener(new View.OnClickListener() {
                public void onClick(View v) {
                    Uri u = Uri.parse("tel:" + catagoriesList.getResult().getFarmerDetails().get(0).getContactNumber());


                    Intent i = new Intent(Intent.ACTION_DIAL, u);

                    try {

                        startActivity(i);
                    } catch (SecurityException s) {

                        Toast.makeText(getContext(), "SecurityException", Toast.LENGTH_LONG)
                                .show();
                    }
                }
            });
        }

        else {
            lyt_mobile.setVisibility(View.GONE);  // mobile.setText(": N/A");
        }

        if (null != catagoriesList.getResult().getFarmerDetails().get(0).getDistrictName())
            dist.setText("" + catagoriesList.getResult().getFarmerDetails().get(0).getDistrictName());
        else
            lyt_dist.setVisibility(View.GONE);// dist.setText(": N/A");

        if (null != catagoriesList.getResult().getFarmerDetails().get(0).getStateName())
            state.setText("" + catagoriesList.getResult().getFarmerDetails().get(0).getStateName());
        else
            lyt_state.setVisibility(View.GONE);// state.setText(": N/A");

        if (!TextUtils.isEmpty(catagoriesList.getResult().getFarmerDetails().get(0).getFarmerPictureLocation()))
            Picasso.with(getContext()).load(catagoriesList.getResult().getFarmerDetails().get(0).getFarmerPictureLocation()).error(R.drawable.ic_user).transform(new CircleTransform()).into(img_profile);


        Log.d(TAG, "--- >> Bind ----->> PIN :" + catagoriesList.getResult().getFarmerDetails().get(0).getPinCode());
        if (null != catagoriesList.getResult().getFarmerDetails().get(0).getPinCode())
            pin.setText("" + catagoriesList.getResult().getFarmerDetails().get(0).getPinCode());
        else
            lyt_pin.setVisibility(View.GONE);//; pin.setText(": N/A");


        if (null != catagoriesList.getResult().getFarmerDetails().get(0).getGuardianName())
            father_name.setText("" + catagoriesList.getResult().getFarmerDetails().get(0).getGuardianName());
        else
            lyt_fathername.setVisibility(View.GONE); // father_name.setText(": N/A");
        if (null != catagoriesList.getResult().getFarmerDetails().get(0).getMobileNumber())
//        if (null != catagoriesList.getResult().getFarmerDetails().get(0).getContactNumber())
           alt_mobile.setText("" + catagoriesList.getResult().getFarmerDetails().get(0).getMobileNumber());
        else
            lyt_alt_mobile.setVisibility(View.GONE); // alt_mobile.setText(": N/A");

        if (null != catagoriesList.getResult().getFarmerDetails().get(0).getEmail() && !   TextUtils.isEmpty(catagoriesList.getResult().getFarmerDetails().get(0).getEmail()+"") && !catagoriesList.getResult().getFarmerDetails().get(0).getEmail().toString().isEmpty())
            email.setText("" + catagoriesList.getResult().getFarmerDetails().get(0).getEmail());
        else
            lyt_email.setVisibility(View.GONE);//;  email.setText(": N/A");




    }

    private void init(View view) {
        img_profile = (ImageView) view.findViewById(R.id.img_profile);
        farmer_name = (TextView) view.findViewById(R.id.farmerName);
        farmer_code =(TextView) view.findViewById(R.id.farmer_Code);
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

        WindowManager manager = (WindowManager)getActivity().getSystemService(WINDOW_SERVICE);
        Display display = manager.getDefaultDisplay();
        Point point = new Point();
        display.getSize(point);
        int width = point.x;
        int height = point.y;
        int smallerDimension = width < height ? width : height;
        smallerDimension = smallerDimension * 4/4;

        //Encode with a QR Code image
        QRCodeEncoder qrCodeEncoder = new QRCodeEncoder(Farmer_code,
                null,
                Contents.Type.TEXT,
                BarcodeFormat.QR_CODE.toString(),
                smallerDimension);
        try {
            Bitmap bitmap = qrCodeEncoder.encodeAsBitmap();
            ImageView myImage = (ImageView) view. findViewById(R.id.imageView1);
            myImage.setImageBitmap(bitmap);

        } catch (WriterException e) {
            e.printStackTrace();
        }

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
