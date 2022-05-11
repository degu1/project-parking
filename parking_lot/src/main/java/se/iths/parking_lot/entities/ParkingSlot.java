package se.iths.parking_lot.entities;

import com.fasterxml.jackson.annotation.JsonIgnore;
import lombok.Data;

import javax.persistence.*;
import javax.validation.constraints.NotNull;
import java.io.Serial;
import java.io.Serializable;

@Entity
@Data
public class ParkingSlot implements Serializable {


    @Serial
    private static final long serialVersionUID = 1L;

    @Id
    @GeneratedValue(strategy = GenerationType.SEQUENCE)
    private Long id;

    private String name;
    private Boolean electricCharge;

    @ManyToOne
    @NotNull
    @JsonIgnore
    private ParkingLot parkingLot;

    @ManyToOne
    private User user;

    public void removeUser() {
        this.user = null;
    }

    @Override
    public String toString() {
        return "ParkingSlot{" +
                "id=" + id +
                ", name='" + name + '\'' +
                ", electricCharge=" + electricCharge +
                '}';
    }
}
