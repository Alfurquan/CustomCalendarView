package com.example.customcalendar.adapter;

import android.content.Context;
import android.util.Log;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.GridView;
import android.widget.ImageView;
import android.widget.TextView;

import com.example.customcalendar.R;

import java.text.SimpleDateFormat;
import java.util.ArrayList;
import java.util.Calendar;
import java.util.Date;
import java.util.List;
import java.util.Locale;

import androidx.annotation.NonNull;
import androidx.recyclerview.widget.RecyclerView;

public class CalendarRecyclerAdapter extends RecyclerView.Adapter<CalendarRecyclerAdapter.MyCalendarViewHolder> {

    private Context context;
    private ArrayList<Calendar> months;
    private SimpleDateFormat formatter = new SimpleDateFormat("MMMM yyyy", Locale.ENGLISH);
    private ArrayList<Date> decoratedDates;
    private ArrayList<Date> selectedDates;
    private boolean shouldDecorate;
    private CalendarGridAdapter adapter;
    private RecyclerView recyclerView;


    public CalendarRecyclerAdapter(Context context, ArrayList<Calendar> months,ArrayList<Date> selectedDates,boolean shouldDecorate,ArrayList<Date> decoratedDates,RecyclerView recyclerView) {
        this.context = context;
        this.months = months;
        this.selectedDates = selectedDates;
        this.decoratedDates = decoratedDates;
        this.shouldDecorate = shouldDecorate;
        this.recyclerView = recyclerView;
    }

//    @Override
//    public void onAttachedToRecyclerView(@NonNull RecyclerView recyclerView) {
//        super.onAttachedToRecyclerView(recyclerView);
//        recyclerView = recyclerView;
//    }

    @NonNull
    @Override
    public MyCalendarViewHolder onCreateViewHolder(@NonNull ViewGroup parent, int viewType) {
        View view = LayoutInflater.from(context).inflate(R.layout.calendar_layout,parent,false);

        return new MyCalendarViewHolder(view);
    }

    @Override
    public void onBindViewHolder(@NonNull MyCalendarViewHolder holder, int position) {

        Calendar displayedMonth = months.get(position);
        String sDate = formatter.format(displayedMonth.getTime());
        holder.currentMonthTextView.setText(sDate);
        setUpGridAdapter(displayedMonth,holder);
        setUpClicks(holder,position);

    }

    private void setUpClicks(MyCalendarViewHolder holder, final int position) {
        holder.nextBtn.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View view) {
                recyclerView.smoothScrollToPosition(position + 1);
            }
        });

        holder.prevBtn.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View view) {
                recyclerView.smoothScrollToPosition(position - 1);
            }
        });
    }

    private void setUpGridAdapter(Calendar displayedMonth, MyCalendarViewHolder holder) {
        List<Date> dayValueInCells = new ArrayList<Date>();
        Calendar mCal = (Calendar)displayedMonth.clone();
        mCal.set(Calendar.DAY_OF_MONTH, 1);
        int myMonth = mCal.get(Calendar.MONTH);
        Log.d("Msg", String.valueOf(mCal.get(Calendar.DAY_OF_WEEK)));
        int firstDayOfTheMonth = mCal.get(Calendar.DAY_OF_WEEK) - 1;
        mCal.add(Calendar.DAY_OF_MONTH, -firstDayOfTheMonth);
        while(dayValueInCells.size() < 42){
            dayValueInCells.add(mCal.getTime());
            mCal.add(Calendar.DAY_OF_MONTH, 1);
        }
        adapter = new CalendarGridAdapter(context,dayValueInCells,displayedMonth,selectedDates,shouldDecorate,decoratedDates);
        holder.calendarGrid.setAdapter(adapter);
    }

    @Override
    public int getItemCount() {
        return months.size();
    }

    public class MyCalendarViewHolder extends RecyclerView.ViewHolder {

        ImageView prevBtn,nextBtn;
        TextView currentMonthTextView;
        GridView calendarGrid;
        public MyCalendarViewHolder(@NonNull View view) {
            super(view);
            prevBtn = view.findViewById(R.id.prevButton);
            nextBtn = view.findViewById(R.id.nextButton);
            currentMonthTextView = view.findViewById(R.id.currentMonth);
            calendarGrid = view.findViewById(R.id.calendar_grid);
        }
    }
}
