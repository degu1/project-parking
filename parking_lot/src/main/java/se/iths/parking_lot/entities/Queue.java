package se.iths.parking_lot.entities;

import lombok.Data;

import javax.persistence.*;
import java.util.ArrayList;
import java.util.List;

@Entity
@Data
public class Queue {

    @Id
    @GeneratedValue(strategy = GenerationType.SEQUENCE)
    private Long id;

    @OneToMany(mappedBy = "queue", cascade = CascadeType.MERGE)
    List<QueueSlot> queueSlots = new ArrayList<>();

    // Method returns a boolean if added queue slot is first in queue.
    public Boolean addSlotToQueue(QueueSlot queueSlot) {
        queueSlots.add(queueSlot);
        queueSlot.setQueue(this);
        if(queueSlots.size() == 1) {
            return true;
        }
        return false;
    }

    public QueueSlot getFirstSlot (Boolean electricCharge) throws Exception {
        return queueSlots.stream()
                .filter(qSlot -> qSlot.getElectricCharge().equals(electricCharge))
                .sorted((qSlot1, qSlot2) -> (int) (qSlot1.getId() - qSlot2.getId()))
                .findFirst()
                .orElseThrow(Exception::new);
    }
}
