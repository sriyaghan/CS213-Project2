package parking;

/**
 * Sedan class that represents a sedan registered for parking.
 * A sedan is charged $2 per hour or $18 per day.
 * @author Sriya Ghankot, Alexandra Xie
 */
public class Sedan extends Vehicle {
    /** The hourly parking rate of a sedan. */
    private static final int HOURLY_RATE = 2;
    /** The daily parking rate of a sedan. */
    private static final int DAILY_RATE = 18;

    /**
     * Create a sedan with the given license plate.
     * @param plate the license plate of the sedan.
     */
    public Sedan(String plate) {
        super(plate);
    }

    /**
     * Calculate the parking fee of the sedan based on the duration.
     * @param deck the deck where the sedan parked.
     * @param enter the timestamp when the sedan entered the deck.
     * @param exit the timestamp when the sedan exited the deck.
     * @return the parking fee.
     */
    @Override
    public int parkingFee(Deck deck, Timestamp enter, Timestamp exit) {
        return parkingFee(deck, enter, exit, HOURLY_RATE, DAILY_RATE);
    }

    /**
     * Return the textual representation of the sedan.
     * @return the license plate followed by the vehicle type.
     */
    @Override
    public String toString() {
        return super.toString() + " [Sedan]";
    }
}
