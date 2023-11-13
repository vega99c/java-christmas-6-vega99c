package christmas;

import java.util.ArrayList;
import java.util.Arrays;
import java.util.Collections;
import java.util.List;
import java.util.Objects;

public enum Menu {
    ROOT("메뉴", null, 0),
    APPTETIZER("애피타이저", ROOT, 0),
    MUSHROOM_SOUP("양송이수프", APPTETIZER, 6000),
    TAPAS("타파스", APPTETIZER, 5500),
    CAESAR_SALAD("시저샐러드", APPTETIZER, 8000),
    MAIN("메인", ROOT, 0),
    T_BONE_STAKE("티본스테이크", MAIN, 55000),
    BBQ_RIBS("바비큐립", MAIN, 54000),
    SEAFOOD_PASTA("해산물파스타", MAIN, 35000),
    CHRISTMAS_PASTA("크리스마스파스타", MAIN, 25000),
    DESSERT("디저트", ROOT, 0),
    CHOCO_CAKE("초코케이크", DESSERT, 15000),
    ICE_CREAM("아이스크림", DESSERT, 5000),
    DRINK("음료", ROOT, 0),
    ZERO_COKE("제로콜라", DRINK, 3000),
    RED_WINE("레드와인", DRINK, 60000),
    CHAMPAGNE("샴페인", DRINK, 25000);

    private final String menuName;
    private final Menu menuCategory;
    private final int price;
    private final List<Menu> childCategories;
    private final List<String> childMenu;

    Menu(String name, Menu menuCategory, int price) {
        this.childMenu = new ArrayList<>();
        this.childCategories = new ArrayList<>();
        this.menuName = name;
        this.menuCategory = menuCategory;
        this.price = price;
        if (Objects.nonNull(menuCategory)) {
            menuCategory.childCategories.add(this);
            menuCategory.childMenu.add(this.menuName);
        }
    }

    public List<Menu> getChildCategories() {
        return Collections.unmodifiableList(childCategories);
    }

    public List<String> getChildMenu() {
        return Collections.unmodifiableList(childMenu);
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

    public Menu contains(String name) {
        Menu menuName = Arrays.stream(Menu.values())
                .filter(menu -> menu.getMenuName().equals(name))
                .findFirst()
                .orElseThrow(() -> new IllegalArgumentException(ErrorMessages.INCORRECT_MENU_ORDER.getErrorMsg()));

        return menuName;
    }

    public int getMenuPrice(String name) {
        Menu menu = contains(name);

        return menu.getPrice();
    }
}
