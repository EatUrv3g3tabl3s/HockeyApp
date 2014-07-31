package com.hockey.hockeyapp.ui.adapters;

import android.support.v4.app.Fragment;
import android.support.v4.app.FragmentManager;
import android.support.v4.app.FragmentPagerAdapter;

import com.hockey.hockeyapp.ui.fragments.BuzzerBeater;
import com.hockey.hockeyapp.ui.fragments.QuickRelease;

/**
 * Created by v3g3tabl3s on 7/30/14.
 */
public class ViewPagerAdapter extends FragmentPagerAdapter {

    final int PAGE_COUNT = 2;

    public ViewPagerAdapter(FragmentManager fm)
    {
        super(fm);
    }

    @Override
    public Fragment getItem(int arg0)
    {
        switch (arg0) {

            case 0:
                BuzzerBeater fragTab1 = new BuzzerBeater();
                return fragTab1;

            case 1:
                QuickRelease fragTab2 = new QuickRelease();
                return fragTab2;
        }
        return null;


        }
    @Override
    public int getCount()
    {
        return PAGE_COUNT;
    }
}
