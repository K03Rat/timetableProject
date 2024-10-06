package com.group16.timetable.repositories;


import com.group16.timetable.model.Timetable;
import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.data.jpa.repository.Query;
import org.springframework.stereotype.Repository;

import java.util.List;
import java.util.Optional;

@Repository
public interface TimetableRepository extends JpaRepository<Timetable, Long> {
    List<Timetable> findByDayOfWeek(int day);

    // Custom SQL query to fetch timetable details
    @Query(value = "SELECT m.module_code AS module_code, m.module_name AS module_name, t.day, " +
            "t.start_time AS start_time, t.venue AS venue, t.event_type_num AS event_type_num, " +
            "t.semester AS semester " +
            "FROM timetables t " +
            "JOIN modules m ON t.module_id = m.id " +
            "JOIN course_modules cm ON cm.module_id = m.id " +
            "JOIN courses c ON cm.course_id = c.id " +
            "WHERE c.course_code = :courseCode", nativeQuery = true)
    List<Object[]> findTimetableDetailsByCourseCode(String courseCode);

}
