package com.example.customcalendar.adapter;

import android.content.Context;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.TextView;

import com.example.customcalendar.R;

import java.util.ArrayList;

import androidx.annotation.NonNull;
import androidx.recyclerview.widget.RecyclerView;

public class YearRecyclerAdapter extends RecyclerView.Adapter<YearRecyclerAdapter.YearViewHolder> {

    private Context context;
    private ArrayList<Integer> yearList;
    private int selectedYear;

    public YearRecyclerAdapter(Context context, ArrayList<Integer> yearList,int selectedYear) {
        this.context = context;
        this.yearList = yearList;
        this.selectedYear = selectedYear;
    }

    @NonNull
    @Override
    public YearViewHolder onCreateViewHolder(@NonNull ViewGroup parent, int viewType) {
       View view = LayoutInflater.from(context).inflate(R.layout.single_year_adapter,parent,false);

       return new YearViewHolder(view);
    }

    @Override
    public void onBindViewHolder(@NonNull YearViewHolder holder, int position) {

        int year = yearList.get(position);
        holder.yearText.setText(String.valueOf(year));
        if(selectedYear == year){
            holder.yearText.setBackgroundColor(context.getResources().getColor(R.color.colorAccent));
            holder.yearText.setTextColor(context.getResources().getColor(R.color.white));
        }else
        {
            holder.yearText.setBackgroundColor(context.getResources().getColor(R.color.white));
            holder.yearText.setTextColor(context.getResources().getColor(R.color.grey));
        }

        setClickListeners(holder,position);
    }

    private void setClickListeners(YearViewHolder holder, final int position) {

        holder.yearText.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View view) {
                selectedYear = yearList.get(position);
                notifyDataSetChanged();
            }
        });

    }

    @Override
    public int getItemCount() {
        return yearList.size();
    }

    public int getSelectedYear() {
        return selectedYear;
    }

    public class YearViewHolder extends RecyclerView.ViewHolder {
        TextView yearText;
        public YearViewHolder(@NonNull View view) {
            super(view);
            yearText = view.findViewById(R.id.yearText);
        }
    }
}
