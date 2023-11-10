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
        String orderInfo = "해산물파스타-1a";
        String orderInfo2 = "해산물파스타1a";
        InputView inputView = new InputView();

        assertThatThrownBy(() -> inputView.isValidForm(orderInfo))
                .isInstanceOf(IllegalArgumentException.class);

        assertThatThrownBy(() -> inputView.isValidForm(orderInfo2))
                .isInstanceOf(IllegalArgumentException.class);
    }

}
