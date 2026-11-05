package com.openclassrooms.starterjwt.payload.request;

import jakarta.validation.constraints.Email;
import jakarta.validation.constraints.NotBlank;
import jakarta.validation.constraints.Size;
import lombok.Data;

@Data
public class SignupRequest {
    @NotBlank
    @Size(max = 50, message = "Email shouldn't exceed 50 characters")
    @Email(message = "Invalid email format")
    private String email;

    @NotBlank(message = "Firstname shouldn't be empty")
    @Size(min = 3, max = 20, message = "Firstname should be between 3 and 20 characters")
    private String firstName;

    @NotBlank(message = "Lastname shouldn't be empty")
    @Size(min = 3, max = 20, message = "Lastname should be between 3 and 20 characters")
    private String lastName;

    @NotBlank(message = "Password shouldn't be empty")
    @Size(min = 6, max = 40, message = "Password should be between 6 and 40 characters")
    private String password;
}
