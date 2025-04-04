package com.assignment.usermanagement;

import com.assignment.usermanagement.Controller.UserController;
import com.assignment.usermanagement.Entity.Role;
import com.assignment.usermanagement.Entity.User;
import com.assignment.usermanagement.Service.UserService;
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
import org.springframework.security.test.context.support.WithMockUser;
import org.springframework.test.web.servlet.MockMvc;
import org.springframework.test.web.servlet.request.MockMvcRequestBuilders;

import java.util.List;
import java.util.Optional;

@WebMvcTest(UserController.class)
public class UserControllerTest {

    @Autowired
    private MockMvc mockMvc;

    @MockBean
    private UserService userService;

    private final ObjectMapper objectMapper = new ObjectMapper();

    @Test
    @WithMockUser(username = "testuser", roles = {"ADMIN"})
    void getAllUsers_ReturnsList() throws Exception {
        List<User> users = List.of(new User(1L, "akshay", "akshay@example.com", Role.ADMIN));
        Mockito.when(userService.getAllUsers()).thenReturn(users);

        mockMvc.perform(MockMvcRequestBuilders.get("/users"))
                .andExpect(status().isOk())
                .andExpect(jsonPath("$.length()").value(1))
                .andExpect(jsonPath("$[0].username").value("akshay"));
    }

    @Test
    @WithMockUser(username = "testuser", roles = { "ADMIN" })
    void getUserById_Found() throws Exception {
        User user = new User(1L, "akshay", "akshay@example.com", Role.ADMIN);
        Mockito.when(userService.getUserById(1L)).thenReturn(Optional.of(user));

        mockMvc.perform(MockMvcRequestBuilders.get("/users/1"))
                .andExpect(status().isOk())
                .andExpect(jsonPath("$.username").value("akshay"));
    }

    @Test
    @WithMockUser(username = "testuser", roles = { "ADMIN" })
    void getUserById_NotFound() throws Exception {
        Mockito.when(userService.getUserById(99L)).thenReturn(Optional.empty());

        mockMvc.perform(MockMvcRequestBuilders.get("/users/99"))
                .andExpect(status().isNotFound())
                .andExpect(jsonPath("$.message").value("User not found with id: 99"));
    }

    @Test
    @WithMockUser(username = "testuser", roles = { "ADMIN" })
    void createUser_Success() throws Exception {
        User user = new User(null, "luna", "luna@example.com", Role.USER);
        User savedUser = new User(2L, "luna", "luna@example.com", Role.USER);
        Mockito.when(userService.createUser(any(User.class))).thenReturn(savedUser);

        mockMvc.perform(MockMvcRequestBuilders.post("/users")
                .contentType(MediaType.APPLICATION_JSON)
                .content(objectMapper.writeValueAsString(user)))
                .andExpect(status().isOk())
                .andExpect(jsonPath("$.id").value(2));
    }

    @Test
    @WithMockUser(username = "testuser", roles = { "ADMIN" })
    void createUser_DatabaseError() throws Exception {
        User user = new User(null, "fail", "fail@example.com", Role.USER);
        Mockito.when(userService.createUser(any(User.class)))
                .thenThrow(new DataAccessException("DB failure") {
                });

        mockMvc.perform(MockMvcRequestBuilders.post("/users")
                .contentType(MediaType.APPLICATION_JSON)
                .content(objectMapper.writeValueAsString(user)))
                .andExpect(status().isInternalServerError())
                .andExpect(jsonPath("$.message").value("Failed to create user."));
    }

    @Test
    @WithMockUser(username = "testuser", roles = { "ADMIN" })
    void updateUser_Success() throws Exception {
        User updated = new User(1L, "UpdateAkshay", "updatedmail@yahoo.com", Role.ADMIN);
        Mockito.when(userService.updateUser(eq(1L), any(User.class))).thenReturn(updated);

        mockMvc.perform(MockMvcRequestBuilders.put("/users/1")
                .contentType(MediaType.APPLICATION_JSON)
                .content(objectMapper.writeValueAsString(updated)))
                .andExpect(status().isOk())
                .andExpect(jsonPath("$.username").value("UpdateAkshay"));
    }

    @Test
    @WithMockUser(username = "testuser", roles = { "ADMIN" })
    void deleteUser_Success() throws Exception {
        mockMvc.perform(MockMvcRequestBuilders.delete("/users/1"))
                .andExpect(status().isOk());
    }
}
