package com.homework.vaisakh.congressapi;

import android.app.Activity;

import android.os.Bundle;
import android.support.annotation.Nullable;
import android.support.design.widget.TabLayout;
import android.support.v4.app.Fragment;
import android.support.v4.app.FragmentActivity;
import android.support.v4.app.FragmentManager;
import android.support.v4.app.FragmentPagerAdapter;
import android.support.v4.view.ViewPager;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.Toast;

/**
 * Created by vaisakh on 11/16/16.
 */

public class LegisMain extends Fragment {

    View myView;

    private SectionsPagerAdapter mSectionsPagerAdapter;
    private ViewPager mViewPager;
    FragmentActivity myContext;
    @Nullable
    @Override
    public View onCreateView(LayoutInflater inflater, ViewGroup container, Bundle savedInstanceState) {
        myView = inflater.inflate(R.layout.legis_main, container,false);

        // Set up the ViewPager with the sections adapter.
        mViewPager = (ViewPager) myView.findViewById(R.id.container);
        //mViewPager.setOffscreenPageLimit(3);
        mViewPager.setAdapter(new SectionsPagerAdapter(getChildFragmentManager()));
        TabLayout tabLayout = (TabLayout) myView.findViewById(R.id.tabs);
        tabLayout.setupWithViewPager(mViewPager);

        return myView;
    }



    @Override
    public void onAttach(Activity activity) {
        myContext=(FragmentActivity) activity;
        super.onAttach(activity);
    }

    public class SectionsPagerAdapter extends FragmentPagerAdapter {

        public SectionsPagerAdapter(FragmentManager fm) {
            super(fm);
        }

        @Override
        public android.support.v4.app.Fragment getItem(int position) {
            switch(position) {
                case 0:
                    LegisOne t1 = new LegisOne();
                    return t1;
                case 1:
                    LegisTwo t2 = new LegisTwo();
                    return t2;
                case 2:
                    LegisThree t3 = new LegisThree();
                    return t3;
                default:
                    return null;
            }
        }

        @Override
        public int getCount() {
            return 3;
        }

        @Override
        public CharSequence getPageTitle(int position) {
            switch (position) {
                case 0:
                    return "All states";
                case 1:
                    return "House";
                case 2:
                    return "Senate";
            }
            return null;
        }
    }
}

