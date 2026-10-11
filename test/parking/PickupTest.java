package parking;

import static org.junit.Assert.assertEquals;

import org.junit.Test;

/**
 * JUnit test class for the parkingFee() method of the Pickup class.
 * It tests one parking activity charged by the hourly rate and one
 * charged by the daily rate.
 * @author Sriya Ghankot, Alexandra Xie
 */
public class PickupTest {
    /** The deck number of the deck used for testing. */
    private static final int DECK_NUMBER = 100;
    /** The expected fee for the parking activity charged by the hour. */
    private static final int HOURLY_FEE = 8;
    /** The expected fee for the parking activity charged by the day. */
    private static final int DAILY_FEE = 30;

    /**
     * Default constructor that creates the test class.
     */
    public PickupTest() {
    }

    /**
     * Test case #1: parking from 10:48 to 11:50 is 1 hour 2 minutes,
     * charged as 2 hours at $4 per hour, which is $8.
     */
    @Test
    public void testHourlyRate() {
        Deck deck = new Deck(DECK_NUMBER, Location.EDISON, Hour.HR5,
                Deck.MAXCAPACITY);
        Vehicle pickup = new Pickup("P53-BJD");
        Timestamp enter = new Timestamp("2026-03-09", "10:48");
        Timestamp exit = new Timestamp("2026-03-09", "11:50");
        assertEquals(HOURLY_FEE, pickup.parkingFee(deck, enter, exit));
    }

    /**
     * Test case #2: parking from 10:00 to 17:25 is 7 hours 25 minutes,
     * which exceeds 6 hours, so the daily rate of $30 is charged.
     */
    @Test
    public void testDailyRate() {
        Deck deck = new Deck(DECK_NUMBER, Location.EDISON, Hour.HR7,
                Deck.MAXCAPACITY);
        Vehicle pickup = new Pickup("P53-BJD");
        Timestamp enter = new Timestamp("2026-03-09", "10:00");
        Timestamp exit = new Timestamp("2026-03-09", "17:25");
        assertEquals(DAILY_FEE, pickup.parkingFee(deck, enter, exit));
    }
}
