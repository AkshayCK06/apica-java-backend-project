package com.assignment.journalservice.Model;
import jakarta.persistence.*;
import lombok.*;

import java.time.LocalDateTime;

@Entity
@Table(name = "journal_events")
@Data
@NoArgsConstructor
@AllArgsConstructor
@Builder
public class JournalEvent {
    @Id
    @GeneratedValue(strategy = GenerationType.IDENTITY)
    private Long id;

    private Long userId;
    private String username;
    private String email;

    @Enumerated(EnumType.STRING)
    private Role role;

    private String action;

    private LocalDateTime timestamp;
}
