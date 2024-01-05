package christmas.domain.reservation;

import christmas.domain.menu.Menu;
import christmas.validation.OrderValidator;
import java.util.ArrayList;
import java.util.List;

public class Order {
    List<OrderCompletedMenu> orders = new ArrayList<>();

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

        return orders;
    }
}
