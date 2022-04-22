package se.iths.parking_lot.repositories;

import org.springframework.data.repository.CrudRepository;
import se.iths.parking_lot.entities.QueueSlot;

public interface QueueSlotRepository extends CrudRepository<QueueSlot, Long> {
}
