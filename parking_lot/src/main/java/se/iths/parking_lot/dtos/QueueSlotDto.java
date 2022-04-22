package se.iths.parking_lot.dtos;

import se.iths.parking_lot.entities.ParkingSlot;
import se.iths.parking_lot.entities.QueueSlot;

import java.util.List;

public record QueueSlotDto(Long id, Boolean electricCharge) {

    public static QueueSlotDto from(QueueSlot queueSlot) {
        return new QueueSlotDto(queueSlot.getId(), queueSlot.getElectricCharger());
    }
    public static List<QueueSlotDto> from(List<QueueSlot> queueSlots) {
        return queueSlots.stream()
                .map(QueueSlotDto::from).toList();
    }

}
