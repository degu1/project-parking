package se.iths.parking_lot.exceptions;

public class NoEmptyParkingSlotException extends Exception {
    public NoEmptyParkingSlotException(String message) {
        super(message);
    }
}
