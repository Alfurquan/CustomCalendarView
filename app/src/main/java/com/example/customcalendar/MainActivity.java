package com.example.customcalendar;

import androidx.appcompat.app.AppCompatActivity;
import androidx.fragment.app.FragmentTransaction;

import android.os.Bundle;
import android.util.Log;
import android.view.View;
import android.widget.Button;

import com.example.customcalendar.interfaces.OnDateSelectedListener;
import com.example.customcalendar.views.CustomCalendar;
import com.example.customcalendar.views.CustomCalendarView;
import com.example.customcalendar.views.CustomDayPickerDialog;
import com.example.customcalendar.views.CustomMonthAndYearPickerDialog;

import java.util.ArrayList;
import java.util.Calendar;
import java.util.Date;

public class MainActivity extends AppCompatActivity {

    ArrayList<Date> decoratedDates = new ArrayList<>();
    Button opnBtn;
    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_main);
        CustomCalendarView customCalendarView = findViewById(R.id.customCalendar);
        opnBtn = findViewById(R.id.open);
        customCalendarView.shouldDecorate(false);
        customCalendarView.setOnDateSelectedListener(new OnDateSelectedListener() {
            @Override
            public void onSelectedDate(ArrayList<Date> selectedDates) {
                Log.d("msgDates", String.valueOf(selectedDates));
            }
        });

        opnBtn.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View view) {
                CustomDayPickerDialog dialog = new CustomDayPickerDialog();
                FragmentTransaction ft =getSupportFragmentManager().beginTransaction();
                ft.addToBackStack(null);
                dialog.show(ft, "dialog");

                dialog.setOnDateSelectedListener(new OnDateSelectedListener() {
                    @Override
                    public void onSelectedDate(ArrayList<Date> selectedDates) {
                        Log.d("msgDates", String.valueOf(selectedDates));
                    }
                });
            }
        });


    }
}
