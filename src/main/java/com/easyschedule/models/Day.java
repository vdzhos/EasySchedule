package com.easyschedule.models;

import lombok.Getter;
import lombok.Setter;

import java.time.DayOfWeek;
import java.util.Arrays;
import java.util.List;
import java.util.stream.Collectors;

public class Day {

    private List<TimeSlot> timeSlots;
    private final static int NUMBER_OF_TIME_SLOTS = 7;
    @Getter
    @Setter
    private DayOfWeek day;
    @Getter
    private int lessonsNumber;



    public Day(List<Lesson> lessons) {
        this.lessonsNumber = lessons.size();
        buildTimeSlots(lessons);
    }

    private void buildTimeSlots(List<Lesson> lessons) {
        List<Lesson>[] timeLessons = new List[NUMBER_OF_TIME_SLOTS];
        Schedule.init(timeLessons);
        lessons.forEach(l -> timeLessons[l.getTime().ordinal()].add(l));
        timeSlots = Arrays.stream(timeLessons).map(TimeSlot::new).collect(Collectors.toList());
        for (int i = 0; i < NUMBER_OF_TIME_SLOTS; i++){
            timeSlots.get(i).setTime(Lesson.Time.values()[i]);
        }
    }

    public List<TimeSlot> getTimeSlots() {
        timeSlots.removeIf(t -> t.getLessons().size()==0);
        return timeSlots;
    }

}
