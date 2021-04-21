package dk.kea.stud.dls.schoolprotocol.controller;

import dk.kea.stud.dls.schoolprotocol.model.Lesson;
import dk.kea.stud.dls.schoolprotocol.model.Subject;
import dk.kea.stud.dls.schoolprotocol.model.Teacher;
import dk.kea.stud.dls.schoolprotocol.repository.LessonRepository;
import dk.kea.stud.dls.schoolprotocol.repository.StudentRepository;
import dk.kea.stud.dls.schoolprotocol.repository.SubjectRepository;
import dk.kea.stud.dls.schoolprotocol.repository.TeacherRepository;
import dk.kea.stud.dls.schoolprotocol.service.RequestService;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.data.repository.query.Param;
import org.springframework.stereotype.Controller;
import org.springframework.ui.Model;
import org.springframework.web.bind.annotation.PostMapping;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RequestParam;
import org.springframework.web.bind.annotation.ResponseBody;

import javax.servlet.http.HttpServletRequest;

@Controller
@RequestMapping("/api")
public class TeacherController {

    @Autowired
    RequestService requestService;
    @Autowired
    SubjectRepository subjectRepository;
    @Autowired
    TeacherRepository teacherRepository;
    @Autowired
    LessonRepository lessonRepository;
    @Autowired
    StudentRepository studentRepository;


    @RequestMapping({"/teacher/dashboard", "/teacher/dashboard.html"})
    public String getDashBoard(@Param("id") Long id, HttpServletRequest request, Model model){ //add model to load repos
        //tryout OK
        String userName = request.getRemoteUser();
        Teacher teacher = teacherRepository.findByUserName(userName);
        Iterable<Subject> subjects = subjectRepository.findAllByTeacher(id);
        model.addAttribute("subjects", subjects);
        return "teacher_dashboard";
    }

    @RequestMapping({"/teacher/subjectDetails", "/teacher/subjectDetails.html"})
    public String getsubjectDetails(@Param("id") Long id, HttpServletRequest request, Model model){ //add model to load repos
        //tryout OK
        Iterable<Lesson> lesson = lessonRepository.getAllbySubject(id);
        model.addAttribute("lesson", lesson);
        Iterable<Subject> subjects = subjectRepository.findAllByTeacher(id);
        model.addAttribute("subjects", subjects);
        String userName = request.getRemoteUser();
        Teacher teacher = teacherRepository.findByUserName(userName);
        model.addAttribute("user", teacher);
        return "subjectDetails";
    }
    @PostMapping("/teacher/subjectDetails")
    public @ResponseBody String addNewClass (@RequestParam String newLesson){
        Lesson lesson = new Lesson();
        return "subjectDetails";
    }
}
