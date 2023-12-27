package christmas.domain.reservation;

import christmas.domain.menu.Menu;

// 주문이 정상적으로 된 메뉴와 수량 정보를 가질 모델
public class OrderCompletedMenu {

    private final Menu menu;
    private final int quantity;

    OrderCompletedMenu(Menu menu, int quantity) {
        this.menu = menu;
        this.quantity = quantity;
    }

    public Menu getMenu() {
        return menu;
    }

    public int getQuantity() {
        return quantity;
    }
}
