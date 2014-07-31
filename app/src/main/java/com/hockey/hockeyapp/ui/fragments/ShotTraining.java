package com.hockey.hockeyapp.ui.fragments;



import android.os.Bundle;
import android.support.v4.app.Fragment;
import android.support.v4.app.FragmentManager;
import android.support.v4.view.ViewPager;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.TabHost;
import android.widget.TabHost.OnTabChangeListener;
import android.widget.TabHost.TabSpec;

import com.actionbarsherlock.app.ActionBar;
import com.hockey.hockeyapp.R;
import com.hockey.hockeyapp.ui.adapters.ViewPagerAdapter;
import com.hockey.hockeyapp.ui.utils.SimpleTabDefinition;
import com.hockey.hockeyapp.ui.utils.TabDefinition;

public class ShotTraining extends Fragment implements OnTabChangeListener{

    //Constants
    private final TabDefinition[] TAB_DEFINITIONS = new TabDefinition[]{
            new SimpleTabDefinition(R.id.tab1, R.layout.simple_tab, R.string.tab_title1, R.id.tabTitle, new BuzzerBeater()),
            new SimpleTabDefinition(R.id.tab2, R.layout.simple_tab, R.string.tab_title2, R.id.tabTitle, new QuickRelease()),
    };


    private View rootView;
    private TabHost tabHost;


    public ShotTraining() {
        // Required empty public constructor
    }


    @Override
    public void onTabChanged(String tabId)
    {
        for(TabDefinition tab: TAB_DEFINITIONS)
        {
            if(tabId != tab.getId())
            {
                continue;
            }

            updateTab(tabId, tab.getFragment(), tab.getTabContentViewId());
            return;
        }

        throw new IllegalArgumentException("The specified tab id '" + tabId + "' does not exist.");
    }

    @Override
    public View onCreateView(LayoutInflater inflater, ViewGroup container,
                             Bundle savedInstanceState) {
        // Inflate the layout for this fragment
        rootView =  inflater.inflate(R.layout.fragment_shot_training, container, false);

        tabHost = (TabHost) rootView.findViewById(android.R.id.tabhost);
        tabHost.setup();

        for(TabDefinition tab: TAB_DEFINITIONS)
        {
            tabHost.addTab(createTab(inflater, tabHost, rootView, tab));
        }

        return rootView;
    }

    @Override
    public void onActivityCreated(Bundle savedInstanceState)
    {
        super.onActivityCreated(savedInstanceState);
        setRetainInstance(true);

        tabHost.setOnTabChangedListener(this);

        if(TAB_DEFINITIONS.length > 0)
        {
            onTabChanged(TAB_DEFINITIONS[0].getId());
        }
    }

    private TabSpec createTab(LayoutInflater inflater, TabHost tabHost, View rootView, TabDefinition tabDefinition)
    {
        ViewGroup tabsView = (ViewGroup) rootView.findViewById(android.R.id.tabs);
        View tabView = tabDefinition.createTabView(inflater, tabsView);

        TabSpec tabSpec = tabHost.newTabSpec(tabDefinition.getId());
        tabSpec.setIndicator(tabView);
        tabSpec.setContent(tabDefinition.getTabContentViewId());

        return tabSpec;
    }

    private void updateTab(String tabId, Fragment fragment, int containerId)
    {
        final FragmentManager fm = getFragmentManager();
        if(fm.findFragmentByTag(tabId) == null)
        {
            fm.beginTransaction().replace(containerId, fragment, tabId).commit();
        }
    }

}
