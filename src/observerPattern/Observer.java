package observerPattern;

public interface Observer {

    /**
     * Receives an event notification from an observable.
     * Will react to the event accordingly.
     * @param event
     */
    void receiveEventNotification(GameEvent event);
}
