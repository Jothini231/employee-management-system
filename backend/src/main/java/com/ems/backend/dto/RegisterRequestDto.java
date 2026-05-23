package com.ems.backend.dto;

import com.ems.backend.entity.Role;
import lombok.AllArgsConstructor;
import lombok.Getter;
import lombok.NoArgsConstructor;
import lombok.Setter;

@Getter
@Setter
@NoArgsConstructor
@AllArgsConstructor
public class RegisterRequestDto {
    private String name;
    private String email;
    private String password;
    private String phone;
    private Role role;
}
