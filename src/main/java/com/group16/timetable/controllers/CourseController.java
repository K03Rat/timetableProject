package com.group16.timetable.controllers;

import com.group16.timetable.model.Course;
import com.group16.timetable.services.CourseService;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.*;

import java.net.URI;
import java.util.List;
import java.util.Optional;

@RestController
@RequestMapping("/api/v1/courses")
public class CourseController {

    private final CourseService courseService;

    @Autowired
    public CourseController(CourseService courseService) {
        this.courseService = courseService;
    }

    // Get all courses
    @GetMapping
    public List<Course> getAllCourses() {
        return courseService.getAllCourses();
    }

    // Get a course by ID
    @GetMapping("/{id}")
    public ResponseEntity<Course> getCourseById(@PathVariable Long id) {
        Course course = courseService.getCourseById(id);
        return new ResponseEntity<>(course, HttpStatus.OK);
    }

    // Create a new course
    @PostMapping
    public ResponseEntity<Course> createCourse( @RequestBody Course course) {
        Course createdCourse = courseService.createCourse(course);
        return ResponseEntity.created(URI.create("/api/v1/courses/" + createdCourse.getId())).body(createdCourse);
    }

    // Update a course
    @PutMapping("/{id}")
    public ResponseEntity<Course> updateCourse(@PathVariable Long id,  @RequestBody Course course) {
        Course updatedCourse = courseService.updateCourse(id, course);
        return new ResponseEntity<>(updatedCourse, HttpStatus.OK);
    }

    // Delete a course
    @DeleteMapping("/{id}")
    public ResponseEntity<Void> deleteCourse(@PathVariable Long id) {
        courseService.deleteCourse(id);
        return ResponseEntity.noContent().build();
    }

    @GetMapping("/codes/module")
    public List<String> getCourseCodesByModule(@RequestParam String moduleName) {
        return courseService.getCourseCodesByModuleName(moduleName);
    }
    @GetMapping("/course")
    public ResponseEntity<Course> getCourseByCode(@RequestParam String code) {
        Optional<Course> course = courseService.findByCode(code); // Call the service method

        return course.map(ResponseEntity::ok) // Return 200 OK if found
                .orElse(ResponseEntity.notFound().build()); // Return 404 Not Found if not found
    }


}
