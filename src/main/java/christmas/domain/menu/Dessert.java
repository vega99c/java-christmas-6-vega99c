package christmas.domain.menu;

public enum Dessert implements Menu {
    CHOCO_CAKE("초코케이크", 15000),
    ICECREAM("아이스크림", 5000);

    private final String menuName;
    private final int menuPrice;


    Dessert(String menuName, int menuPrice) {
        this.menuName = menuName;
        this.menuPrice = menuPrice;
    }

    public String getMenuName() {
        return menuName;
    }

    public int getMenuPrice() {
        return menuPrice;
    }

    public String getMenuType() {
        return "<디저트>";
    }
}
