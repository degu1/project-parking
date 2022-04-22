package se.iths.parking_lot.services;

import org.springframework.stereotype.Service;
import se.iths.parking_lot.entities.ParkingLot;
import se.iths.parking_lot.entities.ParkingSlot;
import se.iths.parking_lot.entities.Queue;
import se.iths.parking_lot.repositories.ParkingLotRepository;
import se.iths.parking_lot.repositories.ParkingSlotRepository;

import javax.persistence.PersistenceContext;
import javax.transaction.Transactional;
import java.util.List;
import java.util.stream.StreamSupport;

@Service
@Transactional
public class ParkingLotService implements CRUDService<ParkingLot> {

    private final ParkingLotRepository parkingLotRepository;
    private final ParkingSlotRepository parkingSlotRepository;


    public ParkingLotService(ParkingLotRepository parkingLotRepository, ParkingSlotRepository parkingSlotRepository) {
        this.parkingLotRepository = parkingLotRepository;
        this.parkingSlotRepository = parkingSlotRepository;
    }

    @Override
    public void create(ParkingLot parkingLot) {
        parkingLot.setQueue(new Queue());
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

    public void removeParkingSlot(Long parkingLotId, Long parkingSlotId) {
        ParkingSlot parkingSlot = parkingSlotRepository.findById(parkingSlotId).orElseThrow(); // TODO
        ParkingLot parkingLot = parkingLotRepository.findById(parkingLotId).orElseThrow(); // TODO

        parkingLot.removeParkingSlot(parkingSlot);
    }
}
