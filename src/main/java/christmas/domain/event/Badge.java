package christmas.domain.event;

import java.util.Arrays;
import java.util.List;

public class Badge {
    public enum BadgeType {
        SANTA_BADGE("산타", 20000),
        TREE_BADGE("트리", 10000),
        STAR_BADGE("별", 5000),
        NONE("없음", 0);
        private int satisfiedBenefitCondition;
        private String badgeName;

        BadgeType(String name, int condition) {
            badgeName = name;
            satisfiedBenefitCondition = condition;
        }

        public String getBadgeName() {
            return badgeName;
        }

        public int getSatisfiedBenefitCondition() {
            return satisfiedBenefitCondition;
        }
    }

    private BadgeType badgeToGive;


    public void checkBadgeEventPossible(int totalBenefit) {
        List<BadgeType> wholeBadges = Arrays.stream(BadgeType.values()).toList();

        for (BadgeType badge : wholeBadges) {
            if (totalBenefit >= badge.getSatisfiedBenefitCondition()) {
                setBadgeToGive(badge);
                return;
            }
        }

        setBadgeToGive(BadgeType.NONE);
    }

    public void setBadgeToGive(BadgeType badge) {
        badgeToGive = badge;
    }

    public BadgeType getBadgeToGive() {
        return badgeToGive;
    }
}
