package dk.kea.stud.dls.schoolprotocol.model;

import javax.persistence.Entity;
import javax.persistence.Id;

@Entity
public class Teacher  extends BaseEntity {

    @Id
    private Long id;
}
