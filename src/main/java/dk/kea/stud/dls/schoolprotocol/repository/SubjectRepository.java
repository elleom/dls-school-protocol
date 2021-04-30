package dk.kea.stud.dls.schoolprotocol.repository;

import dk.kea.stud.dls.schoolprotocol.model.Subject;
import org.springframework.data.jpa.repository.Query;
import org.springframework.data.repository.CrudRepository;
import org.springframework.data.repository.query.Param;

public interface SubjectRepository extends CrudRepository<Subject, Long> {

    //recovers full data obj subject objects into an array
    @Query(value = "select subject.id, subject.name from subject\n" +
            "    join subject_has_teacher sht on subject.id = sht.subject_id\n" +
            "where sht.teacher_id = :id", nativeQuery = true)
    Iterable<Subject> findAllByTeacher(@Param("id")Long teacher_id);

    @Query(value = "select subject.id, subject.name from subject\n" +
            "    join student_has_subject sht on subject.id = sht.subject_id\n" +
            "where sht.student_id = :id", nativeQuery = true)
    Iterable<Subject> findAllByStudent(@Param("id")Long student_id);



}
