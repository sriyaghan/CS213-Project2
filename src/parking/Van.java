package parking;

/**
 * Van class that represents a van registered for parking.
 * A van is charged $3 per hour or $25 per day.
 * @author Sriya Ghankot, Alexandra Xie
 */
public class Van extends Vehicle {
    /** The hourly parking rate of a van. */
    private static final int HOURLY_RATE = 3;
    /** The daily parking rate of a van. */
    private static final int DAILY_RATE = 25;

    /**
     * Create a van with the given license plate.
     * @param plate the license plate of the van.
     */
    public Van(String plate) {
        super(plate);
    }

    /**
     * Calculate the parking fee of the van based on the duration.
     * @param deck the deck where the van parked.
     * @param enter the timestamp when the van entered the deck.
     * @param exit the timestamp when the van exited the deck.
     * @return the parking fee.
     */
    @Override
    public int parkingFee(Deck deck, Timestamp enter, Timestamp exit) {
        return parkingFee(deck, enter, exit, HOURLY_RATE, DAILY_RATE);
    }

    /**
     * Return the textual representation of the van.
     * @return the license plate followed by the vehicle type.
     */
    @Override
    public String toString() {
        return super.toString() + " [Van]";
    }
}
