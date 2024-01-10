package christmas.domain.event;

import christmas.domain.menu.Menu;
import christmas.domain.menu.MenuBoard;
import java.util.List;

public class Gift {
    private final int GIVEN_GIFTS_QUANTITY = 1;
    private final int SATISFIED_TOTAL_PRICE_TO_GIFTS = 120000;
    private final String GIFTS_MENU = "샴페인";
    private int totalGiftBenefit;
    private List<EventDetail> eventDetailList;
    private List<Menu> giftsList;
    private static final String LINE_SEPARATOR = System.lineSeparator();
    private final String GIFTS_EVENT_MESSAGE = "증정 이벤트: -%s원" + LINE_SEPARATOR;
    private final String NONE = "없음" + LINE_SEPARATOR;

    public void addEventDetail(String eventName, int eventBenefit) {
        EventDetail eventInfo = new EventDetail(eventName, eventBenefit);
        eventDetailList.add(eventInfo);
    }

    public int getTotalGiftBenefit() {
        return totalGiftBenefit;
    }

    public int getGiftsQuantity() {
        return GIVEN_GIFTS_QUANTITY;
    }

    public Menu getGiftsMenu() {
        MenuBoard menuBoard = new MenuBoard();

        return menuBoard.findMenu(GIFTS_MENU);
    }
    
    private void checkGiftsEventPossible(int totalOrderPrice) {
        if (totalOrderPrice < SATISFIED_TOTAL_PRICE_TO_GIFTS) {
            addEventDetail(NONE, 0);
            return;
        }

        addEventDetail(GIFTS_EVENT_MESSAGE, getGiftsMenu().getMenuPrice());
    }
}
