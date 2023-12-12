package christmas;

import java.time.LocalDate;
import java.util.Arrays;
import java.util.List;

public class EventPlan {
    private static final String LINE_SEPARATOR = System.lineSeparator();
    private final String CHRIST_MAS_EVENT_MESSAGE = "크리스마스 디데이 할인: -%s원" + LINE_SEPARATOR;
    private final String WEEKDAY_EVENT_MESSAGE = "평일 할인: -%s원" + LINE_SEPARATOR;
    private final String WEEKEND_EVENT_MESSAGE = "주말 할인: -%s원" + LINE_SEPARATOR;
    private final String SPCIAL_EVENT_MESSAGE = "특별 할인: -%s원" + LINE_SEPARATOR;
    private final String GIFTS_EVENT_MESSAGE = "증정 이벤트: -%s원" + LINE_SEPARATOR;
    private final String GIVEN_GIFTS_MENU = "샴페인";
    private final String NONE_VALUE = "없음";
    private final int GIVEN_GIFTS_COUNT = 1;
    private final int CHRIST_MAS_BASE_DISCOUNT = 1000;
    private final int SPCIAL_DAY_DISCOUNT = 1000;
    private final int WEEK_DAY_END_BASE_DISCOUNT = 2023;
    private final int CHRISTMAS_DAY = 25;
    private LocalDate reservationDate;
    private final List<String> weekendList = List.of("FRIDAY", "SATURDAY");
    private final List<Integer> specialDayList = List.of(3, 10, 17, 24, 25, 31);
    private Customer customer;
    private Menu giftsMenu;
    private int eventYear;
    private int eventMonth;
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

    public int getLastDay() {
        return LocalDate.of(eventYear, eventMonth, eventDay).lengthOfMonth();
    }

    public String getDayString() {
        return reservationDate.getDayOfWeek().toString();
    }

    public int getDayInt() {
        return reservationDate.getDayOfMonth();
    }

    public void checkApplyingEvent() {
        checkChristmasDdayEvent();
        checkWeekEvent();
        checkSpecialEvent();
        checkGiftsEvent();
    }

    private void checkChristmasDdayEvent() {
        if (getDayInt() <= CHRISTMAS_DAY) {
            customer.setMyBenefits(CHRIST_MAS_EVENT_MESSAGE,
                    CHRIST_MAS_BASE_DISCOUNT + ((customer.getReservationDate() - 1) * 100));
        }
    }

    private void checkWeekEvent() {
        if (!weekendList.contains(getDayString())) {
            checkAndSetWeekDiscount(customer.getDessertMenuCount(), WEEKDAY_EVENT_MESSAGE, WEEK_DAY_END_BASE_DISCOUNT);
            return;
        }

        checkAndSetWeekDiscount(customer.getMainMenuCount(), WEEKEND_EVENT_MESSAGE, WEEK_DAY_END_BASE_DISCOUNT);
    }

    public void checkAndSetWeekDiscount(int menuCount, String eventMessage, int discountUnit) {
        if (!(menuCount == 0)) {
            customer.setMyBenefits(eventMessage, discountUnit * menuCount);
        }
    }

    private void checkSpecialEvent() {
        if (specialDayList.contains(getDayInt())) {
            customer.setMyBenefits(SPCIAL_EVENT_MESSAGE, SPCIAL_DAY_DISCOUNT);
        }
    }

    private void checkGiftsEvent() {
        if (customer.getGivenGifts()) {
            customer.setMyBenefits(GIFTS_EVENT_MESSAGE, giftsMenu.getPrice() * GIVEN_GIFTS_COUNT);
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

    public void checkBadgeEvent() {
        List<EventBadge> badges = Arrays.stream(EventBadge.values()).toList();

        for (EventBadge badge : badges) {
            if (customer.getTotalBenefits() >= badge.getBenefitsCondition()) {
                customer.setEventBadge(badge.getBadgeName());
                return;
            }
        }

        customer.setEventBadge(NONE_VALUE);
    }

    public void setCustomer(Customer customer) {
        this.customer = customer;
    }
}
