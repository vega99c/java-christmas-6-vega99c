package christmas.domain.customer;

import christmas.domain.event.Badge;
import christmas.domain.reservation.Order;
import java.time.LocalDate;
import java.util.List;

/*
    고객기준에서 언제 주문했는지
    뭘 주문했는지, 총 금액이 얼마 나왔는지, 할인을 얼마 받았는지,
    발급받은 이벤트 배지 종류
 */
public class Customer {
    private List<Order> orders;
    private LocalDate reservationDate;
    private int totalPriceBeforeDiscount;
    private int countOfMainMenuOrder;
    private int countOfDessertOrder;
    private String eventBadge;
    private String eventGift;

    public void setOrders() {
    }

    public List<Order> getOrders() {
        return orders;
    }

    public void setReservationDate() {
    }

    public LocalDate getReservationDate() {
        return reservationDate;
    }

    public void setTotalPriceBeforeDiscount() {
    }

    public int getTotalPriceBeforeDiscount() {
        return totalPriceBeforeDiscount;
    }

    public void increaseCountOfMainMenuOrder() {

    }

    public int getCountOfMainMenuOrder() {
        return countOfMainMenuOrder;
    }

    public void increaseCountOfDessertOrder() {

    }

    public int getCountOfDessertOrder() {
        return countOfMainMenuOrder;
    }

    public void setEventBadge() {
    }

    public String getEventBadge() {
        return eventBadge;
    }

    public void setEventGift() {
    }

    public String getEventGift() {
        return eventGift;
    }
}



