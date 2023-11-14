package christmas;

import java.text.NumberFormat;
import java.util.Hashtable;
import java.util.Set;

public class OutputView {

    private final String ARARM_ORDER_MENU = "<주문 메뉴>\n";
    private final String TOTAL_PRICE_BEFORE_DISCOUNT_MSG = "<할인 전 총주문 금액>\n";
    private final String GIVEN_NOTHING = "없음\n\n";
    private final String GIFTS_MSG = "<증정 메뉴>\n";
    private final String DISCOUNT_HISTORY = "<해택 내역>\n";
    private final String PRIVIEW_MESSAGE = "%d월 %d일에 우테코 식당에서 받을 이벤트 혜택 미리 보기!\n\n";
    NumberFormat moneyFormat;

    public OutputView() {
        moneyFormat = NumberFormat.getInstance();
    }

    public void printCustomerOrders(Customer customer) {
        System.out.print(ARARM_ORDER_MENU);
        Set<String> keySet = customer.getOrderMenuNames();
        Hashtable<String, Integer> customerOrder = customer.getCustomerOrder();

        for (String key : keySet) {
            int value = customerOrder.get(key);
            System.out.printf("%s %d개\n", key, value);
        }
        System.out.println();
    }

    public void printTotalPriceBeforeDiscount(int totalPrice) {
        System.out.print(TOTAL_PRICE_BEFORE_DISCOUNT_MSG);
        System.out.printf("%s 원\n\n", moneyFormat.format(totalPrice));
    }

    public void printGiveGifts(String menu, int count) {
        System.out.print(GIFTS_MSG);
        System.out.print(menu + " " + count + "개\n\n");
    }

    public void isNothingGiven() {
        System.out.print(GIFTS_MSG);
        System.out.print(GIVEN_NOTHING);
    }

    public void isNoting() {
        System.out.print(GIVEN_NOTHING);
    }

    public void printNoticeDiscountHistory() {
        System.out.print(DISCOUNT_HISTORY);
    }

    public void printEventHistoryPreviewMessage(int month, int day) {
        System.out.printf(PRIVIEW_MESSAGE, month, day);
    }

    public void printDiscountApplyHistory(String eventName, int discountPrice) {
        System.out.printf(eventName, moneyFormat.format(discountPrice));
    }
}
