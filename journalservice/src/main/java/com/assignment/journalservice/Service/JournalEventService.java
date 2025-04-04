package com.assignment.journalservice.Service;


import com.assignment.journalservice.Model.JournalEntry;
import com.assignment.journalservice.Repository.JournalEntryRepository;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.dao.DataAccessException;
import org.springframework.stereotype.Service;

import java.util.List;

@Service
public class JournalEventService {

    private final JournalEntryRepository journalEventRepository;

    @Autowired
    public JournalEventService(JournalEntryRepository journalEventRepository) {
        this.journalEventRepository = journalEventRepository;
    }

    public JournalEntry saveEvent(JournalEntry event) {
        try {
            return journalEventRepository.save(event);
        } catch (DataAccessException ex) {
            throw new RuntimeException("Database error while saving the journal event", ex);
        }
    }

    public List<JournalEntry> getAllEvents() {
        try {
            return journalEventRepository.findAll();
        } catch (DataAccessException ex) {
            throw new RuntimeException("Database error while fetching all journal events", ex);
        }
    }

    public JournalEntry getEventById(Long id) {
        try {
            return journalEventRepository.findById(id)
                    .orElseThrow();
        } catch (DataAccessException ex) {
            throw new RuntimeException("Database error while fetching journal event by ID", ex);
        }
    }

    public List<JournalEntry> getEventsByAction(String action) {
        try {
            return journalEventRepository.findByAction(action);
        } catch (DataAccessException ex) {
            throw new RuntimeException("Database error while fetching events by action: " + action, ex);
        }
    }
}
