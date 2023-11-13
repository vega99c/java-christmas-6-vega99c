package christmas;

import static org.assertj.core.api.Assertions.assertThat;

import java.util.ArrayList;
import java.util.List;
import org.junit.jupiter.api.Test;


public class RestaurantTest {
    @Test
    void 총_주문가격확인() {
        List<String> orderList = new ArrayList<>(List.of("티본스테이크-1", "바비큐립-1")); //55,000 + 54,000

        Restaurant restaurant = new Restaurant();

        restaurant.validateMenuOrder(orderList);
        restaurant.calculateTotalPrice();

        assertThat(restaurant.getTotalPrice()).isEqualTo(109000);
    }
}
