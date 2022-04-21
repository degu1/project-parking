package se.iths.parking_lot.repositories;

import org.springframework.data.repository.CrudRepository;
import se.iths.parking_lot.entities.ParkingLot;

public interface ParkingLotRepository extends CrudRepository<ParkingLot, Long> {

}
