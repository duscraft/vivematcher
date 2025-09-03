package com.duscraft.vivematcher.web.user;

public class CreateUserRequest {
    private String displayName;
    private String hero;
    private String rank;

    public String getDisplayName() {
        return this.displayName;
    }

    public void setDisplayName(String displayName) {
        this.displayName = displayName;
    }

    public String getHero() {
        return this.hero;
    }

    public void setHero(String hero) {
        this.hero = hero;
    }

    public String getRank() {
        return this.rank;
    }

    public void setRank(String rank) {
        this.rank = rank;
    }
}
