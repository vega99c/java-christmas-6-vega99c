package christmas.domain.event;

import java.util.ArrayList;
import java.util.List;

public class Event {
    private List<EventDetail> eventDetailList;

    Event() {
        eventDetailList = new ArrayList<>();
    }

    public void addEvent(String eventName, int eventBenefit) {
        
    }

    public List<EventDetail> getEventDetailList() {
        return eventDetailList;
    }
}
