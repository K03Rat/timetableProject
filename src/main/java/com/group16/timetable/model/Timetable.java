package com.group16.timetable.model;

import java.time.LocalTime;
import java.util.List;
import java.util.Set;

import com.fasterxml.jackson.annotation.JsonIgnore;
import com.fasterxml.jackson.annotation.JsonManagedReference;

import jakarta.persistence.Column;
import jakarta.persistence.Entity;
import jakarta.persistence.GeneratedValue;
import jakarta.persistence.GenerationType;
import jakarta.persistence.Id;
import jakarta.persistence.JoinColumn;
import jakarta.persistence.JoinTable;
import jakarta.persistence.ManyToMany;
import jakarta.persistence.ManyToOne;
import jakarta.persistence.Table;

@Entity
@Table(name = "timetables")
public class Timetable {
    @Id
    @GeneratedValue(strategy = GenerationType.IDENTITY)
    @Column(name = "id")
    private Long id; // Primary key
    
    @Column(name = "semester")
    private int semester;
    
    @ManyToOne // Use ManyToOne as a timetable is related to one module
    @JoinColumn(name = "module_id", nullable = false) // Ensure that module_code cannot be null
    @JsonIgnore 
    private Modules moduleTaught; 
    
    @Column(name = "start_time")
    private LocalTime startTime; 

    @Column(name = "day") // Change column name to reflect integer representation
    private int dayOfWeek; // Integer representation for days of the week (0 = Monday, 1 = Tuesday, etc.)

    @Column(name = "venue")
    private String venue; 
    
    @Column(name ="event_type_num")
    private int type;
    


    // Constructor with required fields
    public Timetable(Modules moduleTaught, LocalTime startTime,  int dayOfWeek, int type) {
        this.moduleTaught = moduleTaught;
        this.startTime = startTime;
        this.dayOfWeek = dayOfWeek;
        this.type = type;
    }

    // Default constructor for JPA
    public Timetable() {super();}

    // Getters and Setters
    public Long getId() {
        return id;
    }

    public void setId(Long timetableId) {
        this.id = timetableId;
    }

    public int getSemester() {
        return semester;
    }

    public void setSemester(int semester) {
        this.semester = semester;
    }

    public Modules getModule() {
        return moduleTaught;
    }

    public void setModule(Modules moduleTaught) {
        this.moduleTaught = moduleTaught;
    }

    public LocalTime getStartTime() {
        return startTime;
    }

    public void setStartTime(LocalTime startTime) {
        this.startTime = startTime;
    }

    public int getDayOfWeek() {
        return dayOfWeek;
    }

    public void setDayOfWeek(int dayOfWeek) {
        this.dayOfWeek = dayOfWeek;
    }

    public String getVenue() {
        return venue;
    }

    public void setVenue(String venue) {
        this.venue = venue;
    }

    @Override
    public String toString() {
        return "Timetable{" +
                "timetableId=" + id +
                ", semester=" + semester +
                ", module=" + moduleTaught +
                ", startTime=" + startTime +
                ", dayOfWeek=" + dayOfWeek +
                ", venue='" + venue + '\'' +
                '}';
    }
}
