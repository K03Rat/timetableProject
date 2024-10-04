package com.group16.timetable.model;

import java.util.HashSet;
import java.util.List;
import java.util.Set;

import com.fasterxml.jackson.annotation.JsonIgnore;

import jakarta.persistence.Column;
import jakarta.persistence.Entity;
import jakarta.persistence.GeneratedValue;
import jakarta.persistence.GenerationType;
import jakarta.persistence.Id;
import jakarta.persistence.ManyToMany;
import jakarta.persistence.Table;

@Entity
@Table(name = "lecturers")
public class Lecturer {
	
	@Id
	@GeneratedValue(strategy = GenerationType.IDENTITY)
    @Column(name = "id")
	private Long id;
	
	@Column(name = "first_name", nullable = false)
    private String firstName;

    @Column(name = "last_name", nullable = false)
    private String lastName;

    @Column(name = "email")
    private String email;
	
	@ManyToMany(mappedBy = "lecturers")
	@JsonIgnore
	private List<Modules> modules;
	
	// Default Constructor
	public Lecturer() {
		super();
	}
	
	// Constructor with fields
	public Lecturer(String firstName, String lastName, String email) {
		this.firstName = firstName;
		this.lastName = lastName;
		this.email = email;
	}
	
	// Constructor with timetables
	public Lecturer(String firstName, String lastName, String email, List<Modules> modules) {
		this.firstName = firstName;
		this.lastName = lastName;
		this.email = email;
		this.modules = modules;
	}

	// Getters and Setters
	public String getEmail() {
		return email;
	}
	
	public void setEmail(String email) {
		this.email = email;
	}
	
	public String getFirstName() {
		return firstName;
	}
	
	public void setFirstName(String firstName) {
		this.firstName = firstName;
	}
	
	public String getLastName() {
		return lastName;
	}
	
	public void setLastName(String lastName) {
		this.lastName = lastName;
	}
	
	public Long getId() {
		return id;
	}
	
	public void setId(Long id) {
		this.id = id;
	}
	
	public List<Modules> getTimetables() {
		return modules;
	}

	public void setTimetables(List<Modules> timetables) {
		this.modules = timetables;
	}


	@Override
	public String toString() {
		return "Lecturer{" +
		       "id=" + id +
		       ", firstName='" + firstName + '\'' +
		       ", lastName='" + lastName + '\'' +
		       ", email='" + email + '\'' +
		       '}';
	}
}
