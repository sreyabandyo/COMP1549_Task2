package model;

// Represents a user with an ID and a role.

public class User {

    private final String id;
    private final Role role;

    public User(String id, Role role) {
        this.id = id;
        this.role = role;
    }
    public String getId() {
        return id;
    }
    public Role getRole() {
        return role;
    }

    @Override
    public String toString() {
        return "User{id='" + id + "', role=" + role + "}";
    }
}