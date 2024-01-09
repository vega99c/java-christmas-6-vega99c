package christmas.domain.event;

import christmas.domain.menu.Menu;
import christmas.domain.menu.MenuBoard;
import java.util.List;

public class Gift {
    private final int GIVEN_GIFTS_COUNT = 1;
    private final String GIVEN_GIFTS_MENU = "샴페인";
    private int totalGiftBenefit;
    private List<EventDetail> eventDetailList;

    public void addEventDetail(String eventName, int eventBenefit) {
        EventDetail eventInfo = new EventDetail(eventName, eventBenefit);
        eventDetailList.add(eventInfo);
    }

    public int getTotalGiftBenefit() {
        return totalGiftBenefit;
    }

    public int getGiftsCount() {
        return GIVEN_GIFTS_COUNT;
    }

    public Menu getGiftsMenuName() {
        MenuBoard menuBoard = new MenuBoard();

        return menuBoard.findMenu(GIVEN_GIFTS_MENU);
    }

    private void checkGiftsEventPossible() {

    }
}
