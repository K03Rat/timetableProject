package com.group16.timetable.services;

import java.util.List;

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
	public List<Lecturer> getLecturers(){
		return lecturerRepository.findAll();
	}
}
