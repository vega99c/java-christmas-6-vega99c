package christmas;

import java.util.HashMap;
import java.util.Set;

public class Customer {
    private HashMap<String, Integer> myOrder;
    private HashMap<String, Integer> myBenefits;
    private int reservationDate;
    private int mainMenuCount;
    private int dessertCount;
    private boolean isGivenGifts;
    private int totalBenefits;
    private int totalPrice;
    private int totalPriceAfterDiscount;
    private int totalDiscounts;
    private String eventBadge;

    public Customer() {
        totalPrice = 0;
        mainMenuCount = 0;
        dessertCount = 0;
        reservationDate = 0;
        totalBenefits = 0;
        myBenefits = new HashMap<>();
        myOrder = new HashMap<>();
    }

    public void setMyOrder(HashMap<String, Integer> orders) {
        myOrder = orders;
    }

    public Set<String> getOrderMenuNames() {

        return myOrder.keySet();
    }

    public HashMap<String, Integer> getCustomerOrder() {
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

    public void setMyBenefits(String eventName, int discountPrice) {
        myBenefits.put(eventName, discountPrice);
    }

    public HashMap<String, Integer> getMyBenefits() {
        return myBenefits;
    }

    public void setGivenGifts(boolean isGiven) {
        isGivenGifts = isGiven;
    }

    public boolean getGivenGifts() {
        return isGivenGifts;
    }

    public void updateTotalBenefits(int benefits) {
        totalBenefits += benefits;
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

    public void setTotalDiscounts(int discounts) {
        totalDiscounts = discounts;
    }

    public int getTotalDiscounts() {
        return totalDiscounts;
    }

    public void setEventBadge(String badgeName) {
        eventBadge = badgeName;
    }

    public String getEventBadge() {
        return eventBadge;
    }
}
