package com.mcfly.poll.repository;

import com.mcfly.poll.domain.user_role.Role;
import com.mcfly.poll.domain.user_role.RoleName;
import com.mcfly.poll.domain.user_role.User;
import com.mcfly.poll.exception.AppException;
import com.mcfly.poll.repository.user_role.RoleRepository;
import com.mcfly.poll.repository.user_role.UserRepository;
import org.apache.commons.lang3.RandomStringUtils;
import org.junit.jupiter.api.Test;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.boot.test.autoconfigure.orm.jpa.DataJpaTest;

import java.time.Instant;
import java.util.Collections;
import java.util.Optional;

import static org.junit.jupiter.api.Assertions.assertTrue;

@DataJpaTest
public class UserRoleRepositoryTest {

    @Autowired
    RoleRepository roleRepository;
    @Autowired
    UserRepository userRepository;

    @Test
    void testFindRoleByName() {
        final Optional<Role> roleUserOptional = roleRepository.findByName(RoleName.ROLE_USER);
        assertTrue(roleUserOptional.isPresent());
        final Optional<Role> roleAdminOptional = roleRepository.findByName(RoleName.ROLE_ADMIN);
        assertTrue(roleAdminOptional.isPresent());
    }

    @Test
    void testFindByUsernameOrEmail() {
        final User user = persistRandomUser();
        final Optional<User> userByUsername = userRepository.findByUsernameOrEmail(user.getUsername(), null);
        assertTrue(userByUsername.isPresent());
        final Optional<User> userByEmail = userRepository.findByUsernameOrEmail(null, user.getEmail());
        assertTrue(userByEmail.isPresent());
    }

    @Test
    void testFindByIdIn() {
        // TODO List<User> findByIdIn(List<Long> userIds);
    }

    @Test
    void testFindByUsername() {
        // TODO Optional<User> findByUsername(String username);
    }

    @Test
    void testExistsByUsername() {
        // TODO Boolean existsByUsername(String username);
    }

    @Test
    void testExistsByEmail() {
        // TODO Boolean existsByEmail(String email);
    }

    private User persistRandomUser() {
        final String name = RandomStringUtils.randomAlphabetic(5);
        final String username = RandomStringUtils.randomAlphabetic(5);
        final String email = RandomStringUtils.randomAlphabetic(5) + "@gmail.com";
        final String password = RandomStringUtils.randomAlphabetic(5);
        final User user = new User(name, username, email, password);
        user.setCreatedAt(Instant.now());
        user.setUpdatedAt(Instant.now());
        final Role userRole = roleRepository.findByName(RoleName.ROLE_USER).orElseThrow(() -> new AppException("User Role not set."));
        user.setRoles(Collections.singleton(userRole));
        userRepository.saveAndFlush(user);
        return user;
    }


    /*

    assertThrows(EmptyResultDataAccessException.class, () -> bookRepository.readBookByTitle("No such title"));

    RandomStringUtils.randomAlphabetic(51)

     */

}
