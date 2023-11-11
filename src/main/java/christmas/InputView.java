package christmas;

import camp.nextstep.edu.missionutils.Console;
import java.util.ArrayList;
import java.util.HashSet;
import java.util.Hashtable;
import java.util.List;
import java.util.Set;

public class InputView {
    private final String askReservationDate = "12월 중 식당 예상 방문 날짜는 언제인가요? (숫자만 입력해주세요!)\n";
    private final String askReservationMenuOrder = "주문하실 메뉴를 메뉴와 개수를 알려 주세요. (e.g 해산물파스타-2,제로콜라-1)\n";
    private String errorMsg;
    private final int IDX_MENU_NAME = 0;
    private final int IDX_MENU_PRICE = 1;
    private int totalQuantity = 0;
    private Hashtable<String, Integer> orderHashTable;
    private List<String> menuList;

    public InputView() {
        menuList = new ArrayList<String>();
        orderHashTable = new Hashtable<>();
    }

    public int readDate() {
        System.out.print(askReservationDate);
        String inputDate = Console.readLine();
        int readDate = 0;

        try {
            readDate = validateIsCorrectRange(validateIsInteger(inputDate));
        } catch (IllegalArgumentException error) {
            System.out.print(error.getMessage());
            readDate();
        }

        return readDate;
    }

    public int getTotalQuantity() {
        return totalQuantity;
    }

    public void readMenu() {
        System.out.print(askReservationMenuOrder);
        String inputMenu = Console.readLine();
        List<String> menuList = null;
        menuList = new ArrayList<String>(List.of(inputMenu.split(",")));

        try {
            validateMenuOrder(menuList);
        } catch (IllegalArgumentException error) {
            System.out.print(error.getMessage());
            readMenu();
        }
    }

    public void validateMenuOrder(List<String> orderInfos) {
        for (String order : orderInfos) {
            isValidForm(order);
        }
    }

    public void validateDuplicatedMenu(String menuName, int quantityNumber) {
        menuList.add(menuName);
        Set<String> menuSet = new HashSet<>(menuList);

        if (menuSet.size() != menuList.size()) {
            menuList.clear();
            throw new IllegalArgumentException(ErrorMessages.INCLUDE_DUPLICATED_MENU.getErrorMsg());
        }

        orderHashTable.put(menuName, quantityNumber);
    }

    public void isValidForm(String orderInfo) {
        String menuName = "";
        String quantity = "";

        try {
            menuName = orderInfo.split("-")[IDX_MENU_NAME];
            quantity = orderInfo.split("-")[IDX_MENU_PRICE];
        } catch (ArrayIndexOutOfBoundsException error) {
            throw new IllegalArgumentException(ErrorMessages.INCORRECT_MENU_ORDER.getErrorMsg());
        }

        int quantityNumber = validateIsInteger(quantity);
        isExistMenu(menuName);
        validateTotalOrderQuantity();
        validateDuplicatedMenu(menuName, quantityNumber);
    }

    public void validateTotalOrderQuantity() {
        if ((getTotalQuantity() < 1) || (getTotalQuantity() > 20)) {
            errorMsg = ErrorMessages.NOT_INCLUDE_ORDER_RANGE.getErrorMsg();
            totalQuantity = 0;
            throw new IllegalArgumentException(errorMsg);
        }
    }

    public void isExistMenu(String menuName) {
        Menu menu = Menu.ROOT;
        menu.contains(menuName);
    }

    public int validateIsInteger(String string) {
        int inputDate = 0;
        try {
            inputDate = Integer.parseInt(string);
            totalQuantity += inputDate;
        } catch (NumberFormatException e) {
            errorMsg = ErrorMessages.NOT_NUMBER.getErrorMsg();
            throw new IllegalArgumentException(errorMsg);
        }

        if (inputDate == 0) {
            throw new IllegalArgumentException(ErrorMessages.NOT_ALLOW_ZERO.getErrorMsg());
        }

        return inputDate;
    }

    public int validateIsCorrectRange(int date) {
        if (!((date >= 1) && (date <= 31))) {
            errorMsg = ErrorMessages.INCORRECT_RANGE.getErrorMsg();
            throw new IllegalArgumentException(errorMsg);
        }

        return date;
    }
}
