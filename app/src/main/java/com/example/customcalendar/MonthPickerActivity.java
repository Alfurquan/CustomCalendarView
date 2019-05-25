package com.example.customcalendar;

import androidx.appcompat.app.AppCompatActivity;
import androidx.core.app.ActivityCompat;
import androidx.fragment.app.Fragment;
import androidx.fragment.app.FragmentTransaction;

import android.app.Activity;
import android.app.AlertDialog;
import android.app.DatePickerDialog;
import android.os.Bundle;
import android.util.Log;
import android.view.LayoutInflater;
import android.view.View;
import android.widget.Button;

import com.example.customcalendar.interfaces.OnDateSelectedListener;
import com.example.customcalendar.interfaces.OnMonthSelectedListener;
import com.example.customcalendar.interfaces.OnYearSelectedListener;
import com.example.customcalendar.views.CustomDayPickerDialog;
import com.example.customcalendar.views.CustomMonthAndYearPickerDialog;
import com.example.customcalendar.views.CustomMonthPicker;

import java.util.ArrayList;
import java.util.Calendar;
import java.util.Date;

public class MonthPickerActivity extends AppCompatActivity {

    CustomMonthPicker customMonthPicker;
    Button btn;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_month_picker);
        btn = findViewById(R.id.clickToOpn);


        btn.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View view) {
                CustomDayPickerDialog dialog = new CustomDayPickerDialog();
                FragmentTransaction ft = getSupportFragmentManager().beginTransaction();
                Fragment prev = getSupportFragmentManager().findFragmentByTag("dialog");
                if (prev != null) {
                    ft.remove(prev);
                }
                ft.addToBackStack(null);
                dialog.show(ft, "dialog");

                dialog.setOnDateSelectedListener(new OnDateSelectedListener() {
                    @Override
                    public void onSelectedDate(ArrayList<Date> selectedDates) {
                        Log.d("msgDate", String.valueOf(selectedDates));
                    }
                });

            }
        });
    }
}
