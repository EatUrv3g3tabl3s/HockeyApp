package com.hockey.hockeyapp.ui.fragments;

import android.os.Bundle;
import android.support.v4.app.Fragment;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;

import com.hockey.hockeyapp.R;

import org.androidannotations.annotations.EFragment;


/**
 * Created by Owner on 7/10/2014.
 */

@EFragment(R.layout.shot_log_screen)
public class ShotCounterFragment extends Fragment {

    public ShotCounterFragment(){}

    @Override
    public View onCreateView(LayoutInflater inflater, ViewGroup container, Bundle savedInstanceState)
    {
        View rootView = inflater.inflate(R.layout.shot_log_screen,container, false);
        return rootView;
    }
    }

