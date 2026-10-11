package parking;

/**
 * History class that defines a completed parking activity in the parking
 * history of a vehicle, including the number of the deck where the
 * vehicle parked.
 * @author Sriya Ghankot, Alexandra Xie
 */
public class History {
    /** The number of the deck where the vehicle parked. */
    private int deckNumber;
    /** The completed parking activity. */
    private Parking parking;

    /**
     * Create the history of a completed parking activity in a deck.
     * @param deckNumber the number of the deck where the vehicle parked.
     * @param parking the completed parking activity.
     */
    public History(int deckNumber, Parking parking) {
        this.deckNumber = deckNumber;
        this.parking = parking;
    }

    /**
     * Return the textual representation of the parking history.
     * @return the history in the format [entered Deck# 110:
     *         2026-03-09 10:48][exited: 2026-03-09 11:50]
     */
    @Override
    public String toString() {
        return "[entered Deck# " + deckNumber + ": "
                + parking.getEnter() + "][exited: " + parking.getExit()
                + "]";
    }
}
