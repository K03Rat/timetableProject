package com.group16.timetable.controllers;

import java.net.URI;
import java.util.List;
import java.util.Optional;


import com.group16.timetable.services.LecturerService;
import com.group16.timetable.model.Lecturer;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.*;

@RestController
@RequestMapping(path = "api/v1/lecturer")
public class LecturerController {

	private final LecturerService lecturerService;

	@Autowired
	public LecturerController(LecturerService lecturerService) {
		this.lecturerService = lecturerService;
	}

	// Get all lecturers
	@GetMapping
	public ResponseEntity<List<Lecturer>> getAllLecturers() {
		List<Lecturer> lecturers = lecturerService.getAllLecturers();
		return ResponseEntity.ok(lecturers);
	}

	// Get a lecturer by ID
	@GetMapping("/{id}")
	public ResponseEntity<Lecturer> getLecturerById(@PathVariable Long id) {
		Lecturer lecturer = lecturerService.getLecturerById(id);
		if (lecturer != null) {
			return ResponseEntity.ok(lecturer);
		} else {
			return ResponseEntity.notFound().build();
		}
	}

	// Create a new lecturer
	@PostMapping
	public ResponseEntity<Lecturer> addNewLecturer( @RequestBody Lecturer lecturer) {
		Lecturer createdLecturer = lecturerService.createLecturer(lecturer);
		URI location = URI.create("/api/v1/lecturer/" + createdLecturer.getId());
		return ResponseEntity.created(location).body(createdLecturer);
	}

	// Update an existing lecturer
	@PutMapping("/{id}")
	public ResponseEntity<Lecturer> updateLecturer(@PathVariable Long id,  @RequestBody Lecturer lecturer) {
		// Convert the DTO to a Lecturer entity

		Lecturer updatedLecturer = lecturerService.updateLecturer(id, lecturer);

		if (updatedLecturer != null) {
			return ResponseEntity.ok(updatedLecturer);
		} else {
			return ResponseEntity.notFound().build();
		}
	}

	// Delete a lecturer
	@DeleteMapping("/{id}")
	public ResponseEntity<Void> deleteLecturer(@PathVariable Long id) {
		if (lecturerService.deleteLecturer(id)) {
			return ResponseEntity.noContent().build();
		} else {
			return ResponseEntity.notFound().build();
		}
	}
	// Get a lecturer by email
	@GetMapping("/email")
	public ResponseEntity<Lecturer> getLecturerByEmail(@RequestParam String email) {
		Optional<Lecturer> lecturer = lecturerService.findByEmail(email);
		if (lecturer.isPresent()) {
			return ResponseEntity.ok(lecturer.get());
		} else {
			return ResponseEntity.notFound().build();
		}
	}
	// Get timetable details for a lecturer
	@GetMapping("/{id}/timetable")
	public ResponseEntity<List<Object[]>> getTimetableDetailsByLecturerId(@PathVariable Long id) {
		List<Object[]> timetableDetails = lecturerService.getTimetableDetailsByLecturerId(id);

		if (timetableDetails.isEmpty()) {
			return ResponseEntity.notFound().build(); // Return 404 if no details found
		} else {
			return ResponseEntity.ok(timetableDetails); // Return 200 OK with timetable details
		}
	}
}
