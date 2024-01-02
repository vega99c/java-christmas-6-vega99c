package christmas.domain.menu;

import java.util.ArrayList;
import java.util.List;

// 메뉴 주문 시 Order에서 주문 들어온 메뉴에 대해 정보 제공할 클래스
public class MenuBoard {
    private final List<Menu> menuBoard;

    public MenuBoard() {
        this.menuBoard = new ArrayList<>();
        initializeMenuBoard();
    }

    public void initializeMenuBoard() {
        addDrink();
        addApptizer();
        addDessert();
        addMainCourse();
    }

    public void addDrink() {

    }

    public void addApptizer() {

    }

    public void addDessert() {

    }

    public void addMainCourse() {

    }

    // 생성 된 메뉴판을 기준으로 메뉴가 있는지 찾고 있으면 해당 메뉴 객체반환
    public Menu findMenu(String menuName) {
        for (Menu menu : menuBoard) {
            if (menu.getMenuName().equals(menuName)) {
                return menu;
            }
        }

        throw new IllegalArgumentException();
    }
}
