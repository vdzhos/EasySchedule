package com.easyschedule.controllers.view;

import com.easyschedule.services.interfaces.SpecialtyService;
import lombok.RequiredArgsConstructor;
import org.springframework.stereotype.Controller;
import org.springframework.web.bind.annotation.PostMapping;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RequestParam;
import org.springframework.web.servlet.mvc.support.RedirectAttributes;
import org.springframework.web.servlet.view.RedirectView;

@Controller
@RequestMapping("/specialty")
@RequiredArgsConstructor
public class SpecialtyController {

    private static final String REDIRECT_EDIT_PAGE_URL = "/admin";
    private final SpecialtyService specialtyService;

    @PostMapping("/add")
    public RedirectView addSpecialty(@RequestParam String name, @RequestParam int year, RedirectAttributes redir){
        RedirectView redirectView = new RedirectView(REDIRECT_EDIT_PAGE_URL,true);
        String notification = "Specialty '"+name+" - "+year+"' has been successfully added!";
        boolean success = true;
        try{
            specialtyService.addSpecialty(name, year);
        } catch (Exception e) {
            success = false;
            notification = e.getMessage();
        }
        redir.addFlashAttribute("showNotification", true);
        redir.addFlashAttribute("success", success);
        redir.addFlashAttribute("notification",notification);
        redir.addFlashAttribute("tab",0);
        return redirectView;
    }

    @PostMapping("/delete")
    public RedirectView deleteSpecialty(@RequestParam Long id, RedirectAttributes redir) {
        specialtyService.deleteSpecialty(id);
        RedirectView redirectView = new RedirectView(REDIRECT_EDIT_PAGE_URL,true);
        String notification = "Specialty has been successfully deleted!\nAll subjects that had been taught only on this specialty have been deleted too!";
        redir.addFlashAttribute("showNotification", true);
        redir.addFlashAttribute("success", true);
        redir.addFlashAttribute("notification",notification);
        redir.addFlashAttribute("tab",0);
        return redirectView;
    }

    @PostMapping("/update")
    public RedirectView updateSpecialty(@RequestParam Long id, @RequestParam String specName,
                                        @RequestParam int specYear, RedirectAttributes redir){
        RedirectView redirectView = new RedirectView(REDIRECT_EDIT_PAGE_URL,true);
        String notification = "Specialty has been successfully updated to '" + specName+" - " + specYear + "'!";
        boolean success = true;
        try{
            specialtyService.updateSpecialty(id, specName, specYear);
        } catch (Exception e) {
            success = false;
            notification = e.getMessage();
        }
        redir.addFlashAttribute("showNotification", true);
        redir.addFlashAttribute("success", success);
        redir.addFlashAttribute("notification",notification);
        redir.addFlashAttribute("tab",0);
        return redirectView;
    }
}