package com.example.customcalendar.views;

import android.content.Context;
import android.util.AttributeSet;
import android.view.LayoutInflater;
import android.view.View;
import android.widget.LinearLayout;

import com.example.customcalendar.ManagerClasses.CalendarManager;
import com.example.customcalendar.R;
import com.example.customcalendar.adapter.CalendarRecyclerAdapter;

import java.util.ArrayList;
import java.util.Calendar;
import java.util.Date;

import androidx.annotation.Nullable;
import androidx.recyclerview.widget.LinearLayoutManager;
import androidx.recyclerview.widget.PagerSnapHelper;
import androidx.recyclerview.widget.RecyclerView;
import androidx.recyclerview.widget.SnapHelper;

public class CustomCalendarView extends LinearLayout {

    private Context context;
    private RecyclerView monthRecycler;
    private CalendarRecyclerAdapter adapter;
    private ArrayList<Calendar> months;
    private ArrayList<Date> selectedDates;
    private boolean shouldDecorate;
    private ArrayList<Date> decoratedDates;
    private CalendarManager calendarManager;
    public CustomCalendarView(Context context) {
        super(context);
    }

    public CustomCalendarView(Context context, @Nullable AttributeSet attrs) {
        super(context, attrs);
        this.context = context;
        calendarManager = new CalendarManager(context);

    }



    public CustomCalendarView(Context context, @Nullable AttributeSet attrs, int defStyleAttr) {
        super(context, attrs, defStyleAttr);
    }

    private void initializeUI() {

        LayoutInflater inflater = (LayoutInflater) context.getSystemService(context.LAYOUT_INFLATER_SERVICE);
        View view = inflater.inflate(R.layout.custom_calendar_view, this);
        monthRecycler = view.findViewById(R.id.monthRecycler);
        months = new ArrayList<>();
        selectedDates = new ArrayList<>();
        decoratedDates = new ArrayList<>();
        Date date = calendarManager.getCurrentDateAsDate();
        selectedDates.add(date);
    }

    private void setUpRecycler(){

        months = calendarManager.getAllMonthList();
        int pos = calendarManager.pos;
        LinearLayoutManager layoutManager = new LinearLayoutManager(context, LinearLayoutManager.HORIZONTAL, false);
        monthRecycler.setLayoutManager(layoutManager);
        layoutManager.scrollToPositionWithOffset(pos,0);
        adapter = new CalendarRecyclerAdapter(context,months,selectedDates,shouldDecorate,decoratedDates,monthRecycler);
        monthRecycler.setAdapter(adapter);
        SnapHelper snapHelper = new PagerSnapHelper();
        snapHelper.attachToRecyclerView(monthRecycler);
    }

    public void shouldDecorate(boolean shouldDecorate){
        this.shouldDecorate = shouldDecorate;
        initializeUI();
        setUpRecycler();
    }
}
