package com.example.customcalendar.views;

import android.content.Context;
import android.util.AttributeSet;
import android.view.LayoutInflater;
import android.view.View;
import android.widget.LinearLayout;

import com.example.customcalendar.R;
import com.example.customcalendar.adapter.CalendarPagerAdapter;
import com.example.customcalendar.fragments.MonthFragment;
import com.example.customcalendar.interfaces.OnDateSelectedListener;

import java.util.ArrayList;
import java.util.Calendar;
import java.util.Date;
import java.util.Locale;

import androidx.annotation.Nullable;
import androidx.appcompat.app.AppCompatActivity;
import androidx.viewpager.widget.ViewPager;

public class CustomCalendar extends LinearLayout {
    private static final String TAG = CustomCalendar.class.getSimpleName();
    private Context context;
    private ViewPager calendarPager;
    MonthFragment [] fragList = new MonthFragment[3];
    boolean shouldDecorate;
    public CalendarPagerAdapter calendarPagerAdapter;


    private Calendar currentMonth = Calendar.getInstance(Locale.ENGLISH);
    int focusPage;

    public CustomCalendar(Context context) {
        super(context);
    }

    public CustomCalendar(Context context, @Nullable AttributeSet attrs) {
        super(context, attrs);
        this.context = context;
        initializeUI();
    }



    public CustomCalendar(Context context, @Nullable AttributeSet attrs, int defStyleAttr) {
        super(context, attrs, defStyleAttr);
    }

    private void initializeUI() {
        LayoutInflater inflater = (LayoutInflater) context.getSystemService(context.LAYOUT_INFLATER_SERVICE);
        View view = inflater.inflate(R.layout.calendar_view, this);
        calendarPager = view.findViewById(R.id.calendarPager);

    }

    public void shouldDecorateWithDots(boolean shouldDecorate){
        this.shouldDecorate = shouldDecorate;
        setUpViewPager();
    }

    public void shouldDecorateWithDots(boolean shouldDecorate,ArrayList<Date> decoratedDates){
        this.shouldDecorate = shouldDecorate;
        MonthFragment.decoratedDates = decoratedDates;
        setUpViewPager();
    }

    private void setUpViewPager() {

        Calendar prevMonth = Calendar.getInstance();
        Calendar nextMonth = Calendar.getInstance();
        prevMonth.setTime(currentMonth.getTime());
        nextMonth.setTime(currentMonth.getTime());
        prevMonth.add(Calendar.MONTH,-1);
        nextMonth.add(Calendar.MONTH,1);
        MonthFragment.shouldDecorate = shouldDecorate;
        fragList[0] = MonthFragment.newInstance(prevMonth);
        fragList[1] = MonthFragment.newInstance(currentMonth);
        fragList[2] = MonthFragment.newInstance(nextMonth);
        calendarPagerAdapter = new CalendarPagerAdapter(((AppCompatActivity)getContext()).getSupportFragmentManager(),context,fragList);
        calendarPager.setAdapter(calendarPagerAdapter);
        calendarPager.setOffscreenPageLimit(3);
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



    public void setOnDateSelectedListener(OnDateSelectedListener onDateSelectedListener){
       fragList[0].setOnDateSelectedListener(onDateSelectedListener);
       fragList[1].setOnDateSelectedListener(onDateSelectedListener);
       fragList[2].setOnDateSelectedListener(onDateSelectedListener);
    }
}
