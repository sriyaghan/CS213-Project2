package parking;

import static org.junit.Assert.assertEquals;

import org.junit.Test;

/**
 * JUnit test class for the compareTo() method of the Timestamp class.
 * It implements the 7 test cases from Project 1: three return -1, three
 * return 1, and one returns 0.
 * @author Sriya Ghankot, Alexandra Xie
 */
public class TimestampTest {
    /** The result of compareTo() when the first timestamp is earlier. */
    private static final int BEFORE = -1;
    /** The result of compareTo() when the first timestamp is later. */
    private static final int AFTER = 1;
    /** The result of compareTo() when both timestamps are the same. */
    private static final int SAME = 0;

    /**
     * Default constructor that creates the test class.
     */
    public TimestampTest() {
    }

    /**
     * Test case #1: an earlier date with the same time returns -1.
     */
    @Test
    public void testEarlierDate() {
        Timestamp first = new Timestamp("2026-03-09", "10:48");
        Timestamp second = new Timestamp("2026-03-10", "10:48");
        assertEquals(BEFORE, first.compareTo(second));
    }

    /**
     * Test case #2: the same date with an earlier hour returns -1.
     */
    @Test
    public void testEarlierHour() {
        Timestamp first = new Timestamp("2026-03-09", "09:59");
        Timestamp second = new Timestamp("2026-03-09", "10:00");
        assertEquals(BEFORE, first.compareTo(second));
    }

    /**
     * Test case #3: the same date and hour with an earlier minute
     * returns -1.
     */
    @Test
    public void testEarlierMinute() {
        Timestamp first = new Timestamp("2026-03-09", "10:47");
        Timestamp second = new Timestamp("2026-03-09", "10:48");
        assertEquals(BEFORE, first.compareTo(second));
    }

    /**
     * Test case #4: a later date with an earlier time returns 1.
     */
    @Test
    public void testLaterDate() {
        Timestamp first = new Timestamp("2027-01-01", "00:00");
        Timestamp second = new Timestamp("2026-12-31", "23:59");
        assertEquals(AFTER, first.compareTo(second));
    }

    /**
     * Test case #5: the same date with a later hour returns 1.
     */
    @Test
    public void testLaterHour() {
        Timestamp first = new Timestamp("2026-03-09", "18:30");
        Timestamp second = new Timestamp("2026-03-09", "06:30");
        assertEquals(AFTER, first.compareTo(second));
    }

    /**
     * Test case #6: the same date and hour with a later minute returns 1.
     */
    @Test
    public void testLaterMinute() {
        Timestamp first = new Timestamp("2026-03-09", "10:49");
        Timestamp second = new Timestamp("2026-03-09", "10:48");
        assertEquals(AFTER, first.compareTo(second));
    }

    /**
     * Test case #7: the same date, hour, and minute returns 0.
     */
    @Test
    public void testSameTimestamp() {
        Timestamp first = new Timestamp("2026-03-09", "10:48");
        Timestamp second = new Timestamp("2026-03-09", "10:48");
        assertEquals(SAME, first.compareTo(second));
    }
}
