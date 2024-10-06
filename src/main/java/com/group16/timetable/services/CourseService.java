package com.group16.timetable.services;

import com.group16.timetable.model.Course;
import com.group16.timetable.repositories.CourseRepository;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;

import java.util.List;
import java.util.Optional;

@Service
public class CourseService {

    private final CourseRepository courseRepository;

    @Autowired
    public CourseService(CourseRepository courseRepository) {
        this.courseRepository = courseRepository;
    }

    // Get all courses
    public List<Course> getAllCourses() {
        return courseRepository.findAll();
    }

    // Get a course by ID
    public Course getCourseById(Long id) {
        Optional<Course> optionalCourse = courseRepository.findById(id);
        return optionalCourse.orElseThrow(() -> new RuntimeException("Course not found with id: " + id));
    }

    // Create a new course
    public Course createCourse(Course course) {
        return courseRepository.save(course);
    }

    // Update a course
    public Course updateCourse(Long id, Course updatedCourse) {
        Course course = getCourseById(id); // Fetch existing course
        course.setCode(updatedCourse.getCode()); // Update fields
        // If you have other fields, update them as well
        return courseRepository.save(course);
    }

    // Delete a course
    public void deleteCourse(Long id) {
        courseRepository.deleteById(id);
    }


    public List<String> getCourseCodesByModuleName(String moduleName) {
        return courseRepository.findCourseCodesByModuleName(moduleName);
    }
    // Method to find a course by its code
    public Optional<Course> findByCode(String courseCode) {
        return courseRepository.findByCode(courseCode);
    }

}
