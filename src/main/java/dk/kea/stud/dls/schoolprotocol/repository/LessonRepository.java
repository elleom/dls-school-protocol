package dk.kea.stud.dls.schoolprotocol.repository;

import dk.kea.stud.dls.schoolprotocol.model.Lesson;
import org.springframework.data.jpa.repository.Query;
import org.springframework.data.repository.CrudRepository;
import org.springframework.data.repository.query.Param;

import java.util.Optional;

public interface LessonRepository extends CrudRepository<Lesson, Long> {


    @Query(value = "select * from lesson where subject_id = :subjectId", nativeQuery = true)
    Iterable<Lesson> getAllbySubject(@Param("subjectId")Long subject_id);

    @Query(value = "select * from lesson join attendance a on lesson.id = a.lesson_id where a.student_id = :id;", nativeQuery = true)
    Iterable<Lesson> getAllByStudentAttendance(@Param("id") Long student_id);

    /*retrieves last lesson for the subject */
    @Query(value = "select max(id) from lesson where subject_id = :subjectId", nativeQuery = true)
    Long getLastLessonFromSubject(@Param("subjectId") Long subject_id);

}
