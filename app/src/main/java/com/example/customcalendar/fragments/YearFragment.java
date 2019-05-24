package com.example.customcalendar.fragments;

import android.annotation.SuppressLint;
import android.os.Bundle;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.AdapterView;
import android.widget.GridView;

import com.example.customcalendar.R;
import com.example.customcalendar.adapter.YearGridAdapter;
import com.example.customcalendar.interfaces.OnYearSelectedListener;

import java.util.ArrayList;
import java.util.Calendar;

import androidx.annotation.NonNull;
import androidx.annotation.Nullable;
import androidx.fragment.app.Fragment;

public class YearFragment extends Fragment {

    private ArrayList<Integer> yearList;
    private View view;
    private GridView yearGrid;
    int currentYear;
    public static int selectedYear;
    private YearGridAdapter yearGridAdapter;
    public static OnYearSelectedListener onYearSelectedListener;
    public YearFragment() {

    }

    @SuppressLint("ValidFragment")
    public YearFragment(ArrayList<Integer> yearList) {
        this.yearList = yearList;
    }

    public static YearFragment newInstance(ArrayList<Integer> yearList){
        return new YearFragment(yearList);
    }

    @Nullable
    @Override
    public View onCreateView(@NonNull LayoutInflater inflater, @Nullable ViewGroup container, @Nullable Bundle savedInstanceState) {
        view = inflater.inflate(R.layout.year_view,container,false);
        initializeVariables();
        setUpYearGridAdapter();
        setUpYearGridClicks();
        return view;
    }

    private void initializeVariables() {
        yearGrid = view.findViewById(R.id.yearGrid);
        Calendar calendar = Calendar.getInstance();
        currentYear = calendar.get(Calendar.YEAR);
        selectedYear = currentYear;
    }

    private void setUpYearGridAdapter() {
        yearGridAdapter = new YearGridAdapter(view.getContext(),yearList,currentYear,selectedYear);
        yearGrid.setAdapter(yearGridAdapter);
    }

    private void setUpYearGridClicks() {
        yearGrid.setOnItemClickListener(new AdapterView.OnItemClickListener() {
            @Override
            public void onItemClick(AdapterView<?> adapterView, View view, int position, long l) {
                int year = yearGridAdapter.getItem(position);
                selectedYear = year;
                onYearSelectedListener.onYearSelected(selectedYear);
                setUpYearGridAdapter();
            }
        });
    }

    public void updateUI(ArrayList<Integer> yearList){
        this.yearList = yearList;
        setUpYearGridAdapter();
    }



}
