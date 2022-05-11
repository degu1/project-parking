package se.iths.parking_lot.entities;

import com.fasterxml.jackson.annotation.JsonIgnore;
import lombok.Data;
import se.iths.parking_lot.exceptions.QueueIsEmptyException;

import javax.persistence.*;
import java.io.Serializable;
import java.util.ArrayList;
import java.util.List;

@Entity
@Data
public class Queue implements Serializable {

    @Id
    @GeneratedValue(strategy = GenerationType.SEQUENCE)
    private Long id;

    @OneToMany(mappedBy = "queue", cascade = CascadeType.MERGE, orphanRemoval = true)
    List<QueueSlot> queueSlots = new ArrayList<>();

    @OneToOne(mappedBy = "queue")
    @JsonIgnore
    ParkingLot parkingLot;

    // Method returns a boolean if added queue slot is first in queue.
    public Boolean addSlotToQueue(QueueSlot queueSlot, Boolean electricCharge) {
        queueSlots.add(queueSlot);
        queueSlot.setQueue(this);
        return queueSlots.stream().filter(slot -> slot.getElectricCharge() == electricCharge).count() == 1;
    }

    public void removeQueueSlot(QueueSlot queueSlot) {
        this.getQueueSlots().remove(queueSlot);
    }

    public QueueSlot getFirstSlot(Boolean electricCharge) throws QueueIsEmptyException {
        return queueSlots.stream()
                .filter(qSlot -> qSlot.getElectricCharge().equals(electricCharge)).min((qSlot1, qSlot2) -> (int) (qSlot1.getId() - qSlot2.getId()))
                .orElseThrow(() -> new QueueIsEmptyException("No one in queue with matching criteria"));
    }

    public Long getQueuePosition(QueueSlot queueSlot) {
        return queueSlots.stream()
                .filter(q -> q.getId() <= queueSlot.getId())
                .count();
    }
}
