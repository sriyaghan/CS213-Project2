package parking;

import java.io.File;
import java.io.FileNotFoundException;
import java.util.NoSuchElementException;
import java.util.Scanner;
import java.util.StringTokenizer;
import util.Date;

/**
 * Operation class that is the user interface of the Parking Management
 * System.
 * It reads the command lines entered on the terminal, processes each
 * command, and displays the results until the Q command is entered.
 * @author Sriya Ghankot, Alexandra Xie
 */
public class Operation {
    /** The value returned when a token is not a valid number. */
    private static final int NOT_A_NUMBER = -1;
    /** The number of parts in a time string hh:mm. */
    private static final int TIME_PARTS = 2;
    /** The largest valid hour in a 24-hour time format. */
    private static final int MAX_HOUR = 23;
    /** The largest valid minute. */
    private static final int MAX_MINUTE = 59;
    /** The number of minutes in an hour. */
    private static final int MINUTES_PER_HOUR = 60;
    /** The number of hours in a day. */
    private static final int HOURS_PER_DAY = 24;
    /** The maximum number of days a vehicle can stay in a deck. */
    private static final int MAX_DAYS_PARKED = 2;
    /** The maximum number of minutes a vehicle can stay. */
    private static final long MAX_MINUTES_PARKED =
            MAX_DAYS_PARKED * HOURS_PER_DAY * MINUTES_PER_HOUR;
    /** The delimiter between the hour and the minute. */
    private static final String TIME_DELIMITER = ":";
    /** The text file holding the vehicles to load. */
    private static final String VEHICLE_FILE = "vehicles.txt";
    /** The first character of the license plate of a pickup truck. */
    private static final char PICKUP = 'P';
    /** The first character of the license plate of a van. */
    private static final char VAN = 'V';

    /** The list of registered vehicles. */
    private VehicleList vehicles;
    /** The list of parking decks. */
    private DeckList decks;
    /** The subject sending notifications to the registered vehicles. */
    private Notification notification;

    /**
     * Create the user interface with empty vehicle and deck lists.
     */
    public Operation() {
        vehicles = new VehicleList();
        decks = new DeckList();
        notification = new Notification();
    }

    /**
     * Get the name of the action of a command for exception messages.
     * @param command the command at the beginning of the command line.
     * @return the name of the action.
     */
    private String action(String command) {
        switch (command) {
            case "A":
                return "Register";
            case "R":
                return "Unregister";
            case "O":
                return "Open";
            case "C":
                return "Close";
            case "E":
                return "Enter";
            case "X":
                return "Exit";
            case "L":
                return "Load";
            default:
                return "Print";
        }
    }

    /**
     * Process a single command and handle the exceptions it throws.
     * @param command the command at the beginning of the command line.
     * @param tokens the data tokens following the command.
     */
    private void processCommand(String command, StringTokenizer tokens) {
        try {
            executeCommand(command, tokens);
        } catch (NoSuchElementException exception) {
            System.out.println("Exception [" + action(command)
                    + "] - missing data tokens.");
        } catch (NumberFormatException exception) {
            System.out.println("Exception [" + action(command) + "] - "
                    + exception.getMessage());
        }
    }

    /**
     * Execute a command that changes the system, or pass a display command
     * on to be executed.
     * @param command the command at the beginning of the command line.
     * @param tokens the data tokens following the command.
     */
    private void executeCommand(String command, StringTokenizer tokens) {
        switch (command) {
            case "A":
                addVehicle(tokens);
                break;
            case "R":
                removeVehicle(tokens);
                break;
            case "O":
                openDeck(tokens);
                break;
            case "C":
                closeDeck(tokens);
                break;
            case "E":
                enterDeck(tokens);
                break;
            case "X":
                exitDeck(tokens);
                break;
            case "L":
                loadVehicles();
                break;
            default:
                executePrintCommand(command, tokens);
        }
    }

    /**
     * Execute a command that displays the system information.
     * @param command the command at the beginning of the command line.
     * @param tokens the data tokens following the command.
     */
    private void executePrintCommand(String command,
                                     StringTokenizer tokens) {
        switch (command) {
            case "PP":
                vehicles.printByPlate();
                break;
            case "PD":
                printDecks(tokens);
                break;
            case "PH":
                printHistory(tokens);
                break;
            case "PF":
                decks.printFees();
                break;
            case "PN":
                printNotifications(tokens);
                break;
            default:
                System.out.println(command + " is an invalid command!");
        }
    }

