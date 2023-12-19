package christmas;

import java.text.NumberFormat;
import java.util.HashMap;
import java.util.Hashtable;
import java.util.Set;

public class OutputView {

    private final String ARARM_ORDER_MENU = "<주문 메뉴>";
    private final String TOTAL_PRICE_BEFORE_DISCOUNT_MSG = "<할인 전 총주문 금액>";
    private final String GIVEN_NOTHING = "없음";
    private final String GIFTS_MSG = "<증정 메뉴>";
    private final String BENEFITS_HISTORY = "<혜택 내역>";
    private final String TOTAL_BENEFITS = "<총혜택 금액>";
    private final String EVENT_BADGE_MESSAGE = "<%d월 이벤트 배지>";
    private final String TOTAL_PRICE_AFTER_DISCOUNT = "<할인 후 예상 결제 금액>";
    private final String PRIVIEW_MESSAGE = "%d월 %d일에 우테코 식당에서 받을 이벤트 혜택 미리 보기!";
    private final String WELCOME_MESSAGE = "안녕하세요! 우테코 식당 %d월 이벤트 플래너입니다.";
    private final String MINUS_MARK = "-";
    private static final String LINE_SEPARATOR = System.lineSeparator();
    private static final String DOUBLE_LINE_SEPARATOR = LINE_SEPARATOR + LINE_SEPARATOR;
    NumberFormat moneyFormat;

    public OutputView() {
        moneyFormat = NumberFormat.getInstance();
    }

    public void printCustomerOrders(Customer customer) {
        System.out.print(ARARM_ORDER_MENU + LINE_SEPARATOR);
        Set<String> keySet = customer.getOrderMenuNames();
        HashMap<String, Integer> customerOrder = customer.getCustomerOrder();

        for (String key : keySet) {
            int value = customerOrder.get(key);
            System.out.printf("%s %d개" + LINE_SEPARATOR, key, value);
        }
        System.out.println();
    }

    public void printTotalPriceBeforeDiscount(int totalPrice) {
        System.out.print(TOTAL_PRICE_BEFORE_DISCOUNT_MSG + LINE_SEPARATOR);
        System.out.printf("%s원" + DOUBLE_LINE_SEPARATOR, moneyFormat.format(totalPrice));
    }

    public void printGiveGifts(String menu, int count) {
        System.out.print(GIFTS_MSG + LINE_SEPARATOR);
        System.out.print(menu + " " + count + "개" + DOUBLE_LINE_SEPARATOR);
    }

    public void isNothingGiven() {
        System.out.print(GIFTS_MSG + LINE_SEPARATOR);
        System.out.print(GIVEN_NOTHING + DOUBLE_LINE_SEPARATOR);
    }

    public void isNoting() {
        System.out.print(BENEFITS_HISTORY + LINE_SEPARATOR + GIVEN_NOTHING + DOUBLE_LINE_SEPARATOR);
    }

    public void printNoticeBenefitsHistory() {
        System.out.print(BENEFITS_HISTORY + LINE_SEPARATOR);
    }

    public void printEventHistoryPreviewMessage(int month, int day) {
        System.out.printf(PRIVIEW_MESSAGE + DOUBLE_LINE_SEPARATOR, month, day);
    }

    public void printBenefitsApplyHistory(String eventName, int benefitsPrice) {
        System.out.printf(eventName, moneyFormat.format(benefitsPrice));
    }

    public void printWelcomeMessage(int month) {
        System.out.printf(WELCOME_MESSAGE + LINE_SEPARATOR, month);
    }

    public void printTotalBenefits(int totalBenefits) {
        System.out.print(TOTAL_BENEFITS + LINE_SEPARATOR);
        if (totalBenefits != 0) {
            System.out.printf("%s%s원" + DOUBLE_LINE_SEPARATOR, MINUS_MARK, moneyFormat.format(totalBenefits));
            return;
        }

        System.out.printf("%s원" + DOUBLE_LINE_SEPARATOR, moneyFormat.format(totalBenefits));
    }

    public void printTotalPriceAfterDiscount(int totalPrice) {
        System.out.print(TOTAL_PRICE_AFTER_DISCOUNT + LINE_SEPARATOR);
        System.out.printf("%s원" + DOUBLE_LINE_SEPARATOR, moneyFormat.format(totalPrice));
    }

    public void printEventBadge(int eventMonth, String badgeName) {
        System.out.printf(EVENT_BADGE_MESSAGE + LINE_SEPARATOR + badgeName, eventMonth);
    }
}
