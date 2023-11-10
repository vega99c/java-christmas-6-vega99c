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
        String menuName = "해산물파스타";

        InputView inputView = new InputView();
        inputView.isExistMenu(menuName);
    }
}
