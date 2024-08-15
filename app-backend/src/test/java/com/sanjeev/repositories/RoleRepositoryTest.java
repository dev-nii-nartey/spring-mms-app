package com.sanjeev.repositories;

import com.sanjeev.models.Role;
import org.junit.jupiter.api.BeforeEach;
import org.junit.jupiter.api.Disabled;
import org.junit.jupiter.api.Test;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.boot.test.autoconfigure.jdbc.AutoConfigureTestDatabase;
import org.springframework.boot.test.autoconfigure.orm.jpa.DataJpaTest;

import java.util.Optional;
import static org.junit.jupiter.api.Assertions.*;


@DataJpaTest
@AutoConfigureTestDatabase(replace = AutoConfigureTestDatabase.Replace.NONE)
class RoleRepositoryTest {

    @Autowired
    private RoleRepository roleRepository;
    private Role adminRole;


    @BeforeEach
    void setup() {
        adminRole = new Role();
        adminRole.setName("ADMIN");

    }

    @Test
    void testSaveRole() {
        // Save the role
        Role savedRole = roleRepository.save(adminRole);
        // Verify the save operation
        assertNotNull(savedRole.getId());
        assertEquals("ADMIN", savedRole.getName());
    }

    @Test
    void testFindById() {
        // Save the role and fetch it by ID
        Role savedRole = roleRepository.save(adminRole);
        Optional<Role> foundRole = roleRepository.findById(savedRole.getId());
        // Verify the find operation
        assertTrue(foundRole.isPresent());
        assertEquals(savedRole.getId(), foundRole.get().getId());
        assertEquals("ADMIN", foundRole.get().getName());
    }

    @Test
    void testDeleteRole() {
        // Save the role, then delete it
        Role savedRole = roleRepository.save(adminRole);
        roleRepository.delete(savedRole);
        // Verify the delete operation
        Optional<Role> deletedRole = roleRepository.findById(savedRole.getId());
        assertFalse(deletedRole.isPresent());
    }

    @Test
    void testFindAllRoles() {
        // Save the role and retrieve all roles
        roleRepository.save(adminRole);
        Iterable<Role> roles = roleRepository.findAll();
        // Verify the find all operation
        assertTrue(roles.iterator().hasNext());
    }

}