package se.iths.parking_lot.dtos;

import se.iths.parking_lot.entities.ParkingLot;

import java.util.Objects;

public class ParkingLotDto {
    private final Long id;
    private final String name;

    public ParkingLotDto(Long id, String name) {
        this.id = id;
        this.name = name;
    }

    public static ParkingLotDto from(ParkingLot parkingLot) {
        return new ParkingLotDto(parkingLot.getId(), parkingLot.getName());
    }

    public ParkingLot toParkingLot() {
        return ParkingLot.from(this);
    }

    public Long id() {
        return id;
    }

    public String name() {
        return name;
    }

    @Override
    public boolean equals(Object obj) {
        if (obj == this) return true;
        if (obj == null || obj.getClass() != this.getClass()) return false;
        var that = (ParkingLotDto) obj;
        return Objects.equals(this.id, that.id) &&
                Objects.equals(this.name, that.name);
    }

    @Override
    public int hashCode() {
        return Objects.hash(id, name);
    }

    @Override
    public String toString() {
        return "Dto[" +
                "id=" + id + ", " +
                "name=" + name + ']';
    }

}
