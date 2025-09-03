package com.duscraft.vivematcher.web.user;

import com.duscraft.vivematcher.domain.user.User;
import com.duscraft.vivematcher.service.user.UserService;
import org.springframework.web.bind.annotation.*;

@RestController
public class UserController {
    private final UserService userService;

    public UserController(UserService userService) {
        this.userService = userService;
    }

    @PostMapping("/users")
    public User createUser(@RequestBody CreateUserRequest request) {
        return this.userService.createUser(
                request.getDisplayName(),
                request.getHero(),
                request.getRank()
        );
    }

    @GetMapping("/users/{id}")
    public User getUser(@PathVariable String id) {
        return this.userService.getById(id).orElseThrow();
    }

    @GetMapping("/users/by-name/{name}")
    public User findUserByName(@PathVariable String name) {
        return this.userService.findByDisplayName(name).orElseThrow();
    }
}
