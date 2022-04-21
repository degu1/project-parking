package se.iths.parking_lot.dtos;

import se.iths.parking_lot.entities.ParkingSlot;

public record ParkingSlotDto(Long id, String name, Boolean electricCharge) {

    public static ParkingSlotDto from(ParkingSlot parkingSlot) {
        return new ParkingSlotDto(parkingSlot.getId(), parkingSlot.getName(), parkingSlot.getElectricCharge());
    }

    public ParkingSlot toParkingLot() {
        return ParkingSlot.from(this);
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
