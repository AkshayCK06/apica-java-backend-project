package com.assignment.journalservice.Controller;




import java.util.List;


import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.web.bind.annotation.DeleteMapping;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.PathVariable;
import org.springframework.web.bind.annotation.PostMapping;
import org.springframework.web.bind.annotation.PutMapping;
import org.springframework.web.bind.annotation.RequestBody;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RestController;

import com.assignment.journalservice.Model.JournalEntry;
import com.assignment.journalservice.Model.JournalEvent;
import com.assignment.journalservice.Service.JournalEventService;


@RestController
@RequestMapping("/journal-events")
public class JournalEventController {

    private final JournalEventService journalEventService;

    @Autowired
    public JournalEventController(JournalEventService journalEventService) {
        this.journalEventService = journalEventService;
    }

    //  Get all journaled user events
    @GetMapping
    public List<JournalEntry> getAllJournalEvents() {
        return journalEventService.getAllEvents();
    }

    //  Get a journal event by ID
    @GetMapping("/{id}")
    public JournalEntry getEventById(@PathVariable Long id) {
        return journalEventService.getEventById(id);
    }

    //  Get journaled events by user action type (e.g., CREATED, UPDATED)
    @GetMapping("/action/{action}")
    public List<JournalEntry> getEventsByAction(@PathVariable String action) {
        return journalEventService.getEventsByAction(action);
    }
}