package se.iths.parking_lot.entities;

import se.iths.parking_lot.dtos.ParkingSlotDto;

import javax.persistence.*;

@Entity
public class ParkingSlot {

    @Id
    @GeneratedValue(strategy = GenerationType.SEQUENCE)
    private Long id;

    @Column(unique = true)
    private String name;
    private Boolean electricCharge;

    public ParkingSlot() {
    }

    public ParkingSlot(Long id, String name, Boolean electricCharge) {
        this.id = id;
        this.name = name;
        this.electricCharge = electricCharge;
    }

    public static ParkingSlot from(ParkingSlotDto parkingSlotDto) {
        return new ParkingSlot(parkingSlotDto.id(), parkingSlotDto.name(), parkingSlotDto.electricCharge());
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

    public Boolean getElectricCharge() {
        return electricCharge;
    }

    public void setElectricCharge(Boolean electricCharge) {
        this.electricCharge = electricCharge;
    }
}
