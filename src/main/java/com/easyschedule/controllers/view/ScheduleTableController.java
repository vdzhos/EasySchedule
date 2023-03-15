package com.easyschedule.controllers.view;

import com.easyschedule.models.Lesson;
import com.easyschedule.models.Schedule;
import com.easyschedule.models.Subject;
import com.easyschedule.models.Teacher;
import com.easyschedule.services.interfaces.ScheduleService;
import com.easyschedule.services.interfaces.SpecialtyService;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.beans.factory.annotation.Value;
import org.springframework.stereotype.Controller;
import org.springframework.ui.Model;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RequestParam;

import java.util.List;
import java.util.Set;

@Controller
@RequestMapping("/view")
public class ScheduleTableController {

    @Autowired
    private ScheduleService scheduleService;

    @Autowired
    private SpecialtyService specialtyService;

    private Iterable<Subject> subjects;

    private Iterable<Integer> weeks;

    private Set<String> rooms;

    private List<Lesson> lessons;

    private Set<Teacher> teachers;

    private Long teacherId;

    private String entityName;

    @Value("${spring.application.name}")
    private String appName;

    @GetMapping("/specialty")
    public String getSpecialtySchedule(@RequestParam Long specialtyId, Model model) throws Throwable {
        this.teacherId = null;
        initContainers(false, specialtyId, model);
        this.teachers = this.scheduleService.getTeachersFromSubjects(subjects);
        model.addAttribute("forSpecialty", true);
        model.addAttribute("teachers", teachers);
        return "scheduleTablePage";
    }

    @GetMapping("/teacher")
    public String getTeacherSchedule(@RequestParam Long teacherId, Model model) throws Throwable {
        this.teacherId = teacherId;
        this.teachers = null;
        initContainers(true, teacherId, model);
        model.addAttribute("forSpecialty", false);
        return "scheduleTablePage";
    }

    private void initContainers(boolean forTeacher, Long id, Model model) throws Throwable {
        if(forTeacher) {
            entityName = this.scheduleService.getTeacher(id).getName();
            subjects = this.scheduleService.getTeacherSubjects(id);
            lessons = this.scheduleService.getTeacherLessons(id);
        } else {
            entityName = this.scheduleService.getSpecialty(id).toString();
            subjects = specialtyService.getSpecialtySubjects(id);
            lessons = specialtyService.getSpecialtyLessons(id);
        }
        weeks = this.scheduleService.getSubjectLessonsWeeks(subjects);

        rooms = this.scheduleService.getLessonsRooms(lessons);
        model.addAttribute("appName",appName);
        model.addAttribute("entityName", entityName);
        model.addAttribute("subjects", subjects);
        model.addAttribute("weeks", weeks);
        model.addAttribute("rooms", rooms);
        model.addAttribute("schedule",new Schedule(lessons));
    }
}