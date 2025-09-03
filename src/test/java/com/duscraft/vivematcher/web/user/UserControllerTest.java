package com.duscraft.vivematcher.web.user;

import com.duscraft.vivematcher.domain.user.User;
import com.duscraft.vivematcher.service.user.UserService;
import org.junit.jupiter.api.BeforeEach;
import org.junit.jupiter.api.Test;
import org.junit.jupiter.api.extension.ExtendWith;
import org.mockito.InjectMocks;
import org.mockito.Mock;
import org.mockito.junit.jupiter.MockitoExtension;

import java.util.NoSuchElementException;
import java.util.Optional;

import static org.junit.jupiter.api.Assertions.*;
import static org.mockito.Mockito.*;

@ExtendWith(MockitoExtension.class)
class UserControllerTest {

    @Mock
    private UserService userService;

    @InjectMocks
    private UserController userController;

    private User testUser;
    private final String displayName = "TestPlayer";
    private final String hero = "Mercy";
    private final String rank = "Diamond";
    private final Long userId = 1L;

    @BeforeEach
    void setUp() {
        testUser = new User(hero, rank, displayName);
        testUser.setId(userId);
    }

    @Test
    void createUser_shouldReturnCreatedUser() {
        CreateUserRequest request = new CreateUserRequest();
        request.setDisplayName(displayName);
        request.setHero(hero);
        request.setRank(rank);
        when(userService.createUser(displayName, hero, rank)).thenReturn(testUser);
        
        User result = userController.createUser(request);
        
        assertNotNull(result);
        assertEquals(testUser.getId(), result.getId());
        assertEquals(displayName, result.getDisplayName());
        verify(userService, times(1)).createUser(displayName, hero, rank);
    }

    @Test
    void getUser_whenUserExists_shouldReturnUser() {
        String id = userId.toString();
        when(userService.getById(id)).thenReturn(Optional.of(testUser));
        
        User result = userController.getUser(id);
        
        assertNotNull(result);
        assertEquals(testUser.getId(), result.getId());
        verify(userService, times(1)).getById(id);
    }
    
    @Test
    void getUser_whenUserDoesNotExist_shouldThrowException() {
        String id = "999";
        when(userService.getById(id)).thenReturn(Optional.empty());
        
        assertThrows(NoSuchElementException.class, () -> userController.getUser(id));
        verify(userService, times(1)).getById(id);
    }
}
