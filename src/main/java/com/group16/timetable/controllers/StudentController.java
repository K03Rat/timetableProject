package com.group16.timetable.controllers;

import java.util.List;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RestController;
import com.group16.timetable.services.*;
import com.group16.timetable.model.*;
@RestController
@RequestMapping(path="api/v1/student")
public class StudentController {
	private final StudentService studentService;
	@Autowired
	public StudentController(StudentService studentService) {
		this.studentService = studentService;
	}
	@GetMapping
	public List<Student> getStudents(){
		return studentService.getStudents();
	}
}
