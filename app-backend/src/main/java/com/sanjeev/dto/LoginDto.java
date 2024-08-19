package com.sanjeev.dto;

import lombok.*;
import org.springframework.data.annotation.AccessType;

@Getter
@Setter
@NoArgsConstructor
@AllArgsConstructor
public class LoginDto {
    private String email;

    @Setter(lombok.AccessLevel.NONE)
    private String password;
}

