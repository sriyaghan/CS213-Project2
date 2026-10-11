package parking;

import static org.junit.Assert.assertEquals;

import org.junit.Test;

/**
 * JUnit test class for the parkingFee() method of the Van class.
 * It tests one parking activity charged by the hourly rate and one
 * charged by the daily rate.
 * @author Sriya Ghankot, Alexandra Xie
 */
public class VanTest {
    /** The deck number of the deck used for testing. */
    private static final int DECK_NUMBER = 100;
    /** The expected fee for the parking activity charged by the hour. */
    private static final int HOURLY_FEE = 15;
    /** The expected fee for the parking activity charged by the day. */
    private static final int DAILY_FEE = 25;

    /**
     * Default constructor that creates the test class.
     */
    public VanTest() {
    }

    /**
     * Test case #1: parking from 13:30 to 18:20 is 4 hours 50 minutes,
     * charged as 5 hours at $3 per hour, which is $15.
     */
    @Test
    public void testHourlyRate() {
        Deck deck = new Deck(DECK_NUMBER, Location.EDISON, Hour.HR6,
                Deck.MAXCAPACITY);
        Vehicle van = new Van("V53-BJD");
        Timestamp enter = new Timestamp("2026-03-09", "13:30");
        Timestamp exit = new Timestamp("2026-03-09", "18:20");
        assertEquals(HOURLY_FEE, van.parkingFee(deck, enter, exit));
    }

    /**
     * Test case #2: parking from 8:00 to 17:00 is 9 hours, which
     * exceeds 6 hours, so the daily rate of $25 is charged.
     */
    @Test
    public void testDailyRate() {
        Deck deck = new Deck(DECK_NUMBER, Location.EDISON, Hour.HR7,
                Deck.MAXCAPACITY);
        Vehicle van = new Van("V53-BJD");
        Timestamp enter = new Timestamp("2026-03-09", "08:00");
        Timestamp exit = new Timestamp("2026-03-09", "17:00");
        assertEquals(DAILY_FEE, van.parkingFee(deck, enter, exit));
    }
}
