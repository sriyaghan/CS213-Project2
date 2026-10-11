package parking;

import static org.junit.Assert.assertEquals;

import org.junit.Test;

/**
 * JUnit test class for the parkingFee() method of the Sedan class.
 * It tests one parking activity charged by the hourly rate and one
 * charged by the daily rate.
 * @author Sriya Ghankot, Alexandra Xie
 */
public class SedanTest {
    /** The deck number of the deck used for testing. */
    private static final int DECK_NUMBER = 100;
    /** The expected fee for the parking activity charged by the hour. */
    private static final int HOURLY_FEE = 4;
    /** The expected fee for the parking activity charged by the day. */
    private static final int DAILY_FEE = 18;

    /**
     * Default constructor that creates the test class.
     */
    public SedanTest() {
    }

    /**
     * Test case #1: parking from 19:30 to 21:00 is 1.5 hours,
     * charged as 2 hours at $2 per hour, which is $4.
     */
    @Test
    public void testHourlyRate() {
        Deck deck = new Deck(DECK_NUMBER, Location.EDISON, Hour.HR5,
                Deck.MAXCAPACITY);
        Vehicle sedan = new Sedan("S53-BJD");
        Timestamp enter = new Timestamp("2026-03-09", "19:30");
        Timestamp exit = new Timestamp("2026-03-09", "21:00");
        assertEquals(HOURLY_FEE, sedan.parkingFee(deck, enter, exit));
    }

    /**
     * Test case #2: parking from 9:12 to 17:55 is 8 hours 43 minutes,
     * which exceeds 6 hours, so the daily rate of $18 is charged.
     */
    @Test
    public void testDailyRate() {
        Deck deck = new Deck(DECK_NUMBER, Location.EDISON, Hour.HR7,
                Deck.MAXCAPACITY);
        Vehicle sedan = new Sedan("S53-BJD");
        Timestamp enter = new Timestamp("2026-03-09", "09:12");
        Timestamp exit = new Timestamp("2026-03-09", "17:55");
        assertEquals(DAILY_FEE, sedan.parkingFee(deck, enter, exit));
    }
}
