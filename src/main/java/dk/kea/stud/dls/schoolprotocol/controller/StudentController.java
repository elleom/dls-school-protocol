package dk.kea.stud.dls.schoolprotocol.controller;

import dk.kea.stud.dls.schoolprotocol.model.*;
import dk.kea.stud.dls.schoolprotocol.repository.*;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.data.repository.query.Param;
import org.springframework.stereotype.Controller;
import org.springframework.ui.Model;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.PostMapping;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RequestParam;

import javax.servlet.http.HttpServletRequest;
import java.net.InetAddress;
import java.net.UnknownHostException;
import java.sql.Timestamp;
import java.text.ParseException;
import java.util.ArrayList;
import java.util.Arrays;
import java.util.concurrent.atomic.AtomicInteger;

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
    @Autowired
    TeacherRepository teacherRepository;

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
        model.addAttribute("attendanceCount", totalLessonsAttended);
        model.addAttribute("subjects", subjects);


        return "dashboard";
    }

    @GetMapping({"/subject/details"})
    public String getSubjectDetails(@Param("subjectId") Long subjectId, @Param("studentId") Long studentId, Model model, HttpServletRequest request) throws ParseException {

        String loggedUser = request.getRemoteUser();

        BaseEntity user = studentRepository.findByUserName(loggedUser);
        if (null == user) {
            user = teacherRepository.findByUserName(loggedUser);
        }

        Student student = studentRepository.findById(studentId).get();
        Long studIdAlt = studentId;
        Long userId = user.getId();

        String expectedRole = "TEACHER";
        String userRole = user.getRole();
        boolean IsTeacher = expectedRole.equals(userRole);

        if (studIdAlt != userId) {
            if (!IsTeacher) {
                return "access_denied";
            }
        }

        Subject subject = subjectRepository.findById(subjectId).get();
        Iterable<Lesson> lessons = lessonRepository.getAllBySubject(subjectId);

        AtomicInteger count = new AtomicInteger();
        lessons.forEach(lesson -> {
            count.getAndIncrement();
        });
        boolean containsData = count.intValue() > 0;

        if (!containsData) {
            return "no_data_found";
        }

        Long lastLessonId = lessonRepository.getLastLessonFromSubject(subjectId);
        Lesson lastLesson = lessonRepository.findById(lastLessonId).get();

        Timestamp timeCreated = lastLesson.getDate();
        Timestamp now = new Timestamp(System.currentTimeMillis());

        long millisecondsTimeCreated = timeCreated.getTime();
        long millisecondsNow = now.getTime();
        long diffMilliseconds = millisecondsNow - millisecondsTimeCreated;

        long diffMinutes = diffMilliseconds / (60 * 1000);
        boolean checkIn = false;

        if (diffMinutes <= 15) { // change time if needed
            checkIn = true;
        }

        ArrayList<Attendance> attendances = attendanceRepository.findAllByStudent(studentId);
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
        model.addAttribute("missedLessons", lessonsSubject - attendancesPerStudentToSubject);

        return "userSubjectLessonList";

    }

    @PostMapping({"/student/lessons"})
    public String declareAttendance(@RequestParam("lesson_id") Long lessonId,
                                    @RequestParam("student_id") Long studentId,
                                    @RequestParam("lesson_code") String code,
                                    HttpServletRequest request) {

        boolean isSameNetwork = checkNetworkIp(request);

        if (!isSameNetwork) {
            return "network_alert";
        }

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
        Student student = studentRepository.findByUserName(request.getRemoteUser());
        return student;
    }

    private boolean checkNetworkIp(HttpServletRequest request) {

        final String LOCALHOST_IPV4 = "127.0.0.1";
        final String LOCALHOST_IPV6 = "0:0:0:0:0:0:0:1";
        String localIp = "";
        String subnet = "";

        String requestIp = request.getRemoteAddr();
        String regexIpV4 = "\\."; //splits the string based on the '.'
        String regexIpV6 = "\\:";
        String[] parts = {};

        if (requestIp.equals(LOCALHOST_IPV4) | requestIp.equals(LOCALHOST_IPV6)) {
            return true;
        }
        String ipType;
        if (requestIp.contains(".")) {
            ipType = "ipv4";
        } else {
            ipType = "ipv6";
        }

        switch (ipType) {
            case "ipv4":
                parts = requestIp.split(regexIpV4);
                subnet = parts[0] + "." + parts[1] + ".";
                break;
            case "ipv6":
                parts = requestIp.split(regexIpV6);
                subnet = parts[0] + ":" + parts[1] + ":" + parts[2] + ":" + parts[3];
        }

        System.out.println(Arrays.toString(parts));
        try {
            localIp = InetAddress.getLocalHost().getHostAddress();
        } catch (UnknownHostException e) {
            e.printStackTrace();
        }
        if (localIp.contains(subnet)) {
            return true;
        }
        return false;
    }
}
