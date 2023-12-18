package christmas;

//import static christmas.validation.OrderValidator.validateMenuIsExist;
//import static christmas.validation.OrderValidator.validateOrderForm;


import christmas.validation.*;

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
    private final int MIN_ORDER_QUANTITY = 1;
    private final int MAX_ORDER_QUANTITY = 20;
    private final String EXCEPT_BENEFIT = "증정 이벤트";
    InputView inputView;
    OutputView outputView;
    private Hashtable<String, Integer> orderHashTable;
    private List<String> menuList;
    private int totalQuantity;
    private String errorMsg;
    private Customer customer;
    private Menu menuInfo;
    private int totalPrice;
    private List<String> mainMenu;
    private List<String> dessertMenu;
    private EventPlan eventPlan;
    private static final String LINE_SEPARATOR = System.lineSeparator();

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
        receiptStart(inputView.readDate());
        proceedOrderFlow(inputView.readMenu());
        proceedCalculatePriceAndDiscount();
        showCustomersEventBadge();
    }

    public void proceedCalculatePriceAndDiscount() {
        calculateTotalPrice();
        checkWholeEvent();
        calculateTotalDiscounts();
        calculateTotalPriceAfterDiscount();
    }

    public void proceedOrderFlow(String inputMenu) {
        menuOrderStart(inputMenu);
        showOrderList();
    }

    public void receiptStart(String inputDate) {
        int readDate = 0;

        try {
            readDate = validateIsInteger(inputDate, ErrorMessages.INCORRECT_DATE_RANGE);
            eventPlan = new EventPlan(EVENT_YEAR, EVENT_MONTH, readDate);
            customer.setReservationDate(readDate);
            eventPlan.setCustomer(customer);
        } catch (IllegalArgumentException error) {
            System.out.print(error.getMessage());
            receiptStart(inputView.readDate());
        }
    }

    private void showOrderList() {
        outputView.printEventHistoryPreviewMessage(EVENT_MONTH, customer.getReservationDate());
        outputView.printCustomerOrders(customer);
    }

    public void menuOrderStart(String inputMenu) {
        List<String> menuList = null;
        menuList = new ArrayList<String>(List.of(inputMenu.split(",")));

        try {
            validateMenuOrder(menuList);
        } catch (IllegalArgumentException error) {
            System.out.print(error.getMessage());
            initiateOrderInfo();
            menuOrderStart(inputView.readMenu());
        }
    }

    private void initiateOrderInfo() {
        totalQuantity = 0;
        orderHashTable.clear();
        menuList.clear();
    }

    //완
    public void isValidForm(String orderInfo) {

    }

//    public void addOrderMenu(String menuName) {
//        Menu menu = Menu.ROOT;
//        menu.contains(menuName);
//        menuList.add(menuName);
//    }


    public void validateMenuOrder(List<String> orderInfos) {
        for (String order : orderInfos) {
            isValidForm(order);
        }
        
//        validateOnlyDrink();
        OrderValidator.validateMenuOnlyDrink(menuList);

        customer.setMyOrder(orderHashTable);
        distinctionMenuCategory();
    }

    //완
    public int validateIsInteger(String string, ErrorMessages errorType) {
        int inputData = 0;
        try {
            inputData = Integer.parseInt(string);
        } catch (IllegalArgumentException e) {
            errorMsg = errorType.getErrorMsg();
            throw new IllegalArgumentException(errorMsg);
        }

        //완
        if (inputData == 0) {
            throw new IllegalArgumentException(errorType.getErrorMsg());
        }

        return inputData;
    }

    private void distinctionMenuCategory() {
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

    public int getTotalQuantity() {
        return totalQuantity;
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
        isHavingGifts();
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

    private void checkWholeEvent() {
        if (getTotalPrice() >= EVENT_LEAST_AMOUNT) {
            eventPlan.checkApplyingEvent();
        }

        showBenefitsHistory();
        calculateTotalBenefits();
        outputView.printTotalBenefits(customer.getTotalBenefits());
    }

    private void calculateTotalBenefits() {
        int totalBenefits = 0;
        Set<String> keySet = customer.getMyBenefits().keySet();

        for (String key : keySet) {
            totalBenefits += customer.getMyBenefits().get(key);
        }

        customer.setTotalBenefits(totalBenefits);
        eventPlan.checkBadgeEvent();
    }

    private void calculateTotalDiscounts() {
        int totalDiscounts = 0;
        Set<String> keySet = customer.getMyBenefits().keySet();

        for (String key : keySet) {
            if (!key.contains(EXCEPT_BENEFIT)) {
                totalDiscounts += customer.getMyBenefits().get(key);
            }
        }

        customer.setTotalDiscounts(totalDiscounts);
    }

    private void calculateTotalPriceAfterDiscount() {
        int totalPrice = customer.getTotalPrice() - customer.getTotalDiscounts();
        customer.setTotalPriceAfterDiscount(totalPrice);

        outputView.printTotalPriceAfterDiscount(customer.getTotalPriceAfterDiscount());
    }

    public void showCustomersEventBadge() {
        outputView.printEventBadge(EVENT_MONTH, customer.getEventBadge());
    }

    private void showBenefitsHistory() {
        Hashtable<String, Integer> benefitsHistory = customer.getMyBenefits();

        if (benefitsHistory.isEmpty()) {
            outputView.isNoting();
            return;
        }

        outputView.printNoticeBenefitsHistory();
        for (String key : customer.getMyBenefits().keySet()) {
            outputView.printBenefitsApplyHistory(key, benefitsHistory.get(key));
        }

        System.out.print(LINE_SEPARATOR);
    }
}
