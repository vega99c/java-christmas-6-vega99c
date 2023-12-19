package christmas;

import static org.assertj.core.api.Assertions.assertThat;

import org.junit.jupiter.api.Test;

public class VisitDateTest {

    @Test
    void 요일반환확인() {
        EventPlan plan = new EventPlan(2023, 12, 1);

        assertThat(plan.getDayString()).isEqualTo("FRIDAY");
    }

    @Test
    void 월마지막날확인() {
        EventPlan plan = new EventPlan(2023, 12, 1);

        assertThat(plan.getLastDay()).isEqualTo(31);
    }

    @Test
    void 날짜_유효범위_검사() {
        EventPlan plan = new EventPlan(2023, 12, 3);

        assertThat(plan.validateIsCorrectRange(plan.getDayInt())).isEqualTo(true);
    }
}
