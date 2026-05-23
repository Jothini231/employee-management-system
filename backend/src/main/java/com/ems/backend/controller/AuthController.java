package com.ems.backend.controller;

import com.ems.backend.dto.AuthRequestDto;
import com.ems.backend.dto.RegisterRequestDto;
import com.ems.backend.dto.Response;
import com.ems.backend.service.AuthService;
import lombok.RequiredArgsConstructor;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.*;


@RestController
@RequestMapping("/api/auth")
@RequiredArgsConstructor
public class AuthController {

    private final AuthService authService;

    @PostMapping("/login")
    public ResponseEntity<Response> login(@RequestBody AuthRequestDto request) {
        Response response = authService.login(request);
        return ResponseEntity.status(response.getStatusCode()).body(response);
    }

    @PostMapping("/register")
    public ResponseEntity<Response> register(@RequestBody RegisterRequestDto request) {
        Response response = authService.register(request);
        return ResponseEntity.status(response.getStatusCode()).body(response);
    }
}
