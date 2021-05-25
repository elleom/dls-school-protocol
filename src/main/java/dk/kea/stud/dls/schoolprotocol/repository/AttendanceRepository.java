package dk.kea.stud.dls.schoolprotocol.repository;

import dk.kea.stud.dls.schoolprotocol.model.Attendance;
import dk.kea.stud.dls.schoolprotocol.model.Subject;
import org.springframework.data.jpa.repository.Query;
import org.springframework.data.repository.CrudRepository;
import org.springframework.data.repository.query.Param;

import java.util.ArrayList;
import java.util.Set;

public interface AttendanceRepository extends CrudRepository<Attendance, Long> {

    @Query(value = "select attendance.* from attendance join lesson l on l.id = attendance.lesson_id where student_id = :id", nativeQuery = true)
    ArrayList<Attendance> findAllByStudent(@Param("id")Long student_id);


}
