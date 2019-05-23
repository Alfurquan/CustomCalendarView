package com.example.customcalendar.adapter;

import android.content.Context;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.ArrayAdapter;
import android.widget.TextView;

import com.example.customcalendar.ManagerClasses.CalendarManager;
import com.example.customcalendar.R;

import java.util.List;

import androidx.annotation.NonNull;
import androidx.annotation.Nullable;

public class MonthGridAdapter extends ArrayAdapter {

    private LayoutInflater mInflater;
    private List<Integer> months;
    private Context context;
    private int currentMonth;
    private int selectedMonth;
    private CalendarManager calendarManager;

    public MonthGridAdapter(Context context, List<Integer> months,int currentMonth,int selectedMonth){
        super(context, R.layout.single_month_layout);
        this.context = context;
        this.months = months;
        this.currentMonth = currentMonth;
        this.selectedMonth = selectedMonth;
        calendarManager = new CalendarManager(context);
        mInflater = LayoutInflater.from(context);
    }

    @NonNull
    @Override
    public View getView(int position, @Nullable View convertView, @NonNull ViewGroup parent) {

        int month = months.get(position);
        String monthName = calendarManager.getMonthForInt(month);
        String selectedMonthName = calendarManager.getMonthForInt(selectedMonth);
        View view = convertView;
        if(view == null){
            view = mInflater.inflate(R.layout.single_month_layout, parent, false);
        }
        TextView cellNumber = (TextView)view.findViewById(R.id.calendar_month_id);
        cellNumber.setText(monthName);

        if(monthName.equals(selectedMonthName)){
            cellNumber.setBackgroundColor(context.getResources().getColor(R.color.colorAccent));
            cellNumber.setTextColor(context.getResources().getColor(R.color.white));
        }

        return view;
    }

    @Override
    public int getCount() {
        return months.size();
    }

    @Nullable
    @Override
    public Integer getItem(int position) {
       return months.get(position);
    }


}
