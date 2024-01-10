package christmas.domain.event;

import com.sun.source.tree.WhileLoopTree;
import java.util.Arrays;
import java.util.List;

public enum Badge {
    SANTA_BADGE("산타", 20000),
    TREE_BADGE("트리", 10000),
    STAR_BADGE("별", 5000),
    NONE("없음", 0);

    private int satisfiedBenefitCondition;
    private String badgeName;
    private Badge badgeToGive;

    Badge(String name, int condition) {
        badgeName = name;
        satisfiedBenefitCondition = condition;
    }

    public int getSatisfiedBenefitCondition() {
        return satisfiedBenefitCondition;
    }

    public String getBadgeName() {
        return badgeName;
    }

    public void checkBadgeEventPossible(int totalBenefit) {
        List<Badge> wholeBadges = Arrays.stream(Badge.values()).toList();

        for (Badge badge : wholeBadges) {
            if (totalBenefit >= badge.getSatisfiedBenefitCondition()) {
                setBadgeToGive(badge);
                return;
            }
        }

        setBadgeToGive(NONE);
    }

    public void setBadgeToGive(Badge badge) {
        badgeToGive = badge;
    }

    public Badge getBadgeToGive() {
        return badgeToGive;
    }
}
