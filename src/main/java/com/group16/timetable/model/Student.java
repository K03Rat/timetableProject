package com.group16.timetable.model;

import jakarta.persistence.Column;
import jakarta.persistence.Entity;
import jakarta.persistence.Id;
import jakarta.persistence.JoinColumn;
import jakarta.persistence.ManyToOne;

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
	
	@ManyToOne
    @JoinColumn(name = "course_id", nullable = false)
    private Course course;
	
	private Student() {
		super();
	}
	public Student(Long id, String firstName, String lastName, String email, Course course ) {
		this.id = id;
		this.firstName = firstName;
		this.lastName = lastName;
		this.email = email;
		this.course = course;
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
	
	public String getStudentCourseCode() {
		return course.getCourseCode();
	}
	
	
}
