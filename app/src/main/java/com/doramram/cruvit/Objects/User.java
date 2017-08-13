package com.doramram.cruvit.Objects;


public class User {

    private String firebaseId;
    private String username;
    private String email;
    private String phone;

    public User() {
        // Default constructor required for calls to DataSnapshot.getValue(User.class)
    }

    public User(String username, String email) {
        this.username = username;
        this.email = email;
    }

    public User(String firebaseId, String username, String email) {
        this.username = username;
        this.email = email;
        this.firebaseId = firebaseId;
    }

    public User(String firebaseId, String username, String email, String phone) {
        this.firebaseId = firebaseId;
        this.username = username;
        this.email = email;
        this.phone = phone;
    }

    public String getUsername() {
        return username;
    }

    public void setUsername(String username) {
        this.username = username;
    }

    public String getEmail() {
        return email;
    }

    public void setEmail(String email) {
        this.email = email;
    }

    public String getPhone() {
        return phone;
    }

    public void setPhone(String phone) {
        this.phone = phone;
    }

    public String getFirebaseId() {
        return firebaseId;
    }

    public void setFirebaseId(String firebaseId) {
        this.firebaseId = firebaseId;
    }
}
