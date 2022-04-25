package se.iths.parking_lot.entities;

import com.fasterxml.jackson.annotation.JsonIgnore;
import se.iths.parking_lot.dtos.UserDto;

import javax.persistence.*;
import javax.validation.constraints.NotEmpty;
import java.util.ArrayList;
import java.util.List;
import java.util.Objects;

@Entity
public class User {

    @Id
    @GeneratedValue(strategy = GenerationType.SEQUENCE)
    private Long id;
    @NotEmpty
    private String name;
    @NotEmpty
    @Column(unique = true)
    private String email;

    @OneToMany(mappedBy = "user", cascade = {CascadeType.PERSIST, CascadeType.MERGE}, orphanRemoval = true)
    private List<QueueSlot> queueSlots = new ArrayList<>();

    @OneToMany(mappedBy = "user")
    private List<ParkingSlot> parkingSlots = new ArrayList<>();

    @ManyToMany
    @NotEmpty
    private List<Role> roles = new ArrayList<>();

    public User() {
    }

    public User(Long id, String name, String email) {
        this.id = id;
        this.name = name;
        this.email = email;
    }

    public void addRole(Role role) {
        roles.add(role);
    }

    public List<Role> getRoles() {
        return roles;
    }

    public void setRoles(List<Role> roles) {
        this.roles = roles;
    }

    public static User fromDto(UserDto userDto) {
        return new User(userDto.id(), userDto.name(), userDto.email());
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

    public String getEmail() {
        return email;
    }

    public void setEmail(String email) {
        this.email = email;
    }


    @JsonIgnore
    public List<QueueSlot> getQueueSlots() {
        return queueSlots;
    }

    public void setQueueSlots(List<QueueSlot> queueSlots) {
        this.queueSlots = queueSlots;
    }

    public void removeQueueSlot(QueueSlot queueSlot) {
        this.getQueueSlots().remove(queueSlot);
    }

    public List<ParkingSlot> getParkingSlots() {
        return parkingSlots;
    }

    @JsonIgnore
    public void setParkingSlots(List<ParkingSlot> parkingSlots) {
        this.parkingSlots = parkingSlots;
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
}
