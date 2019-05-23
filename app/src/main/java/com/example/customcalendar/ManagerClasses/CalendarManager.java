package com.example.customcalendar.ManagerClasses;

import android.content.Context;

import java.text.DateFormatSymbols;
import java.util.ArrayList;
import java.util.Calendar;

public class CalendarManager {

    private Context context;

    public CalendarManager(Context context) {
        this.context = context;
    }

    public  String getMonthForInt(int num) {
        String month = "wrong";
        DateFormatSymbols dfs = new DateFormatSymbols();
        String[] months = dfs.getMonths();
        if (num >= 0 && num <= 11 ) {
            month = months[num];
        }
        return getShortNamesForMonths(month);
    }

    String getShortNamesForMonths(String month){

        switch (month){

            case "January":
                return "Jan";


            case "February":
                 return "Feb";


            case "March":
                return "Mar";


            case "April":
                return "Apr";


            case "May":
                return "May";


            case "June":
                return "Jun";


            case "July":
                return "Jul";


            case "August":
                return "Aug";


            case "September":
                return "Sep";


            case "October":
                return "Oct";


            case "November":
                return "Nov";


            case "December":
                return "Dec";

        }
        return null;
    }

    public ArrayList<Integer> getYearList(Calendar currentYear){

        ArrayList<Integer> years = new ArrayList<>();
        Calendar calendar = Calendar.getInstance();
        calendar.setTime(currentYear.getTime());
        int year = calendar.get(Calendar.YEAR);
        years.add(year);
        for(int i=1;i<12;i++){
           calendar.add(Calendar.YEAR,1);
           year = calendar.get(Calendar.YEAR);
           years.add(year);
        }
        return years;
    }
}
