package christmas.domain.event;

import christmas.domain.menu.Menu;
import christmas.domain.menu.MenuBoard;
import java.util.ArrayList;
import java.util.List;

public class Gift {
    private final int GIVEN_GIFTS_QUANTITY = 1;
    private final int SATISFIED_TOTAL_PRICE_TO_GIFTS = 120000;
    private final String GIFTS_MENU = "샴페인";
    private int totalGiftsBenefit;
    private List<EventDetail> eventDetailList;
    private static final String LINE_SEPARATOR = System.lineSeparator();
    private final String GIFTS_EVENT_MESSAGE = "증정 이벤트: -%s원" + LINE_SEPARATOR;
    private final String NONE = "없음" + LINE_SEPARATOR;

    public Gift() {
        this.eventDetailList = new ArrayList<>();
    }

    public void addEventDetail(String eventName, int eventBenefit) {
        EventDetail eventInfo = new EventDetail(eventName, eventBenefit);
        eventDetailList.add(eventInfo);
    }

    public void proceedAddEvent(String message, int benefit) {
        addEventDetail(message, benefit);
        totalGiftsBenefit += benefit;
    }

    public int getTotalGiftBenefit() {
        return totalGiftsBenefit;
    }

    public int getGiftsQuantity() {
        return GIVEN_GIFTS_QUANTITY;
    }

    public Menu getGiftsMenu() {
        MenuBoard menuBoard = new MenuBoard();

        return menuBoard.findMenu(GIFTS_MENU);
    }

    public void checkGiftsEventPossible(int totalOrderPrice) {
        if (totalOrderPrice < SATISFIED_TOTAL_PRICE_TO_GIFTS) {
            proceedAddEvent(NONE, 0);
            return;
        }

        proceedAddEvent(GIFTS_EVENT_MESSAGE, getGiftsMenu().getMenuPrice());
    }

    public List<EventDetail> getEventDetailList() {
        return eventDetailList;
    }
}
