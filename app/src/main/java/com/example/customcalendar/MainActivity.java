package com.example.customcalendar;

import androidx.appcompat.app.AppCompatActivity;

import android.os.Bundle;
import android.util.Log;

import com.example.customcalendar.interfaces.OnDateSelectedListener;
import com.example.customcalendar.views.CustomCalendar;

import java.util.ArrayList;
import java.util.Calendar;
import java.util.Date;

public class MainActivity extends AppCompatActivity {

    ArrayList<Date> decoratedDates = new ArrayList<>();
    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_main);
        CustomCalendar customCalendar = findViewById(R.id.customCalendarView);
        Calendar calendar = Calendar.getInstance();
        decoratedDates.add(calendar.getTime());

        int i = 7;
        while(i > 0){
            calendar.add(Calendar.MONTH,1);
            decoratedDates.add(calendar.getTime());
            i--;
        }
        customCalendar.shouldDecorateWithDots(true,decoratedDates);
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
