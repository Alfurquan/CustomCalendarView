package com.example.customcalendar.views;

import android.graphics.Color;
import android.graphics.drawable.ColorDrawable;
import android.os.Bundle;
import android.util.Log;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.AdapterView;
import android.widget.Button;
import android.widget.GridView;
import android.widget.ImageView;
import android.widget.TextView;

import com.example.customcalendar.ManagerClasses.CalendarManager;
import com.example.customcalendar.R;
import com.example.customcalendar.adapter.MonthGridAdapter;
import com.example.customcalendar.adapter.YearRecyclerAdapter;
import com.example.customcalendar.fragments.YearFragment;
import com.example.customcalendar.interfaces.OnMonthAndYearSelectedListener;
import com.example.customcalendar.interfaces.OnMonthSelectedListener;
import com.example.customcalendar.interfaces.OnYearSelectedListener;

import java.util.ArrayList;
import java.util.Calendar;
import java.util.List;

import androidx.annotation.NonNull;
import androidx.annotation.Nullable;
import androidx.fragment.app.DialogFragment;
import androidx.recyclerview.widget.LinearLayoutManager;
import androidx.recyclerview.widget.PagerSnapHelper;
import androidx.recyclerview.widget.RecyclerView;
import androidx.recyclerview.widget.SnapHelper;

public class CustomMonthAndYearPickerDialog extends DialogFragment {



   private MonthGridAdapter monthGridAdapter;
   private GridView monthGrid;
   private List<Integer> months;
   private Button btnDone,btnCancel;
   private OnMonthSelectedListener onMonthSelectedListener;
   private OnMonthAndYearSelectedListener onMonthAndYearSelectedListener;
   private View view;
   private ArrayList<Integer> years;
   private RecyclerView yearRecycler;
   private YearRecyclerAdapter adapter;
   private int currentMonth,selectedMonth,selectedYear;
    private ImageView nextButton,prevButton;
    private CalendarManager calendarManager;
    private TextView monthName,yearName;
    private SnapHelper snapHelper;
    int position;

    @Nullable
    @Override
    public View onCreateView(@NonNull LayoutInflater inflater, @Nullable ViewGroup container, @Nullable Bundle savedInstanceState) {

        view = inflater.inflate(R.layout.custom_month_dialog,container,false);
        getDialog().getWindow().setBackgroundDrawable(new ColorDrawable(Color.TRANSPARENT));
        initialization();
        setUpMonthGridAdapter();
        setUpRecycler();

        return view;
    }

    private void initialization() {

        monthGrid = view.findViewById(R.id.monthGrid);
        btnDone = view.findViewById(R.id.btnDone1);
        btnCancel = view.findViewById(R.id.btnCancel1);
        nextButton = view.findViewById(R.id.nextButton);
        prevButton = view.findViewById(R.id.prevButton);
        yearRecycler = view.findViewById(R.id.yearRecycler);
        months = new ArrayList<>();
        calendarManager = new CalendarManager(getActivity());
        monthName = view.findViewById(R.id.monthName);
        yearName = view.findViewById(R.id.yearName);
        setUpMonthGrid();
        setOnClickListeners();
        setUpMonthGridClicks();
    }

    private void setUpRecycler(){
        years = calendarManager.getYearList();
        int pos = calendarManager.posYear;
        selectedYear = years.get(pos);
        Log.d("msg", String.valueOf(pos));
        adapter = new YearRecyclerAdapter(getActivity(),years,selectedYear);
        LinearLayoutManager layoutManager = new LinearLayoutManager(getActivity());
        yearRecycler.setLayoutManager(layoutManager);
        layoutManager.scrollToPositionWithOffset(pos,0);
        yearRecycler.setAdapter(adapter);
        snapHelper = new PagerSnapHelper();
        snapHelper.attachToRecyclerView(yearRecycler);
        yearRecycler.addOnScrollListener(new RecyclerView.OnScrollListener() {

            @Override
            public void onScrollStateChanged(RecyclerView recyclerView, int newState) {
                super.onScrollStateChanged(recyclerView, newState);
                if(newState == RecyclerView.SCROLL_STATE_IDLE) {
                    View centerView = snapHelper.findSnapView(recyclerView.getLayoutManager());
                     position = yearRecycler.getLayoutManager().getPosition(centerView);

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
    private void setUpMonthGridAdapter() {
        monthGridAdapter = new MonthGridAdapter(getActivity(),months,currentMonth,selectedMonth);
        monthGrid.setAdapter(monthGridAdapter);
    }



        private void setUpMonthGridClicks() {

            monthGrid.setOnItemClickListener(new AdapterView.OnItemClickListener() {
                @Override
                public void onItemClick(AdapterView<?> adapterView, View view, int position, long l) {
                    int month = monthGridAdapter.getItem(position);
                    selectedMonth = month;
                    monthName.setText(calendarManager.getMonthForInt(month));
                    setUpMonthGridAdapter();
                }
            });


            setOnYearSelectedListener(new OnYearSelectedListener() {
                @Override
                public void onYearSelected(int year) {
                    yearName.setText(String.valueOf(year));

                }
            });
        }


    private void setOnClickListeners(){
        nextButton.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View view) {
//                yearRecycler.smoothScrollToPosition(position + 1);
            }
        });
        prevButton.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View view) {
//                yearRecycler.smoothScrollToPosition(position - 1);
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
                int selectedYear = adapter.getSelectedYear();
                onMonthAndYearSelectedListener.onMonthAndYearSelected(selectedMonth,selectedYear);
                dismiss();
            }
        });

        monthName.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View view) {

            }
        });
    }




    public void setOnMonthSelectedListener(OnMonthSelectedListener onMonthSelectedListener){
        this.onMonthSelectedListener = onMonthSelectedListener;
    }

    public void setOnYearSelectedListener(OnYearSelectedListener onYearSelectedListener){
        YearFragment.onYearSelectedListener = onYearSelectedListener;
    }

    public void setOnMonthAndYearSelectedListener(OnMonthAndYearSelectedListener onMonthAndYearSelectedListener) {
        this.onMonthAndYearSelectedListener = onMonthAndYearSelectedListener;
    }
}
