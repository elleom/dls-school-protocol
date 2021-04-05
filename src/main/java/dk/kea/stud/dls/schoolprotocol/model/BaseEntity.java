package dk.kea.stud.dls.schoolprotocol.model;

import lombok.EqualsAndHashCode;
import lombok.Getter;
import lombok.Setter;
import lombok.ToString;

import java.util.HashSet;
import java.util.Set;

@Getter
@Setter
@EqualsAndHashCode
@ToString
public class BaseEntity {

    private String fullName;
    private String password;
    private String email;

}
