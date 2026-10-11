package parking;

import util.List;

/**
 * Notification class that is the subject of the system notifications.
 * Registered vehicles are subscribed, and each notification is sent to
 * every subscribed vehicle.
 * @author Sriya Ghankot, Alexandra Xie
 */
public class Notification implements Subject {
    /** The vehicles subscribed to the notifications. */
    private List<Vehicle> vehicles;

    /**
     * Create the notification subject with no subscribed vehicles.
     */
    public Notification() {
        vehicles = new List<>();
    }

    /**
     * Subscribe a vehicle to the notifications.
     * @param vehicle the vehicle to subscribe.
     */
    @Override
    public void register(Vehicle vehicle) {
        vehicles.add(vehicle);
    }

    /**
     * Unsubscribe a vehicle from the notifications.
     * @param vehicle the vehicle to unsubscribe.
     */
    @Override
    public void unregister(Vehicle vehicle) {
        vehicles.remove(vehicle);
    }

    /**
     * Send a notification message to all the subscribed vehicles.
     * @param message the notification message.
     */
    @Override
    public void notify(String message) {
        for (Vehicle vehicle : vehicles) {
            vehicle.update(message);
        }
    }
}
