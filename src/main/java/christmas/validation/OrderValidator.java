package christmas.validation;

import christmas.Menu;
import christmas.exception.OrderMenuException;
import com.sun.nio.sctp.IllegalReceiveException;
import java.util.HashMap;
import java.util.List;


public class OrderValidator {
    private static final int IDX_MENU_NAME = 0;
    private static final int IDX_MENU_QUANTITY = 1;
    private static final int MIN_ORDER_QUANTITY = 1;
    private static final int MAX_ORDER_QUANTITY = 20;

    public static void validateOrderForm(String orderInfo) {
        try {
            String name = orderInfo.split("-")[IDX_MENU_NAME];
            String quantity = orderInfo.split("-")[IDX_MENU_QUANTITY];
        } catch (IllegalArgumentException exception) {
            throw new OrderMenuException();
        }
    }

    public static void validateMenuExist(String menuName) {
        try {
            Menu.ROOT.contains(menuName);
        } catch (IllegalReceiveException exception) {
            throw new OrderMenuException();
        }
    }

    public static void validateMenuDuplicate(HashMap<String, Integer> orderInfomations, String orderMenuName) {
        for (String orderedMenuName : orderInfomations.keySet()) {
            if (orderedMenuName.equals(orderMenuName)) {
                throw new OrderMenuException();
            }
        }
    }

    public static void validateMenuOnlyDrink(List<String> menus) {
        List<String> drinkList = Menu.DRINK.getChildMenu();
        boolean triggerIsOnlyDrink = true;

        for (String orderedMenuName : menus) {
            if (!drinkList.contains(orderedMenuName)) {
                triggerIsOnlyDrink = false;
                break;
            }
        }
        if (triggerIsOnlyDrink) {
            throw new OrderMenuException();
        }
    }

    public static void validateTotalOrderQuantity(int totalQuantity) {
        if (totalQuantity < MIN_ORDER_QUANTITY || totalQuantity > MAX_ORDER_QUANTITY) {
            throw new OrderMenuException();
        }
    }

    public static void validateMenuQuantityInteger(String number) {
        try {
            Integer.parseInt(number);
        } catch (IllegalArgumentException exception) {
            throw new OrderMenuException();
        }
    }

    /*
        메뉴값이 0 이거나 20 초과되는 값을 넣으면 안되며 굳이 다음 메뉴 확인 안해도 됨
        위의 총 수량 확인하는 검증은 한번만 수행 할 것이기 때문에 우선 별개로 유효성 검사를 진행한다.
    */
    public static void validateEachMenuQuantity(String number) {
        int parseNumber = Integer.parseInt(number);
        if (parseNumber == 0 || parseNumber > 20) {
            throw new OrderMenuException();
        }
    }
}
