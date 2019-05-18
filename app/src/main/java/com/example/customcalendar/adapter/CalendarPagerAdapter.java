package com.example.customcalendar.adapter;


import android.content.Context;


import com.example.customcalendar.fragments.MonthFragment;

import java.util.Calendar;

import androidx.annotation.NonNull;
import androidx.fragment.app.Fragment;
import androidx.fragment.app.FragmentManager;
import androidx.fragment.app.FragmentPagerAdapter;

public class CalendarPagerAdapter extends FragmentPagerAdapter {

    Context context;
    MonthFragment [] fragList;

    public CalendarPagerAdapter(FragmentManager fm,Context context,MonthFragment[] fragList) {
        super(fm);
        this.context = context;
        this.fragList = fragList;
    }


    @Override
    public Fragment getItem(int position) {
        return fragList[position];
    }

    @Override
    public int getCount() {
        return 3;
    }

    @Override
    public int getItemPosition(@NonNull Object object) {
        return super.getItemPosition(object);
    }
    public void setCalendar(Calendar currentMonth){

        Calendar prevMonth = Calendar.getInstance();
        prevMonth.setTime(currentMonth.getTime());
        prevMonth.add(Calendar.MONTH,-1);

        Calendar nextMonth = Calendar.getInstance();
        nextMonth.setTime(currentMonth.getTime());
        nextMonth.add(Calendar.MONTH, 1);

        fragList[0].updateUI(prevMonth);
        fragList[1].updateUI(currentMonth);
        fragList[2].updateUI(nextMonth);
    }
}
