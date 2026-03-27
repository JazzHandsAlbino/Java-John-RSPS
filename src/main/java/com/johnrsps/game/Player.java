package com.johnrsps.game;

public class Player {
    private final String username;
    private int rights;

    public Player(String username) {
        this.username = username;
        this.rights = 0; // regular user
    }

    public String getUsername() { return username; }
    public int getRights() { return rights; }
    public void setRights(int rights) { this.rights = rights; }
}