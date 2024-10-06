package com.group16.timetable.model;

import com.fasterxml.jackson.annotation.JsonIgnore;
import com.fasterxml.jackson.annotation.JsonProperty;
import jakarta.persistence.*;

import java.util.List;


@Entity
@Table(name = "lecturers")
public class Lecturer {

    @Id
    @GeneratedValue(strategy = GenerationType.IDENTITY)
    @Column(name = "id")
    private Long id;

    @JsonProperty("firstName") // Optional: helps with JSON mapping
    private String firstName;

    @JsonProperty("lastName") // Optional: helps with JSON mapping
    private String lastName;

    @Column(name = "email")
    private String email;

    @ManyToMany
    @JoinTable(name = "lecturer_module", joinColumns = @JoinColumn(name = "lecturer_id"), inverseJoinColumns = @JoinColumn(name = "module_id"))
    @JsonIgnore
    private List<Modules> modules;

    // Default Constructor
    public Lecturer() {
        super();
    }

    // Constructor with fields
    public Lecturer(String firstName, String lastName) {
        this.firstName = firstName;
        this.lastName = lastName;
        this.email = generateEmail(firstName, lastName); // This ensures the email is populated
    }

    private String generateEmail(String firstName, String lastName) {
        return (firstName.charAt(0) + "." + lastName + "@universityofgalway.ie").toLowerCase();
    }

    public Long getId() {
        return id;
    }

    public void setId(Long id) {
        this.id = id;
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

    public String getEmail() {
        return email;
    }

    public void setEmail(String email) {
        this.email = email;
    }


    public List<Modules> getModules() {
        return modules;
    }


    @Override
    public String toString() {
        return "Lecturer{" + "id=" + id + ", firstName='" + firstName + '\'' + ", lastName='" + lastName + '\'' + ", email='" + email + '\'' + '}';
    }
}
