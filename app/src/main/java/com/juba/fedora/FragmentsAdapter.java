package com.juba.fedora;

import android.support.annotation.Nullable;
import android.support.v4.app.Fragment;
import android.support.v4.app.FragmentManager;
import android.support.v4.app.FragmentStatePagerAdapter;

public class FragmentsAdapter extends FragmentStatePagerAdapter {
    public FragmentsAdapter(MainActivity mainActivity, FragmentManager fm) {
        super(fm);
    }

    @Override
    public Fragment getItem(int position) {

        if (position == 0)
        {
            return new ChatsFragment();
        } else if (position == 1)
        {
            return new GroupsFragment();
        }
        return null;

    }

    //determine number of fragments
    @Override
    public int getCount() {

        return 2;
    }

    //determine title of each tab

    @Nullable
    @Override
    public CharSequence getPageTitle(int position) {
        switch (position) {
            case 0:
                return "Chats";
            case 1:
                return "Groups";


            default:
                return null;


        }

    }
}


