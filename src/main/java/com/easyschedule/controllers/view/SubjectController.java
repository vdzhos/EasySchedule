package com.easyschedule.controllers.view;

import com.easyschedule.models.Specialty;
import com.easyschedule.services.implementations.SubjectServiceImpl;
import com.easyschedule.services.interfaces.SubjectService;
import lombok.RequiredArgsConstructor;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Controller;
import org.springframework.web.bind.annotation.PostMapping;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RequestParam;
import org.springframework.web.servlet.mvc.support.RedirectAttributes;
import org.springframework.web.servlet.view.RedirectView;

import java.util.Set;

@Controller
@RequestMapping("/subject")
@RequiredArgsConstructor
public class SubjectController {

    private static final String REDIRECT_EDIT_PAGE_URL = "/admin";
    private final SubjectService subjectService;

    @Autowired
    public SubjectController(SubjectServiceImpl subjectService) {
        this.subjectService = subjectService;
    }

    @PostMapping("/add")
    public RedirectView addSubject(@RequestParam String name, @RequestParam int quantOfGroups,
                                   @RequestParam Set<Specialty> specialties, RedirectAttributes redir){
        RedirectView redirectView= new RedirectView(REDIRECT_EDIT_PAGE_URL,true);
        String notification = "Subject '"+name+"' has been successfully added!";
        boolean success = true;
        try {
            subjectService.addSubject(name, quantOfGroups, specialties);
        }
        catch (Exception e) {
            success = false;
            notification = e.getMessage();
        }
        redir.addFlashAttribute("showNotification", true);
        redir.addFlashAttribute("success", success);
        redir.addFlashAttribute("notification", notification);
        redir.addFlashAttribute("tab",1);
        return redirectView;
    }

    @PostMapping("/delete")
    public RedirectView deleteSubject(@RequestParam Long id, RedirectAttributes redir) {
        subjectService.deleteSubject(id);
        RedirectView redirectView= new RedirectView(REDIRECT_EDIT_PAGE_URL,true);
        String notification = "Subject has been successfully deleted!";
        redir.addFlashAttribute("showNotification", true);
        redir.addFlashAttribute("success", true);
        redir.addFlashAttribute("notification",notification);
        redir.addFlashAttribute("tab",1);
        return redirectView;
    }

    @PostMapping("/update")
    public RedirectView updateSubject(@RequestParam Long id, @RequestParam String subjName,
                                      @RequestParam int subjQuantOfGroups,
                                      @RequestParam Set<Specialty> subjSpecialties,
                                      RedirectAttributes redir){
        RedirectView redirectView= new RedirectView(REDIRECT_EDIT_PAGE_URL,true);
        String notification = "Subject has been successfully updated!";
        boolean success = true;
        try{
            subjectService.updateSubject(id, subjName, subjQuantOfGroups, subjSpecialties);
        } catch (Exception e) {
            success = false;
            notification = e.getMessage();
        }
        redir.addFlashAttribute("showNotification", true);
        redir.addFlashAttribute("success", success);
        redir.addFlashAttribute("notification",notification);
        redir.addFlashAttribute("tab",1);
        return redirectView;
    }
}
