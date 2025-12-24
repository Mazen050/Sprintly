package com.sprintly.backend;

import static org.mockito.ArgumentMatchers.any;
import static org.mockito.ArgumentMatchers.eq;
import static org.springframework.test.web.servlet.request.MockMvcRequestBuilders.*;
import static org.springframework.test.web.servlet.result.MockMvcResultMatchers.*;

import java.util.List;
import java.util.Optional;

import com.fasterxml.jackson.databind.ObjectMapper;
import com.sprintly.backend.dtos.RegisterUserRequest;
import com.sprintly.backend.dtos.UpdateUserRequest;
import com.sprintly.backend.dtos.UserDto;
import com.sprintly.backend.entities.Users;
import com.sprintly.backend.mappers.UserMapper;
import com.sprintly.backend.repositories.UserRepository;

import org.junit.jupiter.api.Test;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.boot.test.autoconfigure.web.servlet.AutoConfigureMockMvc;
import org.springframework.boot.test.autoconfigure.web.servlet.WebMvcTest;
import org.springframework.boot.test.mock.mockito.MockBean;
import org.springframework.http.MediaType;
import org.springframework.security.crypto.password.PasswordEncoder;
import org.springframework.test.web.servlet.MockMvc;

import static org.mockito.Mockito.*;

// @WebMvcTest(UserController.class)
// @AutoConfigureMockMvc(addFilters = false)
class UserControllerTest {

    @Autowired
    private MockMvc mockMvc;

    @Autowired
    private ObjectMapper objectMapper;

    @MockBean
    private UserRepository userRepository;

    @MockBean
    private UserMapper userMapper;

    @MockBean
    private PasswordEncoder passwordEncoder;

    // ================= GET ALL =================
    @Test
    void getUsers_success() throws Exception {
        Users user = new Users();
        user.setId(1L);

        UserDto dto = new UserDto();
        dto.setId(1L);

        when(userRepository.findAll()).thenReturn(List.of(user));
        when(userMapper.toDto(user)).thenReturn(dto);

        mockMvc.perform(get("/users"))
                .andExpect(status().isOk())
                .andExpect(jsonPath("$[0].id").value(1));
    }

    // ================= GET BY ID =================
    @Test
    void getUserById_found() throws Exception {
        Users user = new Users();
        user.setId(1L);

        UserDto dto = new UserDto();
        dto.setId(1L);

        when(userRepository.findById(1L)).thenReturn(Optional.of(user));
        when(userMapper.toDto(user)).thenReturn(dto);

        mockMvc.perform(get("/users/1"))
                .andExpect(status().isOk())
                .andExpect(jsonPath("$.id").value(1));
    }

    @Test
    void getUserById_notFound() throws Exception {
        when(userRepository.findById(99L)).thenReturn(Optional.empty());

        mockMvc.perform(get("/users/99"))
                .andExpect(status().isNotFound());
    }

    // ================= CREATE =================
    @Test
    void createUser_success() throws Exception {
        RegisterUserRequest request = new RegisterUserRequest();
        request.setEmail("test@test.com");
        request.setPassword("password123");

        Users user = new Users();
        user.setId(10L);

        UserDto dto = new UserDto();
        dto.setId(10L);

        when(userMapper.toEntity(any(RegisterUserRequest.class))).thenReturn(user);
        when(passwordEncoder.encode("password123")).thenReturn("hashed");
        when(userRepository.save(any())).thenAnswer(i -> i.getArgument(0));
        when(userMapper.toDto(any())).thenReturn(dto);

        mockMvc.perform(post("/users")
                        .contentType(MediaType.APPLICATION_JSON)
                        .content(objectMapper.writeValueAsString(request)))
                .andExpect(status().isCreated())
                .andExpect(header().string("Location", "http://localhost/users/10"))
                .andExpect(jsonPath("$.id").value(10));
    }

    // ================= UPDATE =================
    @Test
    void updateUser_success() throws Exception {
        UpdateUserRequest request = new UpdateUserRequest();

        Users user = new Users();
        user.setId(1L);

        UserDto dto = new UserDto();
        dto.setId(1L);

        when(userRepository.findById(1L)).thenReturn(Optional.of(user));
        when(userMapper.toDto(user)).thenReturn(dto);

        mockMvc.perform(put("/users/1")
                        .contentType(MediaType.APPLICATION_JSON)
                        .content(objectMapper.writeValueAsString(request)))
                .andExpect(status().isOk())
                .andExpect(jsonPath("$.id").value(1));

        verify(userMapper).update(eq(request), eq(user));
        verify(userRepository).save(user);
    }

    @Test
    void updateUser_notFound() throws Exception {
        when(userRepository.findById(99L)).thenReturn(Optional.empty());

        mockMvc.perform(put("/users/99")
                        .contentType(MediaType.APPLICATION_JSON)
                        .content("{}"))
                .andExpect(status().isNotFound());
    }

    // ================= DELETE =================
    @Test
    void deleteUser_success() throws Exception {
        Users user = new Users();
        user.setId(1L);

        when(userRepository.findById(1L)).thenReturn(Optional.of(user));
        doNothing().when(userRepository).delete(user);

        mockMvc.perform(delete("/users/1"))
                .andExpect(status().isNoContent());

        verify(userRepository).delete(user);
    }

    @Test
    void deleteUser_notFound() throws Exception {
        when(userRepository.findById(99L)).thenReturn(Optional.empty());

        mockMvc.perform(delete("/users/99"))
                .andExpect(status().isNotFound());
    }
}
