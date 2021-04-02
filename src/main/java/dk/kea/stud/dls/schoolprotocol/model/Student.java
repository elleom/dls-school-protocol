package dk.kea.stud.dls.schoolprotocol.model;

import lombok.Getter;
import lombok.Setter;

import javax.persistence.*;
import java.util.HashSet;
import java.util.Set;

@Entity
@Getter
@Setter
public class Student extends BaseEntity {

    @Id
    @GeneratedValue(strategy = GenerationType.IDENTITY)
    private Long id;
    @ManyToMany (mappedBy = "students")
    @JoinColumn (name = "student_id")
    private Set<Lesson> lessons = new HashSet<>();



}
