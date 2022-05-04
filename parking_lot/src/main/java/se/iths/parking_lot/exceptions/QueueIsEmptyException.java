package se.iths.parking_lot.exceptions;

public class QueueIsEmptyException extends Exception {
    public QueueIsEmptyException(String message) {
        super(message);
    }
}
