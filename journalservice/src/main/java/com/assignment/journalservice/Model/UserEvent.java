package com.assignment.journalservice.Model;



import java.time.LocalDateTime;


import lombok.AllArgsConstructor;
import lombok.Builder;
import lombok.Data;
import lombok.NoArgsConstructor;

@Data
@AllArgsConstructor
@NoArgsConstructor
@Builder
public class UserEvent {
    private Long id;
    private String username;
    private String email;
    private Role role;
    private String action;
    private LocalDateTime timestamp;
}