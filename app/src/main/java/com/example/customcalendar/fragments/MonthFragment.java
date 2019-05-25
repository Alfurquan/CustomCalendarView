package com.example.customcalendar.fragments;

import android.annotation.SuppressLint;
import android.graphics.drawable.ColorDrawable;
import android.os.Bundle;
import android.util.Log;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.AdapterView;
import android.widget.GridView;
import android.widget.ImageView;
import android.widget.TextView;

import com.example.customcalendar.MainActivity;
import com.example.customcalendar.R;
import com.example.customcalendar.adapter.CalendarGridAdapter;
import com.example.customcalendar.adapter.CalendarPagerAdapter;
import com.example.customcalendar.interfaces.OnDateSelectedListener;
import com.example.customcalendar.interfaces.OnMonthAndYearSelectedListener;
import com.example.customcalendar.interfaces.OnMonthSelectedListener;
import com.example.customcalendar.interfaces.OnYearSelectedListener;
import com.example.customcalendar.views.CustomMonthAndYearPickerDialog;

import java.text.SimpleDateFormat;
import java.util.ArrayList;
import java.util.Calendar;
import java.util.Date;
import java.util.List;
import java.util.Locale;

import androidx.annotation.NonNull;
import androidx.annotation.Nullable;
import androidx.fragment.app.Fragment;
import androidx.fragment.app.FragmentTransaction;
import androidx.viewpager.widget.ViewPager;

public class MonthFragment extends Fragment {

    View view;
    Calendar displayedMonth;
    GridView calendarGrid;
    CalendarGridAdapter calendarGridAdapter;
    TextView currentMonthText;
    ViewPager calendarPager;
    ImageView next,prev;
    CalendarPagerAdapter calendarPagerAdapter;
    int selectedYear,selectedMonth;
    static ArrayList<Date> selectedDates = new ArrayList<>();
    public static OnDateSelectedListener onDateSelectedListener;
    private static final int MAX_CALENDAR_COLUMN = 42;
    private SimpleDateFormat formatter = new SimpleDateFormat("MMMM yyyy", Locale.ENGLISH);


    public MonthFragment() {
    }

    @SuppressLint("ValidFragment")
    public MonthFragment(Calendar displayedMonth) {
        this.displayedMonth = displayedMonth;
    }

    public static MonthFragment newInstance(Calendar month) {
        return new MonthFragment(month);
    }


    @Nullable
    @Override
    public View onCreateView(@NonNull LayoutInflater inflater, @Nullable ViewGroup container, @Nullable Bundle savedInstanceState) {

        view = inflater.inflate(R.layout.calendar_layout,container,false);
        calendarGrid = view.findViewById(R.id.calendar_grid);
        currentMonthText = view.findViewById(R.id.currentMonth);
        calendarPager = getActivity().findViewById(R.id.calendarPager);
        next = view.findViewById(R.id.nextButton);
        prev = view.findViewById(R.id.prevButton);
        calendarPagerAdapter = (CalendarPagerAdapter) calendarPager.getAdapter();
        setUpAdapter();
        setGridCellClicks();
        setClickListeners();
        return view;
    }

    private void setGridCellClicks() {

        calendarGrid.setOnItemClickListener(new AdapterView.OnItemClickListener() {
            @Override
            public void onItemClick(AdapterView<?> adapterView, View view, int position, long id) {
                Date sel = calendarGridAdapter.getItem(position);
                selectedDates.add(sel);
                onDateSelectedListener.onSelectedDate(selectedDates);
                setUpAdapter();

            }
        });
    }

    public void updateUI(Calendar month) {
        this.displayedMonth = month;
        setUpAdapter();
    }

    private void setUpAdapter(){
        List<Date> dayValueInCells = new ArrayList<Date>();
        Calendar mCal = (Calendar)displayedMonth.clone();
        mCal.set(Calendar.DAY_OF_MONTH, 1);
        int firstDayOfTheMonth = mCal.get(Calendar.DAY_OF_WEEK) - 1;
        mCal.add(Calendar.DAY_OF_MONTH, -firstDayOfTheMonth);
        while(dayValueInCells.size() < MAX_CALENDAR_COLUMN){
            dayValueInCells.add(mCal.getTime());
            mCal.add(Calendar.DAY_OF_MONTH, 1);
        }
        updateTitle();
        calendarGridAdapter = new CalendarGridAdapter(getActivity(), dayValueInCells, displayedMonth,selectedDates);
        calendarGrid.setAdapter(calendarGridAdapter);
    }

    public  Calendar getDisplayedMonth(){
        return displayedMonth;
    }
    private void updateTitle() {
        String sDate = formatter.format(displayedMonth.getTime());
        currentMonthText.setText(sDate);
    }

    private void setClickListeners() {
        prev.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View view) {
                calendarPager.setCurrentItem(calendarPager.getCurrentItem()-1,true);
                updateTitle();
            }
        });
        next.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View view) {
                calendarPager.setCurrentItem(calendarPager.getCurrentItem()+1,true);
                updateTitle();
            }
        });


        currentMonthText.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View view) {
                CustomMonthAndYearPickerDialog dialog = new CustomMonthAndYearPickerDialog();
                FragmentTransaction ft = getActivity().getSupportFragmentManager().beginTransaction();
                Fragment prev = getActivity().getSupportFragmentManager().findFragmentByTag("dialog");
                if (prev != null) {
                    ft.remove(prev);
                }
                ft.addToBackStack(null);
                dialog.show(ft, "dialog");


                dialog.setOnMonthAndYearSelectedListener(new OnMonthAndYearSelectedListener() {
                    @Override
                    public void onMonthAndYearSelected(int month, int year) {
                        Log.d("msgMy", String.valueOf(month));
                        Log.d("msgMy", String.valueOf(year));
                        goToMonthAndYear(month,year);
                    }
                });
            }
        });
    }

    private void goToMonthAndYear(int selectedMonth, int selectedYear) {

        displayedMonth.set(Calendar.MONTH,selectedMonth);
        displayedMonth.set(Calendar.YEAR,selectedYear);
        calendarPagerAdapter.setCalendar(displayedMonth);
    }
}
