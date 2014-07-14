package com.hockey.hockeyapp.ui.fragments;



import android.os.Bundle;
import android.support.v4.app.Fragment;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;

import com.hockey.hockeyapp.R;

import org.androidannotations.annotations.EFragment;

/**
 * A simple {@link Fragment} subclass.
 *
 */
@EFragment(R.layout.fragment_blank)
public class BlankFragment extends Fragment {


    public BlankFragment() {
        // Required empty public constructor
    }


    @Override
    public View onCreateView(LayoutInflater inflater, ViewGroup container,
                             Bundle savedInstanceState) {
        // Inflate the layout for this fragment
        return inflater.inflate(R.layout.fragment_blank, container, false);
    }


}
