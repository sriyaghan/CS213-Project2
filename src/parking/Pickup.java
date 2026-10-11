package parking;

/**
 * Pickup class that represents a pickup truck registered for parking.
 * A pickup truck is charged $4 per hour or $30 per day.
 * @author Sriya Ghankot, Alexandra Xie
 */
public class Pickup extends Vehicle {
    /** The hourly parking rate of a pickup truck. */
    private static final int HOURLY_RATE = 4;
    /** The daily parking rate of a pickup truck. */
    private static final int DAILY_RATE = 30;

    /**
     * Create a pickup truck with the given license plate.
     * @param plate the license plate of the pickup truck.
     */
    public Pickup(String plate) {
        super(plate);
    }

    /**
     * Calculate the parking fee of the pickup truck based on the duration.
     * @param deck the deck where the pickup truck parked.
     * @param enter the timestamp when the pickup truck entered the deck.
     * @param exit the timestamp when the pickup truck exited the deck.
     * @return the parking fee.
     */
    @Override
    public int parkingFee(Deck deck, Timestamp enter, Timestamp exit) {
        return parkingFee(deck, enter, exit, HOURLY_RATE, DAILY_RATE);
    }

    /**
     * Return the textual representation of the pickup truck.
     * @return the license plate followed by the vehicle type.
     */
    @Override
    public String toString() {
        return super.toString() + " [Pickup]";
    }
}
