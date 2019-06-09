package com.example.customcalendar.adapter;

import android.content.Context;
import android.util.Log;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.AdapterView;
import android.widget.GridView;
import android.widget.ImageView;
import android.widget.TextView;

import com.example.customcalendar.ManagerClasses.CalendarManager;
import com.example.customcalendar.R;
import com.example.customcalendar.interfaces.OnDateSelectedListener;
import com.example.customcalendar.interfaces.OnMonthAndYearSelectedListener;
import com.example.customcalendar.views.CustomMonthAndYearPickerDialog;

import java.text.SimpleDateFormat;
import java.util.ArrayList;
import java.util.Calendar;
import java.util.Date;
import java.util.Locale;

import androidx.annotation.NonNull;
import androidx.appcompat.app.AppCompatActivity;
import androidx.fragment.app.FragmentTransaction;
import androidx.recyclerview.widget.LinearLayoutManager;
import androidx.recyclerview.widget.RecyclerView;
import androidx.recyclerview.widget.SnapHelper;

public class CalendarRecyclerAdapter extends RecyclerView.Adapter<CalendarRecyclerAdapter.MyCalendarViewHolder> {

    private Context context;
    private ArrayList<Calendar> months;
    private SimpleDateFormat formatter = new SimpleDateFormat("MMMM yyyy", Locale.ENGLISH);
    private ArrayList<Date> decoratedDates;
    private ArrayList<Date> selectedDates;
    private boolean shouldDecorate;
    private CalendarGridAdapter adapter;
    private RecyclerView recyclerView;
    private ArrayList<Date> dayValueInCells;
    private CalendarManager calendarManager;
    private OnDateSelectedListener onDateSelectedListener;
    private SnapHelper snapHelper;
    private boolean isPicker;


    public CalendarRecyclerAdapter(Context context, ArrayList<Calendar> months, ArrayList<Date> selectedDates, boolean shouldDecorate, ArrayList<Date> decoratedDates, RecyclerView recyclerView, SnapHelper snapHelper,boolean isPicker) {
        this.context = context;
        this.months = months;
        this.selectedDates = selectedDates;
        this.decoratedDates = decoratedDates;
        this.shouldDecorate = shouldDecorate;
        this.recyclerView = recyclerView;
        dayValueInCells = new ArrayList<>();
        calendarManager = new CalendarManager(context);
        this.snapHelper = snapHelper;
        this.isPicker = isPicker;
    }


    @NonNull
    @Override
    public MyCalendarViewHolder onCreateViewHolder(@NonNull ViewGroup parent, int viewType) {
        View view = LayoutInflater.from(context).inflate(R.layout.calendar_layout,parent,false);

        return new MyCalendarViewHolder(view);
    }

    @Override
    public void onBindViewHolder(@NonNull final MyCalendarViewHolder holder, final int position) {

        final Calendar displayedMonth = months.get(position);
        String sDate = formatter.format(displayedMonth.getTime());
        holder.currentMonthTextView.setText(sDate);

        final Calendar calendar = (Calendar) displayedMonth.clone();
        setUpGridAdapter(calendar,holder);
        setUpClicks(holder,position,displayedMonth);
        setUpGridClicks(calendar,holder);

        recyclerView.addOnScrollListener(new RecyclerView.OnScrollListener() {

            @Override
            public void onScrollStateChanged(RecyclerView recyclerView, int newState) {
                super.onScrollStateChanged(recyclerView, newState);
                if(newState == RecyclerView.SCROLL_STATE_IDLE) {
                    View centerView = snapHelper.findSnapView(recyclerView.getLayoutManager());
                    int pos = recyclerView.getLayoutManager().getPosition(centerView);
                    Calendar month = months.get(pos);
                    GridView gridView = centerView.findViewById(R.id.calendar_grid);
                    Calendar calendar = (Calendar) month.clone();
                    dayValueInCells = calendarManager.getAllDateValuesInAMonth(month);
                    adapter = new CalendarGridAdapter(context,dayValueInCells,calendar,selectedDates,shouldDecorate,decoratedDates);
                    gridView.setAdapter(adapter);
                }
            }
        });
    }

    private void setUpGridClicks(final Calendar displayedMonth, final MyCalendarViewHolder holder) {

        holder.calendarGrid.setOnItemClickListener(new AdapterView.OnItemClickListener() {
            @Override
            public void onItemClick(AdapterView<?> adapterView, View view, int position, long l) {
                Date sel = adapter.getItem(position);
                selectedDates.add(sel);
                if(!isPicker){
                    if(onDateSelectedListener!=null)
                        onDateSelectedListener.onSelectedDate(selectedDates);
                }
                //setUpGridAdapter(displayedMonth,holder);
                 notifyDataSetChanged();
            }
        });
    }

    private void setUpClicks(MyCalendarViewHolder holder, final int position, final Calendar displayedMonth) {
        holder.nextBtn.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View view) {
                recyclerView.smoothScrollToPosition(position + 1);
            }
        });

        holder.prevBtn.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View view) {
                if(position > 0){
                    recyclerView.smoothScrollToPosition(position - 1);
                }
            }
        });

        holder.currentMonthTextView.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View view) {

                if(!isPicker){
                    CustomMonthAndYearPickerDialog dialog = new CustomMonthAndYearPickerDialog();
                    FragmentTransaction ft = ((AppCompatActivity)context).getSupportFragmentManager().beginTransaction();
                    ft.addToBackStack(null);
                    dialog.show(ft, "dialog");

                    dialog.setOnMonthAndYearSelectedListener(new OnMonthAndYearSelectedListener() {
                        @Override
                        public void onMonthAndYearSelected(int month, int year) {
                            Log.d("msgMy", String.valueOf(month));
                            Log.d("msgMy", String.valueOf(year));
                            goToMonthAndYear(month,year,displayedMonth);
                        }
                    });
                }

            }
        });
    }

    private void goToMonthAndYear(int month, int year, Calendar displayedMonth) {
        Calendar calendar = (Calendar) displayedMonth.clone();
        calendar.set(year,month,1);
        LinearLayoutManager layoutManager = (LinearLayoutManager) recyclerView.getLayoutManager();
        int pos = months.indexOf(calendar);
        Log.d("msg", String.valueOf(pos));
        layoutManager.scrollToPositionWithOffset(pos,0);
    }

    private void setUpGridAdapter(Calendar displayedMonth, MyCalendarViewHolder holder) {
        Calendar calendar = (Calendar) displayedMonth.clone();
        dayValueInCells = calendarManager.getAllDateValuesInAMonth(displayedMonth);
        adapter = new CalendarGridAdapter(context,dayValueInCells,calendar,selectedDates,shouldDecorate,decoratedDates);
        holder.calendarGrid.setAdapter(adapter);
    }

    @Override
    public int getItemCount() {
        return months.size();
    }

    public class MyCalendarViewHolder extends RecyclerView.ViewHolder {

        ImageView prevBtn,nextBtn;
        TextView currentMonthTextView;
        GridView calendarGrid;
        public MyCalendarViewHolder(@NonNull View view) {
            super(view);
            prevBtn = view.findViewById(R.id.prevButton);
            nextBtn = view.findViewById(R.id.nextButton);
            currentMonthTextView = view.findViewById(R.id.currentMonth);
            calendarGrid = view.findViewById(R.id.calendar_grid);
        }
    }

    public void setOnDateSelectedListener(OnDateSelectedListener onDateSelectedListener) {
        this.onDateSelectedListener = onDateSelectedListener;
    }

    public ArrayList<Date> getSelectedDates() {
        return selectedDates;
    }
}
