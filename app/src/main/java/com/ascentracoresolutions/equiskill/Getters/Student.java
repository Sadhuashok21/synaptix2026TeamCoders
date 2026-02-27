package com.ascentracoresolutions.equiskill.Getters;

public class Student {

    private String name;
    private String score;
    private String student_id;

    public Student(String name, String score, String student_id) {
        this.name = name;
        this.score = score;
        this.student_id = student_id;
    }

    public String getName() {
        return name;
    }

    public String getScore() {
        return score;
    }

    public String getStudentId() {
        return student_id;
    }
}
