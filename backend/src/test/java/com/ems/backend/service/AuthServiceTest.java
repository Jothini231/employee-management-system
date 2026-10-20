package com.ems.backend.service;

import com.ems.backend.dto.AuthRequestDto;
import com.ems.backend.dto.RegisterRequestDto;
import com.ems.backend.dto.Response;
import com.ems.backend.entity.Employee;
import com.ems.backend.entity.Role;
import com.ems.backend.repository.EmployeeRepository;
import com.ems.backend.security.JwtUtil;
import com.ems.backend.service.impl.AuthServiceImpl;
import org.junit.jupiter.api.BeforeEach;
import org.junit.jupiter.api.DisplayName;
import org.junit.jupiter.api.Test;
import org.junit.jupiter.api.extension.ExtendWith;
import org.mockito.InjectMocks;
import org.mockito.Mock;
import org.mockito.junit.jupiter.MockitoExtension;
import org.springframework.security.authentication.AuthenticationManager;
import org.springframework.security.authentication.BadCredentialsException;
import org.springframework.security.authentication.UsernamePasswordAuthenticationToken;
import org.springframework.security.core.Authentication;
import org.springframework.security.crypto.password.PasswordEncoder;

import java.util.Optional;

import static org.assertj.core.api.Assertions.assertThat;
import static org.mockito.ArgumentMatchers.any;
import static org.mockito.Mockito.*;

@ExtendWith(MockitoExtension.class)
class AuthServiceTest {

    @Mock
    private AuthenticationManager authenticationManager;

    @Mock
    private EmployeeRepository employeeRepository;

    @Mock
    private PasswordEncoder passwordEncoder;

    @Mock
    private JwtUtil jwtUtil;

    @InjectMocks
    private AuthServiceImpl authService;

    private Employee employee;

    @BeforeEach
    void setUp() {
        employee = new Employee();
        employee.setId(1L);
        employee.setFirstName("Jane");
        employee.setLastName("Doe");
        employee.setEmail("jane.doe@example.com");
        employee.setPassword("encodedPassword");
        employee.setRole(Role.ADMIN);
    }

    @Test
    @DisplayName("login should return token when authentication succeeds")
    void testLogin_Success() {
        AuthRequestDto request = new AuthRequestDto();
        request.setEmail("jane.doe@example.com");
        request.setPassword("plainPassword");

        Authentication authentication = mock(Authentication.class);
        when(authenticationManager.authenticate(any(UsernamePasswordAuthenticationToken.class)))
                .thenReturn(authentication);
        when(employeeRepository.findByEmail("jane.doe@example.com")).thenReturn(Optional.of(employee));
        when(jwtUtil.generateToken(employee)).thenReturn("mocked-jwt-token");

        Response response = authService.login(request);

        assertThat(response.isSuccess()).isTrue();
        assertThat(response.getStatusCode()).isEqualTo(200);
        assertThat(response.getToken()).isEqualTo("mocked-jwt-token");
        assertThat(response.getRole()).isEqualTo("ADMIN");
        verify(employeeRepository, times(1)).save(employee);
    }

    @Test
    @DisplayName("login should return 401 when authentication fails")
    void testLogin_InvalidCredentials() {
        AuthRequestDto request = new AuthRequestDto();
        request.setEmail("jane.doe@example.com");
        request.setPassword("wrongPassword");

        when(authenticationManager.authenticate(any(UsernamePasswordAuthenticationToken.class)))
                .thenThrow(new BadCredentialsException("Bad credentials"));

        Response response = authService.login(request);

        assertThat(response.isSuccess()).isFalse();
        assertThat(response.getStatusCode()).isEqualTo(401);
        assertThat(response.getMessage()).isEqualTo("Invalid email or password");
    }

    @Test
    @DisplayName("register should succeed when email is not taken")
    void testRegister_Success() {
        RegisterRequestDto request = new RegisterRequestDto();
        request.setName("John Smith");
        request.setEmail("john.smith@example.com");
        request.setPassword("password123");
        request.setPhone("1234567890");
        request.setRole(Role.EMPLOYEE);

        when(employeeRepository.existsByEmail("john.smith@example.com")).thenReturn(false);
        when(passwordEncoder.encode("password123")).thenReturn("encodedPassword123");

        Response response = authService.register(request);

        assertThat(response.isSuccess()).isTrue();
        assertThat(response.getStatusCode()).isEqualTo(200);
        assertThat(response.getMessage()).isEqualTo("User registered successfully!");
        verify(employeeRepository, times(1)).save(any(Employee.class));
    }

    @Test
    @DisplayName("register should fail when email is already in use")
    void testRegister_EmailAlreadyInUse() {
        RegisterRequestDto request = new RegisterRequestDto();
        request.setEmail("jane.doe@example.com");

        when(employeeRepository.existsByEmail("jane.doe@example.com")).thenReturn(true);

        Response response = authService.register(request);

        assertThat(response.isSuccess()).isFalse();
        assertThat(response.getStatusCode()).isEqualTo(400);
        assertThat(response.getMessage()).isEqualTo("Error: Email is already in use!");
        verify(employeeRepository, never()).save(any());
    }
}
