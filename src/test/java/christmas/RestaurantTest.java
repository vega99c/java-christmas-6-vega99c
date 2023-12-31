package christmas;

import static org.assertj.core.api.Assertions.assertThat;
import static org.assertj.core.api.Assertions.assertThatThrownBy;

import java.util.ArrayList;
import java.util.List;
import org.junit.jupiter.api.Test;


public class RestaurantTest {
    private static final String LINE_SEPARATOR = System.lineSeparator();

    @Test
    void 날짜입력값_숫자확인() {
        Customer customer = new Customer();
        Restaurant restaurant = new Restaurant(customer);
        String inputString = "32";

        assertThat(restaurant.validateIsInteger(inputString, ErrorMessages.INCORRECT_DATE_RANGE)).isEqualTo(32);
    }

    @Test
    void 날짜입력값_범위확인() {
        Customer customer = new Customer();
        Restaurant restaurant = new Restaurant(customer);
        EventPlan eventPlan = new EventPlan(2023, 12, 1);
        int inputDate = 32;

        assertThatThrownBy(() -> eventPlan.validateIsCorrectRange(inputDate))
                .isInstanceOf(IllegalArgumentException.class);
    }

    @Test
    void 입력된메뉴_존재여부확인() {
        String orderInfo = "해산물파스타-1";
        String menuName = orderInfo.split("-")[0];

        Customer customer = new Customer();
        Restaurant restaurant = new Restaurant(customer);

        restaurant.addOrderMenu(menuName);
    }

    @Test
    void 입력된주문양식_유효성확인() {
        String orderInfo = "해산물파스타12";
        Customer customer = new Customer();
        Restaurant restaurant = new Restaurant(customer);

        assertThatThrownBy(() -> restaurant.isValidForm(orderInfo))
                .isInstanceOf(IllegalArgumentException.class);
    }

    @Test
    void 총_주문갯수확인() {
        String orderInfo1 = "해산물파스타-2";
        String orderInfo2 = "제로콜라-7";
        Customer customer = new Customer();
        Restaurant restaurant = new Restaurant(customer);

        restaurant.isValidForm(orderInfo1);
        restaurant.isValidForm(orderInfo2);

        assertThat(restaurant.getTotalQuantity()).isEqualTo(9);
    }

    @Test
    void 중복메뉴확인() {
        List<String> orderInfo = new ArrayList<>(List.of("해산물파스타-2", "해산물파스타-3"));

        Customer customer = new Customer();
        Restaurant restaurant = new Restaurant(customer);

        assertThatThrownBy(() -> restaurant.validateMenuOrder(orderInfo))
                .isInstanceOf(IllegalArgumentException.class);
    }

    @Test
    void 음료만주문했는지() {
        String orderInfo1 = "제로콜라-2";
        String orderInfo2 = "레드와인-3";

        Customer customer = new Customer();
        Restaurant restaurant = new Restaurant(customer);

        restaurant.isValidForm(orderInfo1);
        restaurant.isValidForm(orderInfo2);

        assertThatThrownBy(() -> restaurant.validateOnlyDrink())
                .isInstanceOf(IllegalArgumentException.class);
    }

    @Test
    void 메인메뉴_디저트_몇개주문했는지() {
        List<String> orderInfo = new ArrayList<>(List.of("초코케이크-2", "해산물파스타-3"));

        Customer customer = new Customer();
        Restaurant restaurant = new Restaurant(customer);

        restaurant.validateMenuOrder(orderInfo);
        assertThat(customer.getDessertMenuCount()).isEqualTo(2);
        assertThat(customer.getMainMenuCount()).isEqualTo(3);
    }

    @Test
    void 총_주문가격확인() {
        List<String> orderList = new ArrayList<>(List.of("티본스테이크-1", "바비큐립-1")); //55,000 + 54,000

        Customer customer = new Customer();
        Restaurant restaurant = new Restaurant(customer);

        restaurant.validateMenuOrder(orderList);
        restaurant.calculateTotalPrice();

        assertThat(restaurant.getTotalPrice()).isEqualTo(109000);
    }

    @Test
    void 증정이벤트_해당확인() {
        String orders = "티본스테이크-3,제로콜라-3";
        Customer customer = new Customer();
        Restaurant restaurant = new Restaurant(customer);

        restaurant.receiptStart("3");
        restaurant.proceedOrderFlow(orders);
        restaurant.proceedCalculatePriceAndDiscount();
        restaurant.showCustomersEventBadge();

        assertThat(restaurant.isHavingGifts()).isEqualTo(true);
    }

    @Test
    void 날짜이벤트_해당확인() {
        String orders = "티본스테이크-3,제로콜라-3";
        Customer customer = new Customer();
        Restaurant restaurant = new Restaurant(customer);

        restaurant.receiptStart("3");
        restaurant.proceedOrderFlow(orders);
        restaurant.proceedCalculatePriceAndDiscount();
        restaurant.showCustomersEventBadge();

        assertThat(customer.getMyBenefits().keySet()).contains("특별 할인: -%s원" + LINE_SEPARATOR);
        assertThat(customer.getMyBenefits().keySet()).contains("크리스마스 디데이 할인: -%s원" + LINE_SEPARATOR);
    }


    @Test
    void 총혜택금액_확인() {
        String orders = "티본스테이크-3,제로콜라-3";
        Customer customer = new Customer();
        Restaurant restaurant = new Restaurant(customer);

        restaurant.receiptStart("3");
        restaurant.proceedOrderFlow(orders);
        restaurant.proceedCalculatePriceAndDiscount();
        restaurant.showCustomersEventBadge();

        assertThat(customer.getTotalBenefits()).isEqualTo(27200);
    }

    @Test
    void 최종결제금액_확인() {
        String orders = "티본스테이크-3,제로콜라-3";
        Customer customer = new Customer();
        Restaurant restaurant = new Restaurant(customer);

        restaurant.receiptStart("3");
        restaurant.proceedOrderFlow(orders);
        restaurant.proceedCalculatePriceAndDiscount();
        restaurant.showCustomersEventBadge();

        assertThat(customer.getTotalPriceAfterDiscount()).isEqualTo(171800);
    }

    @Test
    void 이벤트배지_발급확인() {
        String orders = "티본스테이크-3,제로콜라-3";
        Customer customer = new Customer();
        Restaurant restaurant = new Restaurant(customer);

        restaurant.receiptStart("3");
        restaurant.proceedOrderFlow(orders);
        restaurant.proceedCalculatePriceAndDiscount();
        restaurant.showCustomersEventBadge();

        assertThat(customer.getEventBadge()).isEqualTo("산타");
    }
}
