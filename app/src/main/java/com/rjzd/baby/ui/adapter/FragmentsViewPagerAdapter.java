package com.rjzd.baby.ui.adapter;

import android.support.v4.app.Fragment;
import android.support.v4.app.FragmentManager;
import android.support.v4.app.FragmentPagerAdapter;

import java.util.List;

/**
 * create time: 2018/6/20  9:46
 * create author: Administrator
 * descriptions: viewpager适配器
 */
public class FragmentsViewPagerAdapter extends FragmentPagerAdapter {

    private List<Fragment> fragments;
   // private List<String> mChannels;
    private  String[] mChannels;
  /*  public FragmentsViewPagerAdapter(FragmentManager fm, List<Fragment> fragments, List<String> channels) {
        super(fm);
        this.fragments = fragments;
        this.mChannels = channels;
    }*/
    public FragmentsViewPagerAdapter(FragmentManager fm, List<Fragment> fragments, String[] channels) {
        super(fm);
        this.fragments = fragments;
        this.mChannels = channels;
    }
    @Override
    public Fragment getItem(int position) {
        return fragments.get(position);
    }

    @Override
    public int getCount() {
        return fragments.size();
    }

    @Override
    public CharSequence getPageTitle(int position) {
        return mChannels == null ? "" : mChannels[position];
    }

}


