package christmas;

import camp.nextstep.edu.missionutils.Console;
import java.util.ArrayList;
import java.util.List;

public class InputView {
    private final String askReservationDate = "12월 중 식당 예상 방문 날짜는 언제인가요? (숫자만 입력해주세요!)\n";
    private final String askReservationMenuOrder = "주문하실 메뉴를 메뉴와 개수를 알려 주세요. (e.g 해산물파스타-2,제로콜라-1)\n";
    private String errorMsg;
    private final int IDX_MENU_NAME = 0;
    private final int IDX_MENU_PRICE = 1;

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

    public void readMenu() {
        System.out.print(askReservationMenuOrder);
        String inputMenu = Console.readLine();
        System.out.println("inputmenu : " + inputMenu);
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

    public void isValidForm(String orderInfo) {
        String menuName = "";
        String quantity = "";

        try {
            menuName = orderInfo.split("-")[IDX_MENU_NAME];
            quantity = orderInfo.split("-")[IDX_MENU_PRICE];
        } catch (ArrayIndexOutOfBoundsException error) {
            throw new IllegalArgumentException(ErrorMessages.INCORRECT_MENU_ORDER.getErrorMsg());
        }

        isExistMenu(menuName);
        validateIsInteger(quantity);
    }

    public void isExistMenu(String menuName) {
        Menu menu = Menu.ROOT;
        menu.contains(menuName);
    }

    public int validateIsInteger(String string) {
        int inputDate = 0;
        try {
            inputDate = Integer.parseInt(string);
        } catch (NumberFormatException e) {
            errorMsg = ErrorMessages.NOT_NUMBER.getErrorMsg();
            throw new IllegalArgumentException(errorMsg);
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