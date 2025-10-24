package com.duscraft.vivematcher.repository.user;

import com.duscraft.vivematcher.domain.user.User;
import org.springframework.data.jpa.repository.JpaRepository;

import java.util.Optional;

public interface UserRepository extends JpaRepository<User, Long> {
  Optional<User> findByDisplayName(String displayName);
}
