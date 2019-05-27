package com.example.customcalendar.views;

import android.os.Bundle;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.Button;

import com.example.customcalendar.R;
import com.example.customcalendar.adapter.CalendarPagerAdapter;
import com.example.customcalendar.fragments.MonthFragment;
import com.example.customcalendar.interfaces.OnDateSelectedListener;

import java.util.ArrayList;
import java.util.Calendar;
import java.util.Date;
import java.util.Locale;

import androidx.annotation.NonNull;
import androidx.annotation.Nullable;
import androidx.fragment.app.DialogFragment;
import androidx.viewpager.widget.ViewPager;

public class CustomDayPickerDialog extends DialogFragment {

    private View view;
    private ViewPager calendarPager;
    MonthFragment[] fragList = new MonthFragment[3];
    public CalendarPagerAdapter calendarPagerAdapter;
    Button btnDone,btnCancel;

    private Calendar currentMonth = Calendar.getInstance(Locale.ENGLISH);
    int focusPage;
    private String mode;


    @Nullable
    @Override
    public View onCreateView(@NonNull LayoutInflater inflater, @Nullable ViewGroup container, @Nullable Bundle savedInstanceState) {

        view = inflater.inflate(R.layout.custom_calendar_dialog,container,false);
        initialization();
        setClickListeners();
        return view;
    }

    private void initialization() {
        calendarPager = view.findViewById(R.id.calendarPager);
        btnDone = view.findViewById(R.id.btnDone1);
        btnCancel = view.findViewById(R.id.btnCancel1);
        setUpViewPager();
    }

    public void setMode(String mode) {
        this.mode = mode;
//        setUpViewPager();
        initialiseFragments();
    }

    private void initialiseFragments(){
        Calendar prevMonth = Calendar.getInstance();
        Calendar nextMonth = Calendar.getInstance();
        prevMonth.setTime(currentMonth.getTime());
        nextMonth.setTime(currentMonth.getTime());
        prevMonth.add(Calendar.MONTH,-1);
        nextMonth.add(Calendar.MONTH,1);
        fragList[0] = MonthFragment.newInstance(prevMonth);
        fragList[1] = MonthFragment.newInstance(currentMonth);
        fragList[2] = MonthFragment.newInstance(nextMonth);
    }
    private void setUpViewPager(){

        calendarPagerAdapter = new CalendarPagerAdapter(getChildFragmentManager(),getActivity(),fragList);
        calendarPager.setAdapter(calendarPagerAdapter);
        calendarPager.setCurrentItem(1,false);
        calendarPager.addOnPageChangeListener(new ViewPager.OnPageChangeListener() {
            @Override
            public void onPageScrolled(int position, float positionOffset, int positionOffsetPixels) {

            }

            @Override
            public void onPageSelected(int position) {

                focusPage = position;
            }

            @Override
            public void onPageScrollStateChanged(int state) {

                if(state == ViewPager.SCROLL_STATE_IDLE){
                    if(focusPage < 1){
                        currentMonth.add(Calendar.MONTH,-1);
                    }else if(focusPage > 1){
                        currentMonth.add(Calendar.MONTH,1);
                    }
                    calendarPagerAdapter.setCalendar(currentMonth);
                    calendarPager.setCurrentItem(1,false);

                }
            }
        });

    }

    private void setClickListeners(){

        btnCancel.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View view) {
                dismiss();
            }
        });

        btnDone.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View view) {
                ArrayList<Date> selectedDates1 = fragList[0].getSelectedDates();
                ArrayList<Date> selectedDates2 = fragList[1].getSelectedDates();
                ArrayList<Date> selectedDates3 = fragList[2].getSelectedDates();
            //    fragList[0].getOnDateSelectedListener().onSelectedDate(selectedDates1);
                fragList[1].getOnDateSelectedListener().onSelectedDate(selectedDates2);
           //     fragList[2].getOnDateSelectedListener().onSelectedDate(selectedDates3);
                dismiss();
            }
        });
    }

    public void setOnDateSelectedListener(OnDateSelectedListener onDateSelectedListener){
        if(fragList[0] !=null && fragList[1] != null && fragList[2] != null){
            fragList[0].setOnDateSelectedListener(onDateSelectedListener);
            fragList[1].setOnDateSelectedListener(onDateSelectedListener);
            fragList[2].setOnDateSelectedListener(onDateSelectedListener);
        }
    }
}

