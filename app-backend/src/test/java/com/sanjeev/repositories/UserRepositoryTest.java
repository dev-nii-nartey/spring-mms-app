package com.sanjeev.repositories;

import com.sanjeev.models.AppUser;
import com.sanjeev.models.Role;
import org.junit.jupiter.api.Test;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.boot.test.autoconfigure.orm.jpa.DataJpaTest;

import java.util.List;
import java.util.Optional;
import static org.junit.jupiter.api.Assertions.*;

@DataJpaTest
class UserRepositoryTest {

    @Autowired
    private UserRepository userRepository;

    @Test
    void createUserWithOneRole() {
        String email = "name@gmail.com";
        String firstName = "name";
        String lastName = "namelast";
        String password = "alom";

        AppUser newUser = new AppUser(password, email, firstName, lastName);
        Role role = new Role();
        role.setName("ADMIN");
        newUser.addRole(role);
        AppUser savedUser = userRepository.save(newUser);
        assertEquals(email, savedUser.getEmail());
        assertNotNull(savedUser.getId());
        assertFalse(savedUser.getRole().isEmpty());
    }

    @Test
    void findByEmail_ReturnsUser_WhenUserExists() {
        String email = "name@gmail.com";
        AppUser user = new AppUser("password", email, "first", "last");
        userRepository.save(user);
        Optional<AppUser> foundUser = userRepository.findByEmail(email);
        assertTrue(foundUser.isPresent());
        assertEquals(email, foundUser.get().getEmail());
    }

    @Test
    void findByEmail_ReturnsEmpty_WhenUserDoesNotExist() {
        Optional<AppUser> foundUser = userRepository.findByEmail("nonexistent@gmail.com");
        assertFalse(foundUser.isPresent());
    }

    @Test
    void findAllActiveUsers_ReturnsOnlyNonDeletedUsers() {
        AppUser activeUser = new AppUser("password1", "active@gmail.com", "first", "last");
        AppUser deletedUser = new AppUser("password2", "deleted@gmail.com", "first", "last");
        deletedUser.setDeleted(true);
        userRepository.save(activeUser);
        userRepository.save(deletedUser);
        List<AppUser> activeUsers = userRepository.findAllActiveUsers();
        assertEquals(1, activeUsers.size());
        assertEquals("active@gmail.com", activeUsers.get(0).getEmail());
    }

    @Test
    void searchByQuery_FindsMatchingUsers() {
        AppUser user1 = new AppUser("password1", "user1@gmail.com", "John", "Doe");
        AppUser user2 = new AppUser("password2", "user2@gmail.com", "Jane", "Doe");
        AppUser deletedUser = new AppUser("password3", "deleted@gmail.com", "Deleted", "User");
        deletedUser.setDeleted(true);
        userRepository.save(user1);
        userRepository.save(user2);
        userRepository.save(deletedUser);
        List<AppUser> result = userRepository.searchByQuery("Doe");
        assertEquals(2, result.size());
        assertTrue(result.stream().anyMatch(u -> "user1@gmail.com".equals(u.getEmail())));
        assertTrue(result.stream().anyMatch(u -> "user2@gmail.com".equals(u.getEmail())));
        assertFalse(result.stream().anyMatch(u -> "deleted@gmail.com".equals(u.getEmail())));
    }
}