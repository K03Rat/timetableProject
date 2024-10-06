package com.group16.timetable.repositories;

import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.data.jpa.repository.Query;
import org.springframework.data.repository.query.Param;
import org.springframework.stereotype.Repository;
import com.group16.timetable.model.*;

import java.util.List;
import java.util.Optional;

@Repository
public interface ModulesRepository extends JpaRepository<Modules, Long>{
    // Method to find a module by its code
    Optional<Modules> findByCode(String moduleCode);

    // JPQL query to find lecturers by module code
    @Query("SELECT l FROM Lecturer l JOIN l.modules m WHERE m.code = :moduleCode")
    List<Lecturer> findLecturersByModuleCode(@Param("moduleCode") String moduleCode);

}
