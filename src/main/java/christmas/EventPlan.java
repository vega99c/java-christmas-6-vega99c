package christmas;

import java.time.LocalDate;
import java.util.ArrayList;
import java.util.List;

public class EventPlan {
    private final String CHRIST_MAS_EVENT_MESSAGE = "크리스마스 디데이 할인: -%s원\n";
    private final String WEEKDAY_EVENT_MESSAGE = "평일 할인: -%s원\n";
    private final String WEEKEND_EVENT_MESSAGE = "주말 할인: -%s원\n";
    private final String SPCIAL_EVENT_MESSAGE = "특별 할인: -%s원\n";
    private final String GIFTS_EVENT_MESSAGE = "증정 이벤트: -%s원\n";
    private final String GIVEN_GIFTS_MENU = "샴페인";
    private final int GIVEN_GIFTS_COUNT = 1;
    private final int CHRIST_MAS_BASE_DISCOUNT = 1000;
    private final int SPCIAL_DAY_DISCOUNT = 1000;
    private final int WEEK_DAY_END_BASE_DISCOUNT = 2023;
    private final int FIRST_DAY = 1;
    private final int CHRISTMAS_DAY = 25;
    private LocalDate reservationDate;
    private final List<String> weekendList = new ArrayList<>(List.of("FRIDAY", "SATURDAY"));
    private final List<Integer> specialDayList = new ArrayList<>(List.of(3, 10, 17, 24, 25, 31));
    private Customer customer;
    private Menu giftsMenu;
    private int eventYear = 2023;
    private int eventMonth = 12;
    private int eventDay = 1;
    private String errorMsg;

    public EventPlan(int year, int month, int day) {
        eventYear = year;
        eventMonth = month;
        validateIsCorrectRange(day);
        eventDay = day;
        giftsMenu = Menu.ROOT.contains(GIVEN_GIFTS_MENU);
        reservationDate = LocalDate.of(eventYear, eventMonth, eventDay);
    }

    public LocalDate getLocalDate() {
        return reservationDate;
    }

    public int getLastDay() {
        return LocalDate.of(eventYear, eventMonth, eventDay).lengthOfMonth();
    }

    public String getDayString() {
        return reservationDate.getDayOfWeek().toString();
    }

    public int getDayInt() {
        return reservationDate.getDayOfMonth();
    }

    public void checkApplyingEvent(Customer customerArg) {
        this.customer = customerArg;
        checkChristmasDdayEvent();
        checkWeekEvent();
        checkSpecialEvent();
        checkGiftsEvent();
    }

    public void checkChristmasDdayEvent() {
        if (getDayInt() <= CHRISTMAS_DAY) {
            customer.setMyDiscounts(CHRIST_MAS_EVENT_MESSAGE,
                    CHRIST_MAS_BASE_DISCOUNT + ((customer.getReservationDate() - 1) * 100));
        }
    }

    public void checkWeekEvent() {
        if (!weekendList.contains(getDayString())) {
            checkAndSetWeekDiscount(customer.getDessertMenuCount(), WEEKDAY_EVENT_MESSAGE, WEEK_DAY_END_BASE_DISCOUNT);
            return;
        }

        checkAndSetWeekDiscount(customer.getMainMenuCount(), WEEKEND_EVENT_MESSAGE, WEEK_DAY_END_BASE_DISCOUNT);
    }

    public void checkAndSetWeekDiscount(int menuCount, String eventMessage, int discountUnit) {
        if (!(menuCount == 0)) {
            customer.setMyDiscounts(eventMessage, discountUnit * menuCount);
        }
    }

    public void checkSpecialEvent() {
        if (specialDayList.contains(getDayInt())) {
            customer.setMyDiscounts(SPCIAL_EVENT_MESSAGE, SPCIAL_DAY_DISCOUNT);
        }
    }

    public void checkGiftsEvent() {
        if (customer.getGivenGifts()) {
            customer.setMyDiscounts(GIFTS_EVENT_MESSAGE, giftsMenu.getPrice() * GIVEN_GIFTS_COUNT);
        }
    }

    public String getGiftsMenuName() {
        return giftsMenu.getMenuName();
    }

    public int getGiftsCount() {
        return GIVEN_GIFTS_COUNT;
    }

    public boolean validateIsCorrectRange(int date) {
        if (!((date >= 1) && (date <= getLastDay()))) {
            errorMsg = ErrorMessages.INCORRECT_DATE_RANGE.getErrorMsg();
            throw new IllegalArgumentException(errorMsg);
        }

        return true;
    }
}
