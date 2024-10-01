package com.group16.timetable.model;

import java.util.List;
import java.util.Set;

import com.fasterxml.jackson.annotation.JsonIgnore;
import com.fasterxml.jackson.annotation.JsonManagedReference;

import jakarta.persistence.Column;
import jakarta.persistence.Entity;
import jakarta.persistence.GeneratedValue;
import jakarta.persistence.GenerationType;
import jakarta.persistence.Id;
import jakarta.persistence.JoinTable;
import jakarta.persistence.ManyToMany;
import jakarta.persistence.Table;
import jakarta.persistence.JoinColumn;
@Entity
@Table(name = "courses")
public class Course {
	@Id
	@GeneratedValue(strategy = GenerationType.AUTO)
	@Column(name = "id")
	private Long id;
	
	@Column(name="course_code")
	private String code;
	
	@ManyToMany
	@JoinTable(
	    name = "course_module",
	    joinColumns = @JoinColumn(name="course_id"),
	    inverseJoinColumns = @JoinColumn(name="module_id")
	)
	@JsonManagedReference
	private List<Modules> modules;
	
	
	public Course() {
		super();
	}
	public Course(String code, List<Modules> modules) {
		this.code = code;
		this.modules = modules;
	}
	
	public List<Modules> getCourseModules(){
		return modules;
	}
	
	public String getCourseCode() {
		return code;
	}
	
	public Long getCourseId() {
		return id;
	}
	
	

	
	
}
