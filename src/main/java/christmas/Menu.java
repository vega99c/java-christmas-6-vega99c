package christmas;

import java.util.Arrays;

public enum Menu {
    ROOT("메뉴", null, 0),
    APPTETIZER("애피타이저", null, 0),
    MUSHROOM_SOUP("양송이수프", APPTETIZER, 6000),
    TAPAS("타파스", APPTETIZER, 5500),
    CAESAR_SALAD("시저샐러드", APPTETIZER, 8000),
    MAIN("메인", null, 0),
    T_BONE_STAKE("티본스테이크", MAIN, 55000),
    BBQ_RIBS("바비큐립", MAIN, 54000),
    SEAFOOD_PASTA("해산물파스타", MAIN, 35000),
    CHRISTMAS_PASTA("크리스마스파트다", MAIN, 25000),
    DESSERT("디저트", null, 0),
    CHOCO_CAKE("초코케이크", DESSERT, 15000),
    ICE_CREAM("아이스크림", DESSERT, 5000),
    DRINK("음료", null, 0),
    ZERO_COKE("제로콜라", DRINK, 3000),
    RED_WINE("레드와인", DRINK, 60000),
    CHAMPAGNE("샴페인", DRINK, 25000);

    private final String menuName;
    private final Menu menuCategory;
    private final int price;

    Menu(String name, Menu menuCategory, int price) {
        this.menuName = name;
        this.menuCategory = menuCategory;
        this.price = price;
    }

    public String getMenuName() {
        return menuName;
    }

    public int getPrice() {
        return price;
    }

    public Menu getCategory() {
        return menuCategory;
    }

    public void contains(String name) {
        Menu menuName = Arrays.stream(Menu.values())
                .filter(menu -> menu.getMenuName().equals(name))
                .findFirst()
                .orElseThrow(() -> new IllegalArgumentException(ErrorMessages.NOT_EXIST_MATCHED_MENU.getErrorMsg()));
    }
}
