package com.sanjeev.dto;


import com.sanjeev.models.Role;
import jakarta.validation.constraints.Email;
import jakarta.validation.constraints.Size;
import lombok.*;

import java.util.Set;

@Getter
@Setter
@NoArgsConstructor
@RequiredArgsConstructor
public class DtoUser {
    @NonNull
    @Email(message = "Email is not valid")
    String email;
    Boolean enabled;
    @Size(min = 3, max = 15, message = "Username must be between 3 and 15 characters")
    @NonNull
    String firstName;
    @NonNull
    @Size(min = 3, max = 15, message = "Username must be between 3 and 15 characters")
    String lastName;
    @NonNull
    String password;
    Set<Role> roles;
}
