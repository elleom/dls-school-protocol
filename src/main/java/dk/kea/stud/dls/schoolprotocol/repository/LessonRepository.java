package dk.kea.stud.dls.schoolprotocol.repository;

import dk.kea.stud.dls.schoolprotocol.model.Lesson;
import org.springframework.data.jpa.repository.Query;
import org.springframework.data.repository.CrudRepository;
import org.springframework.data.repository.query.Param;
import java.time.LocalDateTime;

import java.util.Optional;

public interface LessonRepository extends CrudRepository<Lesson, Long> {


    static void addNewLesson(Lesson lesson) {
        System.out.println(lesson);
    }

    @Query(value = "select * from lesson where subject_id = :id", nativeQuery = true)
    Iterable<Lesson> getAllbySubject(@Param("id")Long subject_id);
}
