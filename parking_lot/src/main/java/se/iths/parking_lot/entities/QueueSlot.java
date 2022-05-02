package se.iths.parking_lot.entities;

import com.fasterxml.jackson.annotation.JsonIgnore;
import lombok.Data;

import javax.persistence.*;
import java.io.Serializable;

@Entity
@Data
public class QueueSlot implements Serializable {

    @Id
    @GeneratedValue(strategy = GenerationType.SEQUENCE)
    private Long id;

    private Boolean electricCharge;

    @JsonIgnore
    @ManyToOne
    private Queue queue;

    @ManyToOne
    private User user;

    public QueueSlot() {
    }

    public QueueSlot(User user, Boolean electricCharge) {
        this.user = user;
        this.electricCharge = electricCharge;
    }
}
