package parking;

import util.List;

/**
 * Deck class that holds the information of a parking deck.
 * A deck has a number, a location, operating hours, a maximum capacity,
 * the parking activities of the vehicles currently parked, and the total
 * parking fees collected.
 * @author Sriya Ghankot, Alexandra Xie
 */
public class Deck implements Comparable<Deck> {
    /** The maximum number of vehicles a deck can hold. */
    public static final int MAXCAPACITY = 6;

    /** The deck number. */
    private int number;
    /** The location of the deck. */
    private Location location;
    /** The operating hours of the deck. */
    private Hour hour;
    /** The parking activities of the vehicles parked in the deck. */
    private List<Parking> parkings;
    /** The maximum number of vehicles the deck can hold. */
    private int capacity;
    /** The total parking fees collected by the deck. */
    private int feesCollected;
    /** Whether the deck is open for operation. */
    private boolean open;

    /**
     * Create a deck with only the deck number, used to look up a deck.
     * @param number the deck number.
     */
    public Deck(int number) {
        this.number = number;
    }

    /**
     * Create an open deck with the given information.
     * @param number the deck number.
     * @param location the location of the deck.
     * @param hour the operating hours of the deck.
     * @param capacity the maximum number of vehicles the deck can hold.
     */
    public Deck(int number, Location location, Hour hour, int capacity) {
        this.number = number;
        this.location = location;
        this.hour = hour;
        this.parkings = new List<>();
        this.capacity = capacity;
        this.feesCollected = 0;
        this.open = true;
    }

    /**
     * Find the parking activity of a vehicle parked in the deck.
     * @param vehicle the vehicle to find.
     * @return the parking activity; null if the vehicle is not parked in
     *         the deck.
     */
    private Parking find(Vehicle vehicle) {
        for (Parking parking : parkings) {
            if (parking.getVehicle().equals(vehicle)) {
                return parking;
            }
        }
        return null;
    }

    /**
     * Add a parking activity when a vehicle enters the deck.
     * @param parking the parking activity to add.
     */
    public void enter(Parking parking) {
        parkings.add(parking);
    }

    /**
     * Remove a parking activity when a vehicle exits the deck.
     * @param parking the parking activity to remove.
     */
    public void exit(Parking parking) {
        parkings.remove(parking);
    }

    /**
     * Get the parking activity of a vehicle parked in the deck.
     * @param vehicle the vehicle to look for.
     * @return the parking activity; null if the vehicle is not parked in
     *         the deck.
     */
    public Parking getParking(Vehicle vehicle) {
        return find(vehicle);
    }

    /**
     * Get the parking activities of the vehicles parked in the deck.
     * @return the list of parking activities.
     */
    public List<Parking> getParkings() {
        return parkings;
    }

    /**
     * Get the deck number.
     * @return the deck number.
     */
    public int getNumber() {
        return number;
    }

    /**
     * Get the location of the deck.
     * @return the location.
     */
    public Location getLocation() {
        return location;
    }

    /**
     * Get the operating hours of the deck.
     * @return the operating hours.
     */
    public Hour getHour() {
        return hour;
    }

    /**
     * Get the total parking fees collected by the deck.
     * @return the fees collected.
     */
    public int getFeesCollected() {
        return feesCollected;
    }

    /**
     * Add a parking fee to the total fees collected by the deck.
     * @param fee the parking fee collected.
     */
    public void collectFee(int fee) {
        feesCollected += fee;
    }

    /**
     * Check if the deck is open for operation.
     * @return true if the deck is open; false if it is closed.
     */
    public boolean isOpen() {
        return open;
    }

    /**
     * Open or close the deck for operation.
     * @param open true to open the deck; false to close it.
     */
    public void setOpen(boolean open) {
        this.open = open;
    }

    /**
     * Check if the deck has reached its maximum capacity.
     * @return true if the deck is full; false otherwise.
     */
    public boolean isFull() {
        return parkings.size() == capacity;
    }

    /**
     * Check if a timestamp is within the operating hours of the deck.
     * @param timestamp the timestamp to check.
     * @return true if the time is within the operating hours;
     *         false otherwise.
     */
    public boolean isWithinHours(Timestamp timestamp) {
        return hour.isWithin(timestamp);
    }

    /**
     * Check if this deck is equal to another deck.
     * @param obj the object to compare with.
     * @return true if both decks have the same deck number; false otherwise.
     */
    @Override
    public boolean equals(Object obj) {
        if (obj instanceof Deck) {
            Deck deck = (Deck) obj;
            return number == deck.number;
        }
        return false;
    }

    /**
     * Compare this deck with another deck by county and then by deck
     * number.
     * @param deck the deck to compare with.
     * @return a negative number if this deck comes first, a positive number
     *         if it comes last, and 0 if both decks are the same.
     */
    @Override
    public int compareTo(Deck deck) {
        int countyOrder = location.getCounty()
                .compareTo(deck.location.getCounty());
        if (countyOrder != 0) {
            return countyOrder;
        }
        return Integer.compare(number, deck.number);
    }

    /**
     * Return the textual representation of the deck.
     * @return the deck in the format Deck#301@PRINCETON[open 6:30 ~ 18:30]
     *         [capacity 5] [0 vehicles] [Mercer]
     */
    @Override
    public String toString() {
        String status = open ? "open" : "closed";
        return "Deck#" + number + "@" + location + "[" + status + " "
                + hour + "] [capacity " + capacity + "] ["
                + parkings.size() + " vehicles] [" + location.getCounty()
                + "]";
    }
}
