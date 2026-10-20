package com.ems.backend.controller;

import com.ems.backend.dto.AuthRequestDto;
import com.ems.backend.dto.RegisterRequestDto;
import com.ems.backend.dto.Response;
import com.ems.backend.entity.Role;
import com.ems.backend.service.AuthService;
import com.fasterxml.jackson.databind.ObjectMapper;
import org.junit.jupiter.api.BeforeEach;
import org.junit.jupiter.api.DisplayName;
import org.junit.jupiter.api.Test;
import org.junit.jupiter.api.extension.ExtendWith;
import org.mockito.InjectMocks;
import org.mockito.Mock;
import org.mockito.junit.jupiter.MockitoExtension;
import org.springframework.http.MediaType;
import org.springframework.test.web.servlet.MockMvc;
import org.springframework.test.web.servlet.setup.MockMvcBuilders;

import static org.mockito.ArgumentMatchers.any;
import static org.mockito.Mockito.when;
import static org.springframework.test.web.servlet.request.MockMvcRequestBuilders.post;
import static org.springframework.test.web.servlet.result.MockMvcResultMatchers.jsonPath;
import static org.springframework.test.web.servlet.result.MockMvcResultMatchers.status;

@ExtendWith(MockitoExtension.class)
class AuthControllerTest {

    @Mock
    private AuthService authService;

    @InjectMocks
    private AuthController authController;

    private MockMvc mockMvc;
    private ObjectMapper objectMapper;

    @BeforeEach
    void setUp() {
        mockMvc = MockMvcBuilders.standaloneSetup(authController).build();
        objectMapper = new ObjectMapper();
    }

    @Test
    @DisplayName("POST /api/auth/login should return 200 and auth payload on successful login")
    void testLogin_Success() throws Exception {
        AuthRequestDto request = new AuthRequestDto();
        request.setEmail("user@example.com");
        request.setPassword("password123");

        Response authResponse = Response.auth("jwt-token-xyz", "ADMIN", "Test User", "user@example.com", 1L);
        when(authService.login(any(AuthRequestDto.class))).thenReturn(authResponse);

        mockMvc.perform(post("/api/auth/login")
                        .contentType(MediaType.APPLICATION_JSON)
                        .content(objectMapper.writeValueAsString(request)))
                .andExpect(status().isOk())
                .andExpect(jsonPath("$.token").value("jwt-token-xyz"))
                .andExpect(jsonPath("$.role").value("ADMIN"))
                .andExpect(jsonPath("$.email").value("user@example.com"));
    }

    @Test
    @DisplayName("POST /api/auth/login should return 401 when invalid credentials")
    void testLogin_Unauthorized() throws Exception {
        AuthRequestDto request = new AuthRequestDto();
        request.setEmail("user@example.com");
        request.setPassword("wrong");

        Response errorResponse = Response.error("Invalid email or password", 401);
        when(authService.login(any(AuthRequestDto.class))).thenReturn(errorResponse);

        mockMvc.perform(post("/api/auth/login")
                        .contentType(MediaType.APPLICATION_JSON)
                        .content(objectMapper.writeValueAsString(request)))
                .andExpect(status().isUnauthorized())
                .andExpect(jsonPath("$.message").value("Invalid email or password"));
    }

    @Test
    @DisplayName("POST /api/auth/register should return 200 when registration succeeds")
    void testRegister_Success() throws Exception {
        RegisterRequestDto request = new RegisterRequestDto();
        request.setName("John Smith");
        request.setEmail("john.smith@example.com");
        request.setPassword("pass123");
        request.setPhone("1234567890");
        request.setRole(Role.EMPLOYEE);

        Response successResponse = Response.success("User registered successfully!");
        when(authService.register(any(RegisterRequestDto.class))).thenReturn(successResponse);

        mockMvc.perform(post("/api/auth/register")
                        .contentType(MediaType.APPLICATION_JSON)
                        .content(objectMapper.writeValueAsString(request)))
                .andExpect(status().isOk())
                .andExpect(jsonPath("$.message").value("User registered successfully!"));
    }
}
