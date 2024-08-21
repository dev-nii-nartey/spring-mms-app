package com.sanjeev.dto;

import jakarta.validation.constraints.Email;
import lombok.*;
import org.springframework.data.annotation.AccessType;

@Getter
@Setter
@NoArgsConstructor
@AllArgsConstructor
public class LoginDto {

    @Email(message = "Email is not a valid email")
    private String email;

    @Setter(lombok.AccessLevel.NONE)
    private String password;
}

