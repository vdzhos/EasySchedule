package com.easyschedule.models;

import com.easyschedule.utils.EntityIdResolver;
import com.fasterxml.jackson.annotation.JsonIdentityInfo;
import com.fasterxml.jackson.annotation.JsonIdentityReference;
import com.fasterxml.jackson.annotation.ObjectIdGenerators;
import lombok.Getter;
import lombok.NoArgsConstructor;
import lombok.Setter;

import javax.persistence.*;
import javax.validation.constraints.NotNull;
import javax.validation.constraints.Pattern;
import java.time.DayOfWeek;
import java.util.Set;
import java.util.SortedSet;
import java.util.TreeSet;

@Entity
@NoArgsConstructor
public class Lesson implements Comparable<Lesson>{

    @Id
    @Getter
    @Setter
    @GeneratedValue(strategy = GenerationType.AUTO)
    private Long id;

    @Getter
    @Setter
    @Column(nullable = false)
    @NotNull(message = "Mandatory field!")
    private Time time;

    @Getter
    @Setter
    @ManyToOne(fetch = FetchType.EAGER)
    @JoinColumn(name = "subject_id", nullable = false)
    @JsonIdentityInfo(
            generator = ObjectIdGenerators.PropertyGenerator.class,
            property = "id",
            scope = Subject.class,
            resolver = EntityIdResolver.class)
    @JsonIdentityReference(alwaysAsId = true)
    @NotNull(message = "Mandatory field!")
    private Subject subject;

    @Getter
    @Setter
    @ManyToOne(fetch = FetchType.EAGER)
    @JoinColumn(name = "teacher_id", nullable = false)
    @JsonIdentityInfo(
            generator = ObjectIdGenerators.PropertyGenerator.class,
            property = "id",
            scope = Teacher.class,
            resolver = EntityIdResolver.class)
    @JsonIdentityReference(alwaysAsId = true)
    @NotNull(message = "Mandatory field!")
    private Teacher teacher;

    @Lob
    @Getter
    @Setter
    @Column(name = "groupValue", nullable = false)
    @NotNull(message = "Mandatory field!")
    private SubjectType group;

    @Getter
    @Setter
    @NotNull(message = "Mandatory field!")
    @Pattern(regexp = "^([1-9][0-9]*(-[1-9][0-9]*)?)(,([1-9][0-9]*(-[1-9][0-9]*)?))*$", message = "Value doesn't match the pattern!")
    @Column(nullable = false)
    private String weeks;

    @Lob
    @Getter
    @Setter
    @Column(nullable = false)
    @NotNull(message = "Mandatory field!")
    private Room room;

    @Getter
    @Setter
    @Column(nullable = false)
    @NotNull(message = "Mandatory field!")
    private DayOfWeek dayOfWeek;

    public Lesson(Time time, Subject subject, Teacher teacher, SubjectType group,
                  String weeks, Room room, DayOfWeek dayOfWeek) {
        this.subject = subject;
        this.time = time;
        this.teacher = teacher;
        this.group = group;
        this.weeks = weeks;
        this.room = room;
        this.dayOfWeek = dayOfWeek;
    }

    public Lesson(Long id, Time time, Subject subject, Teacher teacher, SubjectType group,
                  String weeks, Room room, DayOfWeek dayOfWeek) {
        this.id = id;
        this.subject = subject;
        this.time = time;
        this.teacher = teacher;
        this.group = group;
        this.weeks = weeks;
        this.room = room;
        this.dayOfWeek = dayOfWeek;
    }

    @Override
    public String toString() {
        StringBuilder sb = new StringBuilder();
        sb.append("Lesson{")
                .append("id=").append(id).append(',')
                .append("time=").append(time).append(',')
                .append("subject=").append(subject.getName()).append(',')
                .append("teacher=").append(teacher.getName()).append(',')
                .append("group=").append(group).append(',')
                .append("weeks=").append(weeks).append(',')
                .append("room=").append(room).append(',')
                .append("dayOfWeek=").append(dayOfWeek).append('}');
        return sb.toString();
    }

    @Override
    public int compareTo(Lesson that) {
        if(this.dayOfWeek.getValue()>that.dayOfWeek.getValue()) return 1;
        if(this.dayOfWeek.getValue()<that.dayOfWeek.getValue()) return -1;
        if(this.time.ordinal()>that.time.ordinal()) return 1;
        if(this.time.ordinal()<that.time.ordinal()) return -1;
        if(this.group.getType().ordinal()>that.group.getType().ordinal()) return 1;
        if(this.group.getType().ordinal()<that.group.getType().ordinal()) return -1;
        return 0;
    }

    public Set<Integer> getIntWeeks() {
        SortedSet<Integer> res = new TreeSet<>();
        String[] arr = weeks.split(",");
        for(String ss : arr) {
            if(ss.contains("-")) {
                String[] arr1 = ss.split("-");
                int si = Integer.parseInt(arr1[0]);
                int li = Integer.parseInt(arr1[1]);
                res.add(si);
                res.add(li);
                for(int i = si + 1; i < li; i++)
                    res.add(i);
            } else
                res.add(Integer.parseInt(ss));
        }
        return res;
    }

    public String[] getColumnArray() {
        String[] res = new String[6];
        res[0] = this.time.toString();
        res[1] = this.subject.getName();
        res[2] = this.teacher.getName();
        res[3] = this.group.getType() == SubjectType.SubjectTypeEnum.LECTURE ? "LECTURE" : this.group.getGroup();
        res[4] = this.weeks;
        res[5] = this.room.getType() == Room.RoomType.REMOTELY ? "REMOTELY" : this.room.getRoom();
        return res;
    }


    public enum Time {

        TIME1("8:30-9:50"), TIME2("10:00-11:20"), TIME3("11:40-13:00"), TIME4("13:30-14:50"),
        TIME5("15:00-16:20"), TIME6("16:30-17:50"), TIME7("18:00-19:20");

        private String time;

        Time(String time) {
            this.time = time;
        }

        public String getTime() {
            return time;
        }

        @Override
        public String toString() {
            return time;
        }
    }

}