package com.ascentracoresolutions.equiskill.Getters;

public class Skill {
    private final String name;
    private final int  level;

    public String getName() {
        return name;
    }

    public int getLevel() {
        return level;
    }

    public Skill(String name, int level) {
        this.name = name;
        this.level = level;
    }
}
