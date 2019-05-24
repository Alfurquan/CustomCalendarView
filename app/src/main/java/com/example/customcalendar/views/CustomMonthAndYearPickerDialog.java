package com.example.customcalendar.views;

import android.app.Dialog;
import android.content.Context;
import android.os.Bundle;
import android.util.Log;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.AdapterView;
import android.widget.Button;
import android.widget.GridView;
import android.widget.ImageView;

import com.example.customcalendar.ManagerClasses.CalendarManager;
import com.example.customcalendar.R;
import com.example.customcalendar.adapter.MonthGridAdapter;
import com.example.customcalendar.adapter.YearPagerAdapter;
import com.example.customcalendar.fragments.MonthFragment;
import com.example.customcalendar.fragments.YearFragment;
import com.example.customcalendar.interfaces.OnMonthSelectedListener;
import com.example.customcalendar.interfaces.OnYearSelectedListener;

import java.util.ArrayList;
import java.util.Calendar;
import java.util.List;
import java.util.Locale;

import androidx.annotation.NonNull;
import androidx.annotation.Nullable;
import androidx.fragment.app.DialogFragment;
import androidx.viewpager.widget.ViewPager;

public class CustomMonthAndYearPickerDialog extends DialogFragment {


   private CustomViewPager viewPager;
   private YearPagerAdapter adapter;
   private MonthGridAdapter monthGridAdapter;
   private GridView monthGrid;
   private YearFragment[] fragList = new YearFragment[3];
    List<Integer> months;
   private Button btnDone,btnCancel;
   private OnMonthSelectedListener onMonthSelectedListener;
   private View view;
    ArrayList<Integer> currentYears,prevYears,nextYears;
    Calendar currentYear = Calendar.getInstance(Locale.ENGLISH);
    int currentMonth;
    int selectedMonth;
    int focusPage;
    private ImageView nextButton,prevButton;
    private CalendarManager calendarManager;

    @Nullable
    @Override
    public View onCreateView(@NonNull LayoutInflater inflater, @Nullable ViewGroup container, @Nullable Bundle savedInstanceState) {

        view = inflater.inflate(R.layout.custom_month_dialog,container,false);
        initialization();
        setUpMonthGridAdapter();
        setUpViewPager();
        return view;
    }

    private void initialization() {
        viewPager = view.findViewById(R.id.yearPager);
        monthGrid = view.findViewById(R.id.monthGrid);
        btnDone = view.findViewById(R.id.btnDone1);
        btnCancel = view.findViewById(R.id.btnCancel1);
        nextButton = view.findViewById(R.id.nextButton);
        prevButton = view.findViewById(R.id.prevButton);
        months = new ArrayList<>();
        calendarManager = new CalendarManager(getActivity());
        setUpMonthGrid();
        setOnClickListeners();
        setUpMonthGridClicks();
    }

    private void setUpMonthGrid() {
        Calendar calendar = Calendar.getInstance();
        currentMonth = calendar.get(Calendar.MONTH);
        selectedMonth = currentMonth;
        for(int i=0;i<=11;i++){
            months.add(i);
        }
    }
    private void setUpMonthGridAdapter() {
        monthGridAdapter = new MonthGridAdapter(getActivity(),months,currentMonth,selectedMonth);
        monthGrid.setAdapter(monthGridAdapter);
    }
    private void setUpViewPager() {
        Calendar prevYearCalendar = Calendar.getInstance();
        Calendar nextYearCalendar = Calendar.getInstance();
        nextYearCalendar.add(Calendar.YEAR, 12);
        prevYearCalendar.add(Calendar.YEAR, -12);
        currentYears = calendarManager.getYearList(currentYear);
        Log.d("msgCur", String.valueOf(currentYears));
        fragList[1] = YearFragment.newInstance(currentYears);
        prevYears = calendarManager.getYearList(prevYearCalendar);
        Log.d("msgPrev", String.valueOf(prevYears));
        fragList[0] = YearFragment.newInstance(prevYears);
        nextYears = calendarManager.getYearList(nextYearCalendar);
        Log.d("msgNext", String.valueOf(nextYears));
        fragList[2] = YearFragment.newInstance(nextYears);
        adapter = new YearPagerAdapter(getChildFragmentManager(), getActivity(), fragList);
        viewPager.setAdapter(adapter);
        viewPager.setCurrentItem(1, false);
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
                if (state == ViewPager.SCROLL_STATE_IDLE) {
                    if (focusPage < 1) {
                        currentYear.add(Calendar.YEAR, -12);

                    } else if (focusPage > 1) {

                        currentYear.add(Calendar.YEAR, 12);
                    }
                    adapter.setYears(currentYear);
                    viewPager.setCurrentItem(1, false);

                }

            }
        });
    }
        private void setUpMonthGridClicks() {

            monthGrid.setOnItemClickListener(new AdapterView.OnItemClickListener() {
                @Override
                public void onItemClick(AdapterView<?> adapterView, View view, int position, long l) {
                    int month = monthGridAdapter.getItem(position);
                    selectedMonth = month;
                    setUpMonthGridAdapter();
                }
            });
        }


    private void setOnClickListeners(){
        nextButton.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View view) {
                viewPager.setCurrentItem(viewPager.getCurrentItem() + 1,true);
            }
        });
        prevButton.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View view) {
                viewPager.setCurrentItem(viewPager.getCurrentItem() - 1,true);
            }
        });

        btnCancel.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View view) {
                dismiss();
            }
        });

        btnDone.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View view) {
                onMonthSelectedListener.onSelectedMonth(selectedMonth);
                int selectedYear = YearFragment.selectedYear;
                YearFragment.onYearSelectedListener.onYearSelected(selectedYear);
                dismiss();
            }
        });
    }

    public void setOnMonthSelectedListener(OnMonthSelectedListener onMonthSelectedListener){
        this.onMonthSelectedListener = onMonthSelectedListener;
    }

    public void setOnYearSelectedListener(OnYearSelectedListener onYearSelectedListener){
        YearFragment.onYearSelectedListener = onYearSelectedListener;
    }

}
