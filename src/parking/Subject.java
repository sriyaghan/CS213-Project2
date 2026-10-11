package parking;

/**
 * Interface for the subject in the Observer design pattern.
 * A subject keeps the registered vehicles and sends notifications to them.
 * @author Sriya Ghankot, Alexandra Xie
 */
public interface Subject {

    /**
     * Subscribe a vehicle to the notifications.
     * @param vehicle the vehicle to subscribe.
     */
    void register(Vehicle vehicle);

    /**
     * Unsubscribe a vehicle from the notifications.
     * @param vehicle the vehicle to unsubscribe.
     */
    void unregister(Vehicle vehicle);

    /**
     * Send a notification message to all the subscribed vehicles.
     * @param message the notification message.
     */
    void notify(String message);
}
