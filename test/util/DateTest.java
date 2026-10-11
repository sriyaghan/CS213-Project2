package util;

import static org.junit.Assert.assertFalse;
import static org.junit.Assert.assertTrue;

import org.junit.Test;

/**
 * JUnit test class for the isValid() method of the Date class.
 * It implements the 4 invalid and 2 valid test cases from Project 1.
 * @author Sriya Ghankot, Alexandra Xie
 */
public class DateTest {

    /**
     * Default constructor that creates the test class.
     */
    public DateTest() {
    }

    /**
     * Test case #1: a month greater than 12 is not valid.
     */
    @Test
    public void testMonthOutOfRange() {
        assertFalse(new Date("2026-13-09").isValid());
    }

    /**
     * Test case #2: day 31 in a 30-day month is not valid.
     */
    @Test
    public void testDay31InShortMonth() {
        assertFalse(new Date("2026-04-31").isValid());
    }

    /**
     * Test case #3: February 29 in a non-leap year is not valid.
     */
    @Test
    public void testFeb29InNonLeapYear() {
        assertFalse(new Date("2026-02-29").isValid());
    }

    /**
     * Test case #4: February 29 in a year divisible by 100 but not by 400
     * is not valid.
     */
    @Test
    public void testFeb29InCentennialYear() {
        assertFalse(new Date("1900-02-29").isValid());
    }

    /**
     * Test case #5: February 29 in a year divisible by 400 is valid.
     */
    @Test
    public void testFeb29InQuatercentennialYear() {
        assertTrue(new Date("2000-02-29").isValid());
    }

    /**
     * Test case #6: day 31 in a 31-day month is valid.
     */
    @Test
    public void testDay31InLongMonth() {
        assertTrue(new Date("2026-03-31").isValid());
    }
}
