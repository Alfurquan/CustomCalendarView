package com.example.customcalendar.ManagerClasses;

import android.content.Context;

import java.text.DateFormat;
import java.text.DateFormatSymbols;
import java.text.SimpleDateFormat;
import java.util.ArrayList;
import java.util.Calendar;
import java.util.Date;

public class CalendarManager {

    private Context context;
    public int pos;

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
    public Date getCurrentDateAsDate(){
        Calendar calendar = Calendar.getInstance();
        return calendar.getTime();
    }

    public String convertDateToString(Date date){
        DateFormat df = new SimpleDateFormat("dd/MM/yyyy");
        return df.format(date);
    }
    public ArrayList<Calendar> getAllMonthList(){

        ArrayList<Calendar> monthList = new ArrayList<>();
        Calendar calendar = Calendar.getInstance();
        Calendar current = (Calendar) calendar.clone();
        calendar.set(1970,0,1);
        while(calendar.get(Calendar.YEAR ) != 2100){
            monthList.add(calendar);
            if(calendar.get(Calendar.YEAR) == current.get(Calendar.YEAR) && calendar.get(Calendar.MONTH) == current.get(Calendar.MONTH)){
                pos = monthList.indexOf(calendar);
            }
            calendar.add(Calendar.MONTH,1);
            calendar = (Calendar) calendar.clone();
        }

        return monthList;
    }
}
