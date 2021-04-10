package dk.kea.stud.dls.schoolprotocol.repository;

import dk.kea.stud.dls.schoolprotocol.model.Student;
import dk.kea.stud.dls.schoolprotocol.model.Teacher;
import org.springframework.data.jpa.repository.Query;
import org.springframework.data.repository.CrudRepository;
import org.springframework.data.repository.query.Param;

public interface TeacherRepository extends CrudRepository<Teacher, Long> {

    @Query("SELECT t FROM Teacher t WHERE t.email = :email")
    Teacher findByUserName(@Param("email") String email); //defines a list of Users with the same name

}
