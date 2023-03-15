package com.easyschedule.controllers.view;

import com.easyschedule.models.Lesson;
import com.easyschedule.models.Schedule;
import com.easyschedule.services.interfaces.LessonService;
import com.easyschedule.services.interfaces.SpecialtyService;
import com.easyschedule.services.interfaces.SubjectService;
import com.easyschedule.services.interfaces.TeacherService;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.beans.factory.annotation.Value;
import org.springframework.security.access.prepost.PreAuthorize;
import org.springframework.security.core.Authentication;
import org.springframework.security.core.authority.SimpleGrantedAuthority;
import org.springframework.stereotype.Controller;
import org.springframework.ui.Model;
import org.springframework.web.bind.annotation.GetMapping;

import java.util.Collections;
import java.util.List;

@Controller
public class MainController {

    @Autowired
    private SpecialtyService specialtyService;
    @Autowired
    private SubjectService subjectService;
    @Autowired
    private LessonService lessonService;
    @Autowired
    private TeacherService teacherService;

    @Value("${spring.application.name}")
    private String appName;

    @PreAuthorize("hasRole('ADMIN')")
    @GetMapping("/admin")
    public String getAllAdmin(Model model){
        model.addAttribute("appName",appName);
        model.addAttribute("specialties",specialtyService.getAll());
        model.addAttribute("subjects",subjectService.getAll());
        model.addAttribute("teachers", teacherService.getAll());
        if(model.getAttribute("tab") == null){
            model.addAttribute("tab",0);
        }
        if(model.getAttribute("schedule") == null){
            model.addAttribute("schedule", new Schedule(Collections.emptyList()));
        }
        if(model.getAttribute("lessonSpec") == null){
            model.addAttribute("lessonSpec", -1);
        }

        return "editSchedule";
    }

    @PreAuthorize("hasAnyRole('REGULAR', 'ADMIN')")
    @GetMapping
    public String showAllSchedules(Model model, Authentication authentication){
        model.addAttribute("appName",appName);
        model.addAttribute("specialties",specialtyService.getAll());
        boolean adminLoggedIn = false;
        if(authentication.getAuthorities().contains(new SimpleGrantedAuthority("ROLE_ADMIN"))) {
            adminLoggedIn = true;
        }
        model.addAttribute("adminLoggedIn",adminLoggedIn);
        model.addAttribute("lessons",(List<Lesson>)lessonService.getAll());
        model.addAttribute("teachers", teacherService.getAll());
        return "mainPage";
    }

}
