package parking;

/**
 * Driver class that starts the Parking Management System.
 * The TAs run this class to grade Project 2.
 * @author Sriya Ghankot, Alexandra Xie
 */
public class RunProject2 {

    /**
     * Default constructor that creates a driver for Project 2.
     */
    public RunProject2() {
    }

    /**
     * Start the Parking Management System by running the user interface.
     * @param args command-line arguments, not used.
     */
    public static void main(String[] args) {
        new Operation().run();
    }
}
