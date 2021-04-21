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

    @GetMapping ({"/student/dashboard", "/student/dashboard.html"})
    public String getDashBoard(@Param("subjectId") Long id,@Param("studentId") Long studentId, HttpServletRequest request, Model model){ //add model to load repos
        //tryout OK
        Iterable<Subject> subjects = subjectRepository.findAllByTeacher(id); //todo change interface to student
        Student student = studentRepository.findById(studentId).get();
        model.addAttribute("student", student);
        model.addAttribute("subjects", subjects);
        return "student_dashboard";
    }

    @GetMapping({"/student/showAll", "/student/showAll.html"})
    public String getDashBoard(Model model){ //add model to load repos

        Student students = studentRepository.findById(1L).get();
        model.addAttribute("students", students);
        return "students_list";
    }

    @GetMapping({"/student/lessons"})
    public String getSubjectDetails(@Param("id") Long subjectId, Model model){
        //todo finish
        return null;
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
