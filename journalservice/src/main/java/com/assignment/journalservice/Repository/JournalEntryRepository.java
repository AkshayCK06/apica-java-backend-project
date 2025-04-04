package com.assignment.journalservice.Repository;


import java.util.List;

import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.stereotype.Repository;

import com.assignment.journalservice.Model.JournalEntry;
import com.assignment.journalservice.Model.JournalEvent;

@Repository
public interface JournalEntryRepository extends JpaRepository<JournalEntry, Long> {

    JournalEntry save(JournalEntry journalEvent);
    
    // Find events by action (e.g., CREATED, UPDATED, DELETED)
    List<JournalEntry> findByAction(String action);
}
