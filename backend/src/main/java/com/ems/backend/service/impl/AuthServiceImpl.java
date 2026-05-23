package com.ems.backend.service.impl;

import com.ems.backend.dto.AuthRequestDto;
import com.ems.backend.dto.RegisterRequestDto;
import com.ems.backend.dto.Response;
import com.ems.backend.entity.Employee;
import com.ems.backend.repository.EmployeeRepository;
import com.ems.backend.security.JwtUtil;
import com.ems.backend.service.AuthService;
import lombok.RequiredArgsConstructor;
import org.springframework.security.authentication.AuthenticationManager;
import org.springframework.security.authentication.UsernamePasswordAuthenticationToken;
import org.springframework.security.core.Authentication;
import org.springframework.security.core.context.SecurityContextHolder;
import org.springframework.security.crypto.password.PasswordEncoder;
import org.springframework.stereotype.Service;

import java.time.LocalDate;
import java.util.UUID;

@Service
@RequiredArgsConstructor
public class AuthServiceImpl implements AuthService {

    private final AuthenticationManager authenticationManager;
    private final EmployeeRepository employeeRepository;
    private final PasswordEncoder passwordEncoder;
    private final JwtUtil jwtUtil;

    @Override
    public Response login(AuthRequestDto request) {
        try {
            Authentication authentication = authenticationManager.authenticate(
                    new UsernamePasswordAuthenticationToken(request.getEmail(), request.getPassword())
            );

            SecurityContextHolder.getContext().setAuthentication(authentication);

            Employee employee = employeeRepository.findByEmail(request.getEmail())
                    .orElseThrow(() -> new RuntimeException("User not found"));

            System.out.println(employee.getPassword());

            employee.setLastLogin(LocalDate.now());
            employeeRepository.save(employee);

            String jwt = jwtUtil.generateToken(employee);
            String fullName = employee.getFirstName() + (employee.getLastName() != null && !employee.getLastName().isEmpty() ? " " + employee.getLastName() : "");

            return Response.auth(jwt, employee.getRole().name(), fullName, employee.getEmail());
        } catch (Exception e) {
            e.printStackTrace();
            return Response.error("Invalid email or password", 401);
        }
    }

    @Override
    public Response register(RegisterRequestDto request) {
        if (employeeRepository.existsByEmail(request.getEmail())) {
            return Response.error("Error: Email is already in use!", 400);
        }

        Employee employee = new Employee();
        
        String[] nameParts = request.getName() != null ? request.getName().split(" ", 2) : new String[]{"Unknown"};
        employee.setFirstName(nameParts[0]);
        employee.setLastName(nameParts.length > 1 ? nameParts[1] : "");
        
        employee.setEmail(request.getEmail());
        employee.setPassword(passwordEncoder.encode(request.getPassword()));
        employee.setContactNumber(request.getPhone());
        employee.setRole(request.getRole());
        
        employee.setEmployeeCode("EMP-" + UUID.randomUUID().toString().substring(0, 8).toUpperCase());

        employeeRepository.save(employee);

        return Response.success("User registered successfully!");
    }
}
