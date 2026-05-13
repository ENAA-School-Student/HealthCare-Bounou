package com.HealthCare.HealthCare.dto;

import jakarta.validation.constraints.Email;
import jakarta.validation.constraints.Min;
import jakarta.validation.constraints.NotNull;
import lombok.Data;

@Data
public class UserDto {
    @NotNull
    private String username;
    @Email
    private String email;
    @Min(8)
    private String password;

    private String role;
}
