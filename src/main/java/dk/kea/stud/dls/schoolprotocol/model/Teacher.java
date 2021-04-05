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
public class Teacher  extends BaseEntity {

    public Teacher(){
        super();
    }

    @Id
    private Long id;

    private String role = "teacher";

    @OneToMany
    @JoinColumn(name = "teacher_id" )
    private Set<Lesson> lessons;

    @ManyToMany (mappedBy = "teachers")
    private Set<Subject> subjects;


}
