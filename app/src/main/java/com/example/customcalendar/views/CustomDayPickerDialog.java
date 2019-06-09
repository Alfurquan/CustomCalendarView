package com.example.customcalendar.views;

import android.os.Bundle;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.Button;

import com.example.customcalendar.R;

import com.example.customcalendar.interfaces.OnDateSelectedListener;


import java.util.ArrayList;
import java.util.Date;

import androidx.annotation.NonNull;
import androidx.annotation.Nullable;
import androidx.fragment.app.DialogFragment;

public class CustomDayPickerDialog extends DialogFragment {

    private View view;

    private Button btnDone,btnCancel;
    private String mode;
    private CustomCalendarView customCalendarView;
    private ArrayList<Date> selectedDates;
    private OnDateSelectedListener onDateSelectedListener;

    @Nullable
    @Override
    public View onCreateView(@NonNull LayoutInflater inflater, @Nullable ViewGroup container, @Nullable Bundle savedInstanceState) {

        view = inflater.inflate(R.layout.custom_calendar_dialog,container,false);
        initialization();
        setClickListeners();
        return view;
    }

    private void initialization() {

        customCalendarView = view.findViewById(R.id.customCalendarView);
        customCalendarView.setIsPicker(true);
        selectedDates = new ArrayList<>();
        btnDone = view.findViewById(R.id.btnDone1);
        btnCancel = view.findViewById(R.id.btnCancel1);
    }

    public void setMode(String mode) {
        this.mode = mode;
    }




    private void setClickListeners(){

        btnCancel.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View view) {
                dismiss();
            }
        });

        btnDone.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View view) {
                selectedDates = customCalendarView.getSelectedDates();
                onDateSelectedListener.onSelectedDate(selectedDates);
                dismiss();
            }
        });
    }

    public void setOnDateSelectedListener(OnDateSelectedListener onDateSelectedListener){
            this.onDateSelectedListener = onDateSelectedListener;
    }
}

