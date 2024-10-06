package com.group16.timetable.repositories;

import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.data.jpa.repository.Query;
import org.springframework.data.repository.query.Param;
import org.springframework.stereotype.Repository;

import com.group16.timetable.model.Course;

import java.util.List;
import java.util.Optional;

@Repository
public interface CourseRepository extends JpaRepository<Course, Long> {

    Optional<Course> findByCode(String courseCode);


    // Custom query to find course codes associated with a module name
    @Query("SELECT c.code, m.name FROM Course c " +
            "JOIN c.modules m " +
            "WHERE m.name LIKE %:moduleName%")
    List<String> findCourseCodesByModuleName(@Param("moduleName") String moduleName);

}