    /**
     * Create a sedan, van, or pickup truck based on the first character
     * of the license plate.
     * @param plate the license plate of the vehicle.
     * @return a pickup truck if the plate starts with P, a van if it
     *         starts with V, or a sedan otherwise.
     */
    private Vehicle newVehicle(String plate) {
        char type = Character.toUpperCase(plate.charAt(0));
        if (type == PICKUP) {
            return new Pickup(plate);
        }
        if (type == VAN) {
            return new Van(plate);
        }
        return new Sedan(plate);
    }

    /**
     * Get the registered vehicle with the same license plate.
     * @param vehicle the vehicle holding the license plate to look for.
     * @return the registered vehicle; null if it is not registered.
     */
    private Vehicle registered(Vehicle vehicle) {
        if (!vehicles.contains(vehicle)) {
            return null;
        }
        return vehicles.get(vehicles.indexOf(vehicle));
    }

    /**
     * Get the deck with the given deck number.
     * @param number the deck number.
     * @return the deck; null if the deck does not exist.
     */
    private Deck deck(int number) {
        Deck target = new Deck(number);
        if (!decks.contains(target)) {
            return null;
        }
        return decks.get(decks.indexOf(target));
    }

    /**
     * Check if a license plate is in a valid format.
     * An error message is displayed if the plate is not valid.
     * @param vehicle the vehicle holding the license plate.
     * @param plate the license plate as entered by the user.
     * @return true if the plate is valid; false otherwise.
     */
    private boolean isValidPlate(Vehicle vehicle, String plate) {
        if (!vehicle.isValid()) {
            System.out.println(plate + " - invalid license plate format.");
            return false;
        }
        return true;
    }

    /**
     * Get the deck number from a token containing only digits.
     * @param token the token holding the deck number.
     * @return the deck number.
     * @throws NumberFormatException if the token is not a valid number.
     */
    private int deckNumber(String token) {
        for (int i = 0; i < token.length(); i++) {
            if (token.charAt(i) < '0' || token.charAt(i) > '9') {
                throw new NumberFormatException("For input string: \""
                        + token + "\"");
            }
        }
        return Integer.parseInt(token);
    }

    /**
     * Get the value of an hour or a minute from a token.
     * @param token the token holding the value.
     * @param maxValue the largest valid value.
     * @return the value; NOT_A_NUMBER if it is not a number from 0 to
     *         maxValue.
     */
    private int timeValue(String token, int maxValue) {
        try {
            int value = Integer.parseInt(token);
            if (value < 0 || value > maxValue) {
                return NOT_A_NUMBER;
            }
            return value;
        } catch (NumberFormatException exception) {
            return NOT_A_NUMBER;
        }
    }

    /**
     * Get the timestamp from a date token and a time token.
     * An error message is displayed if the date, hour, or minute is not
     * valid.
     * @param dateToken the date in the format yyyy-mm-dd.
     * @param timeToken the time in the format hh:mm.
     * @return the timestamp; null if the date or time is not valid.
     */
    private Timestamp timestamp(String dateToken, String timeToken) {
        Date date = new Date(dateToken);
        if (!date.isValid()) {
            System.out.println(dateToken + " - invalid calendar date.");
            return null;
        }
        String[] time = timeToken.split(TIME_DELIMITER);
        if (time.length != TIME_PARTS) {
            System.out.println(timeToken + " - invalid time format.");
            return null;
        }
        int hour = timeValue(time[0], MAX_HOUR);
        if (hour == NOT_A_NUMBER) {
            System.out.println(time[0] + " - invalid hour.");
            return null;
        }
        int minute = timeValue(time[1], MAX_MINUTE);
        if (minute == NOT_A_NUMBER) {
            System.out.println(time[1] + " - invalid minute.");
            return null;
        }
        return new Timestamp(date, hour, minute);
    }

    /**
     * Get an existing deck from a token holding the deck number.
     * An error message is displayed if the deck does not exist.
     * @param token the token holding the deck number.
     * @return the deck; null if the deck does not exist.
     */
    private Deck existingDeck(String token) {
        int number = deckNumber(token);
        Deck deck = deck(number);
        if (deck == null) {
            System.out.println("Deck#" + number + " - does not exist.");
        }
        return deck;
    }

