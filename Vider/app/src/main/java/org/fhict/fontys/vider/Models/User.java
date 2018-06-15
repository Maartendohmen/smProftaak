package org.fhict.fontys.vider.Models;

import java.io.Serializable;

public class User implements Serializable {
    private String uid;
    private String name;
    private Role role;
    private String email;
    private String residence;


    public User(String uid, String name, Role role, String email, String residence) {
        this.uid = uid;
        this.name = name;
        this.role = role;
        this.email = email;
        this.residence = residence;
    }

    public User(){

    }

    public String getUid() {
        return uid;
    }

    public void setUid(String uid) {
        this.uid = uid;
    }

    public String getName() {
        return name;
    }

    public void setName(String name) {
        this.name = name;
    }

    public Role getRole() {
        return role;
    }

    public void setRole(Role role) {
        this.role = role;
    }

    public String getEmail() {
        return email;
    }

    public void setEmail(String email) {
        this.email = email;
    }

    public String getResidence() {
        return residence;
    }

    public void setResidence(String residence) {
        this.residence = residence;
    }

    @Override
    public String toString(){
        return name + ", " + residence;
    }
}
