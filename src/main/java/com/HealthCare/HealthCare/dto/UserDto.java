package com.HealthCare.HealthCare.dto;

import jakarta.validation.constraints.Email;
import jakarta.validation.constraints.Min;
import jakarta.validation.constraints.NotNull;
import lombok.Data;

import java.io.Serializable;

@Data
public class UserDto implements Serializable {
    @NotNull
    private String username;
    @Email
    private String email;
    @Min(8)
    private String password;

    private String role;
}
