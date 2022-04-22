package se.iths.parking_lot.entities;

import se.iths.parking_lot.dtos.QueueDto;

import javax.persistence.*;
import java.util.ArrayList;
import java.util.Comparator;
import java.util.List;

@Entity
public class Queue {

    @Id
    @GeneratedValue(strategy = GenerationType.SEQUENCE)
    private Long id;

    @OneToMany(mappedBy = "queue", cascade = CascadeType.MERGE)
    List<QueueSlot> queueSlots = new ArrayList<>();

    public Queue(Long id, List<QueueSlot> queueSlots) {
        this.id = id;
        this.queueSlots = queueSlots;
    }

    public Queue() {

    }

    public static Queue fromDto(QueueDto queueDto) {
        return new Queue(queueDto.id(), QueueSlot.fromDto(queueDto.queueSlots()));
    }

    public Long getId() {
        return id;
    }

    public void setId(Long id) {
        this.id = id;
    }

    // Method returns a boolean if added queue slot is first in queue.
    public Boolean addSlotToQueue(QueueSlot queueSlot) {
        queueSlots.add(queueSlot);
        queueSlot.setQueue(this);
        if(queueSlots.size() == 1) {
            return true;
        }
        return false;
    }

    public void removeQueueSlot(QueueSlot queueSlot) {
        queueSlots.remove(queueSlot);
    }

    public List<QueueSlot> getQueueSlots() {
        return queueSlots;
    }

    public void setQueueSlots(List<QueueSlot> queueSlots) {
        this.queueSlots = queueSlots;
    }

    public QueueSlot getFirstSlot (Boolean electricCharge) throws Exception {
        return queueSlots.stream()
                .filter(qSlot -> qSlot.getElectricCharger().equals(electricCharge))
                .sorted((qSlot1, qSlot2) -> (int) (qSlot1.getId() - qSlot2.getId()))
                .findFirst()
                .orElseThrow(Exception::new);
    }
}
