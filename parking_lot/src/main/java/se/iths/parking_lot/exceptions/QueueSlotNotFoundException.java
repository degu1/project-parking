package se.iths.parking_lot.exceptions;

public class QueueSlotNotFoundException extends Exception {
    public QueueSlotNotFoundException(String message) {
        super(message);
    }
}
