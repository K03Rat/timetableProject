package com.group16.timetable.model;

import java.util.List;
import java.util.Set;

import com.fasterxml.jackson.annotation.JsonBackReference;
import com.fasterxml.jackson.annotation.JsonIgnore;
import com.fasterxml.jackson.annotation.JsonIgnoreProperties;
import com.fasterxml.jackson.annotation.JsonManagedReference;

import jakarta.persistence.*;

@Entity
@Table(name="modules")
public class Modules {
	@Id
	@GeneratedValue(strategy = GenerationType.IDENTITY)
	private Long id;
	
	@Column(name = "module_code")
	private String code;
	
	@Column(name="module_name")
	private String name;

	@JsonIgnore
	@ManyToMany(mappedBy = "modules")
	private Set<Course> courses;

	public Set<Timetable> getTimetables() {
		return timetables;
	}

	public void setTimetables(Set<Timetable> timetables) {
		this.timetables = timetables;
	}

	@JsonIgnore
	@OneToMany(mappedBy = "moduleTaught")
	private Set<Timetable> timetables;

	@JsonIgnore
	@ManyToMany(mappedBy="modules")
	Set<Lecturer> lecturers;
	
	public Modules() {
		super();
	}
	
	public Modules(String code, String name) {
		this.code =code;
		this.name = name;
	}
	
	public Long getId() {
		return id;
	}
	
	public String getModuleName() {
		return name;
	}
	public String getModuleCode() {
		return code;
	}
	
	public void setModuleCode(String code) {
		this.code = code;
	}
	
	public void setModuleName(String name){
		this.name = name;
	}
//	public Set<Course> getCoursesStuding(){
//		return courses;
//	}
	public Set<Lecturer> getLecturers(){return lecturers;}
	
}
