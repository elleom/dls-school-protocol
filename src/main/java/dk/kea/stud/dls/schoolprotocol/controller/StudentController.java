package dk.kea.stud.dls.schoolprotocol.controller;

import dk.kea.stud.dls.schoolprotocol.model.*;
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
import java.sql.Timestamp;
import java.text.ParseException;
import java.util.ArrayList;

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

    @RequestMapping({"/student/courses", "/student/courses.html"})
    public String getCourses(HttpServletRequest request, Model model) { //add model to load repos
        //tryout OK
        Student student = getLoggedStudent(request);
        Iterable<Subject> subjects = subjectRepository.findAllByStudent(student.getId()); //todo change interface to student

        model.addAttribute("student", student);
        model.addAttribute("subjects", subjects);
        return "userCourses";
    }

    @GetMapping({"/dashboard", "/dashboard"})
    public String getDashboard(Model model, HttpServletRequest request) { //add model to load repos

        Student student = getLoggedStudent(request);  //todo change to getUser and set accordingly

        Iterable<Subject> subjects = subjectRepository.findAllByStudent(student.getId());
        Long totalLessonsCount = lessonRepository.count();
        Long totalLessonsAttended = lessonRepository.getTotalAttendanceCount(student.getId());

        model.addAttribute("student", student);
        model.addAttribute("lessonsCount", totalLessonsCount);
        model.addAttribute("attendanceCount",totalLessonsAttended);
        model.addAttribute("subjects", subjects);


        return "dashboard";
    }

    @GetMapping({"/subject/details"})
    public String getSubjectDetails(@Param("subjectId") Long subjectId, @Param("studentId") Long studentId, Model model) throws ParseException {

        Student student = studentRepository.findById(studentId).get();
        Subject subject = subjectRepository.findById(subjectId).get();
        Iterable<Lesson> lessons = lessonRepository.getAllBySubject(subjectId);

        //todo set condition for lessons < 0

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

        ArrayList<Attendance> attendances = attendanceRepository.findAllByStudent(studentId);
        attendances.forEach(x ->
                System.out.println(x.getLesson().getId().toString()));

        Long attendancesPerStudentToSubject = lessonRepository.getAttendanceCountBySubject(studentId, subjectId);
        Long lessonsSubject = lessonRepository.getTotalLessonsBySubject(subjectId);

        model.addAttribute("lessonId", lastLessonId);
        model.addAttribute("checkIn", checkIn);
        model.addAttribute("student", student);
        model.addAttribute("lessons", lessons);
        model.addAttribute("subject", subject);
        model.addAttribute("attendances", attendances);
        model.addAttribute("attendeesSubject", attendancesPerStudentToSubject);
        model.addAttribute("lessonsSubject", lessonsSubject);
        model.addAttribute("missedLessons",  lessonsSubject - attendancesPerStudentToSubject);

        return "userSubjectLessonList";

    }

    @PostMapping({"/student/lessons"})
    public String declareAttendance(@RequestParam("lesson_id") Long lessonId,
                                    @RequestParam("student_id") Long studentId,
                                    @RequestParam("lesson_code") String code) {

        Student student = studentRepository.findById(studentId).get();
        Lesson lesson = lessonRepository.findById(lessonId).get();

        if (lesson.getCode().equalsIgnoreCase(code)) {
            Attendance attendance = new Attendance(student, lesson);
            attendanceRepository.save(attendance); //todo if not then rollback

            return "attendance_success";
        } else {
            return "attendance_fail";
        }
    }

    public Student getLoggedStudent(HttpServletRequest request) {
        String user = request.getRemoteUser();
        Student student = studentRepository.findByUserName(request.getRemoteUser());
        return student;
    }
}
