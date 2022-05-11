package se.iths.parking_lot.services;

import org.springframework.stereotype.Service;
import se.iths.parking_lot.JMS.sender.MessageSender;
import se.iths.parking_lot.entities.ParkingLot;
import se.iths.parking_lot.entities.ParkingSlot;
import se.iths.parking_lot.entities.Queue;
import se.iths.parking_lot.entities.QueueSlot;
import se.iths.parking_lot.exceptions.ParkingLotNotFoundException;
import se.iths.parking_lot.exceptions.ParkingSlotNotFoundException;
import se.iths.parking_lot.exceptions.QueueIsEmptyException;
import se.iths.parking_lot.repositories.ParkingLotRepository;
import se.iths.parking_lot.repositories.ParkingSlotRepository;
import se.iths.parking_lot.repositories.QueueSlotRepository;

import javax.transaction.Transactional;
import java.util.List;
import java.util.stream.StreamSupport;

@Service
@Transactional
public class ParkingSlotService implements CRUDService<ParkingSlot> {

    private final ParkingSlotRepository parkingSlotRepository;
    private final ParkingLotRepository parkingLotRepository;
    private final QueueSlotRepository queueSlotRepository;
    private final MessageSender messageSender;

    public ParkingSlotService(ParkingSlotRepository parkingSlotRepository, ParkingLotRepository parkingLotRepository, QueueSlotRepository queueSlotRepository, MessageSender messageSender) {
        this.parkingSlotRepository = parkingSlotRepository;
        this.parkingLotRepository = parkingLotRepository;
        this.queueSlotRepository = queueSlotRepository;
        this.messageSender = messageSender;
    }

    @Override
    public void create(ParkingSlot parkingSlot) {

    }

    public void create(ParkingSlot parkingSlot, Long parkingLotId) throws ParkingLotNotFoundException {
        ParkingLot foundParkingLot = parkingLotRepository.findById(parkingLotId).orElseThrow(() -> new ParkingLotNotFoundException("Parking lot with id " + parkingLotId + " not found"));
        parkingSlot.setParkingLot(foundParkingLot);
        if (parkingSlot.getElectricCharge() == null) {
            parkingSlot.setElectricCharge(false);
        }
        parkingSlotRepository.save(parkingSlot);

        Queue queue = parkingSlot.getParkingLot().getQueue();

        try {
            QueueSlot queueSlot = queue.getFirstSlot(parkingSlot.getElectricCharge());
            parkingSlot.setUser(queueSlot.getUser());
            queueSlotRepository.delete(queueSlot);
        } catch (QueueIsEmptyException ignored) {
        }
    }

    @Override
    public void updateWithPUT(ParkingSlot parkingSlot) {
        parkingSlotRepository.save(parkingSlot);
    }

    @Override
    public void updateWithPATCH(ParkingSlot parkingSlot) throws ParkingSlotNotFoundException {
        ParkingSlot oldParkingSlot = parkingSlotRepository.findById(parkingSlot.getId()).orElseThrow(() -> new ParkingSlotNotFoundException("Parking slot with id " + parkingSlot.getId() + " not found"));

        if (parkingSlot.getName() != null) {
            oldParkingSlot.setName(parkingSlot.getName());
        }
        if (parkingSlot.getElectricCharge() != null) {
            oldParkingSlot.setElectricCharge(parkingSlot.getElectricCharge());
        }
    }

    @Override
    public List<ParkingSlot> getAll() {
        Iterable<ParkingSlot> parkingSlots = parkingSlotRepository.findAll();
        return StreamSupport.stream(parkingSlots.spliterator(), false).toList();
    }

    @Override
    public ParkingSlot getById(Long id) throws ParkingSlotNotFoundException {
        return parkingSlotRepository
                .findById(id)
                .orElseThrow(() -> new ParkingSlotNotFoundException("Parking slot with id " + id + " not found"));
    }

    @Override
    public void remove(Long id) {
        ParkingSlot parkingSlot = parkingSlotRepository.findById(id).orElseThrow();
        parkingSlot.getParkingLot().removeParkingSlot(parkingSlot);
        parkingSlotRepository.deleteById(id);
    }

    public void removeUserFromParkingSlot(Long parkingSlotId) throws ParkingSlotNotFoundException {
        ParkingSlot parkingSlot = parkingSlotRepository
                .findById(parkingSlotId)
                .orElseThrow(() -> new ParkingSlotNotFoundException("Parking slot with id " + parkingSlotId + " not found"));
        parkingSlot.removeUser();
        Queue queue = parkingSlot.getParkingLot().getQueue();

        try {
            QueueSlot queueSlot = queue.getFirstSlot(parkingSlot.getElectricCharge());

            parkingSlot.setUser(queueSlot.getUser());
            queue.removeQueueSlot(queueSlot);
            queueSlotRepository.delete(queueSlot);

            messageSender.addedToParkingSlotMessage(parkingSlot);
            messageSender.sendQueueUpdateMessage(queue);
        } catch (QueueIsEmptyException ignored) {
        }
    }
}
