package se.iths.parking_lot.controllers;

import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RestController;
import se.iths.parking_lot.dtos.ParkingSlotDto;
import se.iths.parking_lot.services.ParkingSlotService;

import java.util.List;

@RestController
@RequestMapping("parking_slot")
public class ParkingSlotController implements CRUDController<ParkingSlotDto>{

    ParkingSlotService service;

    public ParkingSlotController(ParkingSlotService service) {
        this.service = service;
    }

    @Override
    public void create(ParkingSlotDto parkingSlotDto) {
        service.create(parkingSlotDto.toParkingLot());
    }

    @Override
    public void updateWithPUT(ParkingSlotDto parkingSlotDto) {
        service.updateWithPUT(parkingSlotDto.toParkingLot());
    }

    @Override
    public void updateWithPATCH(ParkingSlotDto parkingSlotDto) {
        service.updateWithPATCH(parkingSlotDto.toParkingLot());
    }

    @Override
    public List<ParkingSlotDto> getAll() {
        return service.getAll().stream()
                .map(ParkingSlotDto::from)
                .toList();
    }

    @Override
    public ParkingSlotDto getById(Long id) {
        return ParkingSlotDto.from(service.getById(id));
    }

    @Override
    public void remove(Long id) {
        service.remove(id);
    }
}
