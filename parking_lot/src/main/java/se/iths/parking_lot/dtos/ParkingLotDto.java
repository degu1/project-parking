package se.iths.parking_lot.dtos;

import se.iths.parking_lot.entities.ParkingLot;
import se.iths.parking_lot.entities.ParkingSlot;
import se.iths.parking_lot.entities.Queue;

import java.util.List;
import java.util.Objects;
import java.util.Set;

public record ParkingLotDto(Long id, String name, List<ParkingSlotDto> parkingSlots, Queue queue) {

    public static ParkingLotDto from(ParkingLot parkingLot) {
        return new ParkingLotDto(parkingLot.getId(), parkingLot.getName(), ParkingSlotDto.from(parkingLot.getParkingSlots()),parkingLot.getQueue());
    }

    public static List<ParkingLotDto> from(List<ParkingLot> parkingLots) {
        return parkingLots.stream()
                .map(ParkingLotDto::from).toList();
    }

    public ParkingLot toParkingLot() {
        return ParkingLot.from(this);
    }

}
