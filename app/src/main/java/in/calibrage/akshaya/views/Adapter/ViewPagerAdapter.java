package in.calibrage.akshaya.views.Adapter;

import android.content.Context;
import android.support.v4.app.Fragment;
import android.support.v4.app.FragmentManager;
import android.support.v4.app.FragmentPagerAdapter;

import java.util.ArrayList;

import in.calibrage.akshaya.views.fragments.StandardRecommendationsFragment;
import in.calibrage.akshaya.views.fragments.TabFragment;
import in.calibrage.akshaya.views.fragments.VideoFragment;
import in.calibrage.akshaya.views.fragments.documentFragment;

public class ViewPagerAdapter extends FragmentPagerAdapter {

    private String[] title;

    public ViewPagerAdapter(FragmentManager manager,String[] title) {
        super(manager);
        this.title = title;
    }

    @Override
    public Fragment getItem(int position) {
        return TabFragment.getInstance(position,this.title.length);
    }

    @Override
    public int getCount() {
        return title.length;
    }

    @Override
    public CharSequence getPageTitle(int position) {
        return title[position];
    }
    public int gettabssize()
    {
       return this.title.length;
    }
}