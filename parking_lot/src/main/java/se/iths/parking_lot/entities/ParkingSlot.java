package se.iths.parking_lot.entities;

import com.fasterxml.jackson.annotation.JsonIgnore;
import se.iths.parking_lot.dtos.ParkingSlotDto;

import javax.persistence.*;
import javax.validation.constraints.NotNull;

@Entity
public class ParkingSlot {

    @Id
    @GeneratedValue(strategy = GenerationType.SEQUENCE)
    private Long id;

    @Column(unique = true)
    private String name;
    private Boolean electricCharge;

    @ManyToOne
    @NotNull
    private ParkingLot parkingLot;

    @ManyToOne
    private User user;

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

    @JsonIgnore
    public ParkingLot getParkingLot() {
        return parkingLot;
    }

    public void setParkingLot(ParkingLot parkingLot) {
        this.parkingLot = parkingLot;
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

    public User getUser() {
        return user;
    }

    public void setUser(User user) {
        this.user = user;
    }

    public void removeUser() {
        this.user = null;
    }

    @Override
    public String toString() {
        return "ParkingSlot{" +
                "id=" + id +
                ", name='" + name + '\'' +
                ", electricCharge=" + electricCharge +
                ", parkingLot=" + parkingLot +
                ", user=" + user +
                '}';
    }
}
