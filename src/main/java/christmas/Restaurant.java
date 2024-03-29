package christmas;

import static christmas.EventPlan.EVENT_MONTH;
import static christmas.EventPlan.EVENT_YEAR;

import christmas.domain.event.EventDetail;
import christmas.domain.reservation.Order;
import christmas.domain.reservation.OrderCompletedMenu;
import christmas.domain.reservation.VisitDate;
import java.util.ArrayList;
import java.util.HashMap;
import java.util.List;
import java.util.Set;

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
    private List<OrderCompletedMenu> orderInfomations;
    private VisitDate visitDate;
    private Order order = new Order();


    public Restaurant(Customer newCustomer) {
        customer = newCustomer;
        mainMenu = Menu.MAIN.getChildMenu();
        dessertMenu = Menu.DESSERT.getChildMenu();
        orderInfomations = new ArrayList<>();
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
            order.setVisitDate(visitDate);
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
            orderInfomations = order.ordersByInputData(inputMenu);
        } catch (IllegalArgumentException error) {
            System.out.print(error.getMessage());
            initiateOrderInfo();
            menuOrderStart(inputView.readMenu());
        }
//        customer.setMyOrder(orderInfomations);
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
        if (order.getTotalOrderPrice() < GIFTS_SATISFIED_PRICE) {
            outputView.isNothingGiven();
            return false;
        }

        customer.setGivenGifts(true);
        outputView.printGiveGifts(order.getGiftsEvent().getGiftsMenu().getMenuName(),
                order.getGiftsEvent().getGiftsQuantity());

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
//        int totalBenefits = 0;
//        Set<String> keySet = customer.getMyBenefits().keySet();
//
//        for (String key : keySet) {
//            totalBenefits += customer.getMyBenefits().get(key);
//        }

        customer.updateTotalBenefits(order.getDiscountEvent().getTotalDiscountBenefit());
        customer.updateTotalBenefits(order.getGiftsEvent().getTotalGiftBenefit());
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
        List<EventDetail> eventDetailList = new ArrayList<>();

        eventDetailList.addAll(order.getDiscountEvent().getEventDetailList());
        eventDetailList.addAll(order.getGiftsEvent().getEventDetailList());

        if (eventDetailList.isEmpty()) {
            outputView.isNoting();
            return;
        }

        outputView.printNoticeBenefitsHistory();
        for (EventDetail event : eventDetailList) {
            outputView.printBenefitsApplyHistory(event.getEventName(), event.getBenefitMoney());
        }

        System.out.print(LINE_SEPARATOR);
    }
}
