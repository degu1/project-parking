package se.iths.parking_lot.entities;

import lombok.Data;
import se.iths.parking_lot.exceptions.NoEmptyParkingSlotException;

import javax.persistence.*;
import java.io.Serializable;
import java.util.ArrayList;
import java.util.List;

@Entity
@Data
public class ParkingLot implements Serializable {

    @Id
    @GeneratedValue(strategy = GenerationType.SEQUENCE)
    private Long id;

    @Column(unique = true)
    private String name;

    @OneToMany(mappedBy = "parkingLot", cascade = {CascadeType.PERSIST, CascadeType.MERGE}, orphanRemoval = true, fetch = FetchType.EAGER)
    private List<ParkingSlot> parkingSlots = new ArrayList<>();

    @OneToOne(orphanRemoval = true, cascade = CascadeType.ALL)
    private Queue queue;

    public void removeParkingSlot(ParkingSlot parkingSlot) {
        parkingSlots.remove(parkingSlot);
    }

    public ParkingSlot emptyParkingSlot(Boolean electricCharge) throws NoEmptyParkingSlotException {
        return this.parkingSlots.stream()
                .filter(parkingSlot -> parkingSlot.getElectricCharge().equals(electricCharge))
                .filter(parkingSlot -> parkingSlot.getUser() == null)
                .findFirst()
                .orElseThrow(() -> new NoEmptyParkingSlotException("No empty parking slot to criteria in parking lot " + name));
    }
}
