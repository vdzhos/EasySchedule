package com.easyschedule.controllers.view;

import com.easyschedule.models.Subject;
import com.easyschedule.services.interfaces.TeacherService;
import lombok.RequiredArgsConstructor;
import org.springframework.stereotype.Controller;
import org.springframework.web.bind.annotation.PostMapping;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RequestParam;
import org.springframework.web.servlet.mvc.support.RedirectAttributes;
import org.springframework.web.servlet.view.RedirectView;

import java.util.Set;

@Controller
@RequestMapping("/teacher")
@RequiredArgsConstructor
public class TeacherController {

    private final TeacherService teacherService;

    @PostMapping("/add")
    public RedirectView addTeacher(@RequestParam String name,
                                   @RequestParam Set<Subject> subjects,
                                   RedirectAttributes redir) {
        RedirectView redirectView = new RedirectView("/admin", true);
        String notification = "Teacher '" + name + "' has been successfully added!";
        boolean success = true;
        try {
            teacherService.addTeacher(name, subjects);
        } catch (Exception e) {
            success = false;
            notification = e.getMessage();
        }
        redir.addFlashAttribute("showNotification", true);
        redir.addFlashAttribute("success", success);
        redir.addFlashAttribute("notification", notification);
        redir.addFlashAttribute("tab", 2);
        return redirectView;
    }

    @PostMapping("/delete")
    public RedirectView deleteTeacher(@RequestParam Long id, RedirectAttributes redir) throws Exception {
        RedirectView redirectView = new RedirectView("/admin", true);
        String name = teacherService.getTeacherById(id).getName();
        String notification = "Teacher '" + name + "' has been successfully deleted!";
        boolean success = teacherService.deleteTeacher(id);
        if (!success) {
            notification = "Teacher has not been deleted!";
        }
        redir.addFlashAttribute("showNotification", true);
        redir.addFlashAttribute("success", success);
        redir.addFlashAttribute("notification", notification);
        redir.addFlashAttribute("tab", 2);
        return redirectView;
    }

    @PostMapping("/update")
    public RedirectView updateTeacher(@RequestParam Long id, @RequestParam String teacherName,
                                      @RequestParam Set<Subject> teacherSubjects,
                                      RedirectAttributes redir) {
        RedirectView redirectView = new RedirectView("/admin", true);
        String notification = "Teacher has been successfully updated!";
        boolean success = true;
        try {
            teacherService.updateTeacher(id, teacherName, teacherSubjects);
        } catch (Exception e) {
            success = false;
            notification = e.getMessage();
        }
        redir.addFlashAttribute("showNotification", true);
        redir.addFlashAttribute("success", success);
        redir.addFlashAttribute("notification", notification);
        redir.addFlashAttribute("tab", 2);
        return redirectView;
    }
}
