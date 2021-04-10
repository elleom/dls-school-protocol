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
@EqualsAndHashCode
@ToString
public class Student extends BaseEntity {

    public Student(){
        super();
    }

    @Id
    @GeneratedValue(strategy = GenerationType.IDENTITY)
    private Long id;

    private String role = "student";
    private String email;

    @OneToMany
    @JoinColumn(name = "student_id" )
    private Set<Attendance> attendance;

    @ManyToMany(mappedBy = "students")
    private Set<Subject> subjects;



}
