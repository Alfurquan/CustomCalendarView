package com.example.customcalendar.adapter;

import android.content.Context;

import com.example.customcalendar.ManagerClasses.CalendarManager;
import com.example.customcalendar.fragments.YearFragment;

import java.util.ArrayList;
import java.util.Calendar;

import androidx.fragment.app.Fragment;
import androidx.fragment.app.FragmentManager;
import androidx.fragment.app.FragmentPagerAdapter;

public class YearPagerAdapter extends FragmentPagerAdapter {

    private Context context;
    YearFragment[] fragList;
    private CalendarManager calendarManager;

    public YearPagerAdapter(FragmentManager fm,Context context,YearFragment[] fragList) {
        super(fm);
        this.context = context;
        this.fragList = fragList;
        calendarManager = new CalendarManager(context);
    }

    @Override
    public Fragment getItem(int position) {
        return fragList[position];
    }

    @Override
    public int getCount() {
        return 3;
    }

    public void setYears(Calendar currentYear){

        Calendar prevYear = Calendar.getInstance();
        prevYear.setTime(currentYear.getTime());
        prevYear.add(Calendar.MONTH,-12);
        int prev = prevYear.get(Calendar.YEAR);

        Calendar nextYear = Calendar.getInstance();
        nextYear.setTime(currentYear.getTime());
        nextYear.add(Calendar.MONTH, 12);
        int next = nextYear.get(Calendar.YEAR);
        int cur = currentYear.get(Calendar.YEAR);

        ArrayList<Integer> prevYears,currentYears,nextYears;
        prevYears = calendarManager.getYearList(prevYear);
        nextYears = calendarManager.getYearList(nextYear);
        currentYears = calendarManager.getYearList(currentYear);

        fragList[0].updateUI(prevYears);
        fragList[1].updateUI(currentYears);
        fragList[2].updateUI(nextYears);

    }
}
