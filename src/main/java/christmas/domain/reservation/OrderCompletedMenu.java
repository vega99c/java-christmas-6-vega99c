package christmas.domain.reservation;

import christmas.domain.menu.Menu;
import christmas.domain.menu.MenuBoard;
import christmas.validation.OrderValidator;

// 주문이 정상적으로 된 메뉴와 수량 정보를 가질 모델
public class OrderCompletedMenu {
    private static final int IDX_MENU_NAME = 0;
    private static final int IDX_MENU_QUANTITY = 1;
    private final Menu menu;
    private final int quantity;

    OrderCompletedMenu(Menu menu, int quantity) {
        this.menu = menu;
        this.quantity = quantity;
    }

    public static OrderCompletedMenu parse(String inputData) {
        OrderCompletedMenu completedMenu = null;
        OrderValidator.validateOrderForm(inputData);

        String orderMenuName = inputData.split("-")[IDX_MENU_NAME];
        String orderQuantity = inputData.split("-")[IDX_MENU_QUANTITY];

        OrderValidator.validateMenuQuantityInteger(orderQuantity);
        OrderValidator.validateEachMenuQuantity(orderQuantity);

        MenuBoard menuBoard = new MenuBoard();
        Menu menu = menuBoard.findMenu(orderMenuName);

        completedMenu = new OrderCompletedMenu(menu, Integer.parseInt(orderQuantity));

        return completedMenu;
    }

    public Menu getMenu() {
        return menu;
    }

    public int getQuantity() {
        return quantity;
    }

    public int getPricePerMenu() {
        return menu.getMenuPrice() * quantity;
    }
}
