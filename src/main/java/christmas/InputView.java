package christmas;

import camp.nextstep.edu.missionutils.Console;

public class InputView {
    private final String askReservationDate = "12월 중 식당 예상 방문 날짜는 언제인가요? (숫자만 입력해주세요!)\n";
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

    public void readMenu() {
        String inputMenu = Console.readLine();
        String menuName = inputMenu.split("-")[IDX_MENU_NAME];
        String price = inputMenu.split("-")[IDX_MENU_PRICE];

        isExistMenu(menuName);
        validateIsInteger(price);
    }

    public void isExistMenu(String menuName) {
        Menu menu = Menu.ROOT;
        menu.contains(menuName);
    }
}
