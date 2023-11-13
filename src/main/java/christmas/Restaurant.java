package christmas;

import java.util.ArrayList;
import java.util.HashSet;
import java.util.Hashtable;
import java.util.List;
import java.util.Set;

public class Restaurant {
    private final int IDX_MENU_NAME = 0;
    private final int IDX_MENU_PRICE = 1;
    InputView inputView;
    private Hashtable<String, Integer> orderHashTable;
    private List<String> menuList;
    private int totalQuantity = 0;
    private String errorMsg;
    private Customer customer;
    private Menu menuInfo;
    private int totalPrice;

    public Restaurant() {
        menuList = new ArrayList<String>();
        orderHashTable = new Hashtable<>();
        inputView = new InputView();
        menuInfo = Menu.ROOT;
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
    }

    public void showOrderList() {
        OutputView outputView = new OutputView();
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
            inputView.readMenu();
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
        validateTotalOrderQuantity();
        validateDuplicatedMenu();
        orderHashTable.put(menuName, quantityNumber);
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
    }

    public void validateMenuOrder(List<String> orderInfos) {
        for (String order : orderInfos) {
            isValidForm(order);
        }

        validateDuplicatedMenu();
        validateOnlyDrink();
        customer = new Customer(orderHashTable);
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

    public void validateTotalOrderQuantity() {
        if ((getTotalQuantity() < 1) || (getTotalQuantity() > 20)) {
            errorMsg = ErrorMessages.NOT_INCLUDE_ORDER_RANGE.getErrorMsg();
            totalQuantity = 0;
            throw new IllegalArgumentException(errorMsg);
        }
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
    }

    public int getTotalPrice() {
        return totalPrice;
    }
}
