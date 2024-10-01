package com.group16.timetable.repositories;

import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.stereotype.Repository;
import com.group16.timetable.model.Student;
@Repository
public interface StudentRepository extends JpaRepository<Student, Long> {

}
