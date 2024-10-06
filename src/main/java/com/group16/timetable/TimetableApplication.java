package com.group16.timetable;

import com.group16.timetable.model.Course;
import com.group16.timetable.model.Student;
import com.group16.timetable.repositories.StudentRepository;
import org.slf4j.LoggerFactory;
import org.slf4j.Logger;
import org.springframework.boot.CommandLineRunner;
import org.springframework.boot.SpringApplication;
import org.springframework.boot.autoconfigure.SpringBootApplication;


@SpringBootApplication
public class TimetableApplication implements CommandLineRunner {
	private static final Logger Logger = LoggerFactory.getLogger(TimetableApplication.class);
	private final StudentRepository studentRepository;
	public TimetableApplication(StudentRepository studentRepository){
		this.studentRepository = studentRepository;
	}
	public static void main(String[] args) {
		SpringApplication.run(TimetableApplication.class, args);
		Logger.info("Application started");
	}
	@Override
	public void run(String... args) throws Exception{
		long id = 22103521;
		studentRepository.save(new Student(id,"Kristian", "Ratnikov", "k.ratnikov1@universityofgalway.ie", 54));
	}
	

}
