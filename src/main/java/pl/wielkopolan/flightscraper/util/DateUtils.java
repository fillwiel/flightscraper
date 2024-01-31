package pl.wielkopolan.flightscraper.util;

import java.time.LocalDate;
import java.time.YearMonth;
import java.util.Calendar;
import java.util.Date;

public final class DateUtils {
    public static Date getFirstDayOfMonth() {
        Calendar calendar = Calendar.getInstance();
        calendar.set(Calendar.DAY_OF_MONTH, 1); // Set to the first day of the month
        resetTimePart(calendar); // Reset time part to midnight
        return calendar.getTime();
    }

    public static Date getLastDayOfMonth() {
        // Get the first day of the next month
        LocalDate firstDayOfNextMonth = LocalDate.now().plusMonths(1).withDayOfMonth(1);
        // Get the last day of the next month using YearMonth class
        YearMonth yearMonth = YearMonth.from(firstDayOfNextMonth);
        return java.sql.Date.valueOf(yearMonth.atEndOfMonth());
    }

    private static void resetTimePart(Calendar calendar) {
        calendar.set(Calendar.HOUR_OF_DAY, 0);
        calendar.set(Calendar.MINUTE, 0);
        calendar.set(Calendar.SECOND, 0);
        calendar.set(Calendar.MILLISECOND, 0);
    }
    private DateUtils() {
        //Util class - private constructor
    }
}
