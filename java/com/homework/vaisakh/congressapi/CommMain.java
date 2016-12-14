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

/**
 * Created by vaisakh on 11/20/16.
 */

public class CommMain extends Fragment {

    View myView;

    private LegisMain.SectionsPagerAdapter mSectionsPagerAdapter;
    private ViewPager mViewPager;
    FragmentActivity myContext;
    @Nullable
    @Override
    public View onCreateView(LayoutInflater inflater, ViewGroup container, Bundle savedInstanceState) {
        myView = inflater.inflate(R.layout.comm_main, container,false);

        //mSectionsPagerAdapter = new SectionsPagerAdapter(myContext.getSupportFragmentManager());
        // Set up the ViewPager with the sections adapter.
        mViewPager = (ViewPager) myView.findViewById(R.id.container2);
        //mViewPager.setOffscreenPageLimit(3);
        mViewPager.setAdapter(new SectionsPagerAdapter(getChildFragmentManager()));
        TabLayout tabLayout = (TabLayout) myView.findViewById(R.id.tabs2);
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
                    CommOne t1 = new CommOne();
                    return t1;
                case 1:
                    CommTwo t2 = new CommTwo();
                    return t2;
                case 2:
                    CommThree t3 = new CommThree();
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
                    return "House";
                case 1:
                    return "Senate";
                case 2:
                    return "Joint";
            }
            return null;
        }
    }
}

