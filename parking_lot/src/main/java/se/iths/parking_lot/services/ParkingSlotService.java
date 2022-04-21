package se.iths.parking_lot.services;

import org.springframework.stereotype.Service;
import se.iths.parking_lot.entities.ParkingSlot;
import se.iths.parking_lot.repositories.ParkingSlotRepository;

import java.util.List;
import java.util.stream.StreamSupport;

@Service
public class ParkingSlotService implements CRUDService<ParkingSlot>{

    private final ParkingSlotRepository parkingSlotRepository;

    public ParkingSlotService(ParkingSlotRepository parkingSlotRepository) {
        this.parkingSlotRepository = parkingSlotRepository;
    }

    @Override
    public void create(ParkingSlot parkingSlot) {
        parkingSlotRepository.save(parkingSlot);
    }

    @Override
    public void updateWithPUT(ParkingSlot parkingSlot) {
        parkingSlotRepository.save(parkingSlot);
    }

    @Override
    public void updateWithPATCH(ParkingSlot parkingSlot) {
        ParkingSlot oldParkingSlot = parkingSlotRepository.findById(parkingSlot.getId()).orElseThrow();//TODO

        if (parkingSlot.getName() != null) {
            oldParkingSlot.setName(parkingSlot.getName());
        }
        if(parkingSlot.getElectricCharge() != null) {
            oldParkingSlot.setElectricCharge(parkingSlot.getElectricCharge());
        }
        parkingSlotRepository.save(oldParkingSlot);
    }

    @Override
    public List<ParkingSlot> getAll() {
        Iterable<ParkingSlot> parkingSlots = parkingSlotRepository.findAll();
        return StreamSupport.stream(parkingSlots.spliterator(), false)
                .toList();
    }

    @Override
    public ParkingSlot getById(Long id) {
        return parkingSlotRepository.findById(id).orElseThrow();  //TODO
    }

    @Override
    public void remove(Long id) {
        parkingSlotRepository.deleteById(id);
    }
}
