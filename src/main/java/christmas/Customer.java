package christmas;

import java.util.Hashtable;
import java.util.Set;

public class Customer {
    private Hashtable<String, Integer> myOrder;
    private Hashtable<String, Integer> myDiscounts;
    private int reservationDate;
    private int mainMenuCount;
    private int dessertCount;
    private boolean isGivenGifts;

    public Customer() {
        mainMenuCount = 0;
        dessertCount = 0;
        reservationDate = 0;
        myDiscounts = new Hashtable<>();
        myOrder = new Hashtable<>();
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

    public void setReservationDate(int date) {
        reservationDate = date;
    }

    public int getReservationDate() {
        return reservationDate;
    }

    public void setMyDiscounts(String eventName, int discountPrice) {
        myDiscounts.put(eventName, discountPrice);
    }

    public Hashtable<String, Integer> getMyDiscounts() {
        return myDiscounts;
    }

    public void setGivenGifts(boolean isGiven) {
        isGivenGifts = isGiven;
    }

    public boolean getGivenGifts() {
        return isGivenGifts;
    }
}
