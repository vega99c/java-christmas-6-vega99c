package christmas;

import camp.nextstep.edu.missionutils.Console;

public class InputView {
    private final String askReservationDate = "12월 중 식당 예상 방문 날짜는 언제인가요? (숫자만 입력해주세요!)\n";
    private final String askReservationMenuOrder = "주문하실 메뉴를 메뉴와 개수를 알려 주세요. (e.g 해산물파스타-2,제로콜라-1)\n";

    public InputView() {
    }

    public String readDate() {
        System.out.print(askReservationDate);
        String inputDate = Console.readLine();

        return inputDate;
    }

    public String readMenu() {
        System.out.print(askReservationMenuOrder);
        String inputMenu = Console.readLine();

        return inputMenu;
    }
}
