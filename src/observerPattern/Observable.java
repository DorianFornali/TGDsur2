package observerPattern;

/**
 * Observable interface for the Observer pattern.
 * The observable will notify the observer when an event that requires the observer to do something occurs.
 */
public interface Observable {

    /**
     * Adds an observer to the observable. An observable can have multiple observers.
     * @param observer
     */
    void addObserver(Observer observer);

    /**
     * Removes an observer from the observable.
     * @param observer
     */
    void removeObserver(Observer observer);

    /**
     * Notifies all the observers that an event has occurred.
     * @param event
     */
    void notifyObservers(GameEvent event);
}
