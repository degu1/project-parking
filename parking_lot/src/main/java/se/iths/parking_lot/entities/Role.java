package se.iths.parking_lot.entities;

import javax.persistence.*;
import javax.validation.constraints.NotEmpty;
import java.util.ArrayList;
import java.util.List;

@Entity
public class Role {

    @Id
    @GeneratedValue(strategy = GenerationType.SEQUENCE)
    private Long id;

    @NotEmpty
    private Type type;

    @ManyToMany
    private List<User> users = new ArrayList<>();

    public Role() {
    }

    public Role(Long id, Type type, List<User> users) {
        this.id = id;
        this.type = type;
        this.users = users;
    }

    public void addUser(User user) {
        users.add(user);
        user.addRole(this);
    }

    public Long getId() {
        return id;
    }

    public void setId(Long id) {
        this.id = id;
    }

    public Type getType() {
        return type;
    }

    public void setType(Type type) {
        this.type = type;
    }

    public List<User> getUsers() {
        return users;
    }

    public void setUsers(List<User> users) {
        this.users = users;
    }
}
