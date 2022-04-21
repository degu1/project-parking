package se.iths.parking_lot.controllers;

import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RestController;
import se.iths.parking_lot.dtos.ParkingSlotDto;
import se.iths.parking_lot.services.ParkingSlotService;

import java.util.List;

@RestController
@RequestMapping("parking_slot")
public class ParkingSlotController implements CRUDController<ParkingSlotDto>{

    ParkingSlotService parkingSlotservice;

    public ParkingSlotController(ParkingSlotService service) {
        this.parkingSlotservice = service;
    }

    @Override
    public void create(ParkingSlotDto parkingSlotDto) {
        parkingSlotservice.create(parkingSlotDto.toParkingLot());
    }

    @Override
    public void updateWithPUT(ParkingSlotDto parkingSlotDto) {
        parkingSlotservice.updateWithPUT(parkingSlotDto.toParkingLot());
    }

    @Override
    public void updateWithPATCH(ParkingSlotDto parkingSlotDto) {
        parkingSlotservice.updateWithPATCH(parkingSlotDto.toParkingLot());
    }

    @Override
    public List<ParkingSlotDto> getAll() {
        return ParkingSlotDto.from(parkingSlotservice.getAll());
    }

    @Override
    public ParkingSlotDto getById(Long id) {
        return ParkingSlotDto.from(parkingSlotservice.getById(id));
    }

    @Override
    public void remove(Long id) {
        parkingSlotservice.remove(id);
    }
}
