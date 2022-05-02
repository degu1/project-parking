package se.iths.parking_lot.entities;

import com.fasterxml.jackson.annotation.JsonIgnore;
import lombok.Data;

import javax.persistence.*;
import javax.validation.constraints.NotEmpty;
import javax.validation.constraints.NotNull;
import java.io.Serializable;
import java.util.ArrayList;
import java.util.List;
import java.util.Objects;

@Entity
@Data
public class User implements Serializable {

    @Id
    @GeneratedValue(strategy = GenerationType.SEQUENCE)
    private Long id;

    @NotEmpty
    private String name;

    @NotEmpty
    @Column(unique = true)
    private String email;

    @NotNull
    private Boolean emailNotification = false;

    @JsonIgnore
    @OneToMany(mappedBy = "user", cascade = {CascadeType.PERSIST, CascadeType.MERGE}, orphanRemoval = true)
    private List<QueueSlot> queueSlots = new ArrayList<>();

    @JsonIgnore
    @OneToMany(mappedBy = "user")
    private List<ParkingSlot> parkingSlots = new ArrayList<>();

    @NotEmpty
    @ManyToMany
    private List<Role> roles = new ArrayList<>();

    public void addRole(Role role) {
        roles.add(role);
    }

    public void removeQueueSlot(QueueSlot queueSlot) {
        this.getQueueSlots().remove(queueSlot);
    }

    @Override
    public boolean equals(Object o) {
        if (this == o) return true;
        if (o == null || getClass() != o.getClass()) return false;
        User user = (User) o;
        return Objects.equals(id, user.id) && Objects.equals(name, user.name) && Objects.equals(email, user.email) && Objects.equals(queueSlots, user.queueSlots);
    }

    @Override
    public int hashCode() {
        return Objects.hash(id, name, email, queueSlots);
    }

    @Override
    public String toString() {
        return "User{" +
                "id=" + id +
                ", name='" + name + '\'' +
                ", email='" + email + '\'' +
                ", queueSlots=" + queueSlots +
                '}';
    }
}
