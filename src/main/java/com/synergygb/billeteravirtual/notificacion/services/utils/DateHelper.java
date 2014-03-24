package com.synergygb.billeteravirtual.notificacion.services.utils;

import java.text.SimpleDateFormat;
import java.util.Calendar;
import java.util.Date;

/**
 * Class defining helper functions for handling date and time
 */
public class DateHelper {
    
    /**
     * Returns the current time as string in the format h:mm a
     * where <b>h</b> refers to hours in 12-hour clock, <b>m</b>
     * refers to minutes and <b>a</b> refers to the meridian time.<br/>
     * 
     * Example: 4:54 PM
     */
    public static String getCurrentTimeAsString() {
        SimpleDateFormat dateFormat = new SimpleDateFormat("HH:mm:ss");
        Calendar calendar = Calendar.getInstance();
        Date date = calendar.getTime();
        return dateFormat.format(date);
    }

    /**
     * Returns the current date as string in the format dd/MM/yyyy h:mm a
     * where <b>dd</b> refers to the day number, <b>MM</b> 
     * refers to the month numerically and <b>yyyy</b> refers to the
     * year.
     * 
     * Example: 29/05/1987
     */
    public static String getCurrentDateAsString() {
        SimpleDateFormat dateFormat = new SimpleDateFormat("dd-MM-yyyy");
        Calendar calendar = Calendar.getInstance();
        Date date = calendar.getTime();
        return dateFormat.format(date);
    }

    public static Date getCurrentDate() {
        SimpleDateFormat dateFormat = new SimpleDateFormat("dd-MM-yyyy");
        Calendar calendar = Calendar.getInstance();
        Date date = calendar.getTime();
        return date;
    }

    /**
     * Returns the current date in the format specified by <code>format</code>
     */
    public static String getCurrentDateAsString(String format) {
        SimpleDateFormat dateFormat = new SimpleDateFormat(format);
        Calendar calendar = Calendar.getInstance();
        Date date = calendar.getTime();
        return dateFormat.format(date);
    }    
    
    /**
     * Returns the current date time as a string in the format "dd/MM/yyyy h:mm a"
     * where <b>dd</b> refers to the day number, <b>MM</b> 
     * refers to the month numerically, <b>yyyy</b> refers to the
     * year, <b>h</b> refers to hours in 12-hour clock, <b>m</b>
     * refers to minutes and <b>a</b> refers to the meridian time.<br/>
     * 
     * Example: 29/05/1987 3:54 PM
     */
    public static String getFullDateAsString() {
        return getCurrentDateAsString()+" "+getCurrentTimeAsString();
    }

    
    /**
     *  Returns the current datetime a {@link Date} instance
     */
    public static Date getDateTime(){
         Calendar calendar = Calendar.getInstance();
         Date date = calendar.getTime();
         return date;
    }

    /**
     * Returns the given datetime {@param d} as a string in the format "dd/MM/yyyy h:mm a"
     * where <b>dd</b> refers to the day number, <b>MM</b> 
     * refers to the month numerically, <b>yyyy</b> refers to the
     * year, <b>h</b> refers to hours in 12-hour clock, <b>m</b>
     * refers to minutes and <b>a</b> refers to the meridian time.<br/>
     * 
     * Example: 29/05/1987 3:54 PM
     */
    public static String formatDateComplete(Date d){
            SimpleDateFormat dateFormat = new SimpleDateFormat("dd/MM/yyyy h:mm a");
            return dateFormat.format(d);
    }
        

}
