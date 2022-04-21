package se.iths.parking_lot.dtos;

import se.iths.parking_lot.entities.ParkingSlot;
import se.iths.parking_lot.entities.User;

import java.util.List;

public record ParkingSlotDto(Long id, String name, Boolean electricCharge) {

    public static ParkingSlotDto from(ParkingSlot parkingSlot) {
        return new ParkingSlotDto(parkingSlot.getId(), parkingSlot.getName(), parkingSlot.getElectricCharge());
    }

    public ParkingSlot toParkingLot() {
        return ParkingSlot.from(this);
    }

    public static List<ParkingSlotDto> from(List<ParkingSlot> parkingSlots) {
        return parkingSlots.stream()
                .map(ParkingSlotDto::from).toList();
    }

    public ParkingSlotDto(Long id, String name, Boolean electricCharge) {
        this.id = id;
        this.name = name;
        if(electricCharge == null){
            this.electricCharge = false;
        } else {
            this.electricCharge = electricCharge;
        }
    }
}
