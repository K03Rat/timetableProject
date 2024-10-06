package com.group16.timetable.repositories;

import com.group16.timetable.model.Timetable;
import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.data.jpa.repository.Query;
import org.springframework.data.repository.query.Param;
import org.springframework.stereotype.Repository;
import com.group16.timetable.model.Lecturer;

import java.util.List;
import java.util.Optional;

@Repository
public interface LecturerRepository extends JpaRepository<Lecturer, Long> {

    // Optional method to find lecturer by email
    Optional<Lecturer> findByEmail(String email);

    // SQL query to get timetable details for a lecturer based on the modules they teach
    @Query(value = "SELECT t.semester, t.day AS day, t.start_time AS time, t.venue AS venue, m.module_code AS module_code, m.module_name AS module_name " +
            "FROM timetables t " +
            "JOIN lecturer_module lm ON t.module_id = lm.module_id " +
            "JOIN modules m ON lm.module_id = m.id " +
            "WHERE lm.lecturer_id = :lecturerId", nativeQuery = true)
    List<Object[]> findTimetableDetailsByLecturerId(@Param("lecturerId") Long lecturerId);
}
