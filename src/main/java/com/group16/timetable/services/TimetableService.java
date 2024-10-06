package com.group16.timetable.services;

import com.group16.timetable.exceptions.ResourceNotFoundException;
import com.group16.timetable.model.Timetable;
import com.group16.timetable.repositories.TimetableRepository;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;

import java.util.List;
import java.util.Map;

@Service
public class TimetableService {

    private final TimetableRepository timetableRepository;

    @Autowired
    public TimetableService(TimetableRepository timetableRepository) {
        this.timetableRepository = timetableRepository;
    }

    // Get all timetables
    public List<Timetable> getAllTimetables() {
        return timetableRepository.findAll();
    }

    // Get timetable by ID
    public Timetable getTimetableById(Long id) {
        return timetableRepository.findById(id)
                .orElseThrow(() -> new ResourceNotFoundException("Timetable not found with id " + id));
    }

    // Create a new timetable
    public Timetable createTimetable(Timetable timetable) {
        return timetableRepository.save(timetable);
    }

    // Update an existing timetable
    public Timetable updateTimetable(Long id, Timetable timetableDetails) {
        Timetable existingTimetable = getTimetableById(id);

        // Update fields
        existingTimetable.setSemester(timetableDetails.getSemester());
        existingTimetable.setModule(timetableDetails.getModule());
        existingTimetable.setStartTime(timetableDetails.getStartTime());
        existingTimetable.setDayOfWeek(timetableDetails.getDayOfWeek());
        existingTimetable.setVenue(timetableDetails.getVenue());
        existingTimetable.setType(timetableDetails.getType());

        return timetableRepository.save(existingTimetable);
    }

    // Delete a timetable
    public void deleteTimetable(Long id) {
        if (!timetableRepository.existsById(id)) {
            throw new ResourceNotFoundException("Timetable not found with id " + id);
        }
        timetableRepository.deleteById(id);
    }

    // Method to find timetables by course code
    public List<Timetable> findByDayOfWeek(int day) {
        return timetableRepository.findByDayOfWeek(day);
    }

    // Method to get timetable details by course code without converting to a map
    public List<Object[]> getTimetableDetailsByCourseCode(String courseCode) {
        return timetableRepository.findTimetableDetailsByCourseCode(courseCode);
    }
}
