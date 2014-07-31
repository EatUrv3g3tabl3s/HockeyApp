package com.hockey.hockeyapp.ui.fragments;

import android.os.Bundle;
import android.support.v4.app.Fragment;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;

import com.hockey.hockeyapp.R;

/**
 * Created by v3g3tabl3s on 7/30/14.
 */
public class QuickRelease extends Fragment {

    @Override
    public View onCreateView(LayoutInflater inflater, ViewGroup container, Bundle savedInstanceState)
    {
        View rootView = inflater.inflate(R.layout.quick_release, container, false);
        return rootView;
    }
}
