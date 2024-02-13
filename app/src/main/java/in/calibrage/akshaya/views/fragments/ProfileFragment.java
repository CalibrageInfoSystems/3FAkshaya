package in.calibrage.akshaya.views.fragments;

import android.content.Context;
import android.net.Uri;
import android.os.Bundle;
//import android.support.design.widget.TabLayout;
//import android.support.v4.app.Fragment;
//import android.support.v4.app.FragmentManager;
//import android.support.v4.app.FragmentPagerAdapter;
//import android.support.v4.view.ViewPager;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;

import java.util.ArrayList;
import java.util.List;

import in.calibrage.akshaya.R;
import in.calibrage.akshaya.common.BaseFragment;
import in.calibrage.akshaya.localData.SharedPrefsData;

import static in.calibrage.akshaya.common.CommonUtil.updateResources;

import androidx.fragment.app.Fragment;
import androidx.fragment.app.FragmentManager;
import androidx.fragment.app.FragmentPagerAdapter;
import androidx.viewpager.widget.ViewPager;

import com.google.android.material.tabs.TabLayout;


public class ProfileFragment extends  BaseFragment {
    public static String TAG = My3FFragment.class.getSimpleName();
    // TODO: Rename parameter arguments, choose names that match
    // the fragment initialization parameters, e.g. ARG_ITEM_NUMBER
    private static final String ARG_PARAM1 = "param1";
    private static final String ARG_PARAM2 = "param2";

    // TODO: Rename and change types of parameters
    private String mParam1;
    private String mParam2;


    public ProfileFragment() {

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


        View view = inflater.inflate(R.layout.fragment_profile,
                container, false);


        ViewPager viewPager = (ViewPager) view.findViewById(R.id.viewpager);
        setupViewPager(viewPager);

        TabLayout tabs = (TabLayout) view.findViewById(R.id.tabs);
        tabs.setupWithViewPager(viewPager);


        return view;

    }


    // Add Fragments to Tabs
    private void setupViewPager(ViewPager viewPager) {

       Adapter adapter = new Adapter(getChildFragmentManager());
        adapter.addFragment(new profile_fragment(),  getResources().getString(R.string.farmer_profile));
        adapter.addFragment(new plot_details(), getResources().getString(R.string.plot_details));


        viewPager.setAdapter(adapter);


    }

    static class Adapter extends FragmentPagerAdapter {
        private final List<Fragment> mFragmentList = new ArrayList<>();
        private final List<String> mFragmentTitleList = new ArrayList<>();

        public Adapter(FragmentManager manager) {
            super(manager);
        }

        @Override
        public Fragment getItem(int position) {
            return mFragmentList.get(position);
        }

        @Override
        public int getCount() {
            return mFragmentList.size();
        }

        public void addFragment(Fragment fragment, String title) {
            mFragmentList.add(fragment);
            mFragmentTitleList.add(title);
        }

        @Override
        public CharSequence getPageTitle(int position) {
            return mFragmentTitleList.get(position);
        }
    }


}
