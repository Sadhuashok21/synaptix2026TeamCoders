package com.ascentracoresolutions.equiskill.Getters;

public class Student {

    private final String name, score, student_id;


    public String getName() {
        return name;
    }

    public String getScore() {
        return score;
    }

    public String getStudent_id() {
        return student_id;
    }

    public Student(String name, String score, String studentId) {
        this.name = name;
        this.score = score;
        student_id = studentId;
    }
}
