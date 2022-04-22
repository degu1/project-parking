package se.iths.parking_lot.entities;

import com.fasterxml.jackson.annotation.JsonIgnore;
import se.iths.parking_lot.dtos.ParkingSlotDto;
import se.iths.parking_lot.dtos.QueueDto;
import se.iths.parking_lot.dtos.QueueSlotDto;

import javax.persistence.*;
import java.util.List;

@Entity
public class QueueSlot {

    @Id
    @GeneratedValue(strategy = GenerationType.SEQUENCE)
    private Long id;

    private Boolean electricCharger;

    @ManyToOne
    private Queue queue;

    @ManyToOne
    private User user;

    public QueueSlot() {

    }

    public QueueSlot(User user, Boolean electricCharger) {
        this.user = user;
        this.electricCharger = electricCharger;
    }

    public QueueSlot(Long id, Boolean electricCharger) {
        this.id = id;
        this.user = user;
        this.electricCharger = electricCharger;
    }


    public Long getId() {
        return id;
    }

    public void setId(Long id) {
        this.id = id;
    }

    public User getUser() {
        return user;
    }

    public void setUser(User user) {
        this.user = user;
    }

    public Boolean getElectricCharger() {
        return electricCharger;
    }

    public void setElectricCharger(Boolean electricCharger) {
        this.electricCharger = electricCharger;
    }

    @JsonIgnore
    public Queue getQueue() {
        return queue;
    }

    public void setQueue(Queue queue) {
        this.queue = queue;
    }

    public static QueueSlot fromDto(QueueSlotDto queueSlotDto) {
        return new QueueSlot(queueSlotDto.id(), queueSlotDto.electricCharge());
    }

    public static List<QueueSlot> fromDto(List<QueueSlotDto> queueSlotDtos) {
        return queueSlotDtos.stream()
                .map(QueueSlot::fromDto)
                .toList();
    }
}
