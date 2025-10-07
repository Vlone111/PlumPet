package com.example.plumpet.Service;

import com.example.plumpet.Models.Role;
import com.example.plumpet.Models.User;
import com.example.plumpet.Repository.UserRepository;
import lombok.RequiredArgsConstructor;
import org.springframework.stereotype.Service;

import java.util.Optional;

@Service
@RequiredArgsConstructor
public class UserService {
    private final UserRepository userRepository;
    public Optional<User> create(User user) {
        user.setRole(Role.User);
        userRepository.save(user);
        return Optional.of(user);

    }
}
