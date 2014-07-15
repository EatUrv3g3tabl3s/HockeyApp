package com.hockey.hockeyapp.ui.fragments;


import android.os.Bundle;
import android.support.v4.app.Fragment;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;

import com.hockey.hockeyapp.R;
import com.hockey.hockeyapp.model.ShotLog;
import com.hockey.hockeyapp.ui.utils.DatabaseHandler;

import java.util.List;


/**
 * Created by Owner on 7/10/2014.
 */

//@EFragment(R.layout.shot_log_screen)
public class ShotCounterFragment extends Fragment {

    public ShotCounterFragment(){}


    @Override
    public View onCreateView(LayoutInflater inflater, ViewGroup container, Bundle savedInstanceState)
    {

        DatabaseHandler db = new DatabaseHandler(this.getActivity());
        db.addShotType(new ShotLog("Wrist", 20));
        db.addShotType(new ShotLog("Snap", 40));
        db.addShotType(new ShotLog("Slap", 69));
        db.addShotType(new ShotLog("Backhand", 50));

        List<ShotLog> shotLogList = db.getAllShotLogs();
        for (ShotLog sL: shotLogList)
        {
            System.out.println(sL.get_shotType()+ ":   "+ sL.get_shotCount());
        }
      //  viewShotCount();
        View rootView = inflater.inflate(R.layout.shot_log_screen,container, false);
        return rootView;

    }


    }

