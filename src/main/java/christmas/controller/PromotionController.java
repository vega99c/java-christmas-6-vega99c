package christmas.controller;

import static christmas.EventPlan.EVENT_MONTH;
import static christmas.EventPlan.EVENT_YEAR;

import christmas.Customer;
import christmas.EventPlan;
import christmas.InputView;
import christmas.Menu;
import christmas.OutputView;
import christmas.domain.event.EventDetail;
import christmas.domain.reservation.Order;
import christmas.domain.reservation.OrderCompletedMenu;
import christmas.domain.reservation.VisitDate;
import java.util.ArrayList;
import java.util.List;

public class PromotionController {
    InputView inputView;
    OutputView outputView;
    private Customer customer;
    private EventPlan eventPlan;
    private static final String LINE_SEPARATOR = System.lineSeparator();
    private List<OrderCompletedMenu> orderInfomations;
    private VisitDate visitDate;
    private Order order = new Order();

    public PromotionController(Customer newCustomer) {
        customer = newCustomer;
        orderInfomations = new ArrayList<>();
        inputView = new InputView();
        outputView = new OutputView();
    }

    public void runRestaurant() {
        showWelcomeMessage();
        receiptStart(inputView.readDate());
        menuOrderStart(inputView.readMenu());
        updateWholeEventBenefits();
        showPromotionPreviewMessage();
        showOrdersInfomation();
    }

    public void receiptStart(String inputDate) {
        int readDate = 0;

        try {
            visitDate = new VisitDate(inputDate);
            readDate = visitDate.getVisitDay();
            customer.setReservationDate(readDate);
            order.setVisitDate(visitDate);
        } catch (IllegalArgumentException error) {
            System.out.print(error.getMessage());
            receiptStart(inputView.readDate());
        }
    }

    public void showOrdersInfomation() {
        showOrderList();                //주문 메뉴
        showTotalOrderPrice();          //할인 전 총 주문 금액
        showHavingGifts();              //증정 메뉴
        showBenefitsHistory();          //혜택 내역
        showTotalBenefits();            //총 혜택 금액
        showTotalPriceAfterDiscount();  //할인 후 예상 결제 금액
        showCustomersEventBadge();      //이벤트 배지
    }

    public void showWelcomeMessage() {
        outputView.printWelcomeMessage(EVENT_MONTH);
    }

    //x월 x일에 우테코 식당에서 받을 이벤트 혜택 미리 보기! 메시지 출력
    public void showPromotionPreviewMessage() {
        outputView.printEventPreviewMessage(EVENT_MONTH, order.getVisitDate().getVisitDay());
    }

    private void showOrderList() {
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

    //이 부분은 컨트롤러 밖으로 빼내야 함
    private void updateWholeEventBenefits() {
        customer.updateTotalBenefits(order.getDiscountEvent().getTotalDiscountBenefit());
        customer.updateTotalBenefits(order.getGiftsEvent().getTotalGiftBenefit());
    }

    public void showTotalOrderPrice() {
        outputView.printTotalPriceBeforeDiscount(order.getTotalOrderPrice());
    }

    public void showHavingGifts() {
        outputView.printGiveGifts(order.getGiftsEvent().getGiftsMenu().getMenuName(),
                order.getGiftsEvent().getGiftsQuantity());
    }

    public void showTotalBenefits() {
        outputView.printTotalBenefits(customer.getTotalBenefits());
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
