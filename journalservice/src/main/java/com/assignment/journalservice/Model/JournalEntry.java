package com.assignment.journalservice.Model;

import jakarta.persistence.*;
import lombok.*;
import java.time.LocalDateTime;

@Entity
@Table(name = "journal_entries")
@Data
@NoArgsConstructor
@AllArgsConstructor
@Builder
public class JournalEntry {

    public JournalEntry(long l, long m, String string, String string2, Role user, String string3, LocalDateTime now) {
        //TODO Auto-generated constructor stub
    }

    @Id
    @GeneratedValue(strategy = GenerationType.IDENTITY)
    private Long entryId;

    private Long userId;

    private String username;

    private String email;

    private String role;

    private String action;

    private LocalDateTime timestamp;
}
