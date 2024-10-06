package com.group16.timetable.model;

import com.fasterxml.jackson.annotation.JsonIgnore;
import jakarta.persistence.*;

import java.util.Set;

@Entity
@Table(name = "courses")
public class Course {


    @Id
    @GeneratedValue(strategy = GenerationType.IDENTITY)
    @Column(name = "id")
    private Long id;


    @Column(name = "course_code")
    private String code;


    @JsonIgnore
    @ManyToMany
    @JoinTable(
            name = "course_modules",
            joinColumns = @JoinColumn(name = "course_id"),
            inverseJoinColumns = @JoinColumn(name = "module_id")
    )
    private Set<Modules> modules;

    public Course() {
        super();
    }

    public Course(String code) {
        this.code = code;

    }

    public Long getId() {
        return id;
    }

    public Set<Modules> getCourseModules() {
        return modules;
    }

    public String getCode() {
        return code;
    }

    public void setCode(String code) {
        this.code = code;
    }
}
