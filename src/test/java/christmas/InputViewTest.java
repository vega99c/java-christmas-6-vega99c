package christmas;

import static org.assertj.core.api.Assertions.assertThat;
import static org.assertj.core.api.Assertions.assertThatThrownBy;

import org.junit.jupiter.api.Test;

public class InputViewTest {
    @Test
    void 날짜입력값_숫자확인() {
        InputView inputView = new InputView();
        String inputString = "32";

        assertThat(inputView.validateIsInteger(inputString)).isEqualTo(32);
    }

    @Test
    void 날짜입력값_범위확인() {
        InputView inputView = new InputView();
        int inputDate = 32;

        assertThatThrownBy(() -> inputView.validateIsCorrectRange(inputDate))
                .isInstanceOf(IllegalArgumentException.class);
    }

    @Test
    void 입력된메뉴_존재여부확인() {
        String orderInfo = "해산물파스타-1";
        String menuName = orderInfo.split("-")[0];

        InputView inputView = new InputView();
        inputView.isExistMenu(menuName);
    }

    @Test
    void 입력된주문양식_유효성확인() {
        String orderInfo = "해산물파스타-1";
        String orderInfo2 = "해산물파스타1a";
        InputView inputView = new InputView();

        assertThatThrownBy(() -> inputView.isValidForm(orderInfo))
                .isInstanceOf(IllegalArgumentException.class);

        assertThatThrownBy(() -> inputView.isValidForm(orderInfo2))
                .isInstanceOf(IllegalArgumentException.class);
    }

    @Test
    void 총_주문갯수확인() {
        String orderInfo1 = "해산물파스타-2";
        String orderInfo2 = "제로콜라-7";
        InputView inputView = new InputView();

        inputView.isValidForm(orderInfo1);
        inputView.isValidForm(orderInfo2);

        assertThat(inputView.getTotalQuantity()).isEqualTo(9);
    }

    @Test
    void 중복메뉴확인() {
        String orderInfo1 = "해산물파스타-2";
        String orderInfo2 = "해산물파스타-7";
        InputView inputView = new InputView();

        inputView.isValidForm(orderInfo1);

        assertThatThrownBy(() -> inputView.isValidForm(orderInfo2))
                .isInstanceOf(IllegalArgumentException.class);
    }

    @Test
    void 음료만주문했는지() {
        String orderInfo1 = "제로콜라-2";
        String orderInfo2 = "레드와인-3";

        InputView inputView = new InputView();

        inputView.isValidForm(orderInfo1);
        inputView.isValidForm(orderInfo2);

        assertThatThrownBy(() -> inputView.validateOnlyDrink())
                .isInstanceOf(IllegalArgumentException.class);
    }
}
