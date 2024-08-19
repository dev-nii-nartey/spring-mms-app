package com.sanjeev.services;

import ch.qos.logback.classic.encoder.JsonEncoder;
import com.sanjeev.dto.DtoUser;
import com.sanjeev.dto.LoginDto;
import com.sanjeev.exceptions.UserAlreadyExistsException;
import com.sanjeev.iservices.AuthService;
import com.sanjeev.models.Role;
import com.sanjeev.models.User;
import com.sanjeev.repositories.RoleRepository;
import com.sanjeev.repositories.UserRepository;
import com.sanjeev.security.JwtTokenProvider;
import com.sanjeev.utils.AppUtils;
import lombok.AllArgsConstructor;
import org.springframework.security.authentication.AuthenticationManager;
import org.springframework.security.authentication.UsernamePasswordAuthenticationToken;
import org.springframework.security.core.Authentication;
import org.springframework.security.core.context.SecurityContextHolder;
import org.springframework.security.crypto.password.PasswordEncoder;
import org.springframework.stereotype.Service;


@Service
@AllArgsConstructor
public class AuthServiceImpl implements AuthService {

    private AuthenticationManager authenticationManager;
    private JwtTokenProvider jwtTokenProvider;
    private UserRepository userRepository;
    private RoleRepository roleRepository;
    private PasswordEncoder passwordEncoder;


    @Override
    public DtoUser register(DtoUser userObject) {
        String email = userObject.getEmail();
        String firstName = userObject.getFirstName();
        String lastName = userObject.getLastName();
        String encodedPassword = passwordEncoder.encode(userObject.getPassword());

        if (userRepository.findByEmail(email).isPresent()) {
            throw new UserAlreadyExistsException("User with this email already exists");
        }
        User newUser = new User(encodedPassword, email, firstName, lastName);
        User saved = userRepository.save(newUser);
        return AppUtils.convertToDto(saved);
    }


    public void addRoleToUser(User user, String roleName) {
        Role role = roleRepository.findRoleByName(roleName)
                .orElseThrow(() -> new RuntimeException("Role not found"));
        user.addRole(role);
        userRepository.save(user);
    }


    @Override
    public String login(LoginDto loginDto) {
        Authentication authentication = authenticationManager.authenticate(new UsernamePasswordAuthenticationToken(
                loginDto.getEmail(),
                loginDto.getPassword()
        ));
        SecurityContextHolder.getContext().setAuthentication(authentication);

        String token = jwtTokenProvider.generateToken(authentication);

        return token;
    }
}
