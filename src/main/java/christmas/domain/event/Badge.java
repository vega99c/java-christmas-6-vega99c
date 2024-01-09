package christmas.domain.event;

public enum Badge {
    SANTA_BADGE("산타", 20000),
    TREE_BADGE("트리", 10000),
    STAR_BADGE("별", 5000);

    private int benefitCondition;
    private String badgeName;

    Badge(String name, int condition) {
        badgeName = name;
        benefitCondition = condition;
    }

    public int getBenefitCondition() {
        return benefitCondition;
    }

    public String getBadgeName() {
        return badgeName;
    }

    public void checkBadgeEventPossible() {

    }
}
