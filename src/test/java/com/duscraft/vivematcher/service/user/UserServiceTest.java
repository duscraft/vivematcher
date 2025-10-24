package com.duscraft.vivematcher.service.user;

import com.duscraft.vivematcher.domain.user.User;
import com.duscraft.vivematcher.repository.user.UserRepository;
import org.junit.jupiter.api.BeforeEach;
import org.junit.jupiter.api.Test;
import org.junit.jupiter.api.extension.ExtendWith;
import org.mockito.InjectMocks;
import org.mockito.Mock;
import org.mockito.junit.jupiter.MockitoExtension;

import java.util.Optional;

import static org.junit.jupiter.api.Assertions.*;
import static org.mockito.ArgumentMatchers.any;
import static org.mockito.Mockito.*;

@ExtendWith(MockitoExtension.class)
class UserServiceTest {

  @Mock
  private UserRepository userRepository;

  @InjectMocks
  private UserService userService;

  private User testUser;
  private final String displayName = "TestPlayer";
  private final String hero = "Mercy";
  private final String rank = "Diamond";

  @BeforeEach
  void setUp() {
    testUser = new User(hero, rank, displayName);
    testUser.setId(1L);
  }

  @Test
  void createUser_shouldSaveAndReturnUser() {
    when(userRepository.save(any(User.class))).thenReturn(testUser);

    User result = userService.createUser(displayName, hero, rank);

    assertNotNull(result);
    assertEquals(testUser.getId(), result.getId());
    assertEquals(displayName, result.getDisplayName());
    assertEquals(hero, result.getHero());
    assertEquals(rank, result.getRank());
    verify(userRepository, times(1)).save(any(User.class));
  }

  @Test
  void findByDisplayName_whenUserExists_shouldReturnUser() {
    when(userRepository.findByDisplayName(displayName)).thenReturn(Optional.of(testUser));

    Optional<User> result = userService.findByDisplayName(displayName);

    assertTrue(result.isPresent());
    assertEquals(testUser, result.get());
    verify(userRepository, times(1)).findByDisplayName(displayName);
  }

  @Test
  void findByDisplayName_whenUserDoesNotExist_shouldReturnEmpty() {
    when(userRepository.findByDisplayName("NonExistentUser")).thenReturn(Optional.empty());

    Optional<User> result = userService.findByDisplayName("NonExistentUser");

    assertFalse(result.isPresent());
    verify(userRepository, times(1)).findByDisplayName("NonExistentUser");
  }

  @Test
  void getById_whenUserExists_shouldReturnUser() {
    Long id = 1L;
    when(userRepository.findById(1L)).thenReturn(Optional.of(testUser));

    Optional<User> result = userService.getById(id);

    assertTrue(result.isPresent());
    assertEquals(testUser, result.get());
    verify(userRepository, times(1)).findById(1L);
  }

  @Test
  void getById_whenUserDoesNotExist_shouldReturnEmpty() {
    Long id = 999L;
    when(userRepository.findById(999L)).thenReturn(Optional.empty());

    Optional<User> result = userService.getById(id);

    assertFalse(result.isPresent());
    verify(userRepository, times(1)).findById(999L);
  }
}
