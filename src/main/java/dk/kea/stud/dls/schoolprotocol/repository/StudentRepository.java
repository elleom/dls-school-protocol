package dk.kea.stud.dls.schoolprotocol.repository;

import dk.kea.stud.dls.schoolprotocol.model.Lesson;
import dk.kea.stud.dls.schoolprotocol.model.Student;
import org.springframework.data.jpa.repository.Query;
import org.springframework.data.repository.CrudRepository;
import org.springframework.data.repository.query.Param;

public interface StudentRepository extends CrudRepository<Student, Long> {

    @Query("SELECT s FROM  Student s WHERE s.email = :email")
    Student findByUserName(@Param("email") String email); //defines a list of Users with the same name

    @Query(value = "select * from student where student_id = :id", nativeQuery = true)
    Iterable<Student> getAllbyId(@Param("id")Long student_id);

    @Query(value = "select * from student join student_has_subject shs on student.id = shs.student_id where subject_id = :id", nativeQuery = true)
    Iterable<Student> findStudentBySubjectsId(@Param("id")Long id);
}
