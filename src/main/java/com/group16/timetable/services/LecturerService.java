package com.group16.timetable.services;

import java.util.List;
import java.util.Optional;

import com.group16.timetable.exceptions.ResourceNotFoundException;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;
import com.group16.timetable.model.*;
import com.group16.timetable.repositories.LecturerRepository;
@Service
public class LecturerService {

	private final LecturerRepository lecturerRepository;

	@Autowired
	public LecturerService(LecturerRepository lecturerRepository) {
		this.lecturerRepository = lecturerRepository;
	}

	// Get all lecturers
	public List<Lecturer> getAllLecturers() {
		return lecturerRepository.findAll();
	}

	// Get a lecturer by ID
	public Lecturer getLecturerById(Long id) {
		return lecturerRepository.findById(id)
				.orElseThrow(() -> new ResourceNotFoundException("Lecturer not found with id " + id));
	}

	// Create a new lecturer
	public Lecturer createLecturer(Lecturer lecturer) {
		// Generate email before saving the lecturer
		if (lecturer.getFirstName() != null && lecturer.getLastName() != null) {
			lecturer.setEmail(generateEmail(lecturer.getFirstName(), lecturer.getLastName()));
		}
		return lecturerRepository.save(lecturer);
	}

	private String generateEmail(String firstName, String lastName) {
		return (firstName.charAt(0) + "." + lastName + "@universityofgalway.ie").toLowerCase();
	}

	// Update an existing lecturer
	public Lecturer updateLecturer(Long id, Lecturer lecturerDetails) {
		Lecturer lecturer = lecturerRepository.findById(id)
				.orElseThrow(() -> new ResourceNotFoundException("Lecturer not found with id " + id));

		lecturer.setFirstName(lecturerDetails.getFirstName());
		lecturer.setLastName(lecturerDetails.getLastName());
		lecturer.setEmail(lecturerDetails.getEmail());
		// Update modules if needed, handle according to your logic

		return lecturerRepository.save(lecturer);
	}

	// Delete a lecturer
	public boolean deleteLecturer(Long id) {
		// Check if the lecturer exists; if not, throw an exception
		lecturerRepository.findById(id)
				.orElseThrow(() -> new ResourceNotFoundException("Lecturer not found with id " + id));

		// Proceed to delete the lecturer
		lecturerRepository.deleteById(id);
		return true;  // Return true if the deletion was successful
	}

	// Method to find a lecturer by email
	public Optional<Lecturer> findByEmail(String email) {
		return lecturerRepository.findByEmail(email);
	}

	// Method to get timetable details for a lecturer
	public List<Object[]> getTimetableDetailsByLecturerId(Long lecturerId) {
		return lecturerRepository.findTimetableDetailsByLecturerId(lecturerId);
	}
}
