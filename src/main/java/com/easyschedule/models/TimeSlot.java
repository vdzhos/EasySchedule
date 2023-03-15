package com.easyschedule.models;

import lombok.Getter;
import lombok.Setter;

import java.util.List;

public class TimeSlot {
    @Getter
    @Setter
    private List<Lesson> lessons;
    @Getter
    @Setter
    private Lesson.Time time;

    public TimeSlot(List<Lesson> lessons) {
        this.lessons = lessons;
    }

}
