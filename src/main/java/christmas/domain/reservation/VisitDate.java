package christmas.domain.reservation;

import static christmas.EventPlan.EVENT_MONTH;
import static christmas.EventPlan.EVENT_YEAR;

import christmas.validation.VisitDateValidator;
import java.time.LocalDate;

public class VisitDate {
    private final int visitDay;
    private LocalDate reservationDate;

    public VisitDate(String date) {
        VisitDateValidator.validateDateInteger(date);
        VisitDateValidator.validateVisitDateRange(date);
        visitDay = Integer.parseInt(date);
        reservationDate = LocalDate.of(EVENT_YEAR, EVENT_MONTH, visitDay);
    }

    public int getVisitDay() {
        return visitDay;
    }

    public LocalDate getReservationDate() {
        return reservationDate;
    }
}
