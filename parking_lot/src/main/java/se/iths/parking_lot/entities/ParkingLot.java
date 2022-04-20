package se.iths.parking_lot.entities;

import se.iths.parking_lot.dtos.ParkingLotDto;

import javax.persistence.*;

@Entity
public class ParkingLot {

    @Id
    @GeneratedValue(strategy = GenerationType.SEQUENCE)
    private Long id;

    @Column(unique = true)
    private String name;

    public ParkingLot() {
    }

    public ParkingLot(Long id, String name) {
        this.id = id;
        this.name = name;
    }

    public static ParkingLot from(ParkingLotDto parkingLotDto) {
        return new ParkingLot(parkingLotDto.id(), parkingLotDto.name());
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

    @Override
    public String toString() {
        return "ParkingLot{" +
                "id=" + id +
                ", name='" + name + '\'' +
                '}';
    }
}
