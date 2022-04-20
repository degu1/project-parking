package se.iths.parking_lot.services;

import org.springframework.stereotype.Service;
import se.iths.parking_lot.dtos.ParkingLotDto;
import se.iths.parking_lot.entities.ParkingLot;
import se.iths.parking_lot.repositories.ExtendedCrudRepository;

import java.util.List;
import java.util.stream.StreamSupport;

@Service
public class ParkingLotService implements ServiceInterface<ParkingLotDto> {

    private final ExtendedCrudRepository parkingLotRepository;

    public ParkingLotService(ExtendedCrudRepository parkingLotRepository) {
        this.parkingLotRepository = parkingLotRepository;
    }

    @Override
    public void create(ParkingLotDto parkingLotDto) {
        parkingLotRepository.save(parkingLotDto.toParkingLot());
    }

    @Override
    public void updateWithPUT(ParkingLotDto parkingLotDto) {
        parkingLotRepository.save(parkingLotDto.toParkingLot());
    }

    @Override
    public void updateWithPATCH(ParkingLotDto parkingLotDto) {
        ParkingLot parkingLot = parkingLotDto.toParkingLot();
        ParkingLot oldParkingLot = parkingLotRepository.findById(parkingLot.getId()).orElseThrow();//TODO
        if (!parkingLot.getName().equals(null)) {
            oldParkingLot.setName(parkingLot.getName());
        }
        parkingLotRepository.save(oldParkingLot);
    }

    @Override
    public List<ParkingLotDto> getAll() {
        Iterable<ParkingLot> parkingLots = parkingLotRepository.findAll();
        return StreamSupport.stream(parkingLots.spliterator(), false)
                .map(ParkingLotDto::from)
                .toList();

    }

    @Override
    public ParkingLotDto getById(Long id) {
        return ParkingLotDto.from(parkingLotRepository.findById(id).orElseThrow());  //TODO
    }

    @Override
    public void remove(Long id) {
        parkingLotRepository.deleteById(id);
    }
}
