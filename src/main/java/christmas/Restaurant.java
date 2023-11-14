package christmas;

import java.util.ArrayList;
import java.util.HashSet;
import java.util.Hashtable;
import java.util.List;
import java.util.Set;

public class Restaurant {
    private final int EVENT_YEAR = 2023;
    private final int EVENT_MONTH = 12;
    private final int IDX_MENU_NAME = 0;
    private final int IDX_MENU_PRICE = 1;
    private final int GIFTS_SATISFIED_PRICE = 120000;
    private final int EVENT_LEAST_AMOUNT = 10000;
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
    private EventPlan eventPlan;

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
        outputView.printWelcomeMessage(EVENT_MONTH);
        receiptStart();
        menuOrderStart();
        showOrderList();
        calculateTotalPrice();
        isHavingGifts();
        checkWholeEvent();
        calculateTotalPriceAfterDiscount();
    }

    public void receiptStart() {
        int readDate = 0;
        String inputDate = inputView.readDate();

        try {
            readDate = validateIsInteger(inputDate, ErrorMessages.INCORRECT_DATE_RANGE);
            eventPlan = new EventPlan(EVENT_YEAR, EVENT_MONTH, readDate);
            customer.setReservationDate(readDate);
        } catch (IllegalArgumentException error) {
            System.out.print(error.getMessage());
            receiptStart();
        }
    }

    public void setEventPlan(EventPlan plan) {
        eventPlan = plan;
    }

    public void showOrderList() {
        outputView.printEventHistoryPreviewMessage(EVENT_MONTH, customer.getReservationDate());
        outputView.printCustomerOrders(customer);
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

    public void initiateOrderInfo() {
        totalQuantity = 0;
        orderHashTable.clear();
        menuList.clear();
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

        isExistMenu(menuName);
        int quantityNumber = validateIsInteger(quantity, ErrorMessages.INCORRECT_MENU_ORDER);
        totalQuantity += quantityNumber;

        if (validateTotalOrderQuantity()) {
            orderHashTable.put(menuName, quantityNumber);
        }
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

    public void validateMenuOrder(List<String> orderInfos) {
        for (String order : orderInfos) {
            isValidForm(order);
        }

        validateDuplicatedMenu();
        validateOnlyDrink();
        customer.setMyOrder(orderHashTable);
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

    public int validateIsInteger(String string, ErrorMessages errorType) {
        int inputData = 0;
        try {
            inputData = Integer.parseInt(string);
        } catch (NumberFormatException e) {
            errorMsg = errorType.getErrorMsg();
            throw new IllegalArgumentException(errorMsg);
        }

        if (inputData == 0) {
            throw new IllegalArgumentException(errorType.getErrorMsg());
        }

        return inputData;
    }

    public int getTotalQuantity() {
        return totalQuantity;
    }

    public boolean validateTotalOrderQuantity() {
        if ((getTotalQuantity() < 1) || (getTotalQuantity() > 20)) {
            errorMsg = ErrorMessages.NOT_INCLUDE_ORDER_RANGE.getErrorMsg();
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

        customer.setTotalPrice(totalPrice);
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
        outputView.printGiveGifts(eventPlan.getGiftsMenuName(), eventPlan.getGiftsCount());
        return true;
    }

    public void checkWholeEvent() {
        if (getTotalPrice() >= EVENT_LEAST_AMOUNT) {
            eventPlan.checkApplyingEvent(customer);
        }

        showBenefitsHistory();
        calculateTotalBenefits();
        outputView.printTotalBenefits(customer.getTotalBenefits());
    }

    public void calculateTotalBenefits() {
        int totalBenefits = 0;
        Set<String> keySet = customer.getMyBenefits().keySet();

        for (String key : keySet) {
            totalBenefits += customer.getMyBenefits().get(key);
        }

        customer.setTotalBenefits(totalBenefits);
    }

    public void calculateTotalPriceAfterDiscount() {
        int totalPrice = customer.getTotalPrice() - customer.getTotalBenefits();
        customer.setTotalPriceAfterDiscount(totalPrice);

        outputView.printTotalPriceAfterDiscount(customer.getTotalPriceAfterDiscount());
    }

    public void showBenefitsHistory() {
        Hashtable<String, Integer> benefitsHistory = customer.getMyBenefits();

        if (benefitsHistory.isEmpty()) {
            outputView.isNoting();
            return;
        }

        outputView.printNoticeBenefitsHistory();
        for (String key : customer.getMyBenefits().keySet()) {
            outputView.printBenefitsApplyHistory(key, benefitsHistory.get(key));
        }

        System.out.print("\n");
    }
}
