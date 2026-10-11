package parking;

import util.List;
import util.Sort;

/**
 * DeckList class that holds the parking decks.
 * It extends the generic List class with the operations specific to a
 * list of decks; closed decks remain in the list.
 * @author Sriya Ghankot, Alexandra Xie
 */
public class DeckList extends List<Deck> {

    /**
     * Create an empty deck list.
     */
    public DeckList() {
        super();
    }

    /**
     * Get the number of decks that are open.
     * @return the number of open decks.
     */
    private int openCount() {
        int count = 0;
        for (Deck deck : this) {
            if (deck.isOpen()) {
                count++;
            }
        }
        return count;
    }

    /**
     * Get the deck where a vehicle is recorded as entered without an exit.
     * @param vehicle the vehicle to look for.
     * @return the deck the vehicle is parked in; null if the vehicle is
     *         not parked in any deck.
     */
    public Deck getDeckWith(Vehicle vehicle) {
        for (Deck deck : this) {
            if (deck.getParking(vehicle) != null) {
                return deck;
            }
        }
        return null;
    }

    /**
     * Display the list of open decks, ordered by county and then by deck
     * number.
     */
    public void printByLocation() {
        if (openCount() == 0) {
            System.out.println("Deck list is empty - no deck is open.");
            return;
        }
        Sort.sort(this);
        System.out.println("** List of decks, ordered by county/deck "
                + "number **");
        for (Deck deck : this) {
            if (deck.isOpen()) {
                System.out.println(deck);
            }
        }
        System.out.println("** end of list **");
    }

    /**
     * Display the list of vehicles parked in a deck, ordered by plate.
     * @param deck the deck whose vehicles are displayed.
     */
    public void printVehicles(Deck deck) {
        List<Parking> parkings = deck.getParkings();
        if (parkings.isEmpty()) {
            System.out.println("Deck#" + deck.getNumber()
                    + " - no vehicle is parked.");
            return;
        }
        Sort.sort(parkings);
        System.out.println("** List of vehicles in Deck# " + deck.getNumber()
                + ", ordered by plate **");
        for (Parking parking : parkings) {
            System.out.println(parking);
        }
        System.out.println("** end of list **");
    }

    /**
     * Display the total parking fees collected by each deck, ordered by
     * deck number. Decks that have not collected any fees are skipped.
     */
    public void printFees() {
        if (isEmpty()) {
            System.out.println("Deck list is empty - no deck is open.");
            return;
        }
        List<Integer> numbers = new List<>();
        for (Deck deck : this) {
            numbers.add(deck.getNumber());
        }
        Sort.sort(numbers);
        System.out.println("** Parking fees, ordered by deck number **");
        for (int number : numbers) {
            Deck deck = get(indexOf(new Deck(number)));
            if (deck.getFeesCollected() > 0) {
                System.out.println("Deck#" + number);
                System.out.println("    ** Fees collected: $ "
                        + deck.getFeesCollected());
            }
        }
        System.out.println("** end of list **");
    }
}
