package com.mcfly.poll.repository;

import com.mcfly.poll.domain.Role;
import com.mcfly.poll.domain.RoleName;
import org.springframework.data.jpa.repository.JpaRepository;

import java.util.Optional;

public interface RoleRepository extends JpaRepository<Role, Long> {

    Optional<Role> findByName(RoleName roleName);
}
