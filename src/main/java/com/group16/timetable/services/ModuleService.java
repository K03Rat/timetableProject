package com.group16.timetable.services;

import java.util.List;
import java.util.Optional;

import com.group16.timetable.model.Lecturer;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;

import com.group16.timetable.model.Modules;
import com.group16.timetable.model.Course;
import com.group16.timetable.repositories.ModulesRepository;

@Service
public class ModuleService {

    @Autowired
    private ModulesRepository moduleRepository;

    // Retrieve all modules
    public List<Modules> getAllModules() {
        return moduleRepository.findAll();
    }

    // Find module by ID
    public Optional<Modules> getModuleById(Long id) {
        return moduleRepository.findById(id);
    }

    // Save a new module or update an existing one
    public Modules saveOrUpdateModule(Modules module) {
        return moduleRepository.save(module);
    }

    // Delete a module by ID
    public void deleteModule(Long id) {
        moduleRepository.deleteById(id);
    }

    public Optional<Modules> getModuleByCode(String moduleCode) {
        return moduleRepository.findByCode(moduleCode);
    }
    public List<Lecturer> getLecturersByModuleCode(String moduleCode) {
        return moduleRepository.findLecturersByModuleCode(moduleCode);
    }

}
