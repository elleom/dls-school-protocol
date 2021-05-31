package dk.kea.stud.dls.schoolprotocol.model;

import lombok.EqualsAndHashCode;
import lombok.Getter;
import lombok.Setter;
import lombok.ToString;

import javax.persistence.Id;

@Getter
@Setter
public class BaseEntity {

    @Id
    private Long id;
    private String fullName;
    private String password;
    private String email;
    private String role;

}