    /**
     * Register a vehicle and subscribe it to the system notifications.
     * @param vehicle the vehicle to register.
     */
    private void register(Vehicle vehicle) {
        vehicles.add(vehicle);
        notification.register(vehicle);
    }

    /**
     * Register a sedan, van, or pickup truck (A command).
     * @param tokens the data tokens holding the license plate.
     */
    private void addVehicle(StringTokenizer tokens) {
        String plate = tokens.nextToken();
        Vehicle vehicle = newVehicle(plate);
        if (!isValidPlate(vehicle, plate)) {
            return;
        }
        if (vehicles.contains(vehicle)) {
            System.out.println(plate + " is already registered.");
            return;
        }
        register(vehicle);
        System.out.println(vehicle + " registered.");
    }

    /**
     * Unregister a vehicle and unsubscribe it from the system
     * notifications (R command).
     * A vehicle parked in a deck or with a parking history is not removed.
     * @param tokens the data tokens holding the license plate.
     */
    private void removeVehicle(StringTokenizer tokens) {
        String plate = tokens.nextToken();
        Vehicle vehicle = newVehicle(plate);
        if (!isValidPlate(vehicle, plate)) {
            return;
        }
        Vehicle registered = registered(vehicle);
        if (registered == null) {
            System.out.println(plate
                    + " does not exist; cannot be unregistered.");
        } else if (decks.getDeckWith(registered) != null) {
            System.out.println("Cannot be unregistered - " + vehicle
                    + " is in a deck.");
        } else if (registered.hasHistory()) {
            System.out.println("Cannot be unregistered - " + vehicle
                    + " has parking history.");
        } else {
            vehicles.remove(registered);
            notification.unregister(registered);
            System.out.println(vehicle + " unregistered.");
        }
    }

    /**
     * Load the vehicles from the text file and register the vehicles that
     * are not registered yet (L command).
     */
    private void loadVehicles() {
        try {
            Scanner reader = new Scanner(new File(VEHICLE_FILE));
            while (reader.hasNext()) {
                Vehicle vehicle = newVehicle(reader.next());
                if (!vehicles.contains(vehicle)) {
                    register(vehicle);
                }
            }
            reader.close();
            System.out.println("vehicles loaded.");
        } catch (FileNotFoundException exception) {
            System.out.println("Exception [Load] - "
                    + exception.getMessage());
        }
    }

    /**
     * Create a new deck from the location, operating hours, and capacity
     * tokens.
     * An error message is displayed if any of the data is not valid.
     * @param number the deck number.
     * @param tokens the data tokens following the deck number.
     * @return the new deck; null if any of the data is not valid.
     */
    private Deck newDeck(int number, StringTokenizer tokens) {
        String city = tokens.nextToken();
        Location location = Location.toLocation(city);
        if (location == null) {
            System.out.println(city + " - invalid location.");
            return null;
        }
        String code = tokens.nextToken();
        Hour hour = Hour.toHour(code);
        if (hour == null) {
            System.out.println(code + " - invalid operation hours");
            return null;
        }
        String capacityToken = tokens.nextToken();
        int capacity = Integer.parseInt(capacityToken);
        if (capacity > Deck.MAXCAPACITY) {
            System.out.println(capacityToken + " - exceeds the maximum deck "
                    + "capacity " + Deck.MAXCAPACITY);
            return null;
        }
        if (capacity < 1) {
            System.out.println(capacityToken + " - invalid capacity.");
            return null;
        }
        return new Deck(number, location, hour, capacity);
    }

    /**
     * Open a new deck or reopen a closed deck (O command).
     * @param tokens the data tokens holding the deck number, location,
     *               operating hours, and capacity.
     */
    private void openDeck(StringTokenizer tokens) {
        int number = deckNumber(tokens.nextToken());
        Deck deck = deck(number);
        if (deck != null && deck.isOpen()) {
            System.out.println("Deck#" + number + " - is already open.");
        } else if (deck != null) {
            deck.setOpen(true);
            System.out.println("Deck#" + number
                    + " - was closed, now reopened.");
        } else {
            deck = newDeck(number, tokens);
            if (deck != null) {
                decks.add(deck);
                System.out.println("Deck#" + number + " opened.");
            }
        }
    }

