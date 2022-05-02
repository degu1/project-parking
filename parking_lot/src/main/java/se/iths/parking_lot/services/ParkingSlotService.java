package se.iths.parking_lot.services;

import org.springframework.stereotype.Service;
import se.iths.parking_lot.entities.ParkingLot;
import se.iths.parking_lot.entities.ParkingSlot;
import se.iths.parking_lot.entities.Queue;
import se.iths.parking_lot.entities.QueueSlot;
import se.iths.parking_lot.repositories.ParkingLotRepository;
import se.iths.parking_lot.repositories.ParkingSlotRepository;
import se.iths.parking_lot.repositories.QueueSlotRepository;
import se.iths.parking_lot.JMS.sender.MessageSender;

import javax.transaction.Transactional;
import java.util.List;
import java.util.stream.StreamSupport;

@Service
@Transactional
public class ParkingSlotService implements CRUDService<ParkingSlot>{

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

    public void create(ParkingSlot parkingSlot, Long parkingLotId) {
        ParkingLot foundParkingLot = parkingLotRepository.findById(parkingLotId).orElseThrow(); //TODO
        parkingSlot.setParkingLot(foundParkingLot);
        parkingSlotRepository.save(parkingSlot);

        Queue queue = parkingSlot.getParkingLot().getQueue();

        try {
            QueueSlot queueSlot = queue.getFirstSlot(parkingSlot.getElectricCharge());
            parkingSlot.setUser(queueSlot.getUser());
            queueSlotRepository.delete(queueSlot);
        } catch (Exception e) {
            System.out.println("FINNS LEDIG PARKERINGS PLATS!!!");
        }
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
        ParkingSlot parkingSlot = parkingSlotRepository.findById(id).orElseThrow();
        parkingSlot.getParkingLot().removeParkingSlot(parkingSlot);
        parkingSlotRepository.deleteById(id);
    }

    public void removeUserFromParkingSlot(Long parkingSlotId){
        ParkingSlot parkingSlot = parkingSlotRepository.findById(parkingSlotId).orElseThrow(); // TODO
        parkingSlot.removeUser();
        Queue queue = parkingSlot.getParkingLot().getQueue();

        try {
            QueueSlot queueSlot = queue.getFirstSlot(parkingSlot.getElectricCharge());

            parkingSlot.setUser(queueSlot.getUser());
            queue.removeQueueSlot(queueSlot);
            queueSlotRepository.delete(queueSlot);

            messageSender.addedToParkingSlotMessage(parkingSlot);
            messageSender.sendQueueUpdateMessage(queue);

        } catch (Exception e) {
            System.out.println("FINNS LEDIG PARKERINGS PLATS!!!");
        }
    }
}
