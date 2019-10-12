package in.calibrage.akshaya.views.Adapter;

import android.support.v4.app.Fragment;
import android.support.v4.app.FragmentManager;
import android.support.v4.app.FragmentPagerAdapter;

import in.calibrage.akshaya.views.fragments.TabFragment;

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