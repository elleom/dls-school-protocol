package dk.kea.stud.dls.schoolprotocol.controller;

import dk.kea.stud.dls.schoolprotocol.model.Attendance;
import dk.kea.stud.dls.schoolprotocol.model.Lesson;
import dk.kea.stud.dls.schoolprotocol.model.Student;
import dk.kea.stud.dls.schoolprotocol.model.Subject;
import dk.kea.stud.dls.schoolprotocol.repository.AttendanceRepository;
import dk.kea.stud.dls.schoolprotocol.repository.LessonRepository;
import dk.kea.stud.dls.schoolprotocol.repository.StudentRepository;
import dk.kea.stud.dls.schoolprotocol.repository.SubjectRepository;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.data.repository.query.Param;
import org.springframework.stereotype.Controller;
import org.springframework.ui.Model;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.PostMapping;
import org.springframework.web.bind.annotation.RequestMapping;

import javax.servlet.http.HttpServletRequest;
import java.sql.Timestamp;
import java.text.DateFormat;
import java.text.ParseException;
import java.text.SimpleDateFormat;
import java.time.LocalDateTime;
import java.time.format.DateTimeFormatter;
import java.util.Calendar;
import java.util.Date;

@Controller
@RequestMapping("/api")
public class StudentController {

    @Autowired
    SubjectRepository subjectRepository;
    @Autowired
    StudentRepository studentRepository;
    @Autowired
    LessonRepository lessonRepository;
    @Autowired
    AttendanceRepository attendanceRepository;

    @RequestMapping({"/student/dashboard", "/student/dashboard.html"})
    public String getDashBoard(@Param("studentId") Long studentId, HttpServletRequest request, Model model){ //add model to load repos
        //tryout OK
        Iterable<Subject> subjects = subjectRepository.findAllByStudent(studentId); //todo change interface to student
        Student student = studentRepository.findById(studentId).get();
        model.addAttribute("student", student);
        model.addAttribute("subjects", subjects);
        return "student_dashboard";
    }

    @GetMapping({"/student/showAll", "/student/showAll.html"})
    public String getDashBoard(Model model){ //add model to load repos

        Student students = studentRepository.findById(1L).get(); //todo change here
        model.addAttribute("students", students);
        return "students_list";
    }

    @GetMapping({"/subject/details"})
    public String getSubjectDetails(@Param("subjectId") Long subjectId, @Param("studentId") Long studentId , Model model) throws ParseException {
        //todo finish
        Student student = studentRepository.findById(studentId).get();
        Subject subject = subjectRepository.findById(studentId).get();
        Iterable<Lesson> lessons = lessonRepository.getAllbySubject(subjectId);

        SimpleDateFormat format = new SimpleDateFormat("MM/dd/yyyy HH:mm:ss");

        Lesson last = lessonRepository.findById(2L).get();
        Date dateStart = last.getDate();
        Date dateLesson = Calendar.getInstance().getTime();

        //in milliseconds
        long diff = dateStart.getTime() - dateLesson.getTime();
        long diffMinutes = diff / (60 * 1000) % 60;




        model.addAttribute("student", student);
        model.addAttribute("lessons", lessons);
        model.addAttribute("subject", subject);


        return "student_subject";

    }

    @PostMapping({"/student/lessons"})
    public void declareAttendance(@Param("lesson_id") Long lessonId,
                                  @Param("student_id") Long studentId){

        Student student = studentRepository.findById(studentId).get();
        Lesson lesson = lessonRepository.findById(lessonId).get();
        Attendance attendance = new Attendance(student, lesson);

        attendanceRepository.save(attendance); //todo if not then rollback

    }


}
