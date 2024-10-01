package com.group16.timetable;

import org.slf4j.LoggerFactory;
import org.slf4j.Logger;
import org.springframework.boot.SpringApplication;
import org.springframework.boot.autoconfigure.SpringBootApplication;


@SpringBootApplication
public class TimetableApplication {
	private static final Logger Logger = LoggerFactory.getLogger(TimetableApplication.class);

	public static void main(String[] args) {
		SpringApplication.run(TimetableApplication.class, args);
		Logger.info("Application started");
	}
	

}
