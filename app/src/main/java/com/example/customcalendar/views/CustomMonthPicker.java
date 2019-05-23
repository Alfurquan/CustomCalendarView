package com.example.customcalendar.views;

import android.app.Activity;
import android.content.Context;
import android.content.ContextWrapper;
import android.util.AttributeSet;
import android.util.Log;
import android.view.LayoutInflater;
import android.view.View;
import android.widget.AdapterView;
import android.widget.GridView;
import android.widget.ImageView;
import android.widget.LinearLayout;

import com.example.customcalendar.ManagerClasses.CalendarManager;
import com.example.customcalendar.R;
import com.example.customcalendar.adapter.MonthGridAdapter;
import com.example.customcalendar.adapter.YearPagerAdapter;
import com.example.customcalendar.fragments.YearFragment;
import com.example.customcalendar.interfaces.OnMonthSelectedListener;
import com.example.customcalendar.interfaces.OnYearSelectedListener;

import java.util.ArrayList;
import java.util.Calendar;
import java.util.List;
import java.util.Locale;

import androidx.annotation.NonNull;
import androidx.annotation.Nullable;
import androidx.appcompat.app.AppCompatActivity;
import androidx.appcompat.view.ContextThemeWrapper;
import androidx.viewpager.widget.ViewPager;

public class CustomMonthPicker extends LinearLayout {
    private Context context;
    private CustomViewPager viewPager;
    YearFragment[] fragList = new YearFragment[3];
    public YearPagerAdapter yearPagerAdapter;
    MonthGridAdapter monthGridAdapter;
    GridView monthView;
    List<Integer> months;
    OnMonthSelectedListener onMonthSelectedListener;
    ArrayList<Integer> currentYears,prevYears,nextYears;
    Calendar currentYear = Calendar.getInstance(Locale.ENGLISH);
    int currentMonth;
    int selectedMonth;
    int focusPage;
    private ImageView nextButton,prevButton;
    private CalendarManager calendarManager;
    public CustomMonthPicker(Context context) {
        super(context);
    }


    public CustomMonthPicker(Context context, @Nullable AttributeSet attrs) {
        super(context, attrs);
        this.context = context;
        initializeUI();
        setUpMonthGridAdapter();
        setUpMonthGridClicks();
        setClickListeners();
    }



    public CustomMonthPicker(Context context, @Nullable AttributeSet attrs, int defStyleAttr) {
        super(context, attrs, defStyleAttr);
    }

    private void initializeUI() {
        LayoutInflater inflater = (LayoutInflater) context.getSystemService(context.LAYOUT_INFLATER_SERVICE);
        View view = inflater.inflate(R.layout.calendar_month_picker, this);
        viewPager = view.findViewById(R.id.yearPager);
        monthView = view.findViewById(R.id.monthGrid);
        nextButton = view.findViewById(R.id.nextButton);
        prevButton = view.findViewById(R.id.prevButton);
        months = new ArrayList<>();
        calendarManager = new CalendarManager(context);
        setUpMonthGrid();
        Calendar prevYearCalendar = Calendar.getInstance();
        Calendar nextYearCalendar = Calendar.getInstance();
        nextYearCalendar.add(Calendar.YEAR,12);
        prevYearCalendar.add(Calendar.YEAR,-12);
        currentYears = calendarManager.getYearList(currentYear);
        Log.d("msgCur", String.valueOf(currentYears));
        fragList[1] = YearFragment.newInstance(currentYears);
        prevYears = calendarManager.getYearList(prevYearCalendar);
        Log.d("msgPrev", String.valueOf(prevYears));
        fragList[0] = YearFragment.newInstance(prevYears);
        nextYears = calendarManager.getYearList(nextYearCalendar);
        Log.d("msgNext", String.valueOf(nextYears));
        fragList[2] = YearFragment.newInstance(nextYears);
        yearPagerAdapter = new YearPagerAdapter(((AppCompatActivity)getContext()).getSupportFragmentManager(),context,fragList);
        viewPager.setAdapter(yearPagerAdapter);
        viewPager.setCurrentItem(1,false);
        viewPager.addOnPageChangeListener(new ViewPager.OnPageChangeListener() {
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
                          currentYear.add(Calendar.YEAR,-12);

                    }else if(focusPage > 1){

                        currentYear.add(Calendar.YEAR,12);
                    }
                    yearPagerAdapter.setYears(currentYear);
                    viewPager.setCurrentItem(1,false);

                }

            }
        });

    }

    private void setUpMonthGrid() {
        Calendar calendar = Calendar.getInstance();
        currentMonth = calendar.get(Calendar.MONTH);
        selectedMonth = currentMonth;
        for(int i=0;i<=11;i++){
            months.add(i);
        }
    }

    //will try this
//    @Nullable
//    public static Activity getActivityFromContext(@NonNull Context context){
//        while (context instanceof ContextWrapper) {
//            if (context instanceof Activity) return (Activity) context;
//            context = ((ContextWrapper)context).getBaseContext();
//        }
//        return null; //we failed miserably
//    }

    private void setClickListeners() {
        nextButton.setOnClickListener(new OnClickListener() {
            @Override
            public void onClick(View view) {
                viewPager.setCurrentItem(viewPager.getCurrentItem() + 1,true);
            }
        });
        prevButton.setOnClickListener(new OnClickListener() {
            @Override
            public void onClick(View view) {
                viewPager.setCurrentItem(viewPager.getCurrentItem() - 1,true);
            }
        });
    }

    private void setUpMonthGridClicks() {

        monthView.setOnItemClickListener(new AdapterView.OnItemClickListener() {
            @Override
            public void onItemClick(AdapterView<?> adapterView, View view, int position, long l) {
                int month = monthGridAdapter.getItem(position);
                selectedMonth = month;
                setUpMonthGridAdapter();
                onMonthSelectedListener.onSelectedMonth(selectedMonth);
            }
        });
    }

    private void setUpMonthGridAdapter() {
        monthGridAdapter = new MonthGridAdapter(context,months,currentMonth,selectedMonth);
        monthView.setAdapter(monthGridAdapter);
    }

    public void setOnMonthSelectedListener(OnMonthSelectedListener onMonthSelectedListener){
        this.onMonthSelectedListener = onMonthSelectedListener;
    }

    public void setOnYearSelectedListener(OnYearSelectedListener onYearSelectedListener){
        YearFragment.onYearSelectedListener = onYearSelectedListener;
    }
    public int getSelectedMonth(){
        return selectedMonth;
    }

    public int getSelectedYear(){
        return YearFragment.selectedYear;
    }
}
