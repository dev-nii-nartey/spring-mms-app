package com.sanjeev.services;

import com.sanjeev.dto.DtoUser;
import com.sanjeev.exceptions.UserAlreadyExistsException;
import com.sanjeev.exceptions.UserNotFoundException;
import com.sanjeev.models.Role;
import com.sanjeev.models.User;
import com.sanjeev.repositories.RoleRepository;
import com.sanjeev.repositories.UserRepository;
import org.junit.jupiter.api.BeforeEach;
import org.junit.jupiter.api.Test;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.boot.test.autoconfigure.orm.jpa.DataJpaTest;

import java.util.HashSet;
import java.util.List;
import java.util.Set;

import static org.junit.jupiter.api.Assertions.*;

@DataJpaTest
class AppUserServiceImplTest {

    private final UserRepository userRepository;
    private final RoleRepository roleRepository;
    private final UserServiceImpl appUserService;

    @Autowired
    public AppUserServiceImplTest(UserRepository userRepository, RoleRepository roleRepository) {
        this.userRepository = userRepository;
        this.roleRepository = roleRepository;
        this.appUserService = new UserServiceImpl(roleRepository, userRepository);
    }

    @BeforeEach
    void setUp() {
        // Create some dummy data in the repositories before each test
        Role adminRole = new Role();
        adminRole.setName("ADMIN");
        roleRepository.save(adminRole);

        Role userRole = new Role();
        userRole.setName("USER");
        roleRepository.save(userRole);

        User user1 = new User("password1", "user1@example.com", "John", "Doe");
        user1.setRoles(new HashSet<>(Set.of(adminRole)));
        userRepository.save(user1);

        User user2 = new User("password2", "user2@example.com", "Jane", "Smith");
        user2.setRoles(new HashSet<>(Set.of(userRole)));
        userRepository.save(user2);
    }

    @Test
    void create_ShouldReturnDtoUser_WhenUserDoesNotExist() {
        DtoUser dtoUser = new DtoUser();
        dtoUser.setEmail("newuser@example.com");
        dtoUser.setFirstName("New");
        dtoUser.setLastName("User");
        dtoUser.setPassword("password");
        dtoUser.setRoles(new HashSet<>());

        DtoUser createdUser = appUserService.create(dtoUser);

        assertNotNull(createdUser);
        assertEquals("newuser@example.com", createdUser.getEmail());
        assertEquals("New", createdUser.getFirstName());
    }

    @Test
    void create_ShouldThrowUserAlreadyExistsException_WhenUserExists() {
        DtoUser dtoUser = new DtoUser();
        dtoUser.setEmail("user1@example.com");
        dtoUser.setFirstName("John");
        dtoUser.setLastName("Doe");
        dtoUser.setPassword("password1");

        assertThrows(UserAlreadyExistsException.class, () -> appUserService.create(dtoUser));
    }

    @Test
    void addRoleToUser_ShouldAddRole_WhenRoleExists() {
        User user = userRepository.findByEmail("user2@example.com").orElseThrow();

        appUserService.addRoleToUser(user, "ADMIN");

        User updatedUser = userRepository.findByEmail("user2@example.com").orElseThrow();
        assertTrue(updatedUser.getRoles().stream().anyMatch(role -> "ADMIN".equals(role.getName())));
    }

    @Test
    void addRoleToUser_ShouldThrowException_WhenRoleDoesNotExist() {
        User user = userRepository.findByEmail("user2@example.com").orElseThrow();

        assertThrows(RuntimeException.class, () -> appUserService.addRoleToUser(user, "NON_EXISTENT_ROLE"));
    }

    @Test
    void update_ShouldReturnUpdatedDtoUser_WhenUserExists() {
        Long id = userRepository.findByEmail("user1@example.com").orElseThrow().getId();

        DtoUser dtoUser = new DtoUser();
        dtoUser.setFirstName("UpdatedName");
        dtoUser.setLastName("UpdatedLastName");
        dtoUser.setPassword("updatedPassword");

        DtoUser updatedUser = appUserService.update(id, dtoUser);

        assertNotNull(updatedUser);
        assertEquals("UpdatedName", updatedUser.getFirstName());
        assertEquals("UpdatedLastName", updatedUser.getLastName());
    }

    @Test
    void update_ShouldThrowUserNotFoundException_WhenUserDoesNotExist() {
        DtoUser dtoUser = new DtoUser();
        dtoUser.setFirstName("NonExistent");

        assertThrows(UserNotFoundException.class, () -> appUserService.update(999L, dtoUser));
    }

    @Test
    void delete_ShouldMarkUserAsDeleted_WhenUserExists() {
        Long id = userRepository.findByEmail("user2@example.com").orElseThrow().getId();

        boolean result = appUserService.delete(id);

        User deletedUser = userRepository.findById(id).orElseThrow();
        assertTrue(result);
        assertTrue(deletedUser.isDeleted());
    }

    @Test
    void delete_ShouldThrowUserNotFoundException_WhenUserDoesNotExist() {
        assertThrows(UserNotFoundException.class, () -> appUserService.delete(999L));
    }

    @Test
    void listAll_ShouldReturnAllActiveUsers() {
        List<DtoUser> users = appUserService.listAll();

        assertEquals(2, users.size());
        assertTrue(users.stream().anyMatch(u -> "user1@example.com".equals(u.getEmail())));
        assertTrue(users.stream().anyMatch(u -> "user2@example.com".equals(u.getEmail())));
    }

    @Test
    void searchUser_ShouldReturnMatchingUsers() {
        List<DtoUser> result = appUserService.searchUser("Doe");

        assertEquals(1, result.size());
        assertEquals("user1@example.com", result.get(0).getEmail());
    }
}
