package com.example.plumpet.Controllers;

import com.example.plumpet.Models.User;
import com.example.plumpet.Repository.UserRepository;
import com.example.plumpet.Service.UserService;
import lombok.RequiredArgsConstructor;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.*;


@RestController
@RequestMapping("/public")
@RequiredArgsConstructor
public class UserController {
    private final UserService userService;
    private final UserRepository userRepository;
    @PostMapping("/signup")
    public ResponseEntity<?> signUp(@RequestBody User user) {
        return userService.create(user)
                .map(u -> ResponseEntity.ok().build())
                .orElseGet(() -> ResponseEntity.badRequest().build());
    }

}
