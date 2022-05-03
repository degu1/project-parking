package se.iths.parking_lot.dtos;

import com.fasterxml.jackson.annotation.JsonIgnoreProperties;

import java.util.Objects;

@JsonIgnoreProperties(ignoreUnknown = true)
public final class AddUserToQueueDto {
    private Long parkingLotId;
    private Boolean electricCharge;

    public AddUserToQueueDto(Long parkingLotId, Boolean electricCharge) {
        this.parkingLotId = parkingLotId;
        this.electricCharge = electricCharge;
    }

    public Long getParkingLotId() {
        return parkingLotId;
    }

    public void setParkingLotId(Long parkingLotId) {
        this.parkingLotId = parkingLotId;
    }

    public Boolean getElectricCharge() {
        return electricCharge;
    }

    public void setElectricCharge(Boolean electricCharge) {
        this.electricCharge = electricCharge;
    }

    @Override
    public boolean equals(Object obj) {
        if (obj == this) return true;
        if (obj == null || obj.getClass() != this.getClass()) return false;
        var that = (AddUserToQueueDto) obj;
        return Objects.equals(this.parkingLotId, that.parkingLotId) &&
                Objects.equals(this.electricCharge, that.electricCharge);
    }

    @Override
    public int hashCode() {
        return Objects.hash(parkingLotId, electricCharge);
    }

    @Override
    public String toString() {
        return "AddUserToQueueDto[" +
                "parkingLotId=" + parkingLotId + ", " +
                "electricCharge=" + electricCharge + ']';
    }

}
