package christmas;

import java.util.Hashtable;
import java.util.Set;

public class Customer {
    private Hashtable<String, Integer> myOrder;

    public Customer(Hashtable<String, Integer> orders) {
        myOrder = orders;
    }

    public Set<String> getOrderMenuNames() {
        Set<String> keySet = myOrder.keySet();

        return keySet;
    }

    public Hashtable<String, Integer> getCustomerOrder() {
        return myOrder;
    }
}
