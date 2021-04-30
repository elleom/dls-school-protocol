package dk.kea.stud.dls.schoolprotocol.model;

import lombok.EqualsAndHashCode;
import lombok.Getter;
import lombok.Setter;
import lombok.ToString;
import org.hibernate.annotations.Columns;

import javax.persistence.*;
import java.util.HashSet;
import java.util.Set;

@Entity
@Getter
@Setter
@EqualsAndHashCode
public class Student extends BaseEntity {

    public Student( ){
        super();
    }

    @Id
    @GeneratedValue(strategy = GenerationType.IDENTITY)
    private Long id;

    @Column(name = "fullname") //important to keep with lowercase
    private String fullName;
    private String password;

    private String role = "student";
    private String email;

    @OneToMany
    @JoinColumn(name = "student_id" )
    private Set<Attendance> attendance;

    @ManyToMany(mappedBy = "students")
    private Set<Subject> subjects;



}
