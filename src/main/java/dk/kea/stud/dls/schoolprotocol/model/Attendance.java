package dk.kea.stud.dls.schoolprotocol.model;

import lombok.Getter;
import lombok.Setter;

import javax.persistence.*;

@Entity
@Getter
@Setter
public class Attendance {

    public Attendance(Student student, Lesson lesson){
        this.student = student;
        this.lesson = lesson;
    }

    @Id
    @GeneratedValue(strategy = GenerationType.IDENTITY)
    private Long id;

    @ManyToOne
    Student student;
    @ManyToOne
    Lesson lesson;



}
