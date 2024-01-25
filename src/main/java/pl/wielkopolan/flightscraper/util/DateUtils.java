package pl.wielkopolan.flightscraper.util;

import java.util.Calendar;
import java.util.Date;

public class DateUtils {

    public static void main(String[] args) {
        Date firstDayOfMonth = getFirstDayOfMonth();
        Date lastDayOfMonth = getLastDayOfMonth();

        System.out.println("First day of current month: " + firstDayOfMonth);
        System.out.println("Last day of current month: " + lastDayOfMonth);
    }

    public static Date getFirstDayOfMonth() {
        Calendar calendar = Calendar.getInstance();
        calendar.set(Calendar.DAY_OF_MONTH, 1); // Set to the first day of the month
        resetTimePart(calendar); // Reset time part to midnight
        return calendar.getTime();
    }

    public static Date getLastDayOfMonth() {
        Calendar calendar = Calendar.getInstance();
        calendar.set(Calendar.DAY_OF_MONTH, calendar.getActualMaximum(Calendar.DAY_OF_MONTH)); // Set to the last day of the month
        setToEndOfDay(calendar); // Set time part to end of the day (23:59:59)
        return calendar.getTime();
    }

    private static void resetTimePart(Calendar calendar) {
        calendar.set(Calendar.HOUR_OF_DAY, 0);
        calendar.set(Calendar.MINUTE, 0);
        calendar.set(Calendar.SECOND, 0);
        calendar.set(Calendar.MILLISECOND, 0);
    }

    private static void setToEndOfDay(Calendar calendar) {
        calendar.set(Calendar.HOUR_OF_DAY, 23);
        calendar.set(Calendar.MINUTE, 59);
        calendar.set(Calendar.SECOND, 59);
        calendar.set(Calendar.MILLISECOND, 999);
    }
}
