package com.duscraft.vivematcher.web;

import com.duscraft.vivematcher.domain.user.User;
import com.duscraft.vivematcher.repository.user.UserRepository;
import com.duscraft.vivematcher.web.user.CreateUserRequest;
import com.fasterxml.jackson.databind.ObjectMapper;
import org.junit.jupiter.api.AfterEach;
import org.junit.jupiter.api.Test;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.boot.test.autoconfigure.web.servlet.AutoConfigureMockMvc;
import org.springframework.boot.test.context.SpringBootTest;
import org.springframework.http.MediaType;
import org.springframework.test.web.servlet.MockMvc;
import org.springframework.test.web.servlet.MvcResult;

import java.util.Optional;

import static org.junit.jupiter.api.Assertions.*;
import static org.springframework.test.web.servlet.request.MockMvcRequestBuilders.*;
import static org.springframework.test.web.servlet.result.MockMvcResultMatchers.*;

@SpringBootTest
@AutoConfigureMockMvc
class UserApiIntegrationTest {

  @Autowired
  private MockMvc mockMvc;

  @Autowired
  private UserRepository userRepository;

  @Autowired
  private ObjectMapper objectMapper;

  @AfterEach
  void tearDown() {
    userRepository.deleteAll();
  }

  @Test
  void createUser_shouldCreateAndReturnUser() throws Exception {
    CreateUserRequest request = new CreateUserRequest();
    request.setDisplayName("ApiTestUser");
    request.setHero("Winston");
    request.setRank("Master");

    MvcResult result = mockMvc.perform(post("/users")
        .contentType(MediaType.APPLICATION_JSON)
        .content(objectMapper.writeValueAsString(request)))
        .andExpect(status().isCreated())
        .andExpect(jsonPath("$.id").isNumber())
        .andExpect(jsonPath("$.displayName").value("ApiTestUser"))
        .andExpect(jsonPath("$.hero").value("Winston"))
        .andExpect(jsonPath("$.rank").value("Master"))
        .andReturn();

    User createdUser = objectMapper.readValue(
        result.getResponse().getContentAsString(),
        User.class);

    Optional<User> fromDb = userRepository.findById(createdUser.getId());
    assertTrue(fromDb.isPresent());
    assertEquals("ApiTestUser", fromDb.get().getDisplayName());
  }

  @Test
  void getUser_withExistingId_shouldReturnUser() throws Exception {
    User user = new User("Reinhardt", "Platinum", "ShieldBearer");
    user = userRepository.save(user);
    Long userId = user.getId();

    mockMvc.perform(get("/users/{id}", userId))
        .andExpect(status().isOk())
        .andExpect(jsonPath("$.id").value(userId))
        .andExpect(jsonPath("$.displayName").value("ShieldBearer"))
        .andExpect(jsonPath("$.hero").value("Reinhardt"))
        .andExpect(jsonPath("$.rank").value("Platinum"));
  }

  @Test
  void getUser_withNonExistentId_shouldReturn404() throws Exception {
    long nonExistentId = 9999L;

    mockMvc.perform(get("/users/{id}", nonExistentId))
        .andExpect(status().isNotFound());
  }
}
