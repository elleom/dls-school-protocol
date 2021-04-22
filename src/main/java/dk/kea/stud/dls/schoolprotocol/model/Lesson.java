package dk.kea.stud.dls.schoolprotocol.model;

import lombok.EqualsAndHashCode;
import lombok.Getter;
import lombok.Setter;

import javax.persistence.*;
import java.sql.Timestamp;
import java.util.Set;

@Entity
@Getter
@Setter
@EqualsAndHashCode(onlyExplicitlyIncluded = true, callSuper = false)
public class Lesson {

    @Id
    @GeneratedValue(strategy = GenerationType.IDENTITY)
    private Long id;
    private Timestamp date;
    private String code;

    @OneToMany
    @JoinColumn(name = "lesson_id")
    Set<Attendance> lessons;

    @ManyToOne
    private Subject subject;
}
