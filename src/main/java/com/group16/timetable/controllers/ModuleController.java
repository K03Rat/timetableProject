package com.group16.timetable.controllers;

import java.util.List;
import java.util.Optional;

import com.group16.timetable.model.Course;
import com.group16.timetable.model.Lecturer;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.*;

import com.group16.timetable.model.Modules;
import com.group16.timetable.services.ModuleService;

@RestController
@RequestMapping("/api/v1/modules")
public class ModuleController {

    @Autowired
    private ModuleService moduleService;

    // Retrieve all modules
    @GetMapping
    public List<Modules> getAllModules() {
        return moduleService.getAllModules();
    }

    // Retrieve a module by ID
    @GetMapping("/{id}")
    public ResponseEntity<Modules> getModuleById(@PathVariable Long id) {
        Optional<Modules> module = moduleService.getModuleById(id);
        return module.map(ResponseEntity::ok)
                .orElseGet(() -> ResponseEntity.notFound().build());
    }

    // Create a new module
    @PostMapping
    public ResponseEntity<Modules> createModule(@RequestBody Modules module) {
        Modules newModule = moduleService.saveOrUpdateModule(module);
        return ResponseEntity.ok(newModule);
    }

    // Update an existing module by ID
    @PutMapping("/{id}")
    public ResponseEntity<Modules> updateModule(@PathVariable Long id, @RequestBody Modules moduleDetails) {
        Optional<Modules> existingModule = moduleService.getModuleById(id);

        if (existingModule.isPresent()) {
            Modules module = existingModule.get();
            module.setModuleCode(moduleDetails.getModuleCode());
            module.setModuleName(moduleDetails.getModuleName());

            Modules updatedModule = moduleService.saveOrUpdateModule(module);
            return ResponseEntity.ok(updatedModule);
        } else {
            return ResponseEntity.notFound().build();
        }
    }

    // Delete a module by ID
    @DeleteMapping("/{id}")
    public ResponseEntity<Void> deleteModule(@PathVariable Long id) {
        Optional<Modules> module = moduleService.getModuleById(id);

        if (module.isPresent()) {
            moduleService.deleteModule(id);
            return ResponseEntity.noContent().build();
        } else {
            return ResponseEntity.notFound().build();
        }
    }

    // Endpoint to get lecturers by module code
    @GetMapping("/lecturers")
    public List<Lecturer> getLecturersByModuleCode(@RequestParam("code") String moduleCode) {
        return moduleService.getLecturersByModuleCode(moduleCode);
    }
    @GetMapping("/module")
    public Optional<Modules> getModuleByCode(@RequestParam String code) {
        return moduleService.getModuleByCode(code); // Call the updated method
    }


}
