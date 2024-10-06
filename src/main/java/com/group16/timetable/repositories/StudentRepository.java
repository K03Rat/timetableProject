package com.group16.timetable.repositories;

import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.data.jpa.repository.Query;
import org.springframework.stereotype.Repository;
import com.group16.timetable.model.Student;

import java.util.List;
import java.util.Optional;

@Repository
public interface StudentRepository extends JpaRepository<Student, Long> {
    // Find a student by their student ID
    Optional<Student> findByFirstNameAndLastName(String firstName, String lastName);


    // SQL query to retrieve timetable based on course code
    @Query(value = "SELECT t.day AS day, m.module_code AS module_code, m.module_name AS module_name, " +
            "t.start_time AS start_time, t.venue AS venue, t.event_type_num AS event_type_num, " +
            "t.semester AS semester " +
            "FROM timetables t " +
            "JOIN modules m ON t.module_id = m.id " +
            "JOIN course_modules cm ON cm.module_id = m.id " +
            "JOIN courses c ON cm.course_id = c.id " +
            "JOIN student s ON s.course_id = c.id " +
            "WHERE s.id = :studentId", nativeQuery = true)
    List<Object[]> findTimetableByStudentId(Long studentId);
}
