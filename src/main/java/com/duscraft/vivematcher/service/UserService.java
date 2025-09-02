package com.duscraft.vivematcher.service;

import com.duscraft.vivematcher.domain.user.User;
import com.duscraft.vivematcher.repository.UserRepository;
import org.springframework.stereotype.Service;
import org.springframework.transaction.annotation.Transactional;

import java.util.Optional;

@Service
public class UserService {
    private final UserRepository repo;

    public UserService(UserRepository repo) {
        this.repo = repo;
    }

    @Transactional
    public User createUser(String displayName, String hero, String rank) {
        User u = new User();
        u.setDisplayName(displayName);
        u.setHero(hero);
        u.setRank(rank);
        return repo.save(u);
    }

    public Optional<User> findByDisplayName(String displayName) {
        return repo.findByDisplayName(displayName);
    }
}
