package com.assignment.journalservice.Kafka;



import lombok.RequiredArgsConstructor;
import lombok.extern.slf4j.Slf4j;

import org.springframework.kafka.annotation.KafkaListener;
import org.springframework.stereotype.Service;

import com.assignment.journalservice.Model.JournalEntry;
import com.assignment.journalservice.Model.UserEvent;
import com.assignment.journalservice.Repository.JournalEntryRepository;

@Service
@Slf4j
@RequiredArgsConstructor
public class KafkaConsumerService {

   private final JournalEntryRepository journalRepository;

    @KafkaListener(topics = "user-events",
    groupId = "journal-service-group",
    containerFactory = "userEventKafkaListenerContainerFactory")
    public void consumeEvent(UserEvent event) {
        log.info("Received user event: {}", event);

        JournalEntry entry = new JournalEntry();
        entry.setAction(event.getAction());
        entry.setUserId(event.getId());
        entry.setUsername(event.getUsername());
        entry.setEmail(event.getEmail());
        entry.setRole(event.getRole().toString());
        entry.setTimestamp(event.getTimestamp());

        journalRepository.save(entry);

    }
}
