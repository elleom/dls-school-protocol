package dk.kea.stud.dls.schoolprotocol.model;

import lombok.Getter;
import lombok.Setter;

import javax.persistence.*;

@Entity
@Getter
@Setter
public class Attendance {

    @Id
    private Long id;

    @ManyToOne
    Student student;
    @ManyToOne
    Lesson lesson;



}
