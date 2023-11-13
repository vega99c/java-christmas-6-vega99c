package christmas;

import java.text.NumberFormat;
import java.util.Hashtable;
import java.util.Set;

public class OutputView {
    private final String ARARM_ORDER_MENU = "<주문 메뉴>\n";
    private final String TOTAL_PRICE_BEFORE_DISCOUNT_MSG = "<할인 전 총주문 금액>\n";
    private final String GIVEN_NOTHING = "없음\n";
    private final String GIFTS_MSG = "<증정 메뉴>\n";
    private final String GIVEN_GIFTS_MENU = "샴페인 1개\n";

    public OutputView() {
    }

    public void printCurtomerOrders(Customer customer) {
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
        NumberFormat moneyFormat = NumberFormat.getInstance();

        System.out.print(TOTAL_PRICE_BEFORE_DISCOUNT_MSG);
        System.out.printf("%s 원\n\n", moneyFormat.format(totalPrice));
    }

    public void printGiveGifts() {
        System.out.print(GIFTS_MSG);
        System.out.print(GIVEN_GIFTS_MENU);
    }

    public void isNothingGiven() {
        System.out.print(GIFTS_MSG);
        System.out.print(GIVEN_NOTHING);
    }
}
