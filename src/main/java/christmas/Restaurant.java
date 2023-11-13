package christmas;

import java.util.ArrayList;
import java.util.HashSet;
import java.util.Hashtable;
import java.util.List;
import java.util.Set;

public class Restaurant {
    private final String CHRIST_MAS_EVENT_MESSAGE = "크리스마스 디데이 할인: -%s원\n";
    private final String WEEKDAY_EVENT_MESSAGE = "평일 할인: -%s원\n";
    private final String WEEKEND_EVENT_MESSAGE = "주말 할인: -%s원\n";
    private final String SPCIAL_EVENT_MESSAGE = "특별 할인: -%s원\n";
    private final String GIFTS_EVENT_MESSAGE = "증정 이벤트: -%s원\n";
    private final int CHRIST_MAS_BASE_DISCOUNT = 1000;
    private final int SPCIAL_DAY_DISCOUNT = 1000;
    private final int WEEK_DAY_END_BASE_DISCOUNT = 2023;
    private final int FIRST_DAY = 1;
    private final int CHRISTMAS_DAY = 25;
    private final int IDX_MENU_NAME = 0;
    private final int IDX_MENU_PRICE = 1;
    private final int GIFTS_SATISFIED_PRICE = 120000;
    private final List<Integer> weekEndList = new ArrayList<>(List.of(1, 2, 8, 9, 15, 16, 22, 23, 29, 30));
    private final List<Integer> weekDayList =
            new ArrayList<>(List.of(3, 4, 5, 6, 7, 10, 11, 12, 13, 14, 17, 18, 19, 20, 21, 24, 25, 26, 27, 28, 31));
    private final List<Integer> spcialDayList = new ArrayList<>(List.of(3, 10, 17, 24, 25, 31));
    InputView inputView;
    OutputView outputView;
    private Hashtable<String, Integer> orderHashTable;
    private List<String> menuList;
    private int totalQuantity;
    private String errorMsg;
    private Customer customer;
    private Menu menuInfo;
    private int totalPrice;
    private int totalDiscountPrice;
    private List<String> mainMenu;
    private List<String> dessertMenu;

    public Restaurant(Customer newCustomer) {
        customer = newCustomer;
        mainMenu = Menu.MAIN.getChildMenu();
        dessertMenu = Menu.DESSERT.getChildMenu();
        menuList = new ArrayList<String>();
        orderHashTable = new Hashtable<>();
        inputView = new InputView();
        outputView = new OutputView();
        menuInfo = Menu.ROOT;
    }

    public void runRestaurant() {
        receiptStart();
        menuOrderStart();
        showOrderList();
        calculateTotalPrice();
        isHavingGifts();
        checkWholeEvent();
    }

    public void receiptStart() {
        int readDate = 0;
        String inputDate = inputView.readDate();

        try {
            readDate = validateIsCorrectRange(validateIsInteger(inputDate));
        } catch (IllegalArgumentException error) {
            System.out.print(error.getMessage());
            inputView.readDate();
        }

        totalQuantity = 0;
        customer.setReservationDate(readDate);
    }

    public void showOrderList() {
        outputView.printCurtomerOrders(customer);
    }

    public void menuOrderStart() {
        String inputMenu = inputView.readMenu();
        List<String> menuList = null;
        menuList = new ArrayList<String>(List.of(inputMenu.split(",")));

        try {
            validateMenuOrder(menuList);
        } catch (IllegalArgumentException error) {
            System.out.print(error.getMessage());
            initiateOrderInfo();
            menuOrderStart();
        }
    }

    public void isValidForm(String orderInfo) {
        String menuName = "";
        String quantity = "";

        try {
            menuName = orderInfo.split("-")[IDX_MENU_NAME];
            quantity = orderInfo.split("-")[IDX_MENU_PRICE];
        } catch (ArrayIndexOutOfBoundsException error) {
            throw new IllegalArgumentException(ErrorMessages.INCORRECT_MENU_ORDER.getErrorMsg());
        }

        int quantityNumber = validateIsInteger(quantity);
        isExistMenu(menuName);
        System.out.println("테스트1");
        if (validateTotalOrderQuantity()) {
            orderHashTable.put(menuName, quantityNumber);
        }
        System.out.println("테스트2");
    }

    public boolean validateOnlyDrink() {
        List<String> drinkList = new ArrayList<>(List.of("제로콜라", "레드와인", "샴페인"));

        for (String menu : menuList) {
            if (!drinkList.contains(menu)) {
                return false;
            }
        }

        menuList.clear();
        throw new IllegalArgumentException(ErrorMessages.NOT_ALLOWED_ONLY_DRINK.getErrorMsg());
    }

    public void validateDuplicatedMenu() {
        Set<String> menuSet = new HashSet<>(menuList);

        if (menuSet.size() != menuList.size()) {
            menuList.clear();
            throw new IllegalArgumentException(ErrorMessages.INCORRECT_MENU_ORDER.getErrorMsg());
        }
    }

    public void initiateOrderInfo() {
        totalQuantity = 0;
        orderHashTable.clear();
        menuList.clear();
    }

