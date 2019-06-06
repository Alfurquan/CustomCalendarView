package com.example.customcalendar;

import androidx.appcompat.app.AppCompatActivity;

import android.os.Bundle;
import android.util.Log;

import com.example.customcalendar.interfaces.OnDateSelectedListener;
import com.example.customcalendar.views.CustomCalendar;
import com.example.customcalendar.views.CustomCalendarView;

import java.util.ArrayList;
import java.util.Calendar;
import java.util.Date;

public class MainActivity extends AppCompatActivity {

    ArrayList<Date> decoratedDates = new ArrayList<>();
    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_main);
        CustomCalendarView customCalendarView = findViewById(R.id.customCalendar);
        customCalendarView.shouldDecorate(false);
        customCalendarView.setOnDateSelectedListener(new OnDateSelectedListener() {
            @Override
            public void onSelectedDate(ArrayList<Date> selectedDates) {
                Log.d("msgDates", String.valueOf(selectedDates));
            }
        });


    }
}
