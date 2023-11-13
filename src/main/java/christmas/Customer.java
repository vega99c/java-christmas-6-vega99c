package christmas;

import java.util.Hashtable;
import java.util.Set;

public class Customer {
    private Hashtable<String, Integer> myOrder;
    private Hashtable<String, Integer> myDiscounts;
    private int reservationDate;
    private boolean isChristmasDdayEvent;
    private boolean isWeekdayEvent;
    private boolean isWeekendEvent;
    private boolean isSpcialEvent;
    private int mainMenuCount;
    private int dessertCount;

    public Customer(int date) {
        mainMenuCount = 0;
        dessertCount = 0;
        reservationDate = date;
        myDiscounts = new Hashtable<>();
    }

    public void setMyOrder(Hashtable<String, Integer> orders) {
        myOrder = orders;
    }

    public Set<String> getOrderMenuNames() {
        Set<String> keySet = myOrder.keySet();

        return keySet;
    }

    public Hashtable<String, Integer> getCustomerOrder() {
        return myOrder;
    }

    public void increaseMainMenuCount(int count) {
        mainMenuCount += count;
    }

    public void increaseDessertCount(int count) {
        dessertCount += count;
    }

    public int getMainMenuCount() {
        return mainMenuCount;
    }

    public int getDessertMenuCount() {
        return dessertCount;
    }
}
