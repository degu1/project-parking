package se.iths.parking_lot.dtos;

import se.iths.parking_lot.entities.ParkingLot;

import java.util.Objects;

public record ParkingLotDto(Long id, String name) {

    public static ParkingLotDto from(ParkingLot parkingLot) {
        return new ParkingLotDto(parkingLot.getId(), parkingLot.getName());
    }

    public ParkingLot toParkingLot() {
        return ParkingLot.from(this);
    }

}
