package christmas.domain.event;

import java.util.List;

public class Gift {
    private final String GIVEN_GIFTS_MENU = "샴페인";
    private int totalGiftBenefit;
    private List<EventDetail> eventDetailList;

    public int getTotalGiftBenefit() {
        return totalGiftBenefit;
    }
}
