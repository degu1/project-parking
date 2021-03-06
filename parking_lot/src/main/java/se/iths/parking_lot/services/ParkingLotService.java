package se.iths.parking_lot.services;

import org.springframework.stereotype.Service;
import se.iths.parking_lot.entities.ParkingLot;
import se.iths.parking_lot.entities.Queue;
import se.iths.parking_lot.exceptions.ParkingLotNotFoundException;
import se.iths.parking_lot.repositories.ParkingLotRepository;

import javax.transaction.Transactional;
import java.util.List;
import java.util.stream.StreamSupport;

@Service
@Transactional
public class ParkingLotService implements CRUDService<ParkingLot> {

    private final ParkingLotRepository parkingLotRepository;

    public ParkingLotService(ParkingLotRepository parkingLotRepository) {
        this.parkingLotRepository = parkingLotRepository;
    }

    @Override
    public void create(ParkingLot parkingLot) {
        parkingLot.setQueue(new Queue());
        parkingLot.getQueue().setParkingLot(parkingLot);
        parkingLotRepository.save(parkingLot);
    }

    @Override
    public void updateWithPUT(ParkingLot parkingLot) {
        parkingLotRepository.save(parkingLot);
    }

    @Override
    public void updateWithPATCH(ParkingLot parkingLot) throws ParkingLotNotFoundException {

        ParkingLot oldParkingLot = parkingLotRepository
                .findById(parkingLot.getId())
                .orElseThrow(() -> new ParkingLotNotFoundException("Parking lot with id " + parkingLot.getId() + " not found"));

        if (parkingLot.getName() != null) {
            oldParkingLot.setName(parkingLot.getName());
        }

    }

    @Override
    public List<ParkingLot> getAll() {
        Iterable<ParkingLot> parkingLots = parkingLotRepository.findAll();
        return StreamSupport.stream(parkingLots.spliterator(), false)
                .toList();

    }

    @Override
    public ParkingLot getById(Long id) throws ParkingLotNotFoundException {
        return parkingLotRepository
                .findById(id)
                .orElseThrow(() -> new ParkingLotNotFoundException("Parking lot with id " + id + " not found"));
    }

    @Override
    public void remove(Long id) {
        parkingLotRepository.deleteById(id);
    }

}
