package dk.kea.stud.dls.schoolprotocol.repository;

import dk.kea.stud.dls.schoolprotocol.model.Lesson;
import dk.kea.stud.dls.schoolprotocol.model.Subject;
import org.springframework.data.jpa.repository.Query;
import org.springframework.data.repository.CrudRepository;
import org.springframework.data.repository.query.Param;
import org.springframework.stereotype.Repository;

@Repository
public interface LessonRepository extends CrudRepository<Lesson, Long> {


    static void addNewLesson(Lesson lesson) {
        System.out.println(lesson);
    }

    @Query(value = "select * from lesson where subject_id = :subjectId", nativeQuery = true)
    Iterable<Lesson> getAllBySubject(@Param("subjectId")Long subject_id);

    @Query(value = "select * from lesson WHERE subject_id = :id ORDER BY id DESC LIMIT 5", nativeQuery = true)
    Iterable<Lesson> findAllByDesc(@Param("id")Long lesson_id);

    @Query(value = "select * from lesson join attendance a on lesson.id = a.lesson_id where a.student_id = :id", nativeQuery = true)
    Iterable<Lesson> getAllByStudentAttendance(@Param("id") Long student_id);

    @Query(value = "select max(id) from lesson where subject_id = :subjectId", nativeQuery = true)
    Long getLastLessonFromSubject(@Param("subjectId") Long subject_id);

    @Query(value = "select count(a.id) from lesson join attendance a on lesson.id = a.lesson_id where a.student_id = :id", nativeQuery = true)
    Long getTotalAttendanceCount(@Param("id") Long student_id);

    @Query(value = "select count(a.id) from lesson join attendance a on lesson.id = a.lesson_id where a.student_id = :id and lesson.subject_id = :subjectId", nativeQuery = true)
    Long getAttendanceCountBySubject(@Param("id") Long student_id, @Param("subjectId") Long subject_id);

    @Query(value = "select count(a.id) from lesson join attendance a on lesson.id = a.lesson_id where lesson.subject_id = :subjectId", nativeQuery = true)
    Long getAllLessonsAttendedCountBySubject(@Param("subjectId") Long subject_id);


    @Query(value = "select count(id) from lesson where lesson.subject_Id = :subjectId", nativeQuery = true)
    Long getTotalLessonsBySubject(@Param("subjectId") Long subjectId);

    @Query (value = "select count(distinct a.student_id) from lesson join attendance a on lesson.id = a.lesson_id where lesson.subject_id = 1", nativeQuery = true)
    Long getCountDiffStudentAttended(@Param("subjectId") Long subjectId);
}
