package se.iths.parking_lot.services;

import org.springframework.stereotype.Service;
import se.iths.parking_lot.entities.ParkingLot;
import se.iths.parking_lot.repositories.ExtendedCrudRepository;

import java.util.List;
import java.util.stream.StreamSupport;

@Service
public class ParkingLotCRUDService implements CRUDService<ParkingLot> {

    private final ExtendedCrudRepository parkingLotRepository;

    public ParkingLotCRUDService(ExtendedCrudRepository parkingLotRepository) {
        this.parkingLotRepository = parkingLotRepository;
    }

    @Override
    public void create(ParkingLot parkingLot) {
        parkingLotRepository.save(parkingLot);
    }

    @Override
    public void updateWithPUT(ParkingLot parkingLot) {
        parkingLotRepository.save(parkingLot);
    }

    @Override
    public void updateWithPATCH(ParkingLot parkingLot) {
        ParkingLot oldParkingLot = parkingLotRepository.findById(parkingLot.getId()).orElseThrow();//TODO

        if (parkingLot.getName() != null) {
            oldParkingLot.setName(parkingLot.getName());
        }
        parkingLotRepository.save(oldParkingLot);
    }

    @Override
    public List<ParkingLot> getAll() {
        Iterable<ParkingLot> parkingLots = parkingLotRepository.findAll();
        return StreamSupport.stream(parkingLots.spliterator(), false)
                .toList();

    }

    @Override
    public ParkingLot getById(Long id) {
        return parkingLotRepository.findById(id).orElseThrow();  //TODO
    }

    @Override
    public void remove(Long id) {
        parkingLotRepository.deleteById(id);
    }
}
