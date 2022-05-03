package se.iths.parking_lot.entities;

import javax.persistence.*;
import java.io.Serializable;
import java.util.ArrayList;
import java.util.List;

@Entity

public class ParkingLot implements Serializable {

    @Id
    @GeneratedValue(strategy = GenerationType.SEQUENCE)
    private Long id;

    @Column(unique = true)
    private String name;

    @OneToMany(mappedBy = "parkingLot", cascade = {CascadeType.PERSIST, CascadeType.MERGE}, orphanRemoval = true, fetch = FetchType.EAGER)
    private List<ParkingSlot> parkingSlots = new ArrayList<>();

    @OneToOne(orphanRemoval = true, cascade = CascadeType.PERSIST)
    private Queue queue;

    public void removeParkingSlot(ParkingSlot parkingSlot) {
        parkingSlots.remove(parkingSlot);
    }

    public ParkingSlot emptyParkingSlot(Boolean electricCharge) throws Exception {
        return this.parkingSlots.stream()
                .filter(parkingSlot -> parkingSlot.getElectricCharge().equals(electricCharge))
                .filter(parkingSlot -> parkingSlot.getUser() == null)
                .findFirst()
                .orElseThrow(Exception::new); // TODO
    }

    public ParkingLot() {
    }

    public ParkingLot(Long id, String name, List<ParkingSlot> parkingSlots, Queue queue) {
        this.id = id;
        this.name = name;
        this.parkingSlots = parkingSlots;
        this.queue = queue;
    }

    public Long getId() {
        return id;
    }

    public void setId(Long id) {
        this.id = id;
    }

    public String getName() {
        return name;
    }

    public void setName(String name) {
        this.name = name;
    }

    public List<ParkingSlot> getParkingSlots() {
        return parkingSlots;
    }

    public void setParkingSlots(List<ParkingSlot> parkingSlots) {
        this.parkingSlots = parkingSlots;
    }

    public Queue getQueue() {
        return queue;
    }

    public void setQueue(Queue queue) {
        this.queue = queue;
    }
}
