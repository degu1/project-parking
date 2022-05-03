package se.iths.parking_lot.entities;

import com.fasterxml.jackson.annotation.JsonIgnore;
import lombok.Data;

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
    public Boolean addSlotToQueue(QueueSlot queueSlot) {
        queueSlots.add(queueSlot);
        queueSlot.setQueue(this);
        if (queueSlots.size() == 1) {
            return true;
        }
        return false;
    }

    public void removeQueueSlot(QueueSlot queueSlot) {
        this.getQueueSlots().remove(queueSlot);
    }

    public QueueSlot getFirstSlot (Boolean electricCharge) throws Exception {
        return queueSlots.stream()
                .filter(qSlot -> qSlot.getElectricCharge().equals(electricCharge))
                .sorted((qSlot1, qSlot2) -> (int) (qSlot1.getId() - qSlot2.getId()))
                .findFirst()
                .orElseThrow(Exception::new);
    }

    public Long getQueuePosition (QueueSlot queueSlot) {
        return queueSlots.stream()
                .filter(q -> q.getId() <= queueSlot.getId())
                .count();
    }

}
