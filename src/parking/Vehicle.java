package parking;

import util.List;

/**
 * Abstract class that represents a vehicle registered for parking.
 * A vehicle is uniquely identified by its license plate, keeps its
 * parking history, and receives the system notifications as an observer.
 * The parking fee depends on the vehicle type defined by the subclasses.
 * @author Sriya Ghankot, Alexandra Xie
 */
public abstract class Vehicle implements Observer, Comparable<Vehicle> {
    /** The license plate format; X is a letter and d is a digit. */
    private static final String PLATE_FORMAT = "Xdd-XXX";
    /** The character in the plate format that stands for a letter. */
    private static final char LETTER = 'X';
    /** The character in the plate format that stands for a digit. */
    private static final char DIGIT = 'd';
    /** The number of minutes in an hour. */
    private static final int MINUTES_PER_HOUR = 60;
    /** The most hours in a day that are charged by the hourly rate. */
    private static final int MAX_HOURLY_HOURS = 6;

    /** The license plate, exactly 7 characters. */
    private String plate;
    /** The parking history of the vehicle. */
    private List<History> history;
    /** The notifications received by the vehicle. */
    private List<String> notifications;

    /**
     * Create a vehicle with the given license plate, no parking history,
     * and no notifications.
     * @param plate the license plate of the vehicle.
     */
    protected Vehicle(String plate) {
        this.plate = plate;
        this.history = new List<>();
        this.notifications = new List<>();
    }

    /**
     * Check if a character matches the expected character in the format.
     * Letters are not case-sensitive.
     * @param expected the character in the plate format.
     * @param actual the character in the license plate.
     * @return true if the character matches the format; false otherwise.
     */
    private static boolean isMatch(char expected, char actual) {
        char upper = Character.toUpperCase(actual);
        if (expected == LETTER) {
            return upper >= 'A' && upper <= 'Z';
        }
        if (expected == DIGIT) {
            return actual >= '0' && actual <= '9';
        }
        return actual == expected;
    }

    /**
     * Calculate the fee for the parking time within a single day.
     * Each started hour is charged by the hourly rate; the daily rate is
     * charged if the parking time exceeds 6 hours.
     * @param minutes the number of minutes parked in the day.
     * @param hourlyRate the hourly rate of the vehicle type.
     * @param dailyRate the daily rate of the vehicle type.
     * @return the fee for the day.
     */
    private static int dayFee(long minutes, int hourlyRate, int dailyRate) {
        long hours = (minutes + MINUTES_PER_HOUR - 1) / MINUTES_PER_HOUR;
        if (hours > MAX_HOURLY_HOURS) {
            return dailyRate;
        }
        return (int) hours * hourlyRate;
    }

    /**
     * Calculate the parking fee with the given hourly and daily rates.
     * Each day before the exit day is charged by the daily rate; the time
     * on the exit day is counted from the opening time of the deck. A
     * parking activity within a single day is counted from the entry time.
     * @param deck the deck where the vehicle parked.
     * @param enter the timestamp when the vehicle entered the deck.
     * @param exit the timestamp when the vehicle exited the deck.
     * @param hourlyRate the hourly rate of the vehicle type.
     * @param dailyRate the daily rate of the vehicle type.
     * @return the parking fee.
     */
    protected int parkingFee(Deck deck, Timestamp enter, Timestamp exit,
                             int hourlyRate, int dailyRate) {
        long days = exit.getDate().dayNumber()
                - enter.getDate().dayNumber();
        long minutes;
        if (days == 0) {
            minutes = exit.minutesSince(enter);
        } else {
            minutes = exit.minuteOfDay() - deck.getHour().openingTime();
        }
        return (int) days * dailyRate
                + dayFee(minutes, hourlyRate, dailyRate);
    }

    /**
     * Calculate the parking fee based on the duration and vehicle type.
     * @param deck the deck where the vehicle parked.
     * @param enter the timestamp when the vehicle entered the deck.
     * @param exit the timestamp when the vehicle exited the deck.
     * @return the parking fee.
     */
    public abstract int parkingFee(Deck deck, Timestamp enter,
                                   Timestamp exit);

    /**
     * Check if the license plate is in the valid format Xdd-XXX, where X
     * is an alphabetic letter and d is a digit.
     * @return true if the license plate is valid; false otherwise.
     */
    public boolean isValid() {
        if (plate.length() != PLATE_FORMAT.length()) {
            return false;
        }
        for (int i = 0; i < plate.length(); i++) {
            if (!isMatch(PLATE_FORMAT.charAt(i), plate.charAt(i))) {
                return false;
            }
        }
        return true;
    }

    /**
     * Get the license plate of the vehicle.
     * @return the license plate.
     */
    public String getPlate() {
        return plate;
    }

    /**
     * Get the parking history of the vehicle.
     * @return the list of completed parking activities.
     */
    public List<History> getHistory() {
        return history;
    }

    /**
     * Get the notifications received by the vehicle.
     * @return the list of notification messages.
     */
    public List<String> getNotifications() {
        return notifications;
    }

    /**
     * Check if the vehicle has any parking history.
     * @return true if the vehicle has a parking history; false otherwise.
     */
    public boolean hasHistory() {
        return !history.isEmpty();
    }

    /**
     * Check if the vehicle has received any notifications.
     * @return true if the vehicle has notifications; false otherwise.
     */
    public boolean hasNotifications() {
        return !notifications.isEmpty();
    }

    /**
     * Add a completed parking activity to the parking history.
     * @param parking the history of the completed parking activity.
     */
    public void addHistory(History parking) {
        history.add(parking);
    }

    /**
     * Receive a notification message and keep it in the notifications.
     * @param message the notification message.
     */
    @Override
    public void update(String message) {
        notifications.add(message);
    }

    /**
     * Check if this vehicle is equal to another vehicle.
     * @param obj the object to compare with.
     * @return true if both vehicles have the same license plate, ignoring
     *         case; false otherwise.
     */
    @Override
    public boolean equals(Object obj) {
        if (obj instanceof Vehicle) {
            Vehicle vehicle = (Vehicle) obj;
            return plate.equalsIgnoreCase(vehicle.plate);
        }
        return false;
    }

    /**
     * Compare this vehicle with another vehicle by license plate.
     * @param vehicle the vehicle to compare with.
     * @return a negative number if this plate comes first, a positive
     *         number if it comes last, and 0 if both plates are the same.
     */
    @Override
    public int compareTo(Vehicle vehicle) {
        return plate.compareToIgnoreCase(vehicle.plate);
    }

    /**
     * Return the textual representation of the vehicle.
     * @return the license plate of the vehicle.
     */
    @Override
    public String toString() {
        return plate;
    }
}
