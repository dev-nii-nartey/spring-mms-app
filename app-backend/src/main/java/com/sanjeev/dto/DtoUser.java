package com.sanjeev.dto;


import com.sanjeev.models.Role;
import lombok.*;

import java.util.Set;

@Getter
@Setter
@NoArgsConstructor
@RequiredArgsConstructor
public class DtoUser {
    @NonNull
    String email;
    Boolean enabled;
    @NonNull
    String firstName;
    @NonNull
    String lastName;
    @NonNull
    String password;
    Set<Role> roles;
}
