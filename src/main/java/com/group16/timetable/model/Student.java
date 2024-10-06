package com.group16.timetable.model;

import jakarta.persistence.Column;
import jakarta.persistence.Entity;
import jakarta.persistence.Id;

@Entity
public class Student {
	@Id
	private Long id;
	@Column(name="student_first_name")
	private String firstName;

	@Column(name="student_last_name")
	private String lastName;

	@Column(name="student_email")
	private String email;


    private int courseId;

	public Student() {
		super();
	}

	public Student(Long id, String firstName, String lastName, String email, int courseId) {
		this.id = id;
		this.firstName = firstName;
		this.lastName = lastName;
		this.email = email;
		this.courseId = courseId;
	}


	public Long getStudentId() {
		return id;
	}

	public String getStudentFirstName() {
		return firstName;
	}

	public String getStudentLastName() {
		return lastName;
	}

	public String getStudentEmail() {
		return email;
	}

	public void setStudentId(Long id) {
		this.id = id;
	}

	public void setStudentFirstName(String firstName) {
		this.firstName = firstName;
	}

	public void setStudentLastName(String lastName) {
		this.lastName = lastName;
	}

	public void setStudentEmail(String email) {
		this.email = email;
	}

	public void setStudentCourse(int course){
		this.courseId = course;
	}



	public int getCourseId() {
		return courseId;
	}
}
