package dk.kea.stud.dls.schoolprotocol.repository;

import dk.kea.stud.dls.schoolprotocol.model.Student;
import org.springframework.data.jpa.repository.Query;
import org.springframework.data.repository.CrudRepository;
import org.springframework.data.repository.query.Param;

public interface StudentRepository extends CrudRepository<Student, Long> {

    @Query(value = "SELECT s.* FROM  student s WHERE s.email = :email", nativeQuery = true)
    Student findByUserName(@Param("email") String email); //defines a list of Users with the same name

}
