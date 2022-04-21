package com.electrogrid.entity;

/**
 * Created By lonewol7f
 * Date: 4/5/2022
 */
public class User {

    private int id;
    private String firstName;
    private String lastName;
    private String email;
    private String role;
    private String password;

    public User() {
    }

    public User(int id, String role, String email, String password, String firstName, String lastName) {
        this.id = id;
        this.role = role;
        this.email = email;
        this.password = password;
        this.firstName = firstName;
        this.lastName = lastName;
    }

    public int getId() {
        return id;
    }

    public void setId(int id) {
        this.id = id;
    }

    public String getFirstName() {
        return firstName;
    }

    public void setFirstName(String firstName) {
        this.firstName = firstName;
    }

    public String getLastName() {
        return lastName;
    }

    public void setLastName(String lastName) {
        this.lastName = lastName;
    }

    public String getEmail() {
        return email;
    }

    public void setEmail(String email) {
        this.email = email;
    }

    public String getRole() {
        return role;
    }

    public void setRole(String role) {
        this.role = role;
    }

    public String getPassword() {
        return password;
    }

    public void setPassword(String password) {
        this.password = password;
    }
}