    /**
     * Close an open deck that has no vehicle parked, and notify the
     * registered vehicles (C command).
     * @param tokens the data tokens holding the deck number.
     */
    private void closeDeck(StringTokenizer tokens) {
        Deck deck = existingDeck(tokens.nextToken());
        if (deck == null) {
            return;
        }
        if (!deck.isOpen()) {
            System.out.println("Deck#" + deck.getNumber()
                    + " - is already closed.");
        } else if (!deck.getParkings().isEmpty()) {
            System.out.println("Deck#" + deck.getNumber()
                    + " - cannot be closed; vehicles are in the deck.");
        } else {
            deck.setOpen(false);
            String message = "Deck#" + deck.getNumber() + " - closed.";
            System.out.println(message);
            notification.notify(message);
        }
    }

    /**
     * Get a deck that is open and not full for a vehicle to enter.
     * An error message is displayed if the deck cannot be entered.
     * @param token the token holding the deck number.
     * @return the deck; null if the deck cannot be entered.
     */
    private Deck availableDeck(String token) {
        Deck deck = existingDeck(token);
        if (deck == null) {
            return null;
        }
        if (!deck.isOpen()) {
            System.out.println("Deck#" + deck.getNumber()
                    + " - is closed for parking.");
            return null;
        }
        if (deck.isFull()) {
            System.out.println("Deck#" + deck.getNumber() + " - is full.");
            return null;
        }
        return deck;
    }

    /**
     * Get a registered vehicle that is not parked in any deck.
     * An error message is displayed if the vehicle cannot enter a deck.
     * @param vehicle the vehicle holding the license plate.
     * @param plate the license plate as entered by the user.
     * @return the registered vehicle; null if it cannot enter a deck.
     */
    private Vehicle parkableVehicle(Vehicle vehicle, String plate) {
        if (!isValidPlate(vehicle, plate)) {
            return null;
        }
        Vehicle registered = registered(vehicle);
        if (registered == null) {
            System.out.println(vehicle + " - is not registered.");
            return null;
        }
        if (decks.getDeckWith(registered) != null) {
            System.out.println("Error entering - " + vehicle
                    + " is already in a deck.");
            return null;
        }
        return registered;
    }

    /**
     * Record a parking activity when a vehicle enters a deck, and notify
     * the registered vehicles if the deck becomes full (E command).
     * @param tokens the data tokens holding the deck number, license
     *               plate, date, and time.
     */
    private void enterDeck(StringTokenizer tokens) {
        Deck deck = availableDeck(tokens.nextToken());
        if (deck == null) {
            return;
        }
        String plate = tokens.nextToken();
        Vehicle vehicle = newVehicle(plate);
        Vehicle registered = parkableVehicle(vehicle, plate);
        if (registered == null) {
            return;
        }
        Timestamp enter = timestamp(tokens.nextToken(), tokens.nextToken());
        if (enter == null) {
            return;
        }
        if (!deck.isWithinHours(enter)) {
            System.out.println("Error entering - not within the operating "
                    + "hour: " + deck.getHour());
            return;
        }
        deck.enter(new Parking(registered, enter));
        System.out.println(vehicle + " entered Deck#" + deck.getNumber()
                + " on " + enter);
        if (deck.isFull()) {
            notification.notify("Deck# " + deck.getNumber() + " is full.");
        }
    }

    /**
     * Check if the exit time of a parking activity is valid.
     * The exit time cannot be before the entry time or more than two days
     * after the entry time.
     * @param parking the parking activity of the exiting vehicle.
     * @param exit the exit timestamp.
     * @return true if the exit time is valid; false otherwise.
     */
    private boolean isValidExit(Parking parking, Timestamp exit) {
        Timestamp enter = parking.getEnter();
        if (exit.compareTo(enter) < 0) {
            System.out.println("Exiting time " + exit
                    + " before entering time " + enter);
            return false;
        }
        if (exit.minutesSince(enter) > MAX_MINUTES_PARKED) {
            System.out.println("Invalid exiting time - exceeds two days.");
            return false;
        }
        return true;
    }

    /**
     * Complete a parking activity when a vehicle exits a deck.
     * The parking fee is calculated based on the vehicle type and added to
     * the fees collected by the deck, and the activity is moved from the
     * deck to the parking history of the vehicle.
     * @param deck the deck the vehicle exits.
     * @param parking the parking activity of the vehicle.
     * @param exit the exit timestamp.
     * @return the parking fee.
     */
    private int checkOut(Deck deck, Parking parking, Timestamp exit) {
        Vehicle vehicle = parking.getVehicle();
        int fee = vehicle.parkingFee(deck, parking.getEnter(), exit);
        parking.setExit(exit);
        deck.exit(parking);
        deck.collectFee(fee);
        vehicle.addHistory(new History(deck.getNumber(), parking));
        return fee;
    }

