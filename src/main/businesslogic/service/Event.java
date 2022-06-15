package main.businesslogic.service;

import main.businesslogic.user.User;

import java.util.Optional;

public class Event {
    private User chefAssigned;
    private String info;

    public Event(String info, Optional<User> chefAssigned) {
        this.info = info;
        this.chefAssigned = chefAssigned.orElse(null);
    }

    public String getInfo() {
        return info;
    }

    public void setInfo(String info) {
        this.info = info;
    }

    public User getChefAssigned() {
        return chefAssigned;
    }

    public void setChefAssigned(User chefAssigned) {
        this.chefAssigned = chefAssigned;
    }
}
