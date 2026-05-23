package com.ems.backend.service;

import com.ems.backend.dto.AuthRequestDto;
import com.ems.backend.dto.RegisterRequestDto;
import com.ems.backend.dto.Response;

public interface AuthService {
    Response login(AuthRequestDto request);
    Response register(RegisterRequestDto request);
}
