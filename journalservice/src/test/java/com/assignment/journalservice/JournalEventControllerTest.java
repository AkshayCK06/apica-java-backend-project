package com.assignment.journalservice;

import com.assignment.journalservice.Controller.JournalEventController;
import com.assignment.journalservice.Model.JournalEntry;
import com.assignment.journalservice.Model.Role;
import com.assignment.journalservice.Service.JournalEventService;
import com.fasterxml.jackson.databind.ObjectMapper;

import org.junit.jupiter.api.Test;
import org.mockito.Mockito;

import static org.mockito.ArgumentMatchers.*;
import static org.springframework.test.web.servlet.result.MockMvcResultMatchers.*;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.boot.test.autoconfigure.web.servlet.WebMvcTest;
import org.springframework.dao.DataAccessException;
import org.springframework.boot.test.mock.mockito.MockBean;
import org.springframework.http.MediaType;
import org.springframework.test.web.servlet.MockMvc;
import org.springframework.test.web.servlet.request.MockMvcRequestBuilders;

import java.time.LocalDateTime;
import java.util.List;
import java.util.Optional;

@WebMvcTest(JournalEventController.class)
public class JournalEventControllerTest {

    @Autowired
    private MockMvc mockMvc;

    @MockBean
    private JournalEventService journalEventService;

    private final ObjectMapper objectMapper = new ObjectMapper();

    @Test
    void getAllJournalEvents_ReturnsList() throws Exception {
        JournalEntry entry = new JournalEntry(1L, 100L, "akshay", "akshay@example.com", Role.USER, "CREATE", LocalDateTime.now());
        Mockito.when(journalEventService.getAllEvents()).thenReturn(List.of(entry));

        mockMvc.perform(MockMvcRequestBuilders.get("/journal-events"))
                .andExpect(status().isOk())
                .andExpect(jsonPath("$.length()").value(1))
                .andExpect(jsonPath("$[0].username").value("akshay"));
    }

    @Test
    void getEventById_Found() throws Exception {
        JournalEntry entry = new JournalEntry(1L, 100L, "akshay", "akshay@example.com", Role.USER, "CREATE", LocalDateTime.now());
        Mockito.when(journalEventService.getEventById(1L)).thenReturn(entry);

        mockMvc.perform(MockMvcRequestBuilders.get("/journal-events/1"))
                .andExpect(status().isOk())
                .andExpect(jsonPath("$.username").value("akshay"));
    }

    @Test
    void getEventById_NotFound() throws Exception {
        Mockito.when(journalEventService.getEventById(99L))
                .thenThrow(new RuntimeException("Event not found with id: 99"));

        mockMvc.perform(MockMvcRequestBuilders.get("/journal-events/99"))
                .andExpect(status().isNotFound())
                .andExpect(jsonPath("$.message").value("Event not found with id: 99"));
    }

    @Test
    void getEventsByAction_Success() throws Exception {
        JournalEntry entry = new JournalEntry(1L, 100L, "akshay", "akshay@example.com", Role.USER, "CREATE", LocalDateTime.now());
        Mockito.when(journalEventService.getEventsByAction("CREATE")).thenReturn(List.of(entry));

        mockMvc.perform(MockMvcRequestBuilders.get("/journal-events/action/CREATE"))
                .andExpect(status().isOk())
                .andExpect(jsonPath("$.length()").value(1));
    }

    @Test
    void getAllJournalEvents_DatabaseError() throws Exception {
        Mockito.when(journalEventService.getAllEvents())
                .thenThrow(new DataAccessException("DB failure") {});

        mockMvc.perform(MockMvcRequestBuilders.get("/journal-events"))
                .andExpect(status().isInternalServerError())
                .andExpect(jsonPath("$.message").value("Failed to fetch journal events."));
    }
}
