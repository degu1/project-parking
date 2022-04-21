package se.iths.parking_lot.dtos;

import se.iths.parking_lot.entities.ParkingLot;
import se.iths.parking_lot.entities.ParkingSlot;

import java.util.List;
import java.util.Objects;

public record ParkingLotDto(Long id, String name) {

    public static ParkingLotDto from(ParkingLot parkingLot) {
        return new ParkingLotDto(parkingLot.getId(), parkingLot.getName());
    }

    public static List<ParkingLotDto> from(List<ParkingLot> parkingLots) {
        return parkingLots.stream()
                .map(ParkingLotDto::from).toList();
    }

    public ParkingLot toParkingLot() {
        return ParkingLot.from(this);
    }

}
