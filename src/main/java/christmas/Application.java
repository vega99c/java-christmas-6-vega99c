package christmas;

public class Application {
    public static void main(String[] args) {
        Customer customer = new Customer();
        Restaurant restaurant = new Restaurant(customer);
        restaurant.runRestaurant();
    }
}
