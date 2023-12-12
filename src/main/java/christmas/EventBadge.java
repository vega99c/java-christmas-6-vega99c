package christmas;

public enum EventBadge {
    SANTA_BADGE("산타", 20000),
    TREE_BADGE("트리", 10000),
    STAR_BADGE("별", 5000);

    private int benefitsCondition;
    private String badgeName;

    EventBadge(String name, int condition) {
        badgeName = name;
        benefitsCondition = condition;
    }

    public int getBenefitsCondition() {
        return benefitsCondition;
    }

    public String getBadgeName() {
        return badgeName;
    }
}
