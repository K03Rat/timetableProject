package com.group16.timetable.controllers;

import java.util.List;

import org.springframework.beans.factory.annotation.Autowired;

import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RestController;

import com.group16.timetable.services.LecturerService;
import com.group16.timetable.model.*;
@RestController 
@RequestMapping(path="api/v1/lecturer")
public class LecturerController {
	private final LecturerService lecturerService;
	@Autowired
	public LecturerController(LecturerService lecturerService) {
		this.lecturerService = lecturerService;
	}
	@GetMapping
	public List<Lecturer> getLecturers(){
		return lecturerService.getLecturers();
	}
	
}
