package com.example.customcalendar;

import androidx.appcompat.app.AppCompatActivity;
import androidx.core.app.ActivityCompat;

import android.app.Activity;
import android.app.AlertDialog;
import android.app.DatePickerDialog;
import android.os.Bundle;
import android.util.Log;
import android.view.LayoutInflater;
import android.view.View;
import android.widget.Button;

import com.example.customcalendar.interfaces.OnMonthSelectedListener;
import com.example.customcalendar.interfaces.OnYearSelectedListener;
import com.example.customcalendar.views.CustomMonthAndYearPickerDialog;
import com.example.customcalendar.views.CustomMonthPicker;

import java.util.Calendar;

public class MonthPickerActivity extends AppCompatActivity {

    CustomMonthPicker customMonthPicker;
    Button btn;
    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_month_picker);
        btn = findViewById(R.id.clickToOpn);
//        customMonthPickerstomMonthPicker = findViewById(R.id.customMonthPicker1);
//        customMonthPicker.setOnMonthSelectedListener(new OnMonthSelectedListener() {
//            @Override
//            public void onSelectedMonth(int month) {
//                Log.d("msgMonth", String.valueOf(month));
//            }
//        });
//
//        customMonthPicker.setOnYearSelectedListener(new OnYearSelectedListener() {
//            @Override
//            public void onYearSelected(int year) {
//                Log.d("msgYear", String.valueOf(year));
//            }
//        });

        btn.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View view) {

                AlertDialog.Builder builder = new AlertDialog.Builder(MonthPickerActivity.this);
                LayoutInflater inflater = getLayoutInflater();
                View view1 = inflater.inflate(R.layout.custom_date_picker,null);
                builder.setView(view1);
                AlertDialog dialog = builder.create();
                dialog.show();



//                CustomMonthAndYearPickerDialog dialog = new CustomMonthAndYearPickerDialog(MonthPickerActivity.this);
//                dialog.setOnMonthSelectedListener(new OnMonthSelectedListener() {
//                    @Override
//                    public void onSelectedMonth(int month) {
//                        Log.d("msgMonth", String.valueOf(month));
//                    }
//                });
//
//                dialog.setOnYearSelectedListener(new OnYearSelectedListener() {
//                    @Override
//                    public void onYearSelected(int year) {
//                        Log.d("msgYear", String.valueOf(year));
//
//                    }
//                });
//                dialog.show();
            }
        });

    }
}
