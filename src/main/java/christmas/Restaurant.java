package christmas;

import christmas.domain.reservation.OrderMenuParser;
import christmas.domain.reservation.VisitDate;

import java.util.HashMap;
import java.util.LinkedHashMap;
import java.util.List;
import java.util.Set;

import static christmas.EventPlan.EVENT_YEAR;
import static christmas.EventPlan.EVENT_MONTH;

public class Restaurant {
    private final int GIFTS_SATISFIED_PRICE = 120000;
    private final int EVENT_LEAST_AMOUNT = 10000;
    private final String EXCEPT_BENEFIT = "증정 이벤트";
    InputView inputView;
    OutputView outputView;
    private int totalQuantity;
    private String errorMsg;
    private Customer customer;
    private Menu menuInfo;
    private int totalPrice;
    private List<String> mainMenu;
    private List<String> dessertMenu;
    private EventPlan eventPlan;
    private static final String LINE_SEPARATOR = System.lineSeparator();
    private HashMap<String, Integer> orderInfomations;
    private VisitDate visitDate;

    public Restaurant(Customer newCustomer) {
        customer = newCustomer;
        mainMenu = Menu.MAIN.getChildMenu();
        dessertMenu = Menu.DESSERT.getChildMenu();
        orderInfomations = new LinkedHashMap<>();
        inputView = new InputView();
        outputView = new OutputView();
        menuInfo = Menu.ROOT;
    }

    public void runRestaurant() {
        outputView.printWelcomeMessage(EVENT_MONTH);
        receiptStart(inputView.readDate());
        proceedOrderFlow(inputView.readMenu());
        proceedCalculatePriceAndDiscount();
        showCustomersEventBadge();
    }

    public void proceedCalculatePriceAndDiscount() {
        calculateTotalPrice();
        checkWholeEvent();
        calculateTotalDiscounts();
        calculateTotalPriceAfterDiscount();
    }

    public void proceedOrderFlow(String inputMenu) {
        menuOrderStart(inputMenu);
        showOrderList();
    }

    public void receiptStart(String inputDate) {
        int readDate = 0;

        try {
            visitDate = new VisitDate(inputDate);
            readDate = visitDate.getVisitDay();
            eventPlan = new EventPlan(EVENT_YEAR, EVENT_MONTH, readDate);
            customer.setReservationDate(readDate);
            eventPlan.setCustomer(customer);
        } catch (IllegalArgumentException error) {
            System.out.print(error.getMessage());
            receiptStart(inputView.readDate());
        }
    }

    private void showOrderList() {
        outputView.printEventHistoryPreviewMessage(EVENT_MONTH, customer.getReservationDate());
        outputView.printCustomerOrders(customer);
    }

    public void menuOrderStart(String inputMenu) {
        try {
            orderInfomations = OrderMenuParser.parse(inputMenu);
        } catch (IllegalArgumentException error) {
            System.out.print(error.getMessage());
            initiateOrderInfo();
            menuOrderStart(inputView.readMenu());
        }
        customer.setMyOrder(orderInfomations);
        distinctionMenuCategory();
    }

    private void initiateOrderInfo() {
        totalQuantity = 0;
    }

    private void distinctionMenuCategory() {
        Set<String> keySet = customer.getOrderMenuNames();
        HashMap<String, Integer> orderTable = customer.getCustomerOrder();

        for (String menuName : keySet) {
            if (mainMenu.contains(menuName)) {
                customer.increaseMainMenuCount(orderTable.get(menuName));
            }

            if (dessertMenu.contains(menuName)) {
                customer.increaseDessertCount(orderTable.get(menuName));
            }
        }
    }

    public int getTotalQuantity() {
        return totalQuantity;
    }


    public void calculateTotalPrice() {
        Set<String> keySet = customer.getOrderMenuNames();
        HashMap<String, Integer> customerOrder = customer.getCustomerOrder();

        for (String key : keySet) {
            int menuCount = customerOrder.get(key);
            int menuPrice = menuInfo.getMenuPrice(key);

            totalPrice = totalPrice + (menuPrice * menuCount);
        }

        customer.setTotalPrice(totalPrice);
        outputView.printTotalPriceBeforeDiscount(totalPrice);
        isHavingGifts();
    }

    public int getTotalPrice() {
        return totalPrice;
    }

    public boolean isHavingGifts() {
        if (getTotalPrice() < GIFTS_SATISFIED_PRICE) {
            outputView.isNothingGiven();
            return false;
        }

        customer.setGivenGifts(true);
        outputView.printGiveGifts(eventPlan.getGiftsMenuName(), eventPlan.getGiftsCount());
        return true;
    }

    private void checkWholeEvent() {
        if (getTotalPrice() >= EVENT_LEAST_AMOUNT) {
            eventPlan.checkApplyingEvent();
        }

        showBenefitsHistory();
        calculateTotalBenefits();
        outputView.printTotalBenefits(customer.getTotalBenefits());
    }

    private void calculateTotalBenefits() {
        int totalBenefits = 0;
        Set<String> keySet = customer.getMyBenefits().keySet();

        for (String key : keySet) {
            totalBenefits += customer.getMyBenefits().get(key);
        }

        customer.setTotalBenefits(totalBenefits);
        eventPlan.checkBadgeEvent();
    }

    private void calculateTotalDiscounts() {
        int totalDiscounts = 0;
        Set<String> keySet = customer.getMyBenefits().keySet();

        for (String key : keySet) {
            if (!key.contains(EXCEPT_BENEFIT)) {
                totalDiscounts += customer.getMyBenefits().get(key);
            }
        }

        customer.setTotalDiscounts(totalDiscounts);
    }

    private void calculateTotalPriceAfterDiscount() {
        int totalPrice = customer.getTotalPrice() - customer.getTotalDiscounts();
        customer.setTotalPriceAfterDiscount(totalPrice);

        outputView.printTotalPriceAfterDiscount(customer.getTotalPriceAfterDiscount());
    }

    public void showCustomersEventBadge() {
        outputView.printEventBadge(EVENT_MONTH, customer.getEventBadge());
    }

    private void showBenefitsHistory() {
        HashMap<String, Integer> benefitsHistory = customer.getMyBenefits();

        if (benefitsHistory.isEmpty()) {
            outputView.isNoting();
            return;
        }

        outputView.printNoticeBenefitsHistory();
        for (String key : customer.getMyBenefits().keySet()) {
            outputView.printBenefitsApplyHistory(key, benefitsHistory.get(key));
        }

        System.out.print(LINE_SEPARATOR);
    }
}
