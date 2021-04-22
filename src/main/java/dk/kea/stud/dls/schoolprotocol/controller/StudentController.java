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
import org.springframework.web.bind.annotation.RequestParam;

import javax.servlet.http.HttpServletRequest;
import java.sql.Time;
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

        Student student = studentRepository.findById(studentId).get();
        Subject subject = subjectRepository.findById(studentId).get();
        Iterable<Lesson> lessons = lessonRepository.getAllbySubject(subjectId);

        Long lastLessonId = lessonRepository.getLastLessonFromSubject(subjectId);
        Lesson lastLesson = lessonRepository.findById(lastLessonId).get();

        Timestamp timeCreated = lastLesson.getDate();
        Timestamp now = new Timestamp(System.currentTimeMillis());

        long millisecondsTimeCreated = timeCreated.getTime();
        long millisecondsNow = now.getTime();
        long diffMilliseconds = millisecondsNow - millisecondsTimeCreated;

        long diffMinutes = diffMilliseconds / (60 * 1000);
        boolean checkIn = false;

        if (diffMinutes <= 100) { // todo change time
            checkIn = true;
        }

        model.addAttribute("lessonId", lastLessonId);
        model.addAttribute("checkIn", checkIn);
        model.addAttribute("student", student);
        model.addAttribute("lessons", lessons);
        model.addAttribute("subject", subject);
        return "student_subject";

    }

    @PostMapping({"/student/lessons"})
    public String declareAttendance(@RequestParam("lesson_id") Long lessonId,
                                  @RequestParam("student_id") Long studentId,
                                  @RequestParam("lesson_code") String code){

        Student student = studentRepository.findById(studentId).get();
        Lesson lesson = lessonRepository.findById(lessonId).get();

        if(lesson.getCode().equalsIgnoreCase(code)) {
            Attendance attendance = new Attendance(student, lesson);
            attendanceRepository.save(attendance); //todo if not then rollback

            return "attendance_success";
        }
        else {
            return "attendance_fail";
        }
    }
}
