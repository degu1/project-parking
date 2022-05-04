package se.iths.parking_lot.exceptions;

public class ParkingSlotNotFoundException extends Exception {
    public ParkingSlotNotFoundException(String message) {
        super(message);
    }
}
