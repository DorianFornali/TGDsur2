package observerPattern;

public class GameEvent {
    private String eventType;
    private Object eventData;

    public GameEvent(String eventType, Object eventData) {
        this.eventType = eventType;
        this.eventData = eventData;
    }

    public String getEventType() {
        return eventType;
    }

    public Object getEventData() {
        return eventData;
    }
}