    public void validateMenuOrder(List<String> orderInfos) {
        for (String order : orderInfos) {
            isValidForm(order);
        }

        System.out.println("테스트3");
        validateDuplicatedMenu();
        System.out.println("테스트4");
        validateOnlyDrink();
        System.out.println("테스트5");
        customer.setMyOrder(orderHashTable);
        System.out.println("테스트6");
        distinctionMenuCategory();
    }

    public void distinctionMenuCategory() {
        Set<String> keySet = customer.getOrderMenuNames();
        Hashtable<String, Integer> orderTable = customer.getCustomerOrder();

        for (String menuName : keySet) {
            if (mainMenu.contains(menuName)) {
                customer.increaseMainMenuCount(orderTable.get(menuName));
            }

            if (dessertMenu.contains(menuName)) {
                customer.increaseDessertCount(orderTable.get(menuName));
            }
        }
    }

    public int validateIsInteger(String string) {
        int inputDate = 0;
        try {
            inputDate = Integer.parseInt(string);
            totalQuantity += inputDate;
        } catch (NumberFormatException e) {
            errorMsg = ErrorMessages.NOT_NUMBER.getErrorMsg();
            throw new IllegalArgumentException(errorMsg);
        }

        if (inputDate == 0) {
            throw new IllegalArgumentException(ErrorMessages.INCORRECT_MENU_ORDER.getErrorMsg());
        }

        return inputDate;
    }

    public int validateIsCorrectRange(int date) {
        if (!((date >= 1) && (date <= 31))) {
            errorMsg = ErrorMessages.INCORRECT_RANGE.getErrorMsg();
            throw new IllegalArgumentException(errorMsg);
        }

        return date;
    }

    public int getTotalQuantity() {
        return totalQuantity;
    }

    public boolean validateTotalOrderQuantity() {
        System.out.println("총 갯수 : " + totalQuantity);
        if ((getTotalQuantity() < 1) || (getTotalQuantity() > 20)) {
            errorMsg = ErrorMessages.NOT_INCLUDE_ORDER_RANGE.getErrorMsg();
            totalQuantity = 0;
            throw new IllegalArgumentException(errorMsg);
        }

        return true;
    }

    public void isExistMenu(String menuName) {
        Menu menu = Menu.ROOT;
        menu.contains(menuName);
        menuList.add(menuName);
    }

    public void calculateTotalPrice() {
        Set<String> keySet = customer.getOrderMenuNames();
        Hashtable<String, Integer> customerOrder = customer.getCustomerOrder();

        for (String key : keySet) {
            int menuCount = customerOrder.get(key);
            int menuPrice = menuInfo.getMenuPrice(key);

            totalPrice = totalPrice + (menuPrice * menuCount);
        }

        outputView.printTotalPriceBeforeDiscount(totalPrice);
    }

    public int getTotalPrice() {
        return totalPrice;
    }

    public boolean isHavingGifts() {
        if (getTotalPrice() < GIFTS_SATISFIED_PRICE) {
            outputView.isNothingGiven();
            return false;
        }

        customer.setGivenGifts(true);
        outputView.printGiveGifts();
        return true;
    }

    public void checkWholeEvent() {
        checkChristmasDdayEvent();
        checkWeekdayEvent();
        checkWeekendEvent();
        checkSpcialEvent();
        checkGiftsEvent();

        showDiscountHistory();
    }

    public void showDiscountHistory() {
        Hashtable<String, Integer> discountHistory = customer.getMyDiscounts();

        outputView.printNoticeDiscountHistory();
        for (String key : customer.getMyDiscounts().keySet()) {
            outputView.printDiscountApplyHistory(key, discountHistory.get(key));
        }
    }

    public void checkChristmasDdayEvent() {
        if ((customer.getReservationDate() >= FIRST_DAY) && (customer.getReservationDate() <= CHRISTMAS_DAY)) {
            int discountPrice = CHRIST_MAS_BASE_DISCOUNT + ((customer.getReservationDate() - 1) * 100);
            customer.setMyDiscounts(CHRIST_MAS_EVENT_MESSAGE, discountPrice);
        }
    }

    public void checkWeekdayEvent() {
        if (customer.getDessertMenuCount() == 0) {
            return;
        }

        if (weekDayList.contains(customer.getReservationDate())) {
            int discountPrice = WEEK_DAY_END_BASE_DISCOUNT * customer.getDessertMenuCount();
            customer.setMyDiscounts(WEEKDAY_EVENT_MESSAGE, discountPrice);
        }
    }

    public void checkWeekendEvent() {
        if (customer.getMainMenuCount() == 0) {
            return;
        }

        if (weekEndList.contains(customer.getReservationDate())) {
            int discountPrice = WEEK_DAY_END_BASE_DISCOUNT * customer.getMainMenuCount();
            customer.setMyDiscounts(WEEKEND_EVENT_MESSAGE, discountPrice);
        }
    }

    public void checkSpcialEvent() {
        if (spcialDayList.contains(customer.getReservationDate())) {
            customer.setMyDiscounts(SPCIAL_EVENT_MESSAGE, SPCIAL_DAY_DISCOUNT);
        }
    }

    public void checkGiftsEvent() {
        if (customer.getGivenGifts()) {
            customer.setMyDiscounts(GIFTS_EVENT_MESSAGE, 25000);
        }
    }
}
