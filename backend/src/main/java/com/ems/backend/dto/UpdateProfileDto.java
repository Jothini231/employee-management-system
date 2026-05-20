package com.ems.backend.dto;

import lombok.AllArgsConstructor;
import lombok.Getter;
import lombok.NoArgsConstructor;
import lombok.Setter;

@Getter
@Setter
@NoArgsConstructor
@AllArgsConstructor
public class UpdateProfileDto {
    private String firstName;
    private String lastName;
    private String email;
    private String contactNumber;
    private String designation;
    private String photo; // Base64 or URL
}
