package christmas.domain.event;

import java.util.List;

public class Discount {
    public static final int EVENT_YEAR = 2023;
    public static final int EVENT_MONTH = 12;
    private static final String LINE_SEPARATOR = System.lineSeparator();
    private final String CHRIST_MAS_EVENT_MESSAGE = "크리스마스 디데이 할인: -%s원" + LINE_SEPARATOR;
    private final String WEEKDAY_EVENT_MESSAGE = "평일 할인: -%s원" + LINE_SEPARATOR;
    private final String WEEKEND_EVENT_MESSAGE = "주말 할인: -%s원" + LINE_SEPARATOR;
    private final String SPCIAL_EVENT_MESSAGE = "특별 할인: -%s원" + LINE_SEPARATOR;
    private int totalDiscountBenefit;
    private List<EventDetail> eventDetailList;

    public void checkWholeDiscountEventPossible() {

    }

    private void checkChristmasDdayEventPossible() {

    }

    //주말 혹은 평일 이벤트 체크
    private void checkWeekEventPossible() {

    }

    private void checkSpecialEventPossible() {

    }

    public int getLastDay() {

    }
}
