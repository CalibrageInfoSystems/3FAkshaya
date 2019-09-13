package in.calibrage.akshaya.views.Adapter;

import android.content.Context;
import android.support.v4.app.Fragment;
import android.support.v4.app.FragmentManager;
import android.support.v4.app.FragmentPagerAdapter;

import in.calibrage.akshaya.views.fragments.StandardRecommendationsFragment;
import in.calibrage.akshaya.views.fragments.TabFragment;
import in.calibrage.akshaya.views.fragments.VideoFragment;
import in.calibrage.akshaya.views.fragments.documentFragment;

public class ViewPagerAdapter extends FragmentPagerAdapter {

    private String title[] = {"One", "Two", "Three"};

    public ViewPagerAdapter(FragmentManager manager) {
        super(manager);
    }

    @Override
    public Fragment getItem(int position) {
        return TabFragment.getInstance(position);
    }

    @Override
    public int getCount() {
        return title.length;
    }

    @Override
    public CharSequence getPageTitle(int position) {
        return title[position];
    }
}