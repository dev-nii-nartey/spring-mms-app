package com.sanjeev.models;


import jakarta.persistence.*;
import lombok.*;

import java.util.HashSet;

import java.util.Set;

@Entity
@Getter
@Setter
@NoArgsConstructor
@RequiredArgsConstructor
public class AppUser {
    @Id
    @GeneratedValue(strategy = GenerationType.IDENTITY)
    @Setter(lombok.AccessLevel.NONE)
    private Long id;

    private boolean enabled = false;

    private boolean isDeleted = false;

    @NonNull
    private String password;

    @NonNull
    @Column(unique = true)
    private String email;

    @NonNull
    private String first_name;

    @NonNull
    private String last_name;

    @ManyToMany
    @JoinTable(name="user_roles", joinColumns=@JoinColumn(name = "user_id"),inverseJoinColumns = @JoinColumn(name = "role_id"))
    private Set<Role> role = new HashSet<>();

    public void addRole(Role role){
        this.role.add(role);
    }

}
