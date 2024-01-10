package christmas.domain.reservation;

import christmas.domain.event.Badge;
import christmas.domain.event.Discount;
import christmas.domain.menu.Menu;
import christmas.validation.OrderValidator;
import java.util.ArrayList;
import java.util.List;
import java.util.Objects;

public class Order {
    List<OrderCompletedMenu> orders = new ArrayList<>();
    VisitDate visitDate;
    Discount discountEvent;
    Badge badgeEvent;

    // 입력된 주문 데이터를 가지고 정상적인 주문들만 orders 리스트에 추가하는 로직
    public List<OrderCompletedMenu> ordersByInputData(String inputData) {
        List<String> orderStringList = new ArrayList<>(List.of(inputData.split(",")));

        for (String order : orderStringList) {
            OrderCompletedMenu orderCompletedMenu = OrderCompletedMenu.parse(order);

            orders.add(orderCompletedMenu);
        }

        OrderValidator.validateMenuDuplicate(orders);
        OrderValidator.validateMenuOnlyDrink(orders);
        OrderValidator.validateTotalOrderQuantity(orders);

        discountEvent.checkWholeDiscountEventPossible();
        badgeEvent.checkBadgeEventPossible(getDiscountEvent().getTotalDiscountBenefit());
        return orders;
    }

    public void setVisitDate(VisitDate visitDate) {
        this.visitDate = visitDate;
        discountEvent = new Discount(visitDate, this);
    }

    public int getDessertMenuOrderCount() {
        int dessertCount = 0;
        for (OrderCompletedMenu menu : orders) {
            if (Objects.equals(menu.getMenu().getMenuType(), "<디저트>")) {
                dessertCount += menu.getQuantity();
            }
        }

        return dessertCount;
    }

    public int getMainMenuOrderCount() {
        int mainMenuCount = 0;
        for (OrderCompletedMenu menu : orders) {

            if (Objects.equals(menu.getMenu().getMenuType(), "<메인>")) {
                mainMenuCount++;
            }
        }

        return mainMenuCount;
    }

    public List<OrderCompletedMenu> getOrders() {
        return orders;
    }

    public Discount getDiscountEvent() {
        return discountEvent;
    }
}
