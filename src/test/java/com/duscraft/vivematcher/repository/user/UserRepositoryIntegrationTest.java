package com.duscraft.vivematcher.repository.user;

import com.duscraft.vivematcher.domain.user.User;
import org.junit.jupiter.api.Test;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.boot.test.autoconfigure.orm.jpa.DataJpaTest;

import java.util.Optional;

import static org.junit.jupiter.api.Assertions.*;

@DataJpaTest
class UserRepositoryIntegrationTest {

  @Autowired
  private UserRepository userRepository;

  @Test
  void findByDisplayName_shouldReturnUser() {
    User user = new User("Pharah", "Gold", "RocketQueen");
    userRepository.save(user);

    Optional<User> found = userRepository.findByDisplayName("RocketQueen");

    assertTrue(found.isPresent());
    assertEquals("RocketQueen", found.get().getDisplayName());
    assertEquals("Pharah", found.get().getHero());
  }

  @Test
  void findByDisplayName_whenNoMatch_shouldReturnEmpty() {
    Optional<User> found = userRepository.findByDisplayName("NonExistentUser");

    assertFalse(found.isPresent());
  }
}
