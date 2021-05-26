package dk.kea.stud.dls.schoolprotocol.controller;

import dk.kea.stud.dls.schoolprotocol.model.*;
import dk.kea.stud.dls.schoolprotocol.repository.*;
import dk.kea.stud.dls.schoolprotocol.service.RequestService;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.data.repository.query.Param;
import org.springframework.stereotype.Controller;
import org.springframework.ui.Model;
import org.springframework.web.bind.annotation.*;
import javax.servlet.http.HttpServletRequest;
import java.sql.Timestamp;
import java.text.ParseException;
import java.time.LocalDateTime;
import java.util.ArrayList;
import java.util.Date;
import java.util.Optional;
import java.util.Random;
import java.util.concurrent.atomic.AtomicInteger;


@Controller
@RequestMapping("/api")
public class TeacherController {

    @Autowired
    RequestService requestService;
    @Autowired
    TeacherRepository teacherRepository;
    @Autowired
    SubjectRepository subjectRepository;
    @Autowired
    StudentRepository studentRepository;
    @Autowired
    LessonRepository lessonRepository;
    @Autowired
    AttendanceRepository attendanceRepository;

    @RequestMapping({"/teacher/dashboard", "/teacher/dashboard.html"})
    public String getDashBoard(HttpServletRequest request, Model model){ //add model to load repos
        //tryout OK
        String userName = request.getRemoteUser();
        Teacher teacher = teacherRepository.findByUserName(userName);
        model.addAttribute("user", teacher);
        Iterable<Subject> subjects = subjectRepository.findAllByTeacher(teacher.getId());
        model.addAttribute("subjects", subjects);
        return "teacher_dashboard";
    }

    @RequestMapping({"/teacher/subjectDetails", "/teacher/subjectDetails.html"})
    public String getsubjectDetails(@Param("id") Long id, HttpServletRequest request, Model model){ //add model to load repos
        //tryout OK
        Iterable<Lesson> lesson = lessonRepository.getAllBySubject(id);
        model.addAttribute("lesson", lesson);

        Iterable<Lesson> lessonDesc = lessonRepository.findAllByDesc(id);
        model.addAttribute("lessonDesc", lessonDesc);

        Iterable<Subject> subjects = subjectRepository.findAllByTeacher(id);
        model.addAttribute("subjects", subjects);

        String userName = request.getRemoteUser();
        Teacher teacher = teacherRepository.findByUserName(userName);
        model.addAttribute("user", teacher);

        Subject subject = subjectRepository.findById(id).get();
        model.addAttribute("subject", subject);

        return "subjectDetails";
    }

    @RequestMapping({"/teacher/teacher_all_Lessons", "/teacher/teacher_all_Lessons.html"})
    public String getAllLessons(@Param("id") Long id, HttpServletRequest request, Model model){ //add model to load repos
        //tryout OK
        Iterable<Lesson> lesson = lessonRepository.getAllBySubject(id);
        model.addAttribute("lesson", lesson);

        Iterable<Subject> subject = subjectRepository.findAllByTeacher(id);
        model.addAttribute("subject", subject);

        String userName = request.getRemoteUser();
        Teacher teacher = teacherRepository.findByUserName(userName);
        model.addAttribute("user", teacher);

        return "teacher_all_lessons";
    }

    @PostMapping("/teacher/subjectDetails")
    public String registerNewLesson (@Param("subjectId") Long subjectId,@Param("id") Long id, HttpServletRequest request, Model model){
        try {
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

            Iterable<Lesson> lesson = lessonRepository.getAllBySubject(id);
            model.addAttribute("lesson", lesson);

            Subject subject = subjectRepository.findById(subjectId).get();
            Lesson lessons = new Lesson();
            lessons.setCode(saltStr);
            lessons.setSubject(subject);

            LocalDateTime now = LocalDateTime.now();
            Timestamp timeCreated = Timestamp.valueOf(now);

            lessons.setDate(timeCreated);
            lessonRepository.save(lessons);
            return "codeGenerationSuccess";
        }
        catch(Exception e){
            return "codeGenerationFail";
        }
    }
    @RequestMapping({"/teacher/students_list", "/teacher/students_list.html"})
    public String getStudentList(@Param("id") Long id, HttpServletRequest request, Model model) {
        String userName = request.getRemoteUser();
        Teacher teacher = teacherRepository.findByUserName(userName);
        model.addAttribute("user", teacher);
        Iterable<Student> students = studentRepository.findStudentBySubjectsId(id);
        model.addAttribute("students", students);
        Subject subject = subjectRepository.findById(id).get();
        model.addAttribute("subject", subject);
        return "students_list";
    }
    @RequestMapping({"/teacher/students_info", "/teacher/students_info.html"})
    public String getStudentsDetail(@Param("id") Long id, HttpServletRequest request, Model model) {
        String userName = request.getRemoteUser();
        Teacher teacher = teacherRepository.findByUserName(userName);
        model.addAttribute("user", teacher);
        Student student = (Student) studentRepository.getAllbyId(id);
        model.addAttribute("student", student);
        ArrayList<Attendance> attendances = attendanceRepository.findAllByStudent(id);
        model.addAttribute("attendances", attendances);

        return "students_info";
    }

    public Teacher getLoggedUser(HttpServletRequest request) {
        String user = request.getRemoteUser();
        Teacher teacher = teacherRepository.findByUserName(request.getRemoteUser());
        return teacher;
    }


}