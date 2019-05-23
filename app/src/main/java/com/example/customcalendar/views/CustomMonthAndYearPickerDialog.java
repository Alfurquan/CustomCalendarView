package com.example.customcalendar.views;

import android.app.Dialog;
import android.content.Context;
import android.view.View;
import android.widget.Button;

import com.example.customcalendar.R;
import com.example.customcalendar.interfaces.OnMonthSelectedListener;
import com.example.customcalendar.interfaces.OnYearSelectedListener;

import androidx.annotation.NonNull;
import androidx.annotation.Nullable;

public class CustomMonthAndYearPickerDialog extends Dialog {
   private CustomMonthPicker customMonthPicker;
   private Button btnDone,btnCancel;
   private OnMonthSelectedListener onMonthSelectedListener;
   private OnYearSelectedListener onYearSelectedListener;

    public CustomMonthAndYearPickerDialog(@NonNull Context context) {
        super(context);
        setContentView(R.layout.custom_month_dialog);
        initialize();
        setClickListeners();
    }



    public CustomMonthAndYearPickerDialog(@NonNull Context context, int themeResId) {
        super(context, themeResId);
    }

    protected CustomMonthAndYearPickerDialog(@NonNull Context context, boolean cancelable, @Nullable OnCancelListener cancelListener) {
        super(context, cancelable, cancelListener);
    }
    private void initialize() {
        customMonthPicker = findViewById(R.id.customMonthPicker1);
        btnDone = findViewById(R.id.btnDone1);
        btnCancel = findViewById(R.id.btnCancel1);
    }
    private void setClickListeners() {
        btnCancel.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View view) {
                dismiss();
            }
        });

        btnDone.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View view) {
                onMonthSelectedListener.onSelectedMonth(customMonthPicker.getSelectedMonth());
                onYearSelectedListener.onYearSelected(customMonthPicker.getSelectedYear());
                dismiss();
            }
        });
    }

    public void setOnMonthSelectedListener(OnMonthSelectedListener onMonthSelectedListener){
        this.onMonthSelectedListener = onMonthSelectedListener;
    }

    public void setOnYearSelectedListener(OnYearSelectedListener onYearSelectedListener){
        this.onYearSelectedListener = onYearSelectedListener;
    }

}
