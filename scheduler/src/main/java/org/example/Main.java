package org.example;
import org.example.Scheduler;

import java.util.Scanner;

public class Main {
    public static void main(String[] args) {
        Scanner scanner = new Scanner(System.in);

        System.out.print("ساعت شروع مطالعه: ");
        int startHour = scanner.nextInt();

        System.out.print("ساعت پایان مطالعه: ");
        int endHour = scanner.nextInt();

        Scheduler scheduler = new Scheduler(startHour, endHour);

        System.out.print("تعداد دروس: ");
        int lessonCount = scanner.nextInt();
        scanner.nextLine();

        for (int i = 0; i < lessonCount; i++) {
            System.out.print("نام درس " + (i + 1) + ": ");
            String name = scanner.nextLine();

            System.out.print("درصد کسب شده در آزمون آزمایشی: ");
            int examScore = scanner.nextInt();
            scanner.nextLine();

            scheduler.addLesson(new org.example.Lesson(name, examScore));
        }
        scheduler.createSchedule();
    }
}