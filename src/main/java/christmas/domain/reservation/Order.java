package christmas.domain.reservation;

import christmas.domain.menu.Menu;
import java.util.ArrayList;
import java.util.List;

public class Order {
    List<OrderCompletedMenu> menus = new ArrayList<>();

    // 입력된 주문 데이터를 가지고 OrderedMenu 리스트에 추가 로직
    public List<OrderCompletedMenu> ordersByInputData(String inputData) {
        return menus;
    }

    public void printOrders() {

    }
}
