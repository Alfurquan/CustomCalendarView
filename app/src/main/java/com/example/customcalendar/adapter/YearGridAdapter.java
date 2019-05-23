package com.example.customcalendar.adapter;

import android.content.Context;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.ArrayAdapter;
import android.widget.TextView;

import com.example.customcalendar.R;

import java.util.List;

import androidx.annotation.NonNull;
import androidx.annotation.Nullable;

public class YearGridAdapter extends ArrayAdapter {

    private LayoutInflater mInflater;
    private List<Integer> years;
    private Context context;
    private int currentYear;
    private int selectedYear;

    public YearGridAdapter(Context context,List<Integer> years,int currentYear,int selectedYear){
        super(context, R.layout.single_month_layout);
        this.context = context;
        this.years = years;
        this.selectedYear = selectedYear;
        this.currentYear = currentYear;
        mInflater = LayoutInflater.from(context);
    }

    @NonNull
    @Override
    public View getView(int position, @Nullable View convertView, @NonNull ViewGroup parent) {
        int year = years.get(position);

        View view = convertView;
        if(view == null){
            view = mInflater.inflate(R.layout.single_month_layout, parent, false);
        }
        TextView cellNumber = view.findViewById(R.id.calendar_month_id);
        cellNumber.setText(String.valueOf(year));

        if(year == selectedYear){
            cellNumber.setBackgroundColor(context.getResources().getColor(R.color.colorAccent));
            cellNumber.setTextColor(context.getResources().getColor(R.color.white));
        }

        return view;
    }

    @Override
    public int getCount() {
        return years.size();
    }

    @Nullable
    @Override
    public Integer getItem(int position) {
        return years.get(position);
    }
}
