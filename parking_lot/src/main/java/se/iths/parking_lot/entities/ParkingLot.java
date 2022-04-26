package se.iths.parking_lot.entities;

import lombok.Data;
import se.iths.parking_lot.dtos.ParkingLotDto;

import javax.persistence.*;
import java.util.ArrayList;
import java.util.HashSet;
import java.util.List;
import java.util.Set;

@Entity
@Data
public class ParkingLot {

    @Id
    @GeneratedValue(strategy = GenerationType.SEQUENCE)
    private Long id;

    @Column(unique = true)
    private String name;

    @OneToMany(mappedBy = "parkingLot",cascade = {CascadeType.PERSIST, CascadeType.MERGE}, orphanRemoval = true)
    private List<ParkingSlot> parkingSlots = new ArrayList<>();

    @OneToOne(orphanRemoval = true, cascade = CascadeType.PERSIST)
    private Queue queue;

    public ParkingLot() {
    }

    public ParkingLot(Long id, String name) {
        this.id = id;
        this.name = name;
    }

    public void removeParkingSlot(ParkingSlot parkingSlot) {
        parkingSlots.remove(parkingSlot);
    }

    public ParkingSlot emptyParkingSlot (Boolean electricCharge) throws Exception {
        return this.parkingSlots.stream()
                .filter(parkingSlot -> parkingSlot.getElectricCharge().equals(electricCharge))
                .filter(parkingSlot -> parkingSlot.getUser() == null)
                .findFirst()
                .orElseThrow(Exception::new); // TODO
    }
}
