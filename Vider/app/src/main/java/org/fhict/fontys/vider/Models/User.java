package org.fhict.fontys.vider.Models;

public class User {
    private String uid;
    private String userName;
    private Role role;
    private String woonplaats;

    public User(String uid, String userName, Role role) {
        this.uid = uid;
        this.userName = userName;
        this.role = role;
    }

    public String getWoonplaats() {
        return woonplaats;
    }

    public void setWoonplaats(String woonplaats) {
        this.woonplaats = woonplaats;
    }

    public String getUid() {
        return uid;
    }

    public void setUid(String uid) {
        this.uid = uid;
    }

    public String getUserName() {
        return userName;
    }

    public void setUserName(String userName) {
        this.userName = userName;
    }

    public Role getRole() {
        return role;
    }

    public void setRole(Role role) {
        this.role = role;
    }
}
