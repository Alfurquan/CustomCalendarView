package com.example.customcalendar.fragments;

import android.annotation.SuppressLint;
import android.os.Bundle;
import android.util.Log;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.AdapterView;
import android.widget.GridView;

import com.example.customcalendar.R;
import com.example.customcalendar.adapter.CalendarGridAdapter;
import com.example.customcalendar.interfaces.OnDateSelectedListener;

import java.util.ArrayList;
import java.util.Calendar;
import java.util.Date;
import java.util.List;

import androidx.annotation.NonNull;
import androidx.annotation.Nullable;
import androidx.fragment.app.Fragment;

public class MonthFragment extends Fragment {

    View view;
    Calendar displayedMonth;
    GridView calendarGrid;
    CalendarGridAdapter calendarGridAdapter;
    static ArrayList<Date> selectedDates = new ArrayList<>();
    public static OnDateSelectedListener onDateSelectedListener;
    private static final int MAX_CALENDAR_COLUMN = 42;

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
        setUpAdapter();
        setGridCellClicks();
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

        calendarGridAdapter = new CalendarGridAdapter(getActivity(), dayValueInCells, displayedMonth,selectedDates);
        calendarGrid.setAdapter(calendarGridAdapter);
    }

    public  Calendar getDisplayedMonth(){
        return displayedMonth;
    }



}
