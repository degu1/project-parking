package se.iths.parking_lot.repositories;

import org.springframework.data.repository.CrudRepository;
import se.iths.parking_lot.entities.ParkingSlot;

public interface ParkingSlotRepository extends CrudRepository<ParkingSlot, Long> {
}
