package com.ascentracoresolutions.equiskill.Getters;

public class Interns {

    private String title;
    private String companyName;
    private String description;
    private int matchScore;

    public Interns(String title, String companyName, String description, int matchScore) {
        this.title = title;
        this.companyName = companyName;
        this.description = description;
        this.matchScore = matchScore;
    }

    public String getTitle() {
        return title;
    }

    public String getCompanyName() {
        return companyName;
    }

    public String getDescription() {
        return description;
    }

    public int getMatchScore() {
        return matchScore;
    }
}