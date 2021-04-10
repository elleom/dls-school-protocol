package dk.kea.stud.dls.schoolprotocol.model;

import lombok.EqualsAndHashCode;
import lombok.Getter;
import lombok.Setter;
import lombok.ToString;

import javax.persistence.*;
import java.util.HashSet;
import java.util.Set;

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
