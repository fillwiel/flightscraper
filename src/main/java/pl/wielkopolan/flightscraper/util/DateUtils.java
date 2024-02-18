package pl.wielkopolan.flightscraper.util;

import java.time.LocalDate;
import java.time.YearMonth;
import java.util.Date;

public final class DateUtils {
    public static Date getLastDayOfMonth(LocalDate localDate) {
        YearMonth yearMonth = YearMonth.from(localDate);
        return java.sql.Date.valueOf(yearMonth.atEndOfMonth());
    }

    private DateUtils() {
        //Util class - private constructor
    }
}
