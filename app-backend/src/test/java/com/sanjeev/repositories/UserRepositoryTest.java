package com.sanjeev.repositories;

import com.sanjeev.models.User;
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

        User newUser = new User(password, email, firstName, lastName);
        Role role = new Role();
        role.setName("ADMIN");
        newUser.addRole(role);
        User savedUser = userRepository.save(newUser);
        assertEquals(email, savedUser.getEmail());
        assertNotNull(savedUser.getId());
        assertFalse(savedUser.getRoles().isEmpty());
    }

    @Test
    void findByEmail_ReturnsUser_WhenUserExists() {
        String email = "name@gmail.com";
        User user = new User("password", email, "first", "last");
        userRepository.save(user);
        Optional<User> foundUser = userRepository.findByEmail(email);
        assertTrue(foundUser.isPresent());
        assertEquals(email, foundUser.get().getEmail());
    }

    @Test
    void findByEmail_ReturnsEmpty_WhenUserDoesNotExist() {
        Optional<User> foundUser = userRepository.findByEmail("nonexistent@gmail.com");
        assertFalse(foundUser.isPresent());
    }

    @Test
    void findAllActiveUsers_ReturnsOnlyNonDeletedUsers() {
        User activeUser = new User("password1", "active@gmail.com", "first", "last");
        User deletedUser = new User("password2", "deleted@gmail.com", "first", "last");
        deletedUser.setDeleted(true);
        userRepository.save(activeUser);
        userRepository.save(deletedUser);
        List<User> activeUsers = userRepository.findAllActiveUsers();
        assertEquals(1, activeUsers.size());
        assertEquals("active@gmail.com", activeUsers.get(0).getEmail());
    }

    @Test
    void searchByQuery_FindsMatchingUsers() {
        User user1 = new User("password1", "user1@gmail.com", "John", "Doe");
        User user2 = new User("password2", "user2@gmail.com", "Jane", "Doe");
        User deletedUser = new User("password3", "deleted@gmail.com", "Deleted", "User");
        deletedUser.setDeleted(true);
        userRepository.save(user1);
        userRepository.save(user2);
        userRepository.save(deletedUser);
        List<User> result = userRepository.searchByQuery("Doe");
        assertEquals(2, result.size());
        assertTrue(result.stream().anyMatch(u -> "user1@gmail.com".equals(u.getEmail())));
        assertTrue(result.stream().anyMatch(u -> "user2@gmail.com".equals(u.getEmail())));
        assertFalse(result.stream().anyMatch(u -> "deleted@gmail.com".equals(u.getEmail())));
    }
}