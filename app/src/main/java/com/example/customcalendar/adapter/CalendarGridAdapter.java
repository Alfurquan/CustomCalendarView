package com.example.customcalendar.adapter;

import android.content.Context;
import android.graphics.Color;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.ArrayAdapter;
import android.widget.ImageView;
import android.widget.TextView;

import com.example.customcalendar.ManagerClasses.CalendarManager;
import com.example.customcalendar.R;

import java.util.ArrayList;
import java.util.Calendar;
import java.util.Date;
import java.util.List;

import androidx.annotation.NonNull;
import androidx.annotation.Nullable;

public class CalendarGridAdapter extends ArrayAdapter {
    private LayoutInflater mInflater;
    private List<Date> monthlyDates;
    private Calendar currentDate;
    private Context context;
    private ArrayList<Date> selectedDates;
    private boolean shouldDecorate;
    private ArrayList<Date> decoratedDates;
    private ArrayList<String> decorDates;
    private CalendarManager calendarManager;

    public CalendarGridAdapter(Context context, List<Date> monthlyDates, Calendar currentDate,ArrayList<Date> selectedDates,boolean shouldDecorate,ArrayList<Date> decoratedDates ){
        super(context, R.layout.single_cell_layout);
        this.monthlyDates = monthlyDates;
        this.currentDate = currentDate;
        this.context = context;
        this.selectedDates = selectedDates;
        this.shouldDecorate = shouldDecorate;
        this.decoratedDates = decoratedDates;
        calendarManager = new CalendarManager(context);
        decorDates = new ArrayList<>();
        mInflater = LayoutInflater.from(context);
    }

    @NonNull
    @Override
    public View getView(int position, @Nullable View convertView, @NonNull ViewGroup parent) {
        Date mDate = monthlyDates.get(position);
        Calendar dateCal = Calendar.getInstance();
        dateCal.setTime(mDate);
        String curDate = calendarManager.convertDateToString(mDate);
        int dayValue = dateCal.get(Calendar.DAY_OF_MONTH);
        int displayMonth = dateCal.get(Calendar.MONTH) + 1;
        int displayYear = dateCal.get(Calendar.YEAR);
        int currentMonth = currentDate.get(Calendar.MONTH) + 1;
        int currentYear = currentDate.get(Calendar.YEAR);
        if(shouldDecorate){
            for(Date date:decoratedDates){
                String selected = calendarManager.convertDateToString(date);
                decorDates.add(selected);
            }
        }
        View view = convertView;
        if(view == null){
            view = mInflater.inflate(R.layout.single_cell_layout, parent, false);
        }
        //Add day to calendar
        TextView cellNumber = (TextView)view.findViewById(R.id.calendar_date_id);
        ImageView dots = view.findViewById(R.id.dots);
        cellNumber.setText(String.valueOf(dayValue));
        if(displayMonth == currentMonth && displayYear == currentYear){
           cellNumber.setTextColor(context.getResources().getColor(R.color.black));
        }else{
            cellNumber.setTextColor(context.getResources().getColor(R.color.grey));
        }
        if(selectedDates.contains(mDate)){
            cellNumber.setBackgroundColor(context.getResources().getColor(R.color.colorAccent));
            cellNumber.setTextColor(context.getResources().getColor(R.color.white));
        }

        if(shouldDecorate  && decorDates.contains(curDate)){
            dots.setVisibility(View.VISIBLE);
        }else{
            dots.setVisibility(View.GONE);
        }

        return view;
    }

    @Override
    public int getCount() {
        return monthlyDates.size();
    }

    @Nullable
    @Override
    public Date getItem(int position) {
        return monthlyDates.get(position);
    }

    @Override
    public int getPosition(@Nullable Object item) {
        return monthlyDates.indexOf(item);
    }
}
