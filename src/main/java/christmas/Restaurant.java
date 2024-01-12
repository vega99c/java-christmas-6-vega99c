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
    private EventPlan eventPlan;
    private static final String LINE_SEPARATOR = System.lineSeparator();
    private List<OrderCompletedMenu> orderInfomations;
    private VisitDate visitDate;
    private Order order = new Order();


    public Restaurant(Customer newCustomer) {
        customer = newCustomer;
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
        showTotalOrderPrice();
        showHavingGifts();
        checkWholeEvent();
        showTotalPriceAfterDiscount();
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
        outputView.printEventPreviewMessage(EVENT_MONTH, customer.getReservationDate());
        outputView.printCustomerOrders(order.getOrders());
    }

    public void menuOrderStart(String inputMenu) {
        try {
            orderInfomations = order.ordersByInputData(inputMenu);
        } catch (IllegalArgumentException error) {
            System.out.print(error.getMessage());
            menuOrderStart(inputView.readMenu());
        }
    }

    public void showTotalOrderPrice() {
        outputView.printTotalPriceBeforeDiscount(order.getTotalOrderPrice());
    }

    public void showHavingGifts() {
        outputView.printGiveGifts(order.getGiftsEvent().getGiftsMenu().getMenuName(),
                order.getGiftsEvent().getGiftsQuantity());
    }

    private void checkWholeEvent() {
        showBenefitsHistory();
        updateWholeEventBenefits();
        outputView.printTotalBenefits(customer.getTotalBenefits());
    }

    //이 부분은 컨트롤러 밖으로 빼내야 함
    private void updateWholeEventBenefits() {
        customer.updateTotalBenefits(order.getDiscountEvent().getTotalDiscountBenefit());
        customer.updateTotalBenefits(order.getGiftsEvent().getTotalGiftBenefit());
    }

    private void showTotalPriceAfterDiscount() {
        outputView.printTotalPriceAfterDiscount(order.getTotalOrderPriceAfterDiscount());
    }

    public void showCustomersEventBadge() {
        outputView.printEventBadge(EVENT_MONTH, order.getBadgeEvent().getBadgeToGive());
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
