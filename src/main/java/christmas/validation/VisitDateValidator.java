package christmas.validation;

import christmas.domain.reservation.VisitDate;
import christmas.exception.VisitDateException;
import java.time.DateTimeException;
import java.time.LocalDate;

import static christmas.EventPlan.EVENT_YEAR;
import static christmas.EventPlan.EVENT_MONTH;

public class VisitDateValidator {
    private static final int START_DAY = 1;
    private int visitDay;

    public static void validateDateInteger(String number) {
        try {
            Integer.parseInt(number);
        } catch (IllegalArgumentException exception) {
            throw new VisitDateException();
        }
    }

    public static void validateVisitDateRange(String number) {
        int day = Integer.parseInt(number);
        if ((day < START_DAY) || (day > getMonthLastDay(day))) {
            throw new VisitDateException();
        }
    }

    public static int getMonthLastDay(int day) {
        int localDate;
        try {
            localDate = LocalDate.of(EVENT_YEAR, EVENT_MONTH, day).lengthOfMonth();
        } catch (DateTimeException exception) {
            throw new VisitDateException();
        }
        return localDate;
    }
}
