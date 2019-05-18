package com.example.customcalendar;

import androidx.appcompat.app.AppCompatActivity;

import android.os.Bundle;
import android.util.Log;
import android.widget.CalendarView;

import com.example.customcalendar.interfaces.OnDateSelectedListener;

import java.util.ArrayList;
import java.util.Date;

public class MainActivity extends AppCompatActivity {

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_main);
        CustomCalendar customCalendar = findViewById(R.id.customCalendarView);
        customCalendar.setOnDateSelectedListener(new OnDateSelectedListener() {
            @Override
            public void onSelectedDate(ArrayList<Date> selectedDates) {
                for(Date date:selectedDates){
                    Log.d("msg", String.valueOf(date));
                }
            }
        });


    }
}
