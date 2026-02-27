package com.ascentracoresolutions.equiskill.Getters;

public class Interns {

    private String title;
    private String companyName;
    private final String description, intern_id;
    private int matchScore;

    public String getIntern_id() {
        return intern_id;
    }

    public Interns(String title, String companyName, String description, String internId, int matchScore) {
        this.title = title;
        this.companyName = companyName;
        this.description = description;
        intern_id = internId;
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