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
 * Created by vaisakh on 11/16/16.
 */

public class BillMain extends Fragment {

    View myView;

    private SectionsPagerAdapter mSectionsPagerAdapter;
    private ViewPager mViewPager;
    FragmentActivity myContext;
    @Nullable
    @Override
    public View onCreateView(LayoutInflater inflater, ViewGroup container, Bundle savedInstanceState) {
        myView = inflater.inflate(R.layout.bill_main, container,false);

        //android.app.FragmentManager fragmentManager = getFragmentManager();
        mSectionsPagerAdapter = new SectionsPagerAdapter(myContext.getSupportFragmentManager());

        // Set up the ViewPager with the sections adapter.
        mViewPager = (ViewPager) myView.findViewById(R.id.container1);
       // mViewPager.setOffscreenPageLimit(2);
        mViewPager.setAdapter(new SectionsPagerAdapter(getChildFragmentManager()));

        TabLayout tabLayout = (TabLayout) myView.findViewById(R.id.tabs1);
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
        public Fragment getItem(int position) {
            switch(position) {
                case 0:
                    BillOne b1 = new BillOne();
                    return b1;
                case 1:
                    BillTwo b2 = new BillTwo();
                    return b2;
                default:
                    return null;
            }
        }

        @Override
        public int getCount() {

            return 2;
        }

        @Override
        public CharSequence getPageTitle(int position) {
            switch (position) {
                case 0:
                    return "Active Bills";
                case 1:
                    return "New Bills";
            }
            return null;
        }
    }
}

