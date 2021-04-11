package dk.kea.stud.dls.schoolprotocol.controller;

import dk.kea.stud.dls.schoolprotocol.model.Student;
import dk.kea.stud.dls.schoolprotocol.model.Subject;
import dk.kea.stud.dls.schoolprotocol.repository.StudentRepository;
import dk.kea.stud.dls.schoolprotocol.repository.SubjectRepository;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.data.repository.query.Param;
import org.springframework.stereotype.Controller;
import org.springframework.ui.Model;
import org.springframework.web.bind.annotation.RequestMapping;

import javax.servlet.http.HttpServletRequest;

@Controller
@RequestMapping("/api")
public class StudentController {

    @Autowired
    SubjectRepository subjectRepository;
    @Autowired
    StudentRepository studentRepository;

    @RequestMapping({"/student/dashboard", "/student/dashboard.html"})
    public String getDashBoard(@Param("id") Long id, HttpServletRequest request, Model model){ //add model to load repos
        //tryout OK
        Iterable<Subject> subjects = subjectRepository.findAllByTeacher(id);
        model.addAttribute("subjects", subjects);
        return "student_dashboard";
    }

    @RequestMapping({"/student/showAll", "/student/showAll.html"})
    public String getDashBoard(Model model){ //add model to load repos

        Student students = studentRepository.findById(1L).get();
        model.addAttribute("students", students);
        return "students_list";
    }


}
