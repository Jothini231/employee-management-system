package com.ems.backend.security;

import com.ems.backend.entity.Employee;
import com.ems.backend.entity.Role;
import com.ems.backend.repository.EmployeeRepository;
import org.junit.jupiter.api.DisplayName;
import org.junit.jupiter.api.Test;
import org.junit.jupiter.api.extension.ExtendWith;
import org.mockito.InjectMocks;
import org.mockito.Mock;
import org.mockito.junit.jupiter.MockitoExtension;
import org.springframework.security.core.userdetails.UserDetails;
import org.springframework.security.core.userdetails.UsernameNotFoundException;

import java.util.Optional;

import static org.assertj.core.api.Assertions.assertThat;
import static org.assertj.core.api.Assertions.assertThatThrownBy;
import static org.mockito.Mockito.when;

@ExtendWith(MockitoExtension.class)
class CustomUserDetailsServiceTest {

    @Mock
    private EmployeeRepository employeeRepository;

    @InjectMocks
    private CustomUserDetailsService userDetailsService;

    @Test
    @DisplayName("loadUserByUsername should return UserDetails when user exists")
    void testLoadUserByUsername_Success() {
        Employee employee = new Employee();
        employee.setEmail("user@example.com");
        employee.setPassword("secret");
        employee.setRole(Role.ADMIN);

        when(employeeRepository.findByEmail("user@example.com")).thenReturn(Optional.of(employee));

        UserDetails userDetails = userDetailsService.loadUserByUsername("user@example.com");

        assertThat(userDetails).isNotNull();
        assertThat(userDetails.getUsername()).isEqualTo("user@example.com");
        assertThat(userDetails.getPassword()).isEqualTo("secret");
        assertThat(userDetails.getAuthorities()).anyMatch(a -> a.getAuthority().equals("ROLE_ADMIN"));
    }

    @Test
    @DisplayName("loadUserByUsername should throw UsernameNotFoundException when user is not found")
    void testLoadUserByUsername_NotFound() {
        when(employeeRepository.findByEmail("unknown@example.com")).thenReturn(Optional.empty());

        assertThatThrownBy(() -> userDetailsService.loadUserByUsername("unknown@example.com"))
                .isInstanceOf(UsernameNotFoundException.class)
                .hasMessageContaining("User not found with email: unknown@example.com");
    }
}
