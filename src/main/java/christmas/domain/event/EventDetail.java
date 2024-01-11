package christmas.domain.event;

public class EventDetail {
    private String eventName;
    private int eventBenefit;

    EventDetail(String eventName, int eventBenefit) {
        this.eventName = eventName;
        this.eventBenefit = eventBenefit;
    }

    public String getEventName() {
        return eventName;
    }

    public int getBenefitMoney() {
        return eventBenefit;
    }
}
