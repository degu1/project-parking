package se.iths.parking_lot.controllers;

import org.springframework.web.bind.annotation.PathVariable;
import org.springframework.web.bind.annotation.PutMapping;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RestController;
import se.iths.parking_lot.dtos.ParkingLotDto;
import se.iths.parking_lot.services.ParkingLotService;

import java.util.List;

@RestController
@RequestMapping("parking_lots")
public class ParkingLotController implements CRUDController<ParkingLotDto> {

    ParkingLotService parkingLotService;

    public ParkingLotController(ParkingLotService service) {
        this.parkingLotService = service;
    }

    @Override
    public void create(ParkingLotDto parkingLotDto) {
        parkingLotService.create(parkingLotDto.toParkingLot());
    }

    @Override
    public void updateWithPUT(ParkingLotDto parkingLotDto) {
        parkingLotService.updateWithPUT(parkingLotDto.toParkingLot());
    }

    @Override
    public void updateWithPATCH(ParkingLotDto parkingLotDto) {
        parkingLotService.updateWithPATCH(parkingLotDto.toParkingLot());
    }

    @Override
    public List<ParkingLotDto> getAll() {
        return ParkingLotDto.from(parkingLotService.getAll());
    }

    @Override
    public ParkingLotDto getById(Long id) {
        return ParkingLotDto.from(parkingLotService.getById(id));
    }

    @Override
    public void remove(Long id) {
        parkingLotService.remove(id);
    }

    @PutMapping("remove_parking_slot")
    public void removeParkingSlot(Long parkingLotId, Long parkingSlotId) {
        parkingLotService.removeParkingSlot(parkingLotId,parkingSlotId);
    }
}
