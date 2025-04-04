package com.assignment.journalservice.Kafka;

import lombok.*;

@Data
@NoArgsConstructor
@AllArgsConstructor
public class UserEventPayload {
    private Long id;
    private String username;
    private String email;
    private String role;
    private String action;
}
