package christmas.domain.event;

import christmas.domain.reservation.OrderCompletedMenu;
import christmas.domain.reservation.VisitDate;
import java.time.LocalDate;
import java.util.List;

public class Discount {
    public static final int EVENT_YEAR = 2023;
    public static final int EVENT_MONTH = 12;
    public static final int EVENT_MONTH_START_DAY = 1;
    public static final int TARGET_D_DAY = 25;
    public static final int D_DAY_BASE_DISCOUNT = 1000;
    private final int SPCIAL_DAY_DISCOUNT = 1000;
    private final List<Integer> specialDayList = List.of(3, 10, 17, 24, 25, 31);
    private final List<String> weekendList = List.of("FRIDAY", "SATURDAY");

    private static final String LINE_SEPARATOR = System.lineSeparator();
    private final String CHRIST_MAS_EVENT_MESSAGE = "크리스마스 디데이 할인: -%s원" + LINE_SEPARATOR;
    private final String WEEKDAY_EVENT_MESSAGE = "평일 할인: -%s원" + LINE_SEPARATOR;
    private final String WEEKEND_EVENT_MESSAGE = "주말 할인: -%s원" + LINE_SEPARATOR;
    private final String SPCIAL_EVENT_MESSAGE = "특별 할인: -%s원" + LINE_SEPARATOR;
    private int totalDiscountBenefit;
    private List<EventDetail> eventDetailList;
    private VisitDate visitDate;
    private List<OrderCompletedMenu> orderCompletedMenus;

    Discount(VisitDate visitDate, List<OrderCompletedMenu> orderCompletedMenus) {
        this.visitDate = visitDate;
        this.orderCompletedMenus = orderCompletedMenus;
    }

    public void addEventDetail(String eventName, int eventBenefit) {
        EventDetail eventInfo = new EventDetail(eventName, eventBenefit);
        eventDetailList.add(eventInfo);
    }

    public void checkWholeDiscountEventPossible() {
        checkChristmasDdayEventPossible();
        checkWeekEventPossible();
        checkSpecialEventPossible();
    }

    private void checkChristmasDdayEventPossible() {
        if (visitDate.getVisitDay() <= TARGET_D_DAY) {
            int dDayDiscount = D_DAY_BASE_DISCOUNT + ((visitDate.getVisitDay() - 1) * 100);
            addEventDetail(CHRIST_MAS_EVENT_MESSAGE, dDayDiscount);
            totalDiscountBenefit += dDayDiscount;
        }
    }

    //주말 혹은 평일 이벤트 체크
    private void checkWeekEventPossible() {
        //평일 할인
        if (!weekendList.contains(visitDate.getReservationDate().getDayOfWeek())) {
            addEventDetail(WEEKDAY_EVENT_MESSAGE, discountUnit * dessertMenuCount);
            return;
        }

        //주말 할인
        addEventDetail(WEEKEND_EVENT_MESSAGE, discountUnit * mainMenuCount);
    }

    private void checkSpecialEventPossible() {
        if (specialDayList.contains(visitDate.getVisitDay())) {
            addEventDetail(SPCIAL_EVENT_MESSAGE, SPCIAL_DAY_DISCOUNT);
        }
    }

    public int getLastDay() {
        return LocalDate.of(EVENT_YEAR, EVENT_MONTH, EVENT_MONTH_START_DAY).lengthOfMonth();
    }

    public List<EventDetail> getEventDetailList() {
        return eventDetailList;
    }

    public int getTotalDiscountBenefit() {
        return totalDiscountBenefit;
    }
}
