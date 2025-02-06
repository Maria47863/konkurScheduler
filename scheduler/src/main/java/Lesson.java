package org.example;

public class Lesson {
    private String name;
    private int duration;
    private int examScore;

    private static final int MIN_TIME = 30;
    private static final int MAX_TIME = 120;

    public Lesson(String name, int examScore) {
        this.name = name;
        this.examScore = examScore;
        this.duration = calculateStudyTime(examScore);
    }

    private int calculateStudyTime(int score) {
        return (int) (MIN_TIME + ((MAX_TIME - MIN_TIME - (MAX_TIME - MIN_TIME) * score / 100)));
    }

    public String getName() {
        return name;
    }

    public int getDuration() {
        return duration;
    }

    public int getExamScore() {
        return examScore;
    }
}





