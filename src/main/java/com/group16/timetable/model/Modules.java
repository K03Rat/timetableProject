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
import jakarta.persistence.ManyToMany;
import jakarta.persistence.Table;

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
	
	@ManyToMany(mappedBy = "modules")
	@JsonIgnore
	List<Course> courses;
	
	public Modules() {
		super();
	}
	
	public Modules(String code, String name) {
		this.code =code;
		this.name = name;
	}
	public Modules(String code, String name, List<Course> courses) {
		this.code = code;
		this.name = name;
		this.courses = courses;
	}
	
	public Long getModuleId() {
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
	public List<Course> getCoursesStuding(){
		return courses;
	}
	
}
