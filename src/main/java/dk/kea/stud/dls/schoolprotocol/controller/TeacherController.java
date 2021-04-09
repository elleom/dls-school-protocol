package dk.kea.stud.dls.schoolprotocol.controller;

import dk.kea.stud.dls.schoolprotocol.model.Subject;
import dk.kea.stud.dls.schoolprotocol.repository.SubjectRepository;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.ui.Model;
import org.springframework.web.bind.annotation.RequestMapping;

import javax.servlet.http.HttpServletRequest;

public class TeacherController {

    @Autowired
    SubjectRepository subjectRepository;

    @RequestMapping({"/teacher/", "/teacher/dashboard", "/teacher/dashboard.html"})
    public String getDashBoard(HttpServletRequest request, Model model){ //add model to load repos

        //tryout OK
        Iterable<Subject> subjects = subjectRepository.findAllByTeacher(1L);

        model.addAttribute("subjects", subjects);
        return "index";
    }

}
