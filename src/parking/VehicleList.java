package parking;

import util.List;
import util.Sort;

/**
 * VehicleList class that holds the registered vehicles.
 * It extends the generic List class with the operations specific to a
 * list of vehicles.
 * @author Sriya Ghankot, Alexandra Xie
 */
public class VehicleList extends List<Vehicle> {
    /** The indentation for the details listed under a vehicle. */
    private static final String INDENT = "    ";

    /**
     * Create an empty vehicle list.
     */
    public VehicleList() {
        super();
    }

    /**
     * Display the parking history of a vehicle, one line per activity.
     * @param vehicle the registered vehicle.
     */
    private void printHistoryLines(Vehicle vehicle) {
        for (History history : vehicle.getHistory()) {
            System.out.println(INDENT + " " + history);
        }
    }

    /**
     * Display the notifications received by a vehicle, if any.
     * @param vehicle the registered vehicle.
     */
    private void printNotificationLines(Vehicle vehicle) {
        if (!vehicle.hasNotifications()) {
            return;
        }
        System.out.println(INDENT + "** notifications ** ");
        for (String message : vehicle.getNotifications()) {
            System.out.println(INDENT + message);
        }
    }

    /**
     * Display the list of registered vehicles, ordered by license plate.
     */
    public void printByPlate() {
        if (isEmpty()) {
            System.out.println("Vehicle list is empty - no vehicle is "
                    + "registered.");
            return;
        }
        Sort.sort(this);
        System.out.println("** List of registered vehicles, ordered by "
                + "license plate **");
        for (Vehicle vehicle : this) {
            System.out.println(vehicle);
        }
        System.out.println("** end of list **");
    }

    /**
     * Display the parking history of all vehicles, ordered by license
     * plate and then by the entry timestamps in ascending order.
     */
    public void printHistory() {
        Sort.sort(this);
        System.out.println("** Parking history for all vehicles, ordered "
                + "by plate/timestamp **");
        for (Vehicle vehicle : this) {
            if (vehicle.hasHistory()) {
                System.out.println(vehicle);
                printHistoryLines(vehicle);
            }
        }
        System.out.println("** end of parking history **");
    }

    /**
     * Display the parking history of a registered vehicle.
     * @param vehicle the vehicle holding the license plate to display.
     */
    public void printHistory(Vehicle vehicle) {
        System.out.println("** Parking history for " + vehicle + " **");
        printHistoryLines(get(indexOf(vehicle)));
        System.out.println("** end of list **");
    }

    /**
     * Display the notifications received by all vehicles, ordered by
     * license plate.
     */
    public void printNotifications() {
        if (isEmpty()) {
            System.out.println("Vehicle list is empty - no vehicle is "
                    + "registered.");
            return;
        }
        Sort.sort(this);
        System.out.println("** Notifications, ordered by plate **");
        for (Vehicle vehicle : this) {
            System.out.println(vehicle);
            printNotificationLines(vehicle);
        }
        System.out.println("** end of list **");
    }

    /**
     * Display the notifications received by a registered vehicle.
     * @param vehicle the vehicle holding the license plate to display.
     */
    public void printNotifications(Vehicle vehicle) {
        System.out.println("** Notifications for " + vehicle + " **");
        printNotificationLines(get(indexOf(vehicle)));
        System.out.println("** end of list **");
    }
}
