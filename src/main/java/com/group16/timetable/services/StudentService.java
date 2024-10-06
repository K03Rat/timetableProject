package com.group16.timetable.services;

import java.util.List;
import java.util.Optional;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.http.HttpStatus;
import org.springframework.stereotype.Service;
import org.springframework.web.server.ResponseStatusException;

import com.group16.timetable.model.Student;
import com.group16.timetable.repositories.StudentRepository;

@Service
public class StudentService {
	private final StudentRepository studentRepository;

	@Autowired
	public StudentService(StudentRepository studentRepository) {
		this.studentRepository = studentRepository;
	}

	// Retrieve all students
	public List<Student> getStudents() {
		return studentRepository.findAll();
	}

	// Retrieve a student by ID
	public Student getStudentById(Long id) {
		return studentRepository.findById(id)
				.orElseThrow(() -> new ResponseStatusException(HttpStatus.NOT_FOUND, "Student not found"));
	}

	// Create a new student
	public Student createStudent(Student student) {
		return studentRepository.save(student);
	}

	// Update an existing student
	public Student updateStudent(Long id, Student studentDetails) {
		Student existingStudent = getStudentById(id);
		existingStudent.setStudentFirstName(studentDetails.getStudentFirstName());
		existingStudent.setStudentLastName(studentDetails.getStudentLastName());
		existingStudent.setStudentEmail(studentDetails.getStudentEmail());
		existingStudent.setStudentId(studentDetails.getStudentId());
		existingStudent.setStudentCourse(studentDetails.getCourseId()); // Ensure you have a setter in Student class for Course
		return studentRepository.save(existingStudent);
	}

	// Delete a student by ID
	public void deleteStudent(Long id) {
		Student existingStudent = getStudentById(id);
		studentRepository.delete(existingStudent);
	}

	public Optional<Student> findStudentByFullName(String firstName, String lastName) {
		return studentRepository.findByFirstNameAndLastName(firstName, lastName);
	}

	// Method to get the timetable for a student based on their ID
	public List<Object[]> getTimetableByStudentId(Long studentId) {
		return studentRepository.findTimetableByStudentId(studentId);
	}
}
