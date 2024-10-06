package com.group16.timetable.controllers;

import com.group16.timetable.exceptions.ResourceNotFoundException;
import com.group16.timetable.model.Modules;
import com.group16.timetable.model.Timetable;
import com.group16.timetable.services.ModuleService;
import com.group16.timetable.services.TimetableService;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.*;


import java.net.URI;
import java.util.List;
import java.util.Map;

@RestController
@RequestMapping(path = "api/v1/timetables")
public class TimetableController {

    private final TimetableService timetableService;
    private final ModuleService moduleService;

    @Autowired
    public TimetableController(TimetableService timetableService, ModuleService moduleService) {
        this.timetableService = timetableService;
        this.moduleService = moduleService;
    }

    // Get all timetables
    @GetMapping
    public ResponseEntity<List<Timetable>> getAllTimetables() {
        List<Timetable> timetables = timetableService.getAllTimetables();
        return ResponseEntity.ok(timetables);
    }

    // Get a timetable by ID
    @GetMapping("/{id}")
    public ResponseEntity<Timetable> getTimetableById(@PathVariable Long id) {
        Timetable timetable = timetableService.getTimetableById(id);
        return ResponseEntity.ok(timetable);
    }

    @PostMapping
    public ResponseEntity<Timetable> createModule(@RequestBody Timetable timetable) {
        Timetable newTimetable = timetableService.createTimetable(timetable);
        return ResponseEntity.ok(newTimetable);
    }



    // Update an existing timetable
    @PutMapping("/{id}")
    public ResponseEntity<Timetable> updateTimetable(@PathVariable Long id, @RequestBody Timetable timetableDetails) {
        Timetable updatedTimetable = timetableService.updateTimetable(id, timetableDetails);
        return ResponseEntity.ok(updatedTimetable);
    }

    // Delete a timetable
    @DeleteMapping("/{id}")
    public ResponseEntity<Void> deleteTimetable(@PathVariable Long id) {
        timetableService.deleteTimetable(id);
        return ResponseEntity.noContent().build();
    }

    // Endpoint to retrieve timetables by course code
    @GetMapping("/by-day")
    public ResponseEntity<List<Timetable>> getTimetablesByDay(@RequestParam int day) {
        List<Timetable> timetables = timetableService.findByDayOfWeek(day);

        if (timetables.isEmpty()) {
            return ResponseEntity.notFound().build(); // Return 404 if no timetables found
        } else {
            return ResponseEntity.ok(timetables); // Return 200 OK with the list of timetables
        }
    }

    // Endpoint to get detailed timetable information by course code
    // Endpoint to get detailed timetable information by course code without conversion
    @GetMapping("/details")
    public ResponseEntity<List<Object[]>> getTimetableDetailsByCourseCode(@RequestParam String courseCode) {
        List<Object[]> timetableDetails = timetableService.getTimetableDetailsByCourseCode(courseCode);

        if (timetableDetails.isEmpty()) {
            return ResponseEntity.notFound().build(); // Return 404 if no details found
        } else {
            return ResponseEntity.ok(timetableDetails); // Return 200 OK with the timetable details
        }
    }
}
