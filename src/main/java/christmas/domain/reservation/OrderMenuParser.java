package christmas.domain.reservation;

import christmas.Menu;
import christmas.validation.OrderValidator;
import java.util.ArrayList;
import java.util.HashMap;
import java.util.LinkedHashMap;
import java.util.List;
import java.util.Map;

public class OrderMenuParser {
    private static final String seperatorEachMenuRegex = ",";
    private static final String seperatorInMenuRegex = "-";
    private static final int menuNameIndex = 0;
    private static final int menuQuantityIndex = 1;

    public static HashMap<String, Integer> parse(String inputData) {
        HashMap<String, Integer> orderInfomations = new LinkedHashMap<>();

        List<String> menuList = new ArrayList<>(List.of(inputData.split(",")));

        for (String orderedMenuInfo : menuList) {
            OrderValidator.validateOrderForm(orderedMenuInfo);
            List<String> menuDetailInfo = separateMenuInfo(orderedMenuInfo);
            OrderValidator.validateMenuExist(menuDetailInfo.get(menuNameIndex));
            OrderValidator.validateMenuQuantityInteger(menuDetailInfo.get(menuQuantityIndex));
            OrderValidator.validateEachMenuQuantity(menuDetailInfo.get(menuQuantityIndex));
            OrderValidator.validateMenuDuplicate(orderInfomations, menuDetailInfo.get(menuNameIndex));
            orderInfomations.put(menuDetailInfo.get(menuNameIndex),
                    Integer.parseInt(menuDetailInfo.get(menuQuantityIndex)));
        }

        return orderInfomations;
    }

    private static List<String> separateMenuInfo(String orderedMenuInfo) {
        return List.of(orderedMenuInfo.split(seperatorInMenuRegex));
    }
}
