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
    private int totalBenefits;
    private int totalPrice;
    private int totalPriceAfterDiscount;

    public Customer() {
        totalPrice = 0;
        mainMenuCount = 0;
        dessertCount = 0;
        reservationDate = 0;
        totalBenefits = 0;
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

    public void setTotalBenefits(int benefits) {
        totalBenefits = benefits;
    }

    public int getTotalBenefits() {
        return totalBenefits;
    }

    public void setTotalPrice(int price) {
        totalPrice = price;
    }

    public int getTotalPrice() {
        return totalPrice;
    }

    public void setTotalPriceAfterDiscount(int totalPrice) {
        totalPriceAfterDiscount = totalPrice;
    }

    public int getTotalPriceAfterDiscount() {
        return totalPriceAfterDiscount;
    }
}
