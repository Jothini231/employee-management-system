package com.ems.backend.security;

import com.ems.backend.entity.Employee;
import com.ems.backend.entity.Role;
import org.junit.jupiter.api.BeforeEach;
import org.junit.jupiter.api.DisplayName;
import org.junit.jupiter.api.Test;
import org.springframework.test.util.ReflectionTestUtils;

import static org.assertj.core.api.Assertions.assertThat;

class JwtUtilTest {

    private JwtUtil jwtUtil;
    private Employee employee;

    @BeforeEach
    void setUp() {
        jwtUtil = new JwtUtil();
        // 256-bit secret key for HMAC-SHA256
        String secret = "404E635266556A586E3272357538782F413F4428472B4B6250645367566B5970";
        long expiration = 3600000L; // 1 hour

        ReflectionTestUtils.setField(jwtUtil, "secret", secret);
        ReflectionTestUtils.setField(jwtUtil, "expiration", expiration);

        employee = new Employee();
        employee.setId(1L);
        employee.setFirstName("Alice");
        employee.setLastName("Wonder");
        employee.setEmail("alice@example.com");
        employee.setRole(Role.ADMIN);
    }

    @Test
    @DisplayName("generateToken should create a valid JWT with correct claims")
    void testGenerateTokenAndExtractClaims() {
        String token = jwtUtil.generateToken(employee);

        assertThat(token).isNotNull().isNotEmpty();
        assertThat(jwtUtil.extractEmail(token)).isEqualTo("alice@example.com");
        assertThat(jwtUtil.extractRole(token)).isEqualTo("ADMIN");
        assertThat(jwtUtil.isTokenExpired(token)).isFalse();
    }

    @Test
    @DisplayName("validateToken should return true for matching email and valid token")
    void testValidateToken_Valid() {
        String token = jwtUtil.generateToken(employee);

        boolean isValid = jwtUtil.validateToken(token, "alice@example.com");
        assertThat(isValid).isTrue();
    }

    @Test
    @DisplayName("validateToken should return false for mismatched email")
    void testValidateToken_InvalidEmail() {
        String token = jwtUtil.generateToken(employee);

        boolean isValid = jwtUtil.validateToken(token, "wrong@example.com");
        assertThat(isValid).isFalse();
    }
}
