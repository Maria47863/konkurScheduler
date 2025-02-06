package org.example;
import org.example.Lesson;
import org.example.OllamaHelper;
import java.util.ArrayList;
import java.util.List;
import java.io.IOException;

public class Scheduler {
    private List<Lesson> lessons;
    private int studyHours;
    private int startHour;
    private int endHour;
    private int currentMinute, currentHour;
    private int totalStudyTime = 0;

    public Scheduler(int startHour, int endHour) {
        this.startHour = startHour;
        this.currentHour = startHour;
        this.endHour = endHour;
        this.currentMinute = 0;
        this.studyHours = endHour - startHour;
        this.lessons = new ArrayList<>();
    }

    public void addLesson(Lesson lesson) {
        lessons.add(lesson);
        totalStudyTime += lesson.getDuration();
    }

    public void createSchedule() {
        int totalMinutes = studyHours * 60;
        int breakTime = 10;
        int allocateMinutes = 0;

        System.out.println("برنامه زمان بندی شده:");
        int currentHour = startHour;
        int currentMinute = 0;
        int availableTime = (endHour - startHour) * 60;

        List<Lesson> reviewLessons = new ArrayList<>();

        for(Lesson lesson : lessons) {
            if (availableTime < lesson.getDuration()) {
                break;
            }
            printTimeSlot(currentHour, currentMinute, lesson.getDuration(), "مطالعه " + lesson.getName());

            updateTime(lesson.getDuration());
            availableTime -= lesson.getDuration();

            reviewLessons.add(lesson);

            allocateMinutes += lesson.getDuration() + breakTime;
            currentMinute += lesson.getDuration() + breakTime;
            currentHour += currentMinute / 60;
            currentMinute %= 60;

            try {
                System.out.println("توصیه های مطالعه:");
                OllamaHelper ollamaHelper = new OllamaHelper();
                String tips = OllamaHelper.getStudyTips(lesson.getName(), lesson.getExamScore());
            } catch (IOException e) {
                System.out.println("خطا در دریافت پیشنهادات");
            }
        }

        System.out.println("مرور دروس:");
        for(Lesson lesson : reviewLessons) {
            int reviewDuration = lesson.getDuration() / 3;
            if(availableTime < reviewDuration)
                break;

            printTimeSlot(currentHour, currentMinute, reviewDuration, "مرور " + lesson.getName());
            updateTime(reviewDuration);
            availableTime -= reviewDuration;

            allocateMinutes += reviewDuration;
            currentMinute += reviewDuration;
            currentHour += currentMinute / 60;
            currentMinute %= 60;
        }

        int remainingTime = totalMinutes - allocateMinutes;
        if (remainingTime > 0) {
            distributeTestTime(remainingTime , lessons , currentHour, currentMinute);
        }
    }

    private void distributeTestTime(int remainingTime, List<Lesson> lessons, int currentHour, int currentMinute) {
        if (remainingTime <= 0)
            return;

        System.out.println("حل تست زماندار:");

        for (Lesson lesson : lessons) {
            int testTime = Math.min(30, remainingTime);
            if(testTime <= 0)
                break;

            printTimeSlot(currentHour, currentMinute, testTime, "تست " + lesson.getName());
            updateTime(testTime);

            if (remainingTime >= 10) {
                printTimeSlot(currentHour, currentMinute, 10, "استراحت");
                updateTime(10);
                remainingTime -= 10;
            }
        }

    }
    private void updateTime(int minutes) {
        currentMinute += minutes;
        currentHour += currentMinute / 60;
        currentMinute %= 60;
    }

    private void printTimeSlot(int startH, int startM, int duration, String task) {
        int endH = startH + (startM + duration) / 60;
        int endM = (startM + duration) % 60;
        System.out.printf("%02d:%02d - %02d:%02d: %s\n", startH, startM, endH, endM, task);
    }
}

