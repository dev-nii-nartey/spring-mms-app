package com.sanjeev.services;

import com.sanjeev.dto.DtoUser;
import com.sanjeev.exceptions.UserAlreadyExistsException;
import com.sanjeev.exceptions.UserNotFoundException;
import com.sanjeev.iservices.AppUserService;
import com.sanjeev.models.Role;
import com.sanjeev.models.User;
import com.sanjeev.repositories.RoleRepository;
import com.sanjeev.repositories.UserRepository;
import com.sanjeev.utils.AppUtils;
import lombok.NoArgsConstructor;
import org.springframework.security.core.GrantedAuthority;
import org.springframework.security.core.authority.SimpleGrantedAuthority;
import org.springframework.security.core.userdetails.UserDetails;
import org.springframework.security.core.userdetails.UserDetailsService;
import org.springframework.security.core.userdetails.UsernameNotFoundException;
import org.springframework.stereotype.Service;

import java.util.List;
import java.util.Set;
import java.util.stream.Collectors;


@Service
@NoArgsConstructor
public class UserServiceImpl implements AppUserService, UserDetailsService {

    RoleRepository roleRepository;
    UserRepository userRepository;

    public UserServiceImpl(RoleRepository roleRepository, UserRepository userRepository) {
        this.roleRepository = roleRepository;
        this.userRepository = userRepository;
    }

    @Override
    public DtoUser create(DtoUser userObject) {
        String email = userObject.getEmail();
        String firstName = userObject.getFirstName();
        String lastName = userObject.getLastName();
        String password = userObject.getPassword();
        if (userRepository.findByEmail(email).isPresent()) {
            throw new UserAlreadyExistsException("User with this email already exists");
        }
        User newUser = new User(password, email, firstName, lastName);
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
    public DtoUser update(Long id, DtoUser userObject) {
            User existingUser = userRepository.findById(id)
                    .orElseThrow(() -> new UserNotFoundException("User not found with id: " + id));

            existingUser.setFirst_name(userObject.getFirstName());
            existingUser.setLast_name(userObject.getLastName());
            existingUser.setPassword(userObject.getPassword());

            User updatedUser = userRepository.save(existingUser);
            return AppUtils.convertToDto(updatedUser);
        }


    @Override
    public boolean delete(Long id) {
        User user = userRepository.findById(id)
                .orElseThrow(() -> new UserNotFoundException("User not found with id: " + id));
        user.setDeleted(true);
        userRepository.save(user);
        return true;
    }

    @Override
    public List<DtoUser> listAll() {
        return userRepository.findAllActiveUsers().stream()
                .map(AppUtils::convertToDto)
                .collect(Collectors.toList());
    }

    @Override
    public List<DtoUser> searchUser(String query) {
        List<User> users = userRepository.searchByQuery(query); // Assuming this method is implemented
        return users.stream()
                .map(AppUtils::convertToDto)
                .collect(Collectors.toList());
    }

    @Override
    public DtoUser findByEmail(String email) {
        User user = userRepository.findByEmail(email)
                .filter(u -> !u.isDeleted())
                .orElseThrow(() -> new UserNotFoundException("User not found with email: " + email));
        return AppUtils.convertToDto(user);
    }

    @Override
    public UserDetails loadUserByUsername(String email) throws UsernameNotFoundException {

        User user = userRepository.findByEmail( email)
                .orElseThrow(() -> new UsernameNotFoundException("User not exists by Username or Email"));

        Set<GrantedAuthority> authorities = user.getRoles().stream()
                .map((role) -> new SimpleGrantedAuthority(role.getName()))
                .collect(Collectors.toSet());

        return new org.springframework.security.core.userdetails.User(
                email,
                user.getPassword(),
                authorities
        );
    }
}
