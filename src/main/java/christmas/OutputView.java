package christmas;

import java.util.Hashtable;
import java.util.Set;

public class OutputView {
    private final String ARARM_ORDER_MENU = "<주문 메뉴>\n";

    public OutputView() {
    }

    public void printCurtomerOrders(Customer customer) {
        System.out.print(ARARM_ORDER_MENU);
        Set<String> keySet = customer.getOrderMenuNames();
        Hashtable<String, Integer> customerOrder = customer.getCustomerOrder();

        for (String key : keySet) {
            int value = customerOrder.get(key);
            System.out.printf("%s %d개\n", key, value);
        }
    }
}
