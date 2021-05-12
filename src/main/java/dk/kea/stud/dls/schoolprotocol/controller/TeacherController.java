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
import org.springframework.web.bind.annotation.*;
import javax.servlet.http.HttpServletRequest;
import java.sql.Timestamp;
import java.time.LocalDateTime;
import java.util.Random;



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
        model.addAttribute("user", teacher);
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
    public String registerNewLesson (@Param("subjectId") Long subjectId,@Param("id") Long id, HttpServletRequest request, Model model){

        //Class code generation
        String lessonCode = "AaBbCcDdEeFfGgHhIiJjKkLlMmNnOoPpQqRrSsTtUuVvWwXxYyZz1234567890";
        StringBuilder salt = new StringBuilder();
        Random random = new Random();
        while (salt.length() < 6) { // length of the random string.
            int index = (int) (random.nextFloat() * lessonCode.length());
            salt.append(lessonCode.charAt(index));
        }
        //factoring to string
        String saltStr = salt.toString();

        String userName = request.getRemoteUser();
        Teacher teacher = teacherRepository.findByUserName(userName);
        model.addAttribute("user", teacher);

        Iterable<Subject> subjects = subjectRepository.findAllByTeacher(id);
        model.addAttribute("subjects", subjects);
        Subject subject = subjectRepository.findById(subjectId).get();

        Lesson lessons = new Lesson();
        lessons.setCode(saltStr);
        lessons.setSubject(subject);

        LocalDateTime now = LocalDateTime.now();
        Timestamp timeCreated = Timestamp.valueOf(now);

        lessons.setDate(timeCreated);
        lessonRepository.save(lessons);
//        if (lessonRepository.count() > oldCountOfClasses){
//            return "codeGenerationSuccess";
//        }
//        else {
//            return "teacher_dashboard";
//        }    //return OK template
        //catch
            //return Error template
        return "codeGenerationSuccess";
   }
}