    /**
     * Record a parking activity when a vehicle exits a deck, and display
     * the parking fee (X command).
     * @param tokens the data tokens holding the license plate, date, and
     *               time.
     */
    private void exitDeck(StringTokenizer tokens) {
        String plate = tokens.nextToken();
        Vehicle vehicle = newVehicle(plate);
        if (!isValidPlate(vehicle, plate)) {
            return;
        }
        Deck deck = decks.getDeckWith(vehicle);
        if (deck == null) {
            System.out.println("Error exiting - " + vehicle
                    + " is not in a deck.");
            return;
        }
        Timestamp exit = timestamp(tokens.nextToken(), tokens.nextToken());
        if (exit == null) {
            return;
        }
        if (!deck.isWithinHours(exit)) {
            System.out.println("Error exiting - not within the operating "
                    + "hour: " + deck.getHour());
            return;
        }
        Parking parking = deck.getParking(vehicle);
        if (isValidExit(parking, exit)) {
            int fee = checkOut(deck, parking, exit);
            System.out.println(vehicle + " exited Deck#" + deck.getNumber()
                    + " on " + exit);
            System.out.println("    * Parking Fee: $ " + fee
                    + " [entered: " + parking.getEnter() + "]");
        }
    }

    /**
     * Display the list of open decks, or the vehicles parked in a deck if
     * a deck number is given (PD command).
     * @param tokens the data tokens, optionally holding a deck number.
     */
    private void printDecks(StringTokenizer tokens) {
        if (!tokens.hasMoreTokens()) {
            decks.printByLocation();
            return;
        }
        Deck deck = existingDeck(tokens.nextToken());
        if (deck == null) {
            return;
        }
        if (!deck.isOpen()) {
            System.out.println("Deck#" + deck.getNumber() + " - is closed.");
        } else {
            decks.printVehicles(deck);
        }
    }

    /**
     * Display the parking history of all vehicles, or of one vehicle if a
     * license plate is given (PH command).
     * @param tokens the data tokens, optionally holding a license plate.
     */
    private void printHistory(StringTokenizer tokens) {
        if (!tokens.hasMoreTokens()) {
            if (decks.isEmpty()) {
                System.out.println("Deck list is empty - no deck is open.");
            } else {
                vehicles.printHistory();
            }
            return;
        }
        String plate = tokens.nextToken();
        Vehicle vehicle = newVehicle(plate);
        if (!isValidPlate(vehicle, plate)) {
            return;
        }
        Vehicle registered = registered(vehicle);
        if (registered == null) {
            System.out.println(plate + " does not exist.");
        } else if (!registered.hasHistory()) {
            System.out.println(plate + " - no parking history.");
        } else {
            vehicles.printHistory(vehicle);
        }
    }

    /**
     * Display the notifications received by all vehicles, or by one
     * vehicle if a license plate is given (PN command).
     * @param tokens the data tokens, optionally holding a license plate.
     */
    private void printNotifications(StringTokenizer tokens) {
        if (!tokens.hasMoreTokens()) {
            vehicles.printNotifications();
            return;
        }
        String plate = tokens.nextToken();
        Vehicle vehicle = newVehicle(plate);
        if (!isValidPlate(vehicle, plate)) {
            return;
        }
        Vehicle registered = registered(vehicle);
        if (registered == null) {
            System.out.println(plate + " does not exist.");
        } else if (!registered.hasNotifications()) {
            System.out.println(plate + " - no notifications.");
        } else {
            vehicles.printNotifications(vehicle);
        }
    }

    /**
     * Run the Parking Management System.
     * It continuously reads and processes the command lines entered on the
     * terminal until the Q command is entered; empty lines are skipped.
     */
    public void run() {
        Scanner scanner = new Scanner(System.in);
        System.out.println("Parking Management System is in operation.");
        System.out.println();
        boolean running = true;
        while (running && scanner.hasNextLine()) {
            StringTokenizer tokens = new StringTokenizer(scanner.nextLine());
            if (tokens.hasMoreTokens()) {
                String command = tokens.nextToken();
                if (command.equals("Q")) {
                    System.out.println("Parking Management System is "
                            + "terminated.");
                    running = false;
                } else {
                    processCommand(command, tokens);
                }
            }
        }
        scanner.close();
    }
}
