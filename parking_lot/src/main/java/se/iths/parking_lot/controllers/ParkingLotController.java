package se.iths.parking_lot.controllers;

import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RestController;
import se.iths.parking_lot.dtos.ParkingLotDto;
import se.iths.parking_lot.services.ParkingLotService;

import java.util.List;

@RestController
@RequestMapping("parking_lot")
public class ParkingLotController implements CRUDController<ParkingLotDto> {

    ParkingLotService service;

    public ParkingLotController(ParkingLotService service) {
        this.service = service;
    }

    @Override
    public void create(ParkingLotDto parkingLotDto) {
        service.create(parkingLotDto.toParkingLot());
    }

    @Override
    public void updateWithPUT(ParkingLotDto parkingLotDto) {
        service.updateWithPUT(parkingLotDto.toParkingLot());
    }

    @Override
    public void updateWithPATCH(ParkingLotDto parkingLotDto) {
        service.updateWithPATCH(parkingLotDto.toParkingLot());
    }

    @Override
    public List<ParkingLotDto> getAll() {
        return service.getAll().stream()
                .map(ParkingLotDto::from)
                .toList();
    }

    @Override
    public ParkingLotDto getById(Long id) {
        return ParkingLotDto.from(service.getById(id));
    }

    @Override
    public void remove(Long id) {
        service.remove(id);
    }
}
