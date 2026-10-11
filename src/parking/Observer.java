package parking;

/**
 * Interface for the observer in the Observer design pattern.
 * An observer receives the notifications sent by a subject.
 * @author Sriya Ghankot, Alexandra Xie
 */
public interface Observer {

    /**
     * Receive a notification message from the subject.
     * @param message the notification message.
     */
    void update(String message);
}
